package com.example.w22comp1008gcvideogame.sprites;

import com.example.w22comp1008gcvideogame.GameConfig;
import com.example.w22comp1008gcvideogame.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

// Extension of Sprite class
public class Ship extends Sprite {

    // Declare variables
    private final int REFRESH_RATE = 20;
    private int currentMissilePause;
    private ArrayList<Missile> activeMissiles;

    /**
     * This is the constructor for the Sprite class
     * @param posX        - The left most position on the Sprite
     * @param posY        - The top most position for the Sprite
     */
    public Ship(int posX, int posY) {
        super(posX, posY, GameConfig.getShip_width(), GameConfig.getShip_height(), GameConfig.getShip_speed());

        // Load image of ship
        image = new Image(Main.class.getResource("images/ship.png").toExternalForm());

        // Create new ArrayList to store active missiles
        activeMissiles = new ArrayList<>();

        // Time between missiles will equal the REFRESH_RATE
        currentMissilePause = REFRESH_RATE;
    }

    // Missile ArrayList getter
    public ArrayList<Missile> getActiveMissiles() {
        return activeMissiles;
    }

    /**
     * When the up arrow key is pressed, this method will decrease the Y coordinate based on the ship
     *  speed until it gets to 0
     */
    public void moveUp()
    {
        posY -= speed;

        if (posY < 0)
            posY = 0;
    }

    /**
     * When the down arrow key is pressed, this method will increase the Y coordinate based on the ship
     *  speed until it reaches the bottom of the scene
     */
    public void moveDown()
    {
        int furthestDown = GameConfig.getGame_height()-GameConfig.getShip_height()-80;

        posY += speed;

        if (posY > furthestDown)
            posY = furthestDown;
    }

    /**
     * When the right arrow key is pressed, this method will increase the X coordinate based on the ship
     *  speed until it reaches the right side of the scene
     */
    public void moveRight()
    {
        int furthestRight = GameConfig.getGame_width()-GameConfig.getShip_width();
        posX += speed;

        if (posX > furthestRight)
            posX = furthestRight;
    }

    /**
     * When the left arrow key is pressed, this method will move the ship to the left until it
     *  reaches the left most edge of the scene
     */
    public void moveLeft()
    {
        posX -= speed;

        if (posX < 0)
            posX = 0;
    }

    /**
     * This method will shoot a missile from the middle of the ship
     */
    public void shootMissile()
    {
        // If the given REFRESH_RATE is now below 0
        if (currentMissilePause < 0)
        {
            // Create a new missile with measurements putting it below the ship
            Missile newMissile = new Missile(posX + imageWidth, posY + imageHeight / 2
                    - GameConfig.getMissile_height() / 2);

            // Add to activeMissiles ArrayList
            activeMissiles.add(newMissile);

            // Reset the missile pause to the REFRESH_RATE
            currentMissilePause = REFRESH_RATE;
        }
    }

    /**
     * This method will draw the ship and then loop over all active missiles to draw them
     * @param gc
     */
    public void draw(GraphicsContext gc)
    {
        // Counts down the from REFRESH_RATE by 1
        currentMissilePause--;

        // Draws the Sprite object
        super.draw(gc);

        // Prints the active missile count to the terminal
        System.out.println("Active Missile: " + activeMissiles.size());

        // Loops through the missiles in the activeMissiles ArrayList an draws them
        for (Missile missile : activeMissiles)
            missile.draw(gc);
    }
}
