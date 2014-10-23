package se.liu.ida.piehe154.tddd78.project.superimposiblegame;

import java.applet.*;
import java.net.*;
import java.awt.*;

/**
 * Created by pierre on 2014-10-23.
 */
public class LjudSpelare{
    private URL u;
    private AudioClip a;

    public LjudSpelare(String filnamn) {
        try {
            u = new URL(filnamn);
        }
        catch (MalformedURLException e) {
            System.out.println("Felaktig URL: " + filnamn);
        }
        a = Applet.newAudioClip(u);
        //a.loop();
        //setSize(400,200);
        //add(new Label("Spelar " + filnamn));
        //setVisible(true);
    }

    public void play(){
        a.stop();
        a.play();
    }

    public static void main (String arg[]) {
        //new LjudSpelare("https://www.dropbox.com/s/6ciy39twgjaalpx/Jump4.wav?dl=1");
    }
}
