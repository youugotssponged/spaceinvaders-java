import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import sun.audio.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;

/**
    AudioCache class by Steven Ryan-Carpenter : 23693703
    extended by Jordan McCann : 23571144
    extended by Peter Byrne: 23719273
*/
public class AudioCache {
    public Clip nickEnter;
    public Clip playerShoot;
    public Clip alienDie;
    public Clip playerHit;

    public AudioCache () {
        try {
            nickEnter = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("audio/bossSpawn.wav"));
            nickEnter.open(audioInputStream);
        } catch (IOException e) {
            System.out.println("Cannot load bossSpawn.wav");
            System.out.print(e.getMessage());
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Audio format not supported");
            System.out.print(e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println(e.getMessage());
        }
        try {
            playerShoot = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("audio/playerShoot.wav"));
            playerShoot.open(audioInputStream);
        } catch (IOException e) {
            System.out.println("Cannot load playerShoot.wav");
            System.out.print(e.getMessage());
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Audio format not supported");
            System.out.print(e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println(e.getMessage());
        }
        try {
            alienDie = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("audio/alienDie.wav"));
            alienDie.open(audioInputStream);
        } catch (IOException e) {
            System.out.println("Cannot load alienDie.wav");
            System.out.print(e.getMessage());
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Audio format not supported");
            System.out.print(e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println(e.getMessage());
        }
        try {
            playerHit = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("audio/playerHit.wav"));
            playerHit.open(audioInputStream);
        } catch (IOException e) {
            System.out.println("Cannot load playerHit.wav");
            System.out.print(e.getMessage());
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Audio format not supported");
            System.out.print(e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println(e.getMessage());
        }
    }
    public Clip getAudio(String audioRequested) {
        try {
            if (audioRequested == "bossSpawn") {
                return nickEnter;
            } else if(audioRequested == "playerShoot"){
                playerShoot.setFramePosition(0); // Resets the clip after being played - helps for repeatability of shooting
                return playerShoot;
            } else if(audioRequested == "alienDie"){
                alienDie.setFramePosition(0); // Resets the clip per use
                return alienDie;
            } else if(audioRequested == "playerHit"){
                playerHit.setFramePosition(0);
                return playerHit;
            }
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
