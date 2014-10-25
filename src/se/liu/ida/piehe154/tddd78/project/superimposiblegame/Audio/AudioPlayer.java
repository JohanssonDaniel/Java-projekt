package se.liu.ida.piehe154.tddd78.project.superimposiblegame.Audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Created by Daniel Johansson on 2014-10-23.
 */
public class AudioPlayer
{
    private Clip clip;
    private static final int SAMPLE_RATE = 16;
    public AudioPlayer(String s) {
	try {
	    AudioInputStream audioInputStream =
		    AudioSystem.getAudioInputStream(
		    	getClass().getResourceAsStream(s));
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

	}
	catch (Exception e){
	    e.printStackTrace();
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

    public void close(){
	stop();
	clip.close();
    }
}
