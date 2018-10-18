package flashlight;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 *
 */
/*

 */
public class Segment
{
    private Point start;
    private Point end;

    public Segment(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public boolean intersects(Segment s){
        return new Line2D.Float(start, end).intersectsLine(new Line2D.Float(s.start, s.end));
    }

    public Line2D getLine(){
        return new Line2D.Float(start, end);
    }

    public void draw(Graphics2D g2d) {
        g2d.drawLine(start.x, start.y, end.x, end.y);
    }

}
