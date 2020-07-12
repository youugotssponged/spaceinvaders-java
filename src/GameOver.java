import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// Jordan McCann: 23571144
// Game Over class for rendering game over screen
public class GameOver {
    private int xAxis = 0; // Xpos
    private int yAxis = 0; // Ypos
    private SpaceInvaders spaceInvaders; // Reference to main class

    // Constructor
    public GameOver(SpaceInvaders spaceInvaders) {
        this.spaceInvaders = spaceInvaders;
    }

    // Renders Game Over image when called
    public void paint(Graphics2D g) {
        SpriteCache spriteCache = spaceInvaders.getSpriteCache();
        BufferedImage img = spriteCache.getImage("gameOver");
        g.drawImage(img, xAxis, yAxis, null);
        spaceInvaders.nicolasCage = null; // Set the boss to null
        spaceInvaders.NICK_CAGE_SPAWNED = false; // Set the spawned state to false
        spaceInvaders.NICK_CAGE_DEAD = false; // Set the dead state to false
        spaceInvaders.nickBullets = new ArrayList();  // Create a new array list
    }
}
