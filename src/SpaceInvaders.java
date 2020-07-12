import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This code is the main body of the program. It contains a constructor for the program as well as
 * constructors for the SpriteCache and instantiates game objects at runtime start
 **/
public class SpaceInvaders extends Canvas implements Stage, KeyListener {

    private BufferStrategy strategy; // BufferStrategy is used to make sure images are fully loaded before presenting. For the purpose of this program, it should be ignored.
    private long usedTime; // Time used in drawing and updating a single frame

    private SpriteCache spriteCache; // Creates a new variable spriteCache of type SpriteCache object
    private AudioCache audioCache;
    private ArrayList actors; // An array containing all actors. An actor is a player or enemy
    private ArrayList bullets;
    private Player player; // A player object called player. This is where the player will be stored
    private GameOver gameOver; // GameOver Object to render game over screen

    public boolean GAME_OVER = false; // Game Over State Switch
    public boolean TITLE_SCREEN = false; // Used as a switch

    public NicolasCage nicolasCage;
    public ArrayList nickBullets = new ArrayList();
    public boolean NICK_CAGE_SPAWNED = false;
    public boolean NICK_CAGE_DEAD = false;

    // Constructor
    public SpaceInvaders() { // Sets up the game
        spriteCache = new SpriteCache(); // Instantiates a SpriteCache object. This will be referred to in the actor.paint methods
        audioCache = new AudioCache();

        JFrame ventana = new JFrame("Space Invaders"); // Ventana is spanish for window, weird variable name. Creates a new JFrame
        JPanel panel = (JPanel) ventana.getContentPane(); // Applies panel to vertana
        setBounds(0, 0, Stage.WIDTH, Stage.HEIGHT); // Set frame size
        panel.setPreferredSize(new Dimension(Stage.WIDTH, Stage.HEIGHT)); // Apply window size
        panel.setLayout(null); // Absolute positioning - no layout manager needed
        panel.add(this); // Adds this insance of the class to the same window it is drawing to. - draw target
        ventana.setBounds(0, 0, Stage.WIDTH, Stage.HEIGHT); // Set window size
        ventana.setVisible(true); // Make window visisble
        ventana.addWindowListener(new WindowAdapter() { // Embedded funciton, adds new listener
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        ventana.setResizable(false); // Can't resize window
        createBufferStrategy(2); // 1 back buffer for image, minimum
        strategy = getBufferStrategy(); // Apply buffer to variable
        requestFocus(); // Focus window
        addKeyListener(this); // Add key listener to current object
    }

    public void initWorld() { // Put starting objects in world
        actors = new ArrayList(); // Make new list for monsters
        bullets = new ArrayList();
        for (int i = 0; i < 10; i++) { // For loop to instantiate 10 enemies
            Monster m = new Monster(this); // Create enemy
            m.setX((int) (Math.random() * Stage.WIDTH)); // Random spawn location horizontally
            m.setY(i * 20); // Spawns further down the screen depending on what number enemy it is
            m.setVx((int) (Math.random() * 20 - 10)); // Velocity in the x direction

            actors.add(m); // Add actors to array
        }

        player = new Player(this); // Instantiate new player object, pass along this object
        player.setX(Stage.WIDTH / 2); // Put player in middle of stage
        player.setY(Stage.HEIGHT - 2 * player.getHeight()); //
    }

    public void addActor(Actor a) { // Add actor to actor array - unused
        actors.add(a);
    }


    public void updateWorld() { // Update all actors
        int i = 0;
        if(!GAME_OVER) { // If the game is not over, do this : Jordan McCann 23571144
            if(TITLE_SCREEN) {
                while (i < actors.size()) { // Loop until all actors have been used
                    Actor m = (Actor) actors.get(i); // Cast to actor
                    if (m.isMarkedForRemoval()) { // If is marked for removal
                        actors.remove(i); // Delete it from array
                        audioCache.getAudio("alienDie").start();
                    } else {
                        m.act(); // Otherwise, do stuff
                        i++; // Increment i
                    }
                }
                i = 0;
                while (i < bullets.size()) {
                    Bullet b = (Bullet) bullets.get(i);
                    if (b.isMarkedForRemoval()) {
                        bullets.remove(i);
                    } else {
                        b.act();
                        i++;
                    }
                }
                while (i < nickBullets.size()) {
                    NickBullet b = (NickBullet) nickBullets.get(i);
                    if (b.isMarkedForRemoval()) {
                        nickBullets.remove(i);
                    } else {
                        i++;
                    }
                }
                player.act(); // Do stuff for player always

                // Jordan McCann : 23571144
                // Checks to see if the player is at the screens edge
                // IF SO THEN, keep the player on screen
                checkPlayerScreenBoundaries();
                checkNicolasCage();
                if (nicolasCage != null) {
                    nicolasCage.act();
                    if (nicolasCage.health <= 0) {
                        nicolasCage = null;
                    }
                }

                // Run check on enemy if they have made it past the player's spawn
                checkEnemyBoundary();
            }
        }
    }

    // Made by Jordan McCann: 23571144
    // Modified by Peter Byrne: 23719273
    // Checks to see if the enemy has made it past the player and is at the bottom of the screen
    // if yes, then deduct the plauer a life
    // and remove that enemy from the list of current enemy actors
    public void checkEnemyBoundary(){
        for(int i = 0; i < actors.size(); i++){
            Actor m = (Actor) actors.get(i);
            if(m.yAxis == 580){
                player.hurt();
                actors.remove(i);
            }
        }
    }

    // Jordan McCann: 23571144
    // Checks to see if the player is at any of the screens edges
    // if yes, then spawn player at opposite side of the screen
    // relative to the edge that the player is on
    public void checkPlayerScreenBoundaries(){
        if(player.yAxis == 0){ // Top of the screen
            player.setY(600);
            player.setX(player.xAxis);
        } else if(player.yAxis == Stage.HEIGHT){ // Bottom of the screen
            player.setY(0);
            player.setX(player.xAxis);
        } else if(player.xAxis == 0){ // Left of the screen
            player.setX(Stage.WIDTH);
            player.setY(player.yAxis);
        } else if(player.xAxis == Stage.WIDTH){ // Right of the screen
            player.setX(0);
            player.setY(player.yAxis);
        }
    }

    public void checkCollisions() { // Check to see if anything is colliding
        Rectangle playerBounds = player.getBounds(); // Define player collision
        for (int i = 0; i < actors.size(); i++) { // Loop through enemies
            Actor a1 = (Actor) actors.get(i); // Cast to actor
            Rectangle r1 = a1.getBounds(); // Assign enemy bounds to r1
            if (r1.intersects(playerBounds)) { // If rectangles overlap
                player.collision(a1); // Collide player with enemy a1
                a1.collision(player); // Collide enemy a1 with player

            }
            for (int j = i + 1; j < actors.size(); j++) { // Loop through enemies
                Actor a2 = (Actor) actors.get(j);
                Rectangle r2 = a2.getBounds();
                if (r1.intersects(r2)) { // If two enemies are touching:
                    a1.collision(a2); // Blow them
                    a2.collision(a1); // both up
                }                     // or bound off or w/e, up to you
            }
            // Bullet collision starts here
            for (int j = 0; j < bullets.size(); j++) {
                Bullet b1 = (Bullet) bullets.get(j);
                Rectangle rb = b1.getBounds();
                if (rb.intersects(a1.getBounds())) {
                    a1.setMarkedForRemoval(true);
                    b1.setMarkedForRemoval(true);
                }
            }
        }

        // Checks for player collision against the bullets that
        // have been omitted by the boss
        for (int j = 0; j < nickBullets.size(); j++) {
            NickBullet b1 = (NickBullet) nickBullets.get(j);
            Rectangle rb = b1.getBounds();
            if (rb.intersects(playerBounds)) {
                if (player.invulnerabilityTimer <= 0) { // If the player is not invulnerable
                    player.hurt(); // Do damage
                    b1.setMarkedForRemoval(true); // remove the bullet
                    player.invulnerabilityTimer = 180; // Give the player added invulnerability time upon respawn
                }
            }
        }

        // Checks for collision - Boss damage via the player bullets
        if (nicolasCage != null) {
            for (int j = 0; j < bullets.size(); j++) {
                Bullet b1 = (Bullet) bullets.get(j);
                Rectangle rb = b1.getBounds();
                if (rb.intersects(nicolasCage.getBounds())) { // If the bullet hits the boss
                    nicolasCage.hurt(); // Do damage to the boss
                    b1.setMarkedForRemoval(true); // Remove the bullet that has been shot
                }
            }
        }
    }

    public void paintWorld() {  // Draw the world
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics(); // Pull graphics from buffer
        g.setColor(Color.black); // Set color to black
        if (!GAME_OVER) { // Game Over clause to stop drawing entities to the screen: Jordan McCann 23571144
            BufferedImage titleScreen = spriteCache.getImage("titleScreen"); // Assigns title Scren
            g.drawImage(titleScreen, 0, 0, null); // Draws title screen immage

            if(TITLE_SCREEN) {
                BufferedImage img = spriteCache.getImage("background"); // Assigns background image
                g.drawImage(img, 0, 0, null); // Draws background image

                for (int i = 0; i < actors.size(); i++) { // Cycles through and draws the actors on screen (monsters)
                    Actor m = (Actor) actors.get(i); // Gets the actors from the array list
                    m.paint(g); // Draws them
                }
                for (int i = 0; i < bullets.size(); i++) { // cycles through bullet array list
                    Bullet b = (Bullet) bullets.get(i); // gets the current bullet in the array
                    b.paint(g); // draws bullet
                }

                // Steven Ryan-Carpenter - 23693703
                // Player damage flicker animation - to illustrate the players temporary invulnerabilty
                // after losing a life
                if (player.getInvulnerabilityTimer() % 20 >= 5 || player.getInvulnerabilityTimer() <= 0) {
                    player.paint(g); // Draw the player
                }
                if (nicolasCage != null) {
                    nicolasCage.paint(g); // Draws the boss on screen
                    if (nickBullets != null) {
                        for (int i = 0; i < nickBullets.size(); i++) { // Draws the bullets that have been fired by the boss
                            NickBullet b = (NickBullet) nickBullets.get(i);
                            b.paint(g);
                        }
                    }
                }
            }
        }
        g.setColor(Color.white); // Set the draw colour to white
        if (usedTime > 0)
            g.drawString(String.valueOf(1000 / usedTime) + " fps", 0, Stage.HEIGHT - 50); // Draw fps text
        else
            g.drawString("--- fps", 0, Stage.HEIGHT - 50); // Draw fps text (if there is no value)

        if(nicolasCage == null && actors.size() == 0){ // if the enemies and the boss has been defeated
            BufferedImage exit_Instruction = spriteCache.getImage("exitGameText"); // Show exit instruction
            g.drawImage(exit_Instruction, 50, Stage.HEIGHT / 2, null); // Draw to the screen
        }

        // Antony Bennett : 21119996
        // Show the player's current health value (may be changed to an image icon)
        // HEALTH BAR
        BufferedImage health_img = spriteCache.getImage("healthIcon");
        if(!GAME_OVER) { // If the game is not over, draw the players health to screen, set the gameOver object to null
            if(TITLE_SCREEN){
                if (player.getCurrentHealth() == 3) { // If the players health is full
                    g.drawImage(health_img, 100, Stage.HEIGHT - 80, null); // Draw 3
                    g.drawImage(health_img, 150, Stage.HEIGHT - 80, null);
                    g.drawImage(health_img, 200, Stage.HEIGHT - 80, null);
                } else if(player.getCurrentHealth() == 2){
                    g.drawImage(health_img, 100, Stage.HEIGHT - 80, null); // Draw 2
                    g.drawImage(health_img, 150, Stage.HEIGHT - 80, null);
                } else if(player.getCurrentHealth() == 1){
                    g.drawImage(health_img, 100, Stage.HEIGHT - 80, null); // Draw 1
                }
                    gameOver = null; // Nullify the game over object as the game is not over
            }
        }

        // Jordan McCann : 23571144
        // Game over clause
        if(GAME_OVER){ // When Game over is triggered, draw the game over screen
            gameOver = new GameOver(this);
            gameOver.paint(g);
        }

        strategy.show(); // Show it on screen via the buffer
    }

    // Antony Bennett : 21119996
    public void gameOver() { // used to change the game over state
        player.setCanMove(false); // stops the players ability to move
        GAME_OVER = true; // trigger game over

        // Steven Ryan-Carpenter : 23693703
        for (int i = 0; i < actors.size();) {
            actors.remove(i); // delete entities from the game
        }
    }

    public SpriteCache getSpriteCache() { // Gets the spriteCache object on demand
        return spriteCache;
    }

    // Jordan McCann : 23571144
    // Handles key presses for the main game
    public void keyPressed(KeyEvent e) { // Handles input, via the player class

        int keyCode = e.getKeyCode(); // Gets the keycode of the key that was pressed
        // JORDAN MCCANN : 23571144
        // GAME OVER CLAUSE
        if(!GAME_OVER) {
            // Passes key presses for the player object to handle
            player.keyPressed(e);
        }

        // Reset functionality
        if(GAME_OVER && keyCode == 32) { // If the game is over, and space was pressed
            // RESET GAME
            System.out.println("SPACE PRESSED IN SPACEINVADERS CLASS"); // DEBUG INFO
            GAME_OVER = false; // Change game state back to original
            initWorld(); // Re-initialise world
        }

        // Used to start the game -- Enter Key
        if(keyCode == KeyEvent.VK_ENTER){
            TITLE_SCREEN = true;
            System.out.println("ENTER KEY PRESSED");
        }

        // Exit Functionality
        if(keyCode == 27){
            System.exit(0); // Ends and closes the game
        }
    }

    public void keyReleased(KeyEvent e) { // Handles input via the player class
        if(!GAME_OVER){
            player.keyReleased(e);
        }
        System.out.println("Releeeeeeessee"); // DEBUG INFO
    }

    public void keyTyped(KeyEvent e) { } // not needed - implemented by keyListener

    public void game() { // The main game loop
        usedTime = 1000;
        initWorld(); // Set up the world
        while (isVisible()) { // While the window/frame is visible
            long startTime = System.currentTimeMillis(); // Get the current time in milliseconds (per frame)
            // FRAME START
            updateWorld();  // Update the world and its entities/actors

            if(!GAME_OVER) { // GAME OVER CLAUSE - STOPS NULLPTR EXCEPTION
                checkCollisions(); // Check positions of the actors on screen - handle if colliding
            }

            paintWorld(); // Draw/Show on screen

            // Game over clause for player death: Peter Byrne: 23719273
            if(!GAME_OVER) { // GAME OVER CLAUSE - STOPS NULLPTR EXCEPTION
                if (player.getCurrentHealth() <= 0) { // When the player's health becomes 0 run gameOver()
                    gameOver();
                }
            }

            // FRAME END
            usedTime = System.currentTimeMillis() - startTime; // Time taken to compute the current frame
            try {
                // FPS COUNTER FIXED BY Peter Byrne: 23719273
                Thread.sleep(1000 / 60); // Caps the draw rate at 60 FPS
            } catch (InterruptedException e) { // Handle any exceptions caused by the current thread
            }
        }
    }

    public ArrayList getBullets() {
        return bullets;
    } // Returns a list of currently spawned bullets

    public void checkNicolasCage() { // Checks if the boss has been spawned
        if (actors.size() == 0 && !NICK_CAGE_SPAWNED) { // If the enemies are defeated
            nicolasCage = new NicolasCage(this, player); // Spawn the boss
            NICK_CAGE_SPAWNED = true;
        }
        if (nicolasCage == null && NICK_CAGE_SPAWNED) { // If the boss has been defeated
            NICK_CAGE_DEAD = true; // Remove the boss
        }
    }

    public static void main(String[] args) { // MAIN METHOD
        SpaceInvaders inv = new SpaceInvaders(); // Creates a new instance of the game
        inv.game(); // Starts the game loop
    }
}