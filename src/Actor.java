import java.awt.*;
import java.awt.image.BufferedImage;

public class Actor {
    private SpaceInvaders spaceInvaders;
    private String actor;
    public int xAxis = 0;
    public int yAxis = 0;
    private boolean isMarkedForRemoval;

    public Actor(SpaceInvaders spaceInvaders, String actor) {
        this.spaceInvaders = spaceInvaders;
        this.actor = actor;
    }

    public void collision(Actor a) {

    }

    public void collision(Player a) {

    }

    public void setX(int xAxis) {
        this.xAxis = xAxis;
    }

    public void setY(int yAxis) {
        this.yAxis = yAxis;
    }

    public void setMarkedForRemoval(boolean isMarkedForRemoval) {
        this.isMarkedForRemoval = isMarkedForRemoval;
    }

    public boolean isMarkedForRemoval() {
        return isMarkedForRemoval;
    }

    public void act() {

    }

    public void shootBullet() {
        if (spaceInvaders.getBullets().size() < 6) {
            Bullet bullet = new Bullet(actor, this.xAxis + 15, this.yAxis, this.spaceInvaders);
            spaceInvaders.getBullets().add(bullet);
        }
    }


    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(this.xAxis, this.yAxis, 33, 24);
        return bounds;
    }

    public void paint(Graphics2D g) {
        SpriteCache spriteCache = spaceInvaders.getSpriteCache();
        BufferedImage img = spriteCache.getImage(actor);
        g.drawImage(img, xAxis, yAxis, null);
    }

}
