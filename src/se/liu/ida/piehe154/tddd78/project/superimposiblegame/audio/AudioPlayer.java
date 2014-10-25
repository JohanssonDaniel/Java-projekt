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
 * Created by Daniel Johansson on 2014-10-23.
 */
public class AudioPlayer
{
    private Clip clip = null;
    private static final int SAMPLE_RATE = 16;
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

    public void play(){
	if (clip == null){
	    return;
	}
	stop();
	clip.setFramePosition(0);
	clip.start();
    }

    public void stop(){
	if (clip.isRunning()){
	    clip.stop();
	}
    }
}
