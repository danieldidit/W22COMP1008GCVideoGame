package com.example.w22comp1008gcvideogame.sprites;

import com.example.w22comp1008gcvideogame.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// Extension of Sprite class
public class Explosion extends Sprite{

    // Declare variables
    private final int REFRESH_RATE = 5;
    private int currentCount;
    private int explosionPosition;
    private int[] spriteStartX;

    /**
     * This is the constructor for the Explosion class
     *
     * @param posX        - The left most position on the Sprite
     * @param posY        - The top most position for the Sprite
     * @param imageWidth  - The width of the image when drawn
     * @param imageHeight - The height of the image when drawn
     */
    public Explosion(int posX, int posY, int imageWidth, int imageHeight) {
        super(posX, posY, imageWidth, imageHeight, 0);

        // Loads image file with 5 seperate images for explosion the animation
        image = new Image(Main.class.getResource("images/fullExplosion2.png").toExternalForm());

        // Default explosion position for spriteStartX
        explosionPosition = 0;

        // 5 X values for the starting position for each explosion image
        spriteStartX = new int[]{0, 170, 330, 520, 710};

        // Sets the currentCount to the REFRESH_RATE every time a new explosion is created
        currentCount = REFRESH_RATE;
    }

    /**
     * This method will draw the explosion in the same position
     *  every 5th time the method is called, it will advance to the next explosion image
     *  until the final image is shown. It will then set the "isAlive" status to false
     * @param gc
     */
    @Override
    public void draw (GraphicsContext gc)
    {
        // Every 5th time the explosion is drawn, increase the explosion position to select
        //  a bigger explosion
        if (--currentCount < 0)
        {
            explosionPosition++;
            currentCount = REFRESH_RATE;
        }

        // If we get to explosion position 5, the explosion is complete, and we set the isAlive() to false
        if (explosionPosition == spriteStartX.length)
        {
            setAlive(false);
        }

        // But if it is still alive, draw the explosion
        else
        {
            if (isAlive()){
                // image, sourceX, sourceY, sourceWidth, sourceHeight, posX, posY, imageWidth, imageHeight
                gc.drawImage(image, spriteStartX[explosionPosition],0, 184, 368, posX, posY,
                        imageWidth, imageHeight );
            }
        }
    }
}
