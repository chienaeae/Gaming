package controllers;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioRC {

    private static AudioRC irc;
    private Map<String, AudioClip> audPairs;

    private AudioRC() 
    {
        this.audPairs = new HashMap<>();
    }

    public static AudioRC getInstance() {
        if (irc == null) {
            irc = new AudioRC();
        }
        return irc;
    }
    
    public void play(String filename) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AudioInputStream audioInputStream;
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(new File(System.getProperty("user.dir") + "\\src" + filename));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.setFramePosition(0);
                    // values have min/max values, for now don't check for outOfBounds values
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(5f);
                    clip.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    Logger.getLogger(AudioRC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }
    public AudioClip tryGetAudio(String path){
        
        AudioClip clip = this.audPairs.get(path);
        if(clip == null)
        {
            return addAudio(path);
        }
        return clip;
    }
    private AudioClip addAudio(String path){
        try{
            AudioClip clip = Applet.newAudioClip(getClass().getResource(path));
            this.audPairs.put(path, clip);
            return clip;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
