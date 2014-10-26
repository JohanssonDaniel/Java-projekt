package se.liu.ida.piehe154.tddd78.project.superimposiblegame.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Handles the sound clips that are specified in the classes which uses sound.
 * Reads the format of the sound file and decode it if it is a .mp3 file.
 */
public class AudioPlayer
{
    private Clip clip = null;
    private static final int SAMPLE_RATE = 16;

    /**
     * Finds the file in the specified path and decodes it
     * @param audioPath relative file path
     */
    public AudioPlayer(Path audioPath) {
	try {
	    AudioInputStream audioInputStream =
		    AudioSystem.getAudioInputStream(audioPath.toAbsolutePath().toFile());
	    AudioFormat baseFormat = audioInputStream.getFormat();
	    AudioFormat decodeFormat = new AudioFormat(
		    AudioFormat.Encoding.PCM_SIGNED,
		    baseFormat.getSampleRate(),
		    SAMPLE_RATE,
		    baseFormat.getChannels(),
		    baseFormat.getChannels() * 2,
		    baseFormat.getSampleRate(),
		    false

	    );
	    AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat,audioInputStream);
	    clip = AudioSystem.getClip();
	    clip.open(dais);
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (UnsupportedAudioFileException ae){
	    ae.printStackTrace();
	}catch (LineUnavailableException ue){
	    ue.printStackTrace();
	}
    }

    /**
     * Plays the clip
     */
    public void play(){
	if (clip == null){
	    return;
	}
	stop();
	clip.setFramePosition(0);
	clip.start();
    }

    /**
     * Stops the clip
     */
    public void stop(){
	if (clip.isRunning()){
	    clip.stop();
	}
    }
}
