package sdp.vision.vision.common;

import java.awt.*;
import java.awt.geom.Point2D;


/**
 * This class represents a robot on the field.
 *
 * @author Gediminas Liktaras
 */
public final class Robot {

    /**
     * Length of the robot's top plate.
     */
    public static final double LENGTH = 70.0;
    /**
     * Width of the robot's top plate.
     */
    public static final double WIDTH = 50.0;


    /**
     * Coordinates of the robot's center on the field.
     */
    private Point2D.Double coords;
    /**
     * The angle the robot is facing, in radians.
     */
    private double angle;

    /**
     * Coordinates of the robot's corners.
     */
    private Point[] platePoints;


    /**
     * The main constructor.
     *
     * @param coords The robot's coordinates.
     * @param angle  The angle the robot is facing, in radians.
     */
    public Robot (Point2D.Double coords, double angle, Point[] platePoints) {

        this.coords = coords;
        this.angle = angle;
        this.platePoints = platePoints;
    }


    /**
     * Get coordinates of the robot's center.
     *
     * @return Coordinates of the robot's center.
     */
    public final Point2D.Double getCoords () {

        return coords;
    }

    /**
     * Get the angle the robot is facing, in radians.
     *
     * @return The angle the robot is facing, in radians.
     */
    public final double getAngle () {

        return angle;
    }

    public Point[] getPlatePoints () {

        return platePoints;
    }

    public String toString () {

        return "Robot center at: " + coords.toString() + "; Angle the robot is facing in radians: " + angle + "; Plate coords: " + platePoints.toString();
    }
}