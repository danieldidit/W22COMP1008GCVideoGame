package com.example.w22comp1008gcvideogame;

import com.example.w22comp1008gcvideogame.sprites.Alien;
import com.example.w22comp1008gcvideogame.sprites.Explosion;
import com.example.w22comp1008gcvideogame.sprites.Missile;
import com.example.w22comp1008gcvideogame.sprites.Ship;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;

public class GameBoardController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button startButton;

    private HashSet<KeyCode> activeKeys;

    private AnimationTimer timer;

    /**
     * This method Starts and runs the game
     * @param event
     */
    @FXML
    private void startGame(ActionEvent event)
    {
        // Makes the start button invisible
        startButton.setVisible(false);

        // Creates a new Hashset to store pressed keys
        activeKeys = new HashSet<>();

        // This is an example of an anonymous inner class
        //  Will store any pressed keys in activeKeys
        anchorPane.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyPressed) {
                System.out.println(keyPressed.getCode() + "-> activeKeys: " + activeKeys);
                activeKeys.add(keyPressed.getCode());
            }
        });

        // This will remove any pressed keys from activeKeys once they are no longer pressed
        anchorPane.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyReleased) {
                activeKeys.remove(keyReleased.getCode());
            }
        });

        // A canvas can be used to "draw" on. The GraphicsContext is the tool used for the drawing
        Canvas canvas = new Canvas(GameConfig.getGame_width(), GameConfig.getGame_height());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Loads the background image
        Image background = new Image(getClass().getResource("images/space.png").toExternalForm());

        // Created the Ship Sprite object
        Ship ship = new Ship(100,100);

        // Create 5 aliens coming from random yet limited positions on the canvas
        SecureRandom rng = new SecureRandom();
        ArrayList<Alien> aliens = new ArrayList<>();
        for (int i = 1; i <=5; i++)
            aliens.add(new Alien(rng.nextInt(500, GameConfig.getGame_width()),
                    rng.nextInt(0, GameConfig.getGame_height() - GameConfig.getAlien_height()-80)));

        // create a collection to hold all the explosions
        ArrayList<Explosion> explosions = new ArrayList<>();

        // Creates animation timer
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                // Draws the background image on the canvas
                gc.drawImage(background,0,0, GameConfig.getGame_width(), GameConfig.getGame_height());

                // Update the ship position with updateShip() method and draw it on canvas
                updateShip(ship);
                ship.draw(gc);

                // Loop through aliens
                for (Alien alien : aliens)
                {
                    // draw aliens on canvas
                    alien.draw(gc);

                    // Loops through activeMissiles
                    for (Missile missile : ship.getActiveMissiles())
                    {
                        // If missile collides with the alien
                        if (missile.collidesWith(alien))
                        {
                            // add an explosion to explosions ArrayList
                            explosions.add(new Explosion(alien.getPosX()-40, alien.getPosY()-40,
                                    125, 125));

                            // Kill the missile and the alien
                            missile.setAlive(false);
                            alien.setAlive(false);
                        }
                    }

                    // If alien collides with the ship
                    if (alien.collidesWith(ship))
                    {
                        // add an explosion to the explosions ArrayList
                        explosions.add(new Explosion(ship.getPosX()-40, ship.getPosY()-70,
                                200, 200));

                        // Kill the ship and the alien
                        ship.setAlive(false);
                        alien.setAlive(false);
                    }

                    // If the explosion animation is over, and the ship is dead, display message and end game
                    if (explosions.size()== 0 && !ship.isAlive()) {
                        finalMessage(gc, "The Aliens Got You!!", Color.RED);
                        timer.stop();
                    }
                }

                // Remove explosion from ArrayList if it is not alive
                explosions.removeIf(explosion -> !explosion.isAlive());

                // Loop through explosions Arraylist and draw explosions on canvas
                for (Explosion explosion : explosions)
                    explosion.draw(gc);

                // Call methods to remove any dead aliens or missiles from their ArrayList
                removeDeceasedAliens(aliens);
                removeExplodedMissile(ship.getActiveMissiles());

                // Update the games scoreboard
                updateStats(gc, aliens);

                // Check to see if aliens are all dead, and player is still alive. If so end game with good message
                if (aliens.size() == 0 && ship.isAlive())
                {
                    finalMessage(gc, "Congratulations - you have saved the universe!", Color.WHITE);

                    // End game once explosion animation is over
                    if (explosions.size()==0)
                     timer.stop();
                }

                // Check to see if aliens are all dead, but last alien killed player. If so end game with bad message
                if (aliens.size() == 0 && !ship.isAlive())
                {
                    finalMessage(gc, "The Aliens Got You!!", Color.WHITE);

                    // End game once explosion animation is over
                    if (explosions.size()==0)
                        timer.stop();
                }
            }
        };

        // Starts the AnimationTimer
        timer.start();

        // Attach the canvas to the anchorPane
        anchorPane.getChildren().add(canvas);
    }

    /**
     * This method will display the final message to the person playing the game
     */
    private void finalMessage(GraphicsContext gc, String message, Color color)
    {
        // Font type, weight and size
        Font font = Font.font("Arial", FontWeight.NORMAL, 32);

        // Sets font, color, and location to the GraphicsContext
        gc.setFont(font);
        gc.setFill(color);
        gc.fillText(message, 250, 350);
    }

    /**
     * This method will remove the Alien object if it is no longer alive
     * @param aliens
     */
    private void removeDeceasedAliens(ArrayList<Alien> aliens) {

            aliens.removeIf(alien -> !alien.isAlive());
    }

    /**
     * This method will remove the missile object if it has exploded
     * @param activeMissiles
     */
    private void removeExplodedMissile(ArrayList<Missile> activeMissiles) {

        activeMissiles.removeIf(missile -> !missile.isAlive());
    }

    /**
     * This method will update the location of the ship based on the keys pressed. Will also shoot missiles if
     *  spacebar is pressed
     */
    private void updateShip(Ship ship)
    {
        if (activeKeys.contains(KeyCode.DOWN) || activeKeys.contains(KeyCode.S))
            ship.moveDown();
        if (activeKeys.contains(KeyCode.UP) || activeKeys.contains(KeyCode.W))
            ship.moveUp();
        if (activeKeys.contains(KeyCode.RIGHT) || activeKeys.contains(KeyCode.D))
            ship.moveRight();
        if (activeKeys.contains(KeyCode.LEFT) || activeKeys.contains(KeyCode.A))
            ship.moveLeft();
        if (activeKeys.contains(KeyCode.SPACE))
            ship.shootMissile();
    }

    /**
     * This method will update the scoreboard of the canvas
     */
    private void updateStats(GraphicsContext gc, ArrayList<Alien> aliens)
    {
        // draw a black rectangle bar across the bottom of the canvas
        gc.setFill(Color.BLACK);
        gc.fillRect(0, GameConfig.getGame_height()-80, GameConfig.getGame_width(), 80);

        // write how many aliens are left
        Font font = Font.font("Arial", FontWeight.NORMAL, 32);
        gc.setFont(font);
        gc.setFill(Color.WHITE);
        gc.fillText("Aliens Remaining: "+aliens.size(), 600, 750);
    }
}
