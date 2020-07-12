import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.image.BufferedImage;

// Jordan McCann : 23571144
// Bullet Class Skeleton 12/03/2018 - 10:13

public class Bullet {

    private SpaceInvaders spaceInvaders; // Reference to the sprite cache
    private int bulletSpeed = 5; // Bullet Speed
    private String ownerID;
    private boolean isMarkedForRemoval = false;

    public int xAxis;
    public int yAxis;

    // Constructor //
    public Bullet(String ownerID, int xAxis, int yAxis, SpaceInvaders spaceInvaders) {
        this.ownerID = ownerID;
        if (ownerID == "player") {
            bulletSpeed = -5; // Bullet goes up
        }
        if (ownerID == "enemy") {
            bulletSpeed = 5; // Bullet goes down
        }
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.spaceInvaders = spaceInvaders;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void act(){
        yAxis += bulletSpeed; // Makes the bullet go up constantly
        if (this.yAxis < 0) {
            isMarkedForRemoval = true;
        }
    }

    // Util functions
    public boolean isMarkedForRemoval(){
        return isMarkedForRemoval;
    }

    public void setMarkedForRemoval(boolean isMarkedForRemoval) {
        this.isMarkedForRemoval = isMarkedForRemoval;
    }

    public Rectangle getBounds(){
        Rectangle bounds = new Rectangle(xAxis, yAxis, 3, 9);
        return bounds;
    }

    public int getBulletSpeed(){
        return bulletSpeed;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public void paint(Graphics2D g) {
        SpriteCache spriteCache = spaceInvaders.getSpriteCache();
        BufferedImage img = spriteCache.getImage("bullet");
        g.drawImage(img, xAxis, yAxis, null);
    }

}
