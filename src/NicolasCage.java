import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class NicolasCage extends Actor implements Stage{

    public boolean spawnTextShown = false; // switch to determine whether text should be shown or not
    private AudioCache audioCache; // Reference to the audio cache
    private boolean moveRight = true; // Sets the ability to move right
    private int bulletTimer = 0; // Sets the inital bullet timer
    private Player player; // Reference to the player
    private SpaceInvaders spaceInvaders; // Reference to the main class
    public int health = 24; // Boss health

    // Constructor, sets the position of the boss, the audio Cache, player and main class references
    public NicolasCage(SpaceInvaders spaceInvaders, Player player) {
        super(spaceInvaders, "bossMain");
        xAxis = (Stage.WIDTH / 2 )- 100; // Sets the Xpos
        yAxis = -280; // Sets the Ypos
        audioCache = new AudioCache(); // Creates a new audioCache
        this.player = player; // Sets the player reference
        this.spaceInvaders = spaceInvaders; // Sets the main class reference
    }

    // Used to make the boss move and shoot on its own
    public void act() {
        if (yAxis < 0) {
            yAxis++; // Move down
        } else if (!spawnTextShown) {
            audioCache.getAudio("bossSpawn").start();
            spawnTextShown = true; // Spawns the boss's sound
        } else {
            if (moveRight) {
                if (xAxis < Stage.WIDTH - 200) { // if the boss's Xpos is less than the stage's width - 200 pixels
                    xAxis++; // Move right
                } else {
                    moveRight = false; // disable the ability to move right
                    xAxis--; // Move left
                }
            } else if (!moveRight) { // If move left
                if (xAxis > 0) { // If Xpos is 0
                    xAxis--; // Move left
                } else {
                    moveRight = true; // Set the ability to move right
                    xAxis++; // Increase the xpos, going right
                }
            }
            if (bulletTimer < 30) { // If the bullet timer is less than 30
                bulletTimer++; // increase the timer
            } else {
                nickBullet(); // Shoot bullet at the player
                bulletTimer = 0; // Reset the bullet timer
            }
            if (spaceInvaders.nickBullets != null) {
                actNickBullets(); // If there are boss bullets on screen, make them move at the player
            }
        }

    }

    public void nickBullet() {
        int playerX = player.xAxis; // sets the players position locally
        int playerY = player.yAxis; // sets the players position locally
        NickBullet nickBullet = new NickBullet(playerX, playerY, xAxis + 100, yAxis + 140, spaceInvaders); // Create the bullet
        spaceInvaders.nickBullets.add(nickBullet); // Add the bullet to the current list of boss bullets
    }

    private void actNickBullets() { // Makes the bullets move and do damage to the player
        for (int i = 0; i < spaceInvaders.nickBullets.size(); i++) { // for every boss bullet
            NickBullet b = (NickBullet)spaceInvaders.nickBullets.get(i); // get the current bullet
            b.act(); // move it
        }
    }

    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(this.xAxis, this.yAxis, 280, 200);
        return bounds;
    }

    public void hurt() { // Used to deal damage to the boss
        health--; // decrement the boss's health
        System.out.println(health); // DEBUG INFO : BOSS HEALTH
    }

}
