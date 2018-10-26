package flashlight;

import entity.Player;
import entity.Tile;
import main.GameComponent;
import map.TileMap;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
@SuppressWarnings("MagicNumber")
public class FlashLight
{
    // The range of the flashlight
    private static final int RANGE = 400;
    private static final float DARKNESS_ALPHA = 1.0f;

    private TileMap tm;

    private int x;
    private int y;
    private int targetX;
    private int targetY;

    private Polygon poly;

    private int offsetAngle;
    // The angle to which the cursor is pointing
    private double targetAngle;

    // The angles in which the lines should be checked
    //private List<Float> uniAngles;
    private List<Segment> segments;
    //private List<Point> points;
    private List<Point> intersections;

    private Player player;

    public FlashLight(TileMap tm, Player player) {
        this.tm = tm;
        this.player = player;

        // The center of the flashlight is based on the playcers position
        x = player.getXMap();
        y = player.getYMap();

        // Where the flashlight is pointing towards
        targetX = 0;
        targetY = 0;

        // Contains line segments which the flashlight lines can intersect with
        segments = new ArrayList<>();

        // The points the the flashlight lines intersect with the map tiles
        intersections = new ArrayList<>();

        // The flashlight polygon ( points will be set )
        poly = new Polygon(new int[]{0}, new int[]{0}, 0);

        // How broad the flashlight is.
        offsetAngle = 10;
    }

    /**
     * Transforms an angle to a value between 0 - 360 degrees
     *
     * @param angle the angle to transform
     * @return the angles value between 0 - 360 degrees
     */
    public static double normalAbsoluteAngleDegrees(double angle) {
        angle %= 360;
        if (angle >= 0) return angle;
        else return angle + 360;
    }

    /**
     * Get the point where two lines intersect
     *
     * @param l1 the first line
     * @param l2 the second line
     * @return the intersection point
     */
    private static Point findIntersection(Line2D l1, Line2D l2) {
        double a1 = l1.getY2() - l1.getY1();
        double b1 = l1.getX1() - l1.getX2();
        double c1 = a1 * l1.getX1() + b1 * l1.getY1();

        double a2 = l2.getY2() - l2.getY1();
        double b2 = l2.getX1() - l2.getX2();
        double c2 = a2 * l2.getX1() + b2 * l2.getY1();

        double delta = a1 * b2 - a2 * b1;
        return new Point((int) ((b2 * c1 - b1 * c2) / delta), (int) ((a1 * c2 - a2 * c1) / delta));
    }

    public void update(Point mousePos) {
        if(mousePos != null){
            targetX = (int)mousePos.getX();
            targetY = (int)mousePos.getY();
        }

        // reset the lists
        segments.clear();
        intersections.clear();

        // set to the players position
        x = player.getXMap() + player.getWidth() / 2;
        y = player.getYMap() + player.getHeight() / 2;

        targetAngle = (getAngle(new Point(targetX, targetY), new Point(x, y)));

        // Create segments from tiles
        setSegments();
        setIntersections();

        // The poly which will be the flashlight
        poly = new Polygon();

        // set the points to draw the polygon
        for (int i = 0; i < intersections.size(); i++) {
            poly.addPoint(intersections.get(i).x, intersections.get(i).y);
        }

        // One point has to be the players position
        poly.addPoint(x, y);
    }

    /**
     * Loops through all the tiles and creates line segments based on their sides and the players position.
     */
    private void setSegments() {
        // create segments from each tile
        for (Tile[] tiles : tm.getTiles()) {
            for (Tile tile : tiles) {
                // Get the bounding box rect from the tile
                Rectangle rect = tile.getRectangle();
                // If the flashlight can collide with the tile
                if (tile.isSolid() && player.inRange(tile.getX(), tile.getY(), RANGE + 60)) {
                    // The corners of the tile
                    Point upperLeft = new Point(rect.x, rect.y);
                    Point lowerLeft = new Point(rect.x, rect.y + rect.height);
                    Point upperRight = new Point(rect.x + rect.width, rect.y);
                    Point lowerRight = new Point(rect.x + rect.width, rect.y + rect.height);

                    // The sides of the tile
                    Segment left = new Segment(upperLeft, lowerLeft);
                    Segment top = new Segment(upperLeft, upperRight);
                    Segment bottom = new Segment(lowerLeft, lowerRight);
                    Segment right = new Segment(upperRight, lowerRight);

                    // Upper left
                    if (x <= upperRight.x && y <= lowerLeft.y) {
                        segments.add(top);
                        segments.add(left);
                    }
                    // Upper right
                    else if (x >= upperLeft.x && y <= lowerRight.y) {
                        segments.add(top);
                        segments.add(right);
                    }
                    // Lower right
                    else if (x >= lowerLeft.x && y >= upperRight.y) {
                        segments.add(bottom);
                        segments.add(right);
                    }
                    //Lower left
                    else if (x <= upperLeft.x && y >= lowerRight.y) {
                        segments.add(bottom);
                        segments.add(left);
                    }
                }
            }
        }
    }


    // TODO: 2018-07-27 Move this function into a separeate class (Segment maybe )

    /**
     * Check if the angle collides with a segment. Returns true or false.
     *
     * @param angle         the angle from the players position to check for an intersecion
     * @param rangeModifier changes the range of the "ray" to form a "light bulb"
     * @return a boolean telling if there is a collision or not
     */
    public boolean checkIntersection(float angle, double rangeModifier) {
        Point closestIntersection = null;
        double closestDistance = 0;

        // Find closest intersection
        for (int j = 0; j < segments.size(); j++) {

            Segment segment = segments.get(j);

            // Calculate the currently closest intersection point
            if (closestIntersection != null)
                closestDistance = Math.hypot(closestIntersection.x - x, closestIntersection.y - y);

            // Looks for an intersection point
            Point intersect = getIntersection(new Segment(new Point(x + (int) (-(RANGE - rangeModifier) * Math.cos(Math.toRadians(angle))),
                    y + (int) (-(RANGE - rangeModifier) * Math.sin(Math.toRadians(angle)))), new Point(x, y)), segment);

            // Search for another intersection point if no-one is found
            if (intersect == null) {
                continue;
            }

            // If the current intersection point is closer than the closest
            if (closestIntersection == null || Math.hypot(x - intersect.x, y - intersect.y) < closestDistance) {
                closestIntersection = intersect;
            }
        }

        // If there is a valid intersection point
        if (closestIntersection != null) {
            intersections.add(closestIntersection);
            return true;
        }
        return false;
    }


    // TODO: 2018-07-27 Move this function into a separeate class (Segment maybe )

    /**
     * Sets the intersection points
     */
    // TODO: 2018-07-26 Set the max range for each intersection
    public void setIntersections() {
        for (double i = -offsetAngle; i < offsetAngle; i += (float) offsetAngle * 2 / 20) {
            // Change the range of the "ray" to form a "light bulb"
            double rangeModifier = (Math.pow(Math.abs(i * 0.5), 2));
            if (!checkIntersection((float) (normalAbsoluteAngleDegrees(targetAngle + i)), rangeModifier)) {
                intersections.add(new Point(x + (int) (-(RANGE - rangeModifier) * Math.cos(Math.toRadians((normalAbsoluteAngleDegrees(targetAngle + i))))),
                        y + (int) (-(RANGE - rangeModifier) * Math.sin(Math.toRadians((normalAbsoluteAngleDegrees(targetAngle + i)))))));
            }
        }

        // How much the angles should turn when sorting them.
        int offset = 0;

        // Fixes a problem when the angle goes between 0 and 360 degrees
        if (normalAbsoluteAngleDegrees(targetAngle - offsetAngle) > normalAbsoluteAngleDegrees(targetAngle + offsetAngle)) {
            offset = 180;
        }
        // Sort the intersections based on their distance clockwise from the outer angle
        for (int i = 0; i < intersections.size(); i++) {
            for (int j = 1; j < intersections.size(); j++) {
                // Swaps the position of the intersections based on their angle ( smallest first )
                if (normalAbsoluteAngleDegrees(getAngle(intersections.get(j - 1), new Point(x, y)) + offset) <
                        normalAbsoluteAngleDegrees(getAngle(intersections.get(j), new Point(x, y)) + offset)) {
                    Collections.swap(intersections, j, j - 1);
                }
            }
        }
    }

    /**
     * Get the angle between two points
     * Returns a value between 0 - 360 degrees
     *
     * @param p1 the first point
     * @param p2 the second point
     * @return the angle between the points
     */
    public double getAngle(Point p1, Point p2) {
        double angle = Math.toDegrees(Math.atan2(p2.getY() - p1.getY(), p2.getX() - p1.getX()));
        return normalAbsoluteAngleDegrees(angle);
    }

    /**
     * Checks if two line segments intersect and returns the intersection point if so
     *
     * @param s1 the first line segment
     * @param s2 the second line segment
     * @return the point in which the lines collide (if they do)
     */
    public Point getIntersection(Segment s1, Segment s2) {
        // Get the point if the lines intersect
        if (s1.intersects(s2)) {
            return findIntersection(s1.getLine(), s2.getLine());
        }
        return null;
    }

    /**
     * Draw the intersection lines and intersection points
     *
     * @param g2d the grapgic object
     */

    private void drawIntersections(Graphics2D g2d) {
        g2d.setColor(Color.red);
        for (int i = 0; i < intersections.size(); i++) {
            Point intersect = intersections.get(i);
            g2d.drawLine(x, y, intersect.x, intersect.y);
            //g2d.fillOval(intersect.x - 5, intersect.y - 5, 10, 10);
        }
    }


    /**
     * Draw the flashlight
     *
     * @param g2d the graphic object
     */
    public void draw(Graphics2D g2d) {
        // This creates the "tone-out" flashlight effect
        float[] fractions = new float[]{0.0f, 0.9f};
        Color[] colors = new Color[]{new Color(0.0f, 0.0f, 0.0f, 0.0f), Color.BLACK};
        Point2D center = new Point2D.Float(x, y);
        RadialGradientPaint p = new RadialGradientPaint(center, RANGE, fractions, colors);

        // Sets the alpha-channel of the black foreground which covers the screen
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, DARKNESS_ALPHA));

        g2d.setColor(Color.BLACK);

        // Sets an area which is the entire frame except the flashlight
        Area outer = new Area(
                new Rectangle(0, 0, GameComponent.WIDTH * GameComponent.SCALE, GameComponent.HEIGHT * GameComponent.SCALE));
        outer.subtract(new Area(poly));

        //outer.subtract(new Area(new Rectangle(100 + (int) tm.getXMap(), 100 + (int) tm.getYMap(), 500, 50)));
        g2d.fill(outer);

        g2d.setPaint(p);
        // The flashlight light
        g2d.fillPolygon(poly);

        /*for (Segment s : segments) {
            s.draw(g2d);
        }*/

        // Reset the alpha-channel
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

        //drawIntersections(g2d);
    }


    /*
    Setters and getters
     */
    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }
}