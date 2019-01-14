package sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * Loads a sound from a path which then can be played.
 */
public class SoundPlayer {
    private Clip sound = null;


    public SoundPlayer(String path) {
	try {
	    sound = AudioSystem.getClip();
	    sound.open(AudioSystem.getAudioInputStream(new File(path)));

	} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
	    e.printStackTrace();
	}
    }

    public void playOnce(){
	sound.start();
    }

    public void loop(){
	final double inf = Double.POSITIVE_INFINITY;
	sound.loop((int) inf);
    }

    public void loop(int n){
	sound.loop(n);
    }

    public void stop(){
	sound.stop();
    }

    public boolean playing(){
	return sound.isRunning();
    }
}
