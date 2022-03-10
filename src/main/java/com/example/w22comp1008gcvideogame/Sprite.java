package com.example.w22comp1008gcvideogame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Sprite {
    private Image image;
    private int posX, posY, imageWidth, imageHeight;
    private double speed;
    private boolean alive;

    /**
     * This is the constructor for the Sprite class
     * @param image - javafx image
     * @param posX - The left most position on the Sprite
     * @param posY - The top most position for the Sprite
     * @param imageWidth - The width of the image when drawn
     * @param imageHeight - The height of the image when drawn
     * @param speed - How many pixels the Sprite can move
     */
    public Sprite(Image image, int posX, int posY, int imageWidth, int imageHeight, double speed) {
        setImage(image);
        setPosX(posX);
        setPosY(posY);
        setImageWidth(imageWidth);
        setImageHeight(imageHeight);
        setSpeed(speed);
        alive = true;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

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
        int furthestDown = GameConfig.getGame_height()-imageHeight;

        if (posY >= 0 && posY <= furthestDown)
            this.posY = posY;
        else throw new IllegalArgumentException("posY must be in the range of 0-" + furthestDown);
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void draw(GraphicsContext gc)
    {
        gc.drawImage(image, posX, posY, imageWidth, imageHeight);
    }

    public void moveRight()
    {
        posX = posX + 5;

        if (posX >= GameConfig.getGame_width()-imageWidth/2)
            posX = GameConfig.getGame_width()-GameConfig.getGame_width();
    }


}