import java.awt.*;

public class Monster extends Actor {

    private int Vx;

    public Monster(SpaceInvaders spaceInvaders) {
        super(spaceInvaders, "monster");
    }

    public void setVx(int Vx) {
        /* Jordan McCann : 23571144 */
        // Fixes the problem of
        // a single alien being unable to move
        while(Vx == 0) { // While the Vx of the alien is 0
            Vx = (int) (Math.random() * 20 - 10); // Recalculate / Randomise
        }
        this.Vx = Vx; // Assign Vx to the instance
    }

    public void act() {
        if (xAxis >= Stage.WIDTH) {
            Vx *= -1;
            yAxis += 20;
        } else if (xAxis <= 0) {
            Vx *= -1;
            yAxis += 20;
        }
        xAxis = xAxis + Vx;
    }



}
