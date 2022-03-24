package com.example.w22comp1008gcvideogame.sprites;

import com.example.w22comp1008gcvideogame.GameConfig;
import com.example.w22comp1008gcvideogame.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// Extension of Sprite class
public class Alien  extends  Sprite{

    /**
     * This is the constructor for the Alien class
     *
     * @param posX        - The left most position on the Sprite
     * @param posY        - The top most position for the Sprite
     */
    public Alien(int posX, int posY) {
        super(posX, posY, GameConfig.getAlien_width(), GameConfig.getAlien_height(), GameConfig.getAlien_speed());

        // Load in image of the alien
        image = new Image(Main.class.getResource("images/alien.png").toExternalForm());
    }

    /**
     * The alien will move from the right side of the game to the left, When the alien gets to the far
     * left side it will reappear on the right side.
     */
    public void moveLeft()
    {
        // Aliens move at the speed of the speed variable
        posX -= speed;

        // If alien reaches left side of the game, restart at the right side
        if(posX < 0)
            posX = GameConfig.getGame_width();
    }

    /**
     * Draws the Aliens and calls the moveLeft() method
     * @param gc
     */
    public void draw(GraphicsContext gc)
    {
        super.draw(gc);
        moveLeft();
    }
}
