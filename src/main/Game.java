package main;

import javax.swing.*;

/**
 * This is where the program runs from
 */
public class Game extends JFrame
{
    public static void main(String[] args){
        GameComponent comp = new GameComponent();

        comp.run();
    }
}
