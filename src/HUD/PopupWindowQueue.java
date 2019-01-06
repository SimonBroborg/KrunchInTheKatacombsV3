package HUD;

import java.awt.*;
import java.util.LinkedList;

public class PopupWindowQueue{
    private LinkedList<PopupWindow> popups;
    private PopupWindow current;

    private boolean displaying;

    public PopupWindowQueue(){
        popups = new LinkedList<>();
        current = null;
        displaying = false;
    }

    public void update(){
        // Check if there is a popup to display
        if(current != null) {
            current.update();
            displaying = true;
        }else if(popups.size() > 0) {
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
