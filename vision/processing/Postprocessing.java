package sdp.vision.vision.processing;

import sdp.vision.vision.common.Robot;
import sdp.vision.vision.common.WorldState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: diagoras
 * Date: 27/01/13
 * Time: 21:54
 * To change this template use File | Settings | File Templates.
 */
public class Postprocessing {

    //Needed for finding orientation
    ArrayList<Point> newBluePixels;
    ArrayList<Point> newYellowPixels;

    public WorldState postProcess (WorldState worldState, BufferedImage image, ArrayList<Point> bluePixels, ArrayList<Point> yellowPixels) {

        Point fixedBall;
        Robot blueRobot = worldState.getBlueRobot();
        Robot yellowRobot = worldState.getYellowRobot();

        if ((worldState.getBlueRobot().getCoords().getX() != 0) && (worldState.getBlueRobot().getCoords().getY() != 0)) {
            blueRobot = new Robot(fixParallax(blueRobot.getCoords()), blueRobot.getAngle(), blueRobot.getPlatePoints());
        }

        if ((worldState.getYellowRobot().getCoords().getX() != 0) && (worldState.getYellowRobot().getCoords().getY() != 0)) {
            yellowRobot = new Robot(fixParallax(yellowRobot.getCoords()), yellowRobot.getAngle(), yellowRobot.getPlatePoints());
        }

        for (Point p : bluePixels) {

            if (VisionTools.isInRectangle(p, worldState.getBlueRobot().getPlatePoints())) {
                newBluePixels.add(fixParallax(p));
                }
            }
        for (Point p : yellowPixels) {

            if (VisionTools.isInRectangle(p, worldState.getYellowRobot().getPlatePoints())) {
                newYellowPixels.add(fixParallax(p));
            }
        }

        return worldState;
    }

    public Point fixParallax (Point point) {

        float x = (244f / 2f) * 0.2f -
                (VisionTools.pixelsToCM(point.getX()) * 0.2f) +
                (245f * VisionTools.pixelsToCM(point.getY()));
        x = (x / 245f);

        float y = (122f / 2.0f) * (0.2f) -
                (VisionTools.pixelsToCM(point.getX()) * 0.2f) +
                (245f * VisionTools.pixelsToCM(point.getY()));
        y = (y / 245f);

        y = VisionTools.cmToPixels(y);
        x = VisionTools.cmToPixels(x);

        return new Point((int) x, (int) y);
    }
}
