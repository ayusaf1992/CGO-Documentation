package sdp.vision.vision.processing;

/**
 * Created with IntelliJ IDEA.
 * User: diagoras
 * Date: 26/01/13
 * Time: 21:40
 * To change this template use File | Settings | File Templates.
 */

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import static sdp.vision.vision.processing.Preprocessing.barrelCorrected;

/**
 * Finding rectangles around the green plate
 *
 * @author Dale Myers
 */
public class VisionTools {


    /**
     * Get the corners of a plate
     * <p/>
     * Use Dale's triangle method to get the corners of a plate
     *
     * @param points The list of points that make up a plate
     * @return The four corners of the plate
     */
    public static Point[] getCorners (ArrayList<Point> points) {

        Point centroid = getCentroid(points);
        Point furthest = new Point(0, 0);
        Point opposite = new Point(0, 0);
        Point adjacent = new Point(0, 0);
        Point adjacent2 = new Point(0, 0);
        double dist = 0;
        for (Point p : points) {
            if (Point.distance(p.x, p.y, centroid.x, centroid.y) > dist) {
                furthest = p;
                dist = Point.distance(p.x, p.y, centroid.x, centroid.y);
            }
        }
        dist = 0;
        for (Point p : points) {
            if (Point.distance(p.x, p.y, furthest.x, furthest.y) > dist) {
                opposite = p;
                dist = Point.distance(p.x, p.y, furthest.x, furthest.y);
            }
        }
        dist = 0;
        for (Point p : points) {
            if (Line2D.ptLineDist(furthest.x, furthest.y, opposite.x, opposite.y, p.x, p.y) > dist) {
                adjacent = p;
                dist = Line2D.ptLineDist(furthest.x, furthest.y, opposite.x, opposite.y, p.x, p.y);
            }
        }
        dist = 0;
        ArrayList<Point> outside = new ArrayList<Point>();
        for (Point p : points) {
            if (! isPointInTriangle(furthest, opposite, adjacent, p)) {
                outside.add(p);
            }
        }
        for (Point p : outside) {
            if (Line2D.ptLineDist(furthest.x, furthest.y, opposite.x, opposite.y, p.x, p.y) > dist) {
                adjacent2 = p;
                dist = Line2D.ptLineDist(furthest.x, furthest.y, opposite.x, opposite.y, p.x, p.y);
            }
        }
        return new Point[]{
                barrelCorrected(furthest),
                barrelCorrected(opposite),
                barrelCorrected(adjacent),
                barrelCorrected(adjacent2)
        };
    }

    /**
     * Dale's triangle
     * <p/>
     * Looks slightly further than it should to ensure it finds the farthest point
     *
     * @param a One of the corners of triangle
     * @param b One of the corners of triangle
     * @param c One of the corners of triangle
     * @param p The point to check if its in the triangle
     * @return True if the points are in the triangle
     */
    public static boolean isPointInTriangle (Point a, Point b, Point c, Point p) {

        Point v0 = new Point(c.x - a.x, c.y - a.y);
        Point v1 = new Point(b.x - a.x, b.y - a.y);
        Point v2 = new Point(p.x - a.x, p.y - a.y);

        int dot00 = dot(v0, v0);
        int dot01 = dot(v0, v1);
        int dot02 = dot(v0, v2);
        int dot11 = dot(v1, v1);
        int dot12 = dot(v1, v2);

        double invDenom = 1.0 / (double) (dot00 * dot11 - dot01 * dot01);
        double u = (dot11 * dot02 - dot01 * dot12) * invDenom;
        double v = (dot00 * dot12 - dot01 * dot02) * invDenom;

        // Check if point is in triangle
        return (u >= - 0.1) && (v >= - 0.1) && (u + v < 1.1);

    }

    /**
     * Like Dale's but doesn't look too far
     * <p/>
     * Used for orientation finding so that we don't look off of green plate
     *
     * @param a One of the corners of triangle
     * @param b One of the corners of triangle
     * @param c One of the corners of triangle
     * @param p The point to check if its in the triangle
     * @return True if in triangle
     */
    public static boolean isPointInNotShitTriangle (Point a, Point b, Point c, Point p) {

        Point v0 = new Point(c.x - a.x, c.y - a.y);
        Point v1 = new Point(b.x - a.x, b.y - a.y);
        Point v2 = new Point(p.x - a.x, p.y - a.y);

        int dot00 = dot(v0, v0);
        int dot01 = dot(v0, v1);
        int dot02 = dot(v0, v2);
        int dot11 = dot(v1, v1);
        int dot12 = dot(v1, v2);

        double invDenom = 1.0 / (double) (dot00 * dot11 - dot01 * dot01);
        double u = (dot11 * dot02 - dot01 * dot12) * invDenom;
        double v = (dot00 * dot12 - dot01 * dot02) * invDenom;

        // Check if point is in triangle
        return (u >= 0) && (v >= 0) && (u + v < 1);

    }

    /**
     * Dot product?
     *
     * @param a One Point
     * @param b One Point
     * @return The value of the dot product
     */
    public static int dot (Point a, Point b) {

        return (a.x * b.x) + (a.y * b.y);
    }

    /**
     * Get centroid of plate?
     *
     * @param points The points that make up the plate
     * @return The position of the centroid (Point)
     */
    public static Point getCentroid (ArrayList<Point> points) {

        Point centroid = new Point(0, 0);
        try {
            for (Point p : points) {
                centroid.translate(p.x, p.y);
            }
            return new Point(centroid.x / points.size(), centroid.y / points.size());
        } catch (Exception e) {
            return new Point(0, 0);
        }
    }

    /**
     * Use the triangle stuff to generate rectangle
     *
     * @param p      a point
     * @param points array of four points, forming a rectangle
     * @return whether p is in the rectangle formed from the four points
     */
    public static boolean isInRectangle (Point p, Point[] points) {

        if (p == new Point(0, 0)) {
            return false;
        }

        boolean a;
        boolean b;

        a = isPointInNotShitTriangle(points[0], points[2], points[3], p);
        b = isPointInNotShitTriangle(points[1], points[2], points[3], p);

        return a || b;
    }

    /**
     * Convert between cm and pixels
     *
     * @param cm The number of cm to convert
     * @return Number of pixels
     */
    public static int cmToPixels (float cm) {

        float width = (float) (650 - 0);
        float pixel = ((width / 244f) * cm);
        return (int) pixel;
    }

    /**
     * Convert between pixels and cm
     *
     * @param pixelValue Number of pixels to convert
     * @return Number of cms
     */
    public static float pixelsToCM (double pixelValue) {

        float width = (float) (640 - 0);
        float cm = (float) ((244f / width) * pixelValue);
        return cm;
    }

    public static float pixelsToCM (int pixelValue) {

        return pixelsToCM((double) pixelValue);
    }
}