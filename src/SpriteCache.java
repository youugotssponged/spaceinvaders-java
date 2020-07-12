
import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.*;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;

import javax.imageio.ImageIO;

/**
 * A resource manager / Cache to get and store assets to the game.
 * - Jordan McCann: 23571144
 * - Steven Ryan-Carpenter: 23693703
 * - Antony Bennett: 21119996
 * - Peter Byrne: 23719273
 */

public class SpriteCache {
    private BufferedImage playerImage;
    private BufferedImage monsterImage;
    private BufferedImage bulletImage;
    private BufferedImage gameOverImage;
    private BufferedImage bossMainImage;
    private BufferedImage bossFireImage;
    private BufferedImage bossTauntImage;
    private BufferedImage backgroundImage;
    private BufferedImage beeBulletImage;
    private BufferedImage healthIconImage;
    private BufferedImage titleScreen;
    private BufferedImage exitGameText;

    public SpriteCache() {
        try {
            playerImage = ImageIO.read(new File("images/player.png"));
        } catch (IOException e) {
            System.out.println("Cannot load player image");
            System.out.print(e.getMessage());
        }
        try {
            monsterImage = ImageIO.read(new File("images/monster.png"));
        } catch (IOException e) {
            System.out.println("Cannot load monster image");
            System.out.print(e.getMessage());
        }
        try {
            bulletImage = ImageIO.read(new File("images/bullet.png"));
        } catch (IOException e) {
            System.out.println("Cannot load bullet image");
            System.out.print(e.getMessage());
        }
        try{
            gameOverImage = ImageIO.read(new File("images/gameOver.png"));
        } catch(IOException e) {
            System.out.println("Cannot load gameOver Image");
            System.out.println(e.getMessage());
        }
        try {
            bossMainImage = ImageIO.read(new File ("images/bossMain.png"));
        } catch (IOException e) {
            System.out.println("Cannot load boss image");
            System.out.print(e.getMessage());
        }
        try {
            bossFireImage = ImageIO.read(new File ("images/bossFire.png"));
        } catch (IOException e) {
            System.out.println("Cannot load boss image");
            System.out.print(e.getMessage());
        }
        try {
            bossTauntImage = ImageIO.read(new File ("images/bossTaunt.png"));
        } catch (IOException e) {
            System.out.println("Cannot load boss image");
            System.out.print(e.getMessage());
        }
        try {
            backgroundImage = ImageIO.read(new File ("images/background.png"));
        } catch (IOException e) {
            System.out.println("Cannot load background image");
        }
        try {
            beeBulletImage = ImageIO.read(new File ("images/beeBullet.png"));
        } catch (IOException e) {
            System.out.println("Cannot load BULLETS OF BEES! image");
            System.out.print(e.getMessage());
        }
        try {
            healthIconImage = ImageIO.read(new File ("images/healthIcon.png"));
        } catch (IOException e) {
            System.out.println("Cannot load health icon image");
            System.out.print(e.getMessage());
        }
        try{
            titleScreen = ImageIO.read(new File("images/titleScreen.png"));
        } catch(IOException e){
            System.out.println("Cannot load title screen image");
            System.out.print(e.getMessage());
        }
        try{
            exitGameText = ImageIO.read(new File("images/exitText.png"));
        } catch(IOException e){
            System.out.println("Cannot load exit text instruction image");
            System.out.print(e.getMessage());
        }
    }

    public BufferedImage getImage(String imageRequested) {
        try {
            if (imageRequested == "player") {
                return playerImage;
            } else if (imageRequested == "monster") {
                return monsterImage;
            } else if (imageRequested == "bullet") {
                return bulletImage;
            } else if(imageRequested == "gameOver") {
                return gameOverImage;
            } else if (imageRequested == "bossMain") {
                return bossMainImage;
            } else if (imageRequested == "bossFire") {
                return bossFireImage;
            } else if (imageRequested == "bossTaunt") {
                return bossTauntImage;
            } else if (imageRequested == "background") {
                return backgroundImage;
            } else if(imageRequested == "beeBullet") {
                return beeBulletImage;
            } else if(imageRequested == "healthIcon") {
                return healthIconImage;
            } else if(imageRequested == "titleScreen") {
                return titleScreen;
            } else if(imageRequested == "exitGameText"){
                return exitGameText;
            }
            else {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.out.println("Image request error");
        }
        return null;
    }
}