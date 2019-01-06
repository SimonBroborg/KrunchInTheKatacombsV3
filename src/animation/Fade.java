package animation;

import main.GameComponent;
import main.Sprite;

/**
 * Animation which let's an entity fade in and out
 */
public class Fade
{
    private int ms;
    private boolean running;

    private Sprite sprite;

    private float from;
    private float to;
    private float alpha;
    private float speed;

    /**
     * The constructor
     * @param ms The time in milliseconds which the animation will run for
     * @param from Alhpa value which the animation will start at
     * @param to The fadeAlpha value which the animation will end at
     */
    public Fade(int ms, float from, float to){
        this.ms = ms;
        this.from  = from;
        this.to = to;
        alpha = from;
        speed = (float) ms / GameComponent.FPS / 100;
        System.out.println(speed);

    }

    public void run(){
        running = true;
    }

    public void update(){
        if(running){
            // Decide if fadeAlpha should increase or decrease
            if(from >= to && alpha >= to){
                alpha -= speed;
                if(alpha <= 0.0f) alpha = 0.0f;
            }
            else if(from <= to && alpha <= to){
                alpha += speed;
                if(alpha >= 1.0f) alpha = 1.0f;
            }
        }
    }

    public float getAlpha() {
        return alpha;
    }

    public boolean isRunning() {
        return running;
    }
}
