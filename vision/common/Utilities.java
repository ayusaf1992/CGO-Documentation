package sdp.vision.vision.common;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;


/**
 * Contains various utility methods, which do not fit anywhere else.
 *
 * @author Gediminas Liktaras
 */
public class Utilities {

    /**
     * Return a deep copy of the given BufferedImage.
     * <p/>
     * Taken from
     * http://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage.
     *
     * @param image BufferedImage to copy.
     * @return A deep copy of image.
     */
    public static BufferedImage deepBufferedImageCopy (BufferedImage image) {

        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }


    /**
     * Rotate point p2 around point p1 by the given angle in degrees.
     *
     * @param origin Rotation point.
     * @param point  Point to rotate.
     * @param angle  Angle of rotation in degrees.
     * @return Rotated point.
     */
    public static Point2D.Double rotatePoint (Point2D.Double origin,
                                              Point2D.Double point, double degrees) {

        double radAngle = Math.toRadians(degrees);

        double xDiff = point.x - origin.x;
        double yDiff = point.y - origin.y;

        double rotX = (xDiff * Math.cos(radAngle)) - (yDiff * Math.sin(- radAngle)) + origin.x;
        double rotY = (xDiff * Math.sin(- radAngle)) + (yDiff * Math.cos(radAngle)) + origin.y;

        return new Point2D.Double(rotX, rotY);
    }

    /**
     * Rotate point p2 around point p1 by the given angle in degrees.
     *
     * @param origin Rotation point.
     * @param point  Point to rotate.
     * @param angle  Angle of rotation in degrees.
     * @return Rotated point.
     */
    public static Point rotatePoint (Point origin, Point point, double angle) {

        Point2D.Double origin_f = new Point2D.Double(origin.x, origin.y);
        Point2D.Double point_f = new Point2D.Double(point.x, point.y);
        Point2D.Double retValue_f = rotatePoint(origin_f, point_f, angle);

        return new Point((int) retValue_f.x, (int) retValue_f.y);
    }


    /**
     * Translate the given point by some offset.
     *
     * @param point  The point to translate.
     * @param offset Translate offset.
     */
    public static void translatePoint (Point2D.Double point, Point2D.Double offset) {

        point.x += offset.x;
        point.y += offset.y;
    }


    /**
     * Check whether the given value is within specified bounds.
     * <p/>
     * If lower > upper, the function checks if the given value is within the
     * (-INF; upper] OR [lower; +INF) interval.
     *
     * @param value Value to check.
     * @param lower Lower bound of the interval.
     * @param upper Upper bound of the interval.
     * @return Whether the value is within the specified interval.
     */
    public static boolean valueWithinBounds (int value, int lower, int upper) {

        if (lower > upper) {
            return ((value >= lower) || (value <= upper));
        } else {
            return ((value >= lower) && (value <= upper));
        }
    }
}