package hud;

import java.awt.*;
import java.util.LinkedList;

/**
 * A queue of popup windows. Goes through all windows before any other action can be performed.
 */
public class PopupWindowQueue{
    private LinkedList<PopupWindow> popups;
    private PopupWindow current = null;

    private boolean displaying;

    public PopupWindowQueue(){
        popups = new LinkedList<>();
        displaying = false;
    }

    public void update(){
        // Check if there is a popup to display
        if(current != null) {
            current.update();
            displaying = true;
        } else if (!popups.isEmpty()) {
            nextPopup();
        }else{
            displaying = false;
        }
    }

    public void draw(Graphics2D g2d){
        if(current != null){
            current.draw(g2d);
        }
    }

    public void addPopup(PopupWindow popup){
        popups.add(popup);
    }

    public void nextPopup(){
        current = popups.poll();
    }

    public boolean isDisplaying() {
        return displaying;
    }
}
