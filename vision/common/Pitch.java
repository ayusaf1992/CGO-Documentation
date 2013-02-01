package sdp.vision.vision.common;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: s1043077
 * Date: 29/01/13
 * Time: 16:30
 * Created a container class for the pitch.
 * Still to be updated with the co-ordinates.
 * Methods for returning all co-ordinates and the lengths of all sides.
 * Any other things to store in this class?
 */
public class Pitch {

    //TODO Find actual co-ordinates.
    // Co-ordinates of the pitch corners.
    public static final Point TopLeft = new Point();

    public static final Point BottomLeft = new Point();

    public static final Point TopRight = new Point();

    public static final Point BottomRight = new Point();


    /**
     * The main constructor.
     *
     * Currently no variables, are there any to add?
     */
    public Pitch () {

    }


    /**
     * Get coordinates of the top left corner.
     *
     * @return Coordinates of the top left corner.
     */
    public final Point getTopLeft () {

        return TopLeft;
    }

    /**
     * Get coordinates of the bottom left corner.
     *
     * @return Coordinates of the bottom left corner.
     */
    public final Point getBottomLeft() {

        return BottomLeft;
    }

    /**
     * Get coordinates of the top right corner.
     *
     * @return Coordinates of the top right corner.
     */
    public final Point getTopRight () {

        return TopRight;
    }

    /**
     * Get coordinates of the bottom right corner.
     *
     * @return Coordinates of the bottom right corner.
     */
    public final Point getBottomRight() {

        return BottomRight;
    }

    /**
     * Get length of the left side of the pitch.
     *
     * @return Length of left side of pitch.
     */
    public final int getLeftLength() {
        double TopLeftX = TopLeft.getX();
        double TopLeftY = TopLeft.getY();
        double BottomLeftX = BottomLeft.getX();
        double BottomLeftY = BottomLeft.getY();

        return (int) (TopLeftX - BottomLeftX + TopLeftY - BottomLeftY);

    }

    /**
     * Get length of the bottom side of the pitch.
     *
     * @return Length of bottom side of pitch.
     */
    public final int getBottomLength() {
        double BottomRightX = BottomRight.getX();
        double BottomRightY = BottomRight.getY();
        double BottomLeftX = BottomLeft.getX();
        double BottomLeftY = BottomLeft.getY();

        return (int) (BottomRightX - BottomLeftX + BottomRightY - BottomLeftY);

    }

    /**
     * Get length of the right side of the pitch.
     *
     * @return Length of right side of pitch.
     */
    public final int getRightLength() {
        double TopRightX= TopRight.getX();
        double TopRightY = TopRight.getY();
        double BottomRightX = BottomRight.getX();
        double BottomRightY = BottomRight.getY();

        return (int) (TopRightX - BottomRightX + TopRightY - BottomRightY);

    }

    /**
     * Get length of the top side of the pitch.
     *
     * @return Length of top side of pitch.
     */
    public final int getTopLength() {
        double TopRightX= TopRight.getX();
        double TopRightY = TopRight.getY();
        double TopLeftX = TopLeft.getX();
        double TopLeftY = TopLeft.getY();

        return (int) (TopRightX - TopLeftX + TopRightY - TopLeftY);

    }

    public String toString () {

        return "Pitch top left is: " + TopLeft.toString() + "; Pitch bottom left is: " + BottomLeft.toString() + "; Pitch top right is: " + TopRight.toString() + "; Pitch bottom right is: " + BottomRight.toString();
    }
}
