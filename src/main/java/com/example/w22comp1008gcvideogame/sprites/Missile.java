package com.example.w22comp1008gcvideogame.sprites;

import com.example.w22comp1008gcvideogame.GameConfig;
import com.example.w22comp1008gcvideogame.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Missile extends Sprite{
    /**
     * This is the constructor for the Missile class
     *
     * @param posX        - The left most position on the Sprite
     * @param posY        - The top most position for the Sprite
     */
    public Missile(int posX, int posY) {
        super(posX, posY, GameConfig.getMissile_width(), GameConfig.getMissile_height(), GameConfig.getMissile_speed());

        // Load in the image of the missile
        image = new Image(Main.class.getResource("images/missile.png").toExternalForm());
    }

    /**
     * This method will move the missile from the ship to the right side of the screen when spacebar is pressed
     */
    private void moveRight()
    {
        // Missile moves at the speed of the speed variable
        posX += speed;

        // If the missile hits the right wall it will not be alive anymore
        if (posX > GameConfig.getGame_width())
            setAlive(false);
    }

    /**
     * This method draws the missile and implements the moveRight() method
     * @param gc
     */
    public void draw(GraphicsContext gc)
    {
        super.draw(gc);
        moveRight();
    }
}
