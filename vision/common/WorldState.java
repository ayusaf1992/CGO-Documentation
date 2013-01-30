package sdp.vision.vision.common;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * The state of the field.
 *
 * @author Gediminas Liktaras
 */
public final class WorldState {

    /**
     * Location of the ball.
     */
    private Point ballCoords;
    /**
     * The blue robot.
     */
    private Robot blueRobot;
    /**
     * The yellow robot.
     */
    private Robot yellowRobot;

    /**
     * Picture of the world.
     */
    private BufferedImage worldImage;

    /**
     * The pitch.
     */
    private Pitch pitch;

    /**
     * The main constructor.
     *
     * @param ballCoords  Coordinates of the ball.
     * @param blueRobot   The blue robot.
     * @param yellowRobot The yellow robot.
     * @param worldImage  The picture of the field.
     */
    public WorldState (Point ballCoords, Robot blueRobot, Robot yellowRobot, BufferedImage worldImage) {

        this.ballCoords = ballCoords;
        this.blueRobot = blueRobot;
        this.yellowRobot = yellowRobot;
        this.worldImage = worldImage;
    }


    /**
     * Get the location of the ball.
     *
     * @return The location of the ball.
     */
    public final Point getBallCoords () {

        return ballCoords;
    }

    /**
     * Get the blue robot.
     *
     * @return The blue robot.
     */
    public final Robot getBlueRobot () {

        return blueRobot;
    }

    /**
     * Get the yellow robot.
     *
     * @return The yellow robot.
     */
    public final Robot getYellowRobot () {

        return yellowRobot;
    }

    /**
     * Get the image of the world.
     *
     * @return The image of the world.
     */
    public final BufferedImage getWorldImage () {

        return worldImage;
    }

    /**
     * Get the pitch.
     *
      * @return The pitch.
     */
    public final Pitch getPitch () {

        return pitch;
    }

    public String toString () {

        return "Ball: " + ballCoords.toString() + "; BlueRobot " + blueRobot.toString() + "; YellowRobot " + yellowRobot.toString() + pitch.toString();
    }
}
