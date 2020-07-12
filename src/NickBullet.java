import java.awt.*;
import java.awt.image.BufferedImage;

public class NickBullet {

    private float xAxis; // The X-Position of the bullet
    private float yAxis; // The Y-Position of the bullet
    private float movementX; // The movement towards the X position
    private float movementY; // The movement towards the Y position

    private SpaceInvaders spaceInvaders; // Reference to the main class

    public boolean isMarkedForRemoval; // Used as a switch to determine if the bullet should be deleted or not

    // Constructor, sets the above variables upon instantiation
    public NickBullet (int playerX, int playerY, int xAxis, int yAxis, SpaceInvaders spaceInvaders) {
        this.xAxis = xAxis; // sets the xpos
        this.yAxis = yAxis; // sets the ypos
        movementX = (playerX - this.xAxis) * 0.01f; // sets the movement based on the distance between the boss and the player
        movementY = (playerY - this.yAxis) * 0.01f; // sets the movement based on the distance between the boss and the player
        this.spaceInvaders = spaceInvaders; // Sets the reference to the main class
    }

    // Used to make the bullet move
    public void act() {
        xAxis += movementX; // Increase Xpos
        yAxis += movementY; // Increase Ypos
    }

    // Used to draw the bullet to the screen
    public void paint(Graphics2D g) {
        SpriteCache spriteCache = spaceInvaders.getSpriteCache();
        BufferedImage img = spriteCache.getImage("bullet");
        g.drawImage(img, (int)xAxis, (int)yAxis, null);
    }

    public Rectangle getBounds(){
        Rectangle bounds = new Rectangle((int)xAxis, (int)yAxis, 3, 9);
        return bounds;
    }

    public void setMarkedForRemoval(boolean markedForRemoval) {
        isMarkedForRemoval = markedForRemoval;
    }
    public boolean isMarkedForRemoval() {
        return isMarkedForRemoval;
    }
}
