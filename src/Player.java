import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

public class Player extends Actor implements KeyListener{


    private int movementSpeed = 2; // The player's movement speed

    private boolean canMove; // Can the player move

    private boolean moveUp = false; // Can the player move up
    private boolean moveDown = false; // Can the player move down
    private boolean moveLeft = false; // Can the player move left
    private boolean moveRight = false; // Can the player move right
    private int currentHealth = 3; // Sets the players health
    public int invulnerabilityTimer = 0; // Sets the timer to 0

    private AudioCache audioCache; //  Reference to the audio cache


    // Constructor
    public Player(SpaceInvaders spaceInvaders) {
        super(spaceInvaders, "player");
        canMove = true; // Player can move
        audioCache = new AudioCache(); // Creates a new audio cache
    }

    // Returns the players height
    public int getHeight() {
        return 50;
    }

    // Gets the player health
    public int getCurrentHealth() {
        return currentHealth;
    }

    public void keyTyped(KeyEvent e){} // Required but not used

    // Handles the player's input
    public void keyPressed(KeyEvent e) {
        // Jordan McCann : 23571144
        // fetches and stores
        // the keycode of the key pressed
        int keyCode = e.getKeyCode(); // Gets the keyCode of the key that has been pressed

        // Event conditions for moving the player
        // and using the spacebar to shoot

        /**
         * UP - DOWN - LEFT - RIGHT
         **/

        // 38 = UP ARROW PRESSED
        if(keyCode == 38) {
            moveUp = true; // Enable the ability to move up
        }

        // 40 = DOWN ARROW PRESSED
        if(keyCode == 40) {
            moveDown = true; // Enable the ability to move down
        }

        // 37 = LEFT ARROW PRESSED
        if(keyCode == 37) {
            moveLeft = true; // Enable the ability to move left
        }

        // 39 = RIGHT ARROW PRESSED
        if(keyCode == 39) {
            moveRight = true; // Enable the ability to move right
        }

        // IF PLAYER PRESSES SPACE BAR
        if(keyCode == 32) {
            System.out.println("SPACE WAS PRESSED!"); // Debug Info
            //  PEW PEW PEW
            shootBullet();
            audioCache.getAudio("playerShoot").start();
        }

    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode(); // Gets the keyCode of the key that has been released

        if(keyCode == 38) {
            moveUp = false; // Disable the ability to move up
        }
        if (keyCode == 40) {
            moveDown = false; // Disable the ability to move down
        }
        if (keyCode == 37) {
            moveLeft = false; // Disable the ability to move left
        }
        if (keyCode == 39) {
            moveRight = false; // Disable the ability to move right
        }
    }

    public void setCanMove(boolean canMove){
        this.canMove = canMove;
    }

    public boolean getCanMove(){
        return canMove;
    }

    public void hurt() {
        currentHealth -= 1;
        audioCache.getAudio("playerHit").start();
    }
    public void hurt(int damage) {
        currentHealth -= damage;
        audioCache.getAudio("playerHit").start();
    }

    public void collision(Actor a) { // Handle the event in which a player collides with a monster
        if (invulnerabilityTimer <= 0) {
            invulnerabilityTimer = 180;
            this.hurt();
            a.setMarkedForRemoval(true);
        }
    }

    public void act() {
        if (canMove) {
            if (this.moveUp) { // If the up arrow has been pressed, move up
                this.yAxis = this.yAxis - movementSpeed;
            }
            if (this.moveDown) { // If the down arrow has been pressed, move down
                this.yAxis = this.yAxis + movementSpeed;
            }
            if (this.moveLeft) { // If the left arrow has been pressed, move left
                this.xAxis = this.xAxis - movementSpeed;
            }
            if (this.moveRight) { // If the right arrow has been pressed, move right
                this.xAxis = this.xAxis + movementSpeed;
            }
        }
        if (invulnerabilityTimer >= 0) {
            invulnerabilityTimer -= 1;
        }

    }

    // Gets the invulnerability timer
    public int getInvulnerabilityTimer() {
        return invulnerabilityTimer;
    }

    public Rectangle getBounds() { //
        Rectangle bounds = new Rectangle(xAxis, yAxis, 33, 27);
        return bounds;
    }

}
