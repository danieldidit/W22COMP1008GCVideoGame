package com.example.w22comp1008gcvideogame.sprites;

import com.example.w22comp1008gcvideogame.GameConfig;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite {

    // Declare variables
    protected Image image;
    protected int posX, posY, imageWidth, imageHeight;
    protected double speed;
    private boolean alive;

    /**
     * This is the constructor for the Sprite class
     * @param posX - The left most position on the Sprite
     * @param posY - The top most position for the Sprite
     * @param imageWidth - The width of the image when drawn
     * @param imageHeight - The height of the image when drawn
     * @param speed - How many pixels the Sprite can move
     */
    public Sprite(int posX, int posY, int imageWidth, int imageHeight, double speed) {
        setPosX(posX);
        setPosY(posY);
        setImageWidth(imageWidth);
        setImageHeight(imageHeight);
        setSpeed(speed);

        // Sprite is alive when created by default
        alive = true;
    }

    // Image getters & setters
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    // Position getters & setters
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        int furthestRight = GameConfig.getGame_width() - imageWidth;

        if (posX >= 0 && posX <= furthestRight)
            this.posX = posX;
        else
            throw new IllegalArgumentException("posX must be in the range of 0-" + furthestRight);
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        int furthestDown = GameConfig.getGame_height()-imageHeight-80;

        if (posY >= 0 && posY <= furthestDown)
            this.posY = posY;
        else throw new IllegalArgumentException("posY must be in the range of 0-" + furthestDown);
    }

    // Speed getter & setter
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    // isAlive getter & setter
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * This method will draw the image to the canvas if the object is alive
     * @param gc
     */
    public void draw(GraphicsContext gc)
    {
        if (alive)
            gc.drawImage(image, posX, posY, imageWidth, imageHeight);
    }

    /**
     * This method detects collisions between different sprite objects
     * @param sprite
     * @return
     */
    public boolean collidesWith(Sprite sprite)
    {
        return ((posX + imageWidth/2 > sprite.posX) && (posX < sprite.posX + sprite.imageWidth/2) &&
                (posY + imageHeight/2 > sprite.posY) && (posY < sprite.posY + sprite.imageHeight/2));
    }
}
