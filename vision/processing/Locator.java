package sdp.vision.vision.processing;

import sdp.vision.vision.common.Robot;
import sdp.vision.vision.common.WorldState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: diagoras
 * Date: 26/01/13
 * Time: 19:17
 * To change this template use File | Settings | File Templates.
 */
public class Locator {

    private int[][] redBallThresh = new int[2][3];
    private int[][] yellowRobotThresh = new int[2][3];
    private int[][] blueRobotThresh = new int[2][3];
    private int[][] greenPlatesThresh = new int[2][1];
    private Point[] blueGreenPlate4Points = new Point[]{new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0)};
    private Point[] yellowGreenPlate4Points = new Point[]{new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0)};
    private ArrayList<Point> bluePixels = new ArrayList<Point>();
    private ArrayList<Point> yellowPixels = new ArrayList<Point>();

    /* Ball. */
    private int ball_r = 130;
    private int ball_g = 90;
    private int ball_b = 90;

    /* Blue Robot. */
    private int blue_r = 130;
    private int blue_g = 180;
    private int blue_b = 100;

    /* Yellow Robot. */
    private int yellow_r_low = 245;
    private int yellow_r_high = 260;
    private int yellow_g_low = 220;
    private int yellow_g_high = 242;
    private int yellow_b_low = 65;
    private int yellow_b_high = 115;

    /* Grey Circle. */
    private int grey_r_low;
    private int grey_r_high;
    private int grey_g_low;
    private int grey_g_high;
    private int grey_b_low;
    private int grey_b_high;

    /* Green plates */
    private int green_RG = 75;
    private int green_GB = 50;
    private int green_g = 125;

    private ArrayList<Point> blueGreenPlate;
    private ArrayList<Point> yellowGreenPlate;
    private Color cE;
    private Color cS;
    private Color cEN;
    private Color cEE;
    private Color cSS;
    private Color cSW;

    private Color c;

    public WorldState locate (WorldState previousState, BufferedImage img, int left, int right, int top, int bottom) {

        ;

        //stops it fucking up the locations before we've given it the thresholds
        //if (worldState.isClickingDone()){

        ArrayList<Point> bluePixels = new ArrayList<Point>();
        ArrayList<Point> yellowPixels = new ArrayList<Point>();
        /*pitch = worldState.getRoom();
        width = right-left;
        height = top-bottom;*/

        /*
       Initialising to one to stop java dividing by 0 when it shouldn't
         */
        int redCountA = 0;
        int redCountB = 0;
        int redCountC = 0;
        int redCountD = 0;
        int redCountE = 0;

        Point redCentroidA = new Point(0, 0);
        Point redCentroidB = new Point(0, 0);
        Point redCentroidC = new Point(0, 0);
        Point redCentroidD = new Point(0, 0);
        Point redCentroidE = new Point(0, 0);

        int blueCountA = 0;
        int blueCountB = 0;
        int blueCountC = 0;
        int blueCountD = 0;
        int blueCountE = 0;

        Point blueCentroidA = new Point(0, 0);
        Point blueCentroidB = new Point(0, 0);
        Point blueCentroidC = new Point(0, 0);
        Point blueCentroidD = new Point(0, 0);
        Point blueCentroidE = new Point(0, 0);

        int yellowCountA = 0;
        int yellowCountB = 0;
        int yellowCountC = 0;
        int yellowCountD = 0;
        int yellowCountE = 0;

        Point yellowCentroidA = new Point(0, 0);
        Point yellowCentroidB = new Point(0, 0);
        Point yellowCentroidC = new Point(0, 0);
        Point yellowCentroidD = new Point(0, 0);
        Point yellowCentroidE = new Point(0, 0);

        int GB = 0;
        for (int i = left; i < right; i++) {
            for (int j = top; j < bottom; j++) {
                //Vision.logger.debug("Oh dear (i,j) = " + Integer.toString(i) + "," + Integer.toString(j) + ")");
                c = new Color(img.getRGB(i, j));

                GB = Math.abs((c.getBlue() - c.getGreen()));
                int RG = Math.abs((c.getRed() - c.getGreen()));
                //RB = Math.abs((c.getRed() - c.getBlue()));

                double randy;
                if (isRed(c, GB)) { //  was inside  RB > 50 && RG > 50
                    img.setRGB(i, j, Color.red.getRGB()); //Red Ball
                    randy = Math.random();
                    if (randy > 0 && randy <= 0.2) {
                        redCountA++;
                        redCentroidA.setLocation(redCentroidA.getX() + i, redCentroidA.getY() + j);
                    } else if (randy > 0.2 && randy <= 0.4) {
                        redCountB++;
                        redCentroidB.setLocation(redCentroidB.getX() + i, redCentroidB.getY() + j);
                    } else if (randy > 0.4 && randy <= 0.6) {
                        redCountC++;
                        redCentroidC.setLocation(redCentroidC.getX() + i, redCentroidC.getY() + j);
                    } else if (randy > 0.6 && randy <= 0.8) {
                        redCountD++;
                        redCentroidD.setLocation(redCentroidD.getX() + i, redCentroidD.getY() + j);
                    } else if (randy > 0.8 && randy <= 1) {
                        redCountE++;
                        redCentroidE.setLocation(redCentroidE.getX() + i, redCentroidE.getY() + j);
                    }
                } else if (isYellow(c)) {
                    //TODO Reimplement noise reduction in a better location. Make sure to do it for blue below as well.
                    setCs(i,j,right,left,top,bottom, img);
                    if (isYellow(cS) && isYellow(cE) && isYellow(cEE) && isYellow(cEN) && isYellow(cSS) && isYellow(cSW)) {
                    img.setRGB(i, j, Color.yellow.getRGB()); // Yellow robot

                    ArrayList<Integer> yellowRobotX = new ArrayList<Integer>();
                    ArrayList<Integer> yellowRobotY = new ArrayList<Integer>();
                    yellowRobotX.add(i);
                    yellowRobotY.add(j);
                    randy = Math.random();
                    if (randy > 0 && randy <= 0.2) {
                        yellowCountA++;
                        yellowCentroidA.setLocation(yellowCentroidA.getX() + i, yellowCentroidA.getY() + j);
                    } else if (randy > 0.2 && randy <= 0.4) {
                        yellowCountB++;
                        yellowCentroidB.setLocation(yellowCentroidB.getX() + i, yellowCentroidB.getY() + j);
                    } else if (randy > 0.4 && randy <= 0.6) {
                        yellowCountC++;
                        yellowCentroidC.setLocation(yellowCentroidC.getX() + i, yellowCentroidC.getY() + j);
                    } else if (randy > 0.6 && randy <= 0.8) {
                        yellowCountD++;
                        yellowCentroidD.setLocation(yellowCentroidD.getX() + i, yellowCentroidD.getY() + j);
                    } else if (randy > 0.8 && randy <= 1) {
                        yellowCountE++;
                        yellowCentroidE.setLocation(yellowCentroidE.getX() + i, yellowCentroidE.getY() + j);
                    }
                    yellowPixels.add(new Point(i, j));
                    }
                } else if (isBlue(c)) {
                    setCs(i,j,right,left,top,bottom, img);
                    if (isBlue(cS) && isBlue(cE) && isBlue(cEE) && isBlue(cEN) && isBlue(cSS) && isBlue(cSW)) {
                    img.setRGB(i, j, Color.blue.getRGB()); // Blue robot

                    ArrayList<Integer> blueRobotX = new ArrayList<Integer>();
                    ArrayList<Integer> blueRobotY = new ArrayList<Integer>();
                    blueRobotX.add(i);
                    blueRobotY.add(j);
                    randy = Math.random();
                    if (randy > 0 && randy <= 0.2) {
                        blueCountA++;
                        blueCentroidA.setLocation(blueCentroidA.getX() + i, blueCentroidA.getY() + j);
                    } else if (randy > 0.2 && randy <= 0.4) {
                        blueCountB++;
                        blueCentroidB.setLocation(blueCentroidB.getX() + i, blueCentroidB.getY() + j);
                    } else if (randy > 0.4 && randy <= 0.6) {
                        blueCountC++;
                        blueCentroidC.setLocation(blueCentroidC.getX() + i, blueCentroidC.getY() + j);
                    } else if (randy > 0.6 && randy <= 0.8) {
                        blueCountD++;
                        blueCentroidD.setLocation(blueCentroidD.getX() + i, blueCentroidD.getY() + j);
                    } else if (randy > 0.8 && randy <= 1) {
                        blueCountE++;
                        blueCentroidE.setLocation(blueCentroidE.getX() + i, blueCentroidE.getY() + j);
                    }
                    bluePixels.add(new Point(i, j));
                    }
                    //make blue thresholds for the different pitches in that [pitch][x] style
                    //TODO Find a better way for isGreen to get access to the WorldState data of robot locations
                } else if (isGreen(c, GB, RG)) {
                    img.setRGB(i, j, Color.green.getRGB()); // GreenPlates
                    if (previousState == null) {
                        blueGreenPlate = new ArrayList<Point>();
                        yellowGreenPlate = new ArrayList<Point>();
                    }
                    else {
                        if (Point.distance(previousState.getBlueRobot().getCoords().getX(),
                                previousState.getBlueRobot().getCoords().getY(),
                                i, j) < 34) {
                            blueGreenPlate.add(new Point(i, j));
                        }
                        if (Point.distance(previousState.getYellowRobot().getCoords().getX(),
                                previousState.getYellowRobot().getCoords().getX(),
                                i, j) < 34) {
                            yellowGreenPlate.add(new Point(i, j));
                        }
                    }
                }

            }
        }

        if (redCountA == 0) redCountA++;
        if (redCountB == 0) redCountB++;
        if (redCountC == 0) redCountC++;
        if (redCountD == 0) redCountD++;
        if (redCountE == 0) redCountE++;
        if (blueCountA == 0) blueCountA++;
        if (blueCountB == 0) blueCountB++;
        if (blueCountC == 0) blueCountC++;
        if (blueCountD == 0) blueCountD++;
        if (blueCountE == 0) blueCountE++;
        if (yellowCountA == 0) yellowCountA++;
        if (yellowCountB == 0) yellowCountB++;
        if (yellowCountC == 0) yellowCountC++;
        if (yellowCountD == 0) yellowCountD++;
        if (yellowCountE == 0) yellowCountE++;

        float totalRedX = 0;
        float totalRedY = 0;
        int numRedCentroids = 0;


        redCentroidA.setLocation(redCentroidA.getX() / redCountA, redCentroidA.getY() / redCountA);
        redCentroidB.setLocation(redCentroidB.getX() / redCountB, redCentroidB.getY() / redCountB);
        redCentroidC.setLocation(redCentroidC.getX() / redCountC, redCentroidC.getY() / redCountC);
        redCentroidD.setLocation(redCentroidD.getX() / redCountD, redCentroidD.getY() / redCountD);
        redCentroidE.setLocation(redCentroidE.getX() / redCountE, redCentroidE.getY() / redCountE);

        float totalYellowX = 0;
        float totalYellowY = 0;
        int numYellowCentroids = 0;


        yellowCentroidA.setLocation(yellowCentroidA.getX() / yellowCountA, yellowCentroidA.getY() / yellowCountA);
        yellowCentroidB.setLocation(yellowCentroidB.getX() / yellowCountB, yellowCentroidB.getY() / yellowCountB);
        yellowCentroidC.setLocation(yellowCentroidC.getX() / yellowCountC, yellowCentroidC.getY() / yellowCountC);
        yellowCentroidD.setLocation(yellowCentroidD.getX() / yellowCountD, yellowCentroidD.getY() / yellowCountD);
        yellowCentroidE.setLocation(yellowCentroidE.getX() / yellowCountE, yellowCentroidE.getY() / yellowCountE);


        float totalBlueX = 0;
        float totalBlueY = 0;
        int numBlueCentroids = 0;


        blueCentroidA.setLocation(blueCentroidA.getX() / blueCountA, blueCentroidA.getY() / blueCountA);
        blueCentroidB.setLocation(blueCentroidB.getX() / blueCountB, blueCentroidB.getY() / blueCountB);
        blueCentroidC.setLocation(blueCentroidC.getX() / blueCountC, blueCentroidC.getY() / blueCountC);
        blueCentroidD.setLocation(blueCentroidD.getX() / blueCountD, blueCentroidD.getY() / blueCountD);
        blueCentroidE.setLocation(blueCentroidE.getX() / blueCountE, blueCentroidE.getY() / blueCountE);

        c = new Color(img.getRGB((int) redCentroidA.getX(), (int) redCentroidA.getY()));
        if (isRed(c, GB)) {
            totalRedX += redCentroidA.getX();
            totalRedY += redCentroidA.getY();
            numRedCentroids++;
        }
        c = new Color(img.getRGB((int) redCentroidB.getX(), (int) redCentroidB.getY()));
        if (isRed(c, GB)) {
            totalRedX += redCentroidB.getX();
            totalRedY += redCentroidB.getY();
            numRedCentroids++;
        }
        c = new Color(img.getRGB((int) redCentroidC.getX(), (int) redCentroidC.getY()));
        if (isRed(c, GB)) {
            totalRedX += redCentroidC.getX();
            totalRedY += redCentroidC.getY();
            numRedCentroids++;
        }
        c = new Color(img.getRGB((int) redCentroidD.getX(), (int) redCentroidD.getY()));
        if (isRed(c, GB)) {
            totalRedX += redCentroidD.getX();
            totalRedY += redCentroidD.getY();
            numRedCentroids++;
        }
        c = new Color(img.getRGB((int) redCentroidE.getX(), (int) redCentroidE.getY()));
        if (isRed(c, GB)) {
            totalRedX += redCentroidE.getX();
            totalRedY += redCentroidE.getY();
            numRedCentroids++;
        }

        if (numRedCentroids == 0) {
            numRedCentroids++;
        }

        float redX = (float) (totalRedX / numRedCentroids);
        float redY = (float) (totalRedY / numRedCentroids);

        /*System.out.println(yellowCentroidA.toString());
        System.out.println(yellowCentroidB.toString());
        System.out.println(yellowCentroidC.toString());
        System.out.println(yellowCentroidD.toString());
        System.out.println(yellowCentroidE.toString());*/

        c = new Color(img.getRGB((int) yellowCentroidA.getX(), (int) yellowCentroidA.getY()));
        if (isYellow(c)) {
            totalYellowX += yellowCentroidA.getX();
            totalYellowY += yellowCentroidA.getY();
            numYellowCentroids++;
        }
        c = new Color(img.getRGB((int) yellowCentroidB.getX(), (int) yellowCentroidB.getY()));
        if (isYellow(c)) {
            totalYellowX += yellowCentroidB.getX();
            totalYellowY += yellowCentroidB.getY();
            numYellowCentroids++;
        }
        c = new Color(img.getRGB((int) yellowCentroidC.getX(), (int) yellowCentroidC.getY()));
        if (isYellow(c)) {
            totalYellowX += yellowCentroidC.getX();
            totalYellowY += yellowCentroidC.getY();
            numYellowCentroids++;
        }
        c = new Color(img.getRGB((int) yellowCentroidD.getX(), (int) yellowCentroidD.getY()));
        if (isYellow(c)) {
            totalYellowX += yellowCentroidD.getX();
            totalYellowY += yellowCentroidD.getY();
            numYellowCentroids++;
        }
        c = new Color(img.getRGB((int) yellowCentroidE.getX(), (int) yellowCentroidE.getY()));
        if (isYellow(c)) {
            totalYellowX += yellowCentroidE.getX();
            totalYellowY += yellowCentroidE.getY();
            numYellowCentroids++;
        }

        System.out.println(totalYellowX);
        System.out.println(totalYellowY);
        System.out.println(numYellowCentroids);

        if (numYellowCentroids == 0) {
            numYellowCentroids++;
        }

        float yellowX = (float) (totalYellowX / numYellowCentroids);
        float yellowY = (float) (totalYellowY / numYellowCentroids);
        Point yellowCenter = new Point((int) yellowX, (int) yellowY);

        c = new Color(img.getRGB((int) blueCentroidA.getX(), (int) blueCentroidA.getY()));
        if (isBlue(c)) {
            totalBlueX += blueCentroidA.getX();
            totalBlueY += blueCentroidA.getY();
            numBlueCentroids++;
        }
        c = new Color(img.getRGB((int) blueCentroidB.getX(), (int) blueCentroidB.getY()));
        if (isBlue(c)) {
            totalBlueX += blueCentroidB.getX();
            totalBlueY += blueCentroidB.getY();
            numBlueCentroids++;
        }
        c = new Color(img.getRGB((int) blueCentroidC.getX(), (int) blueCentroidC.getY()));
        if (isBlue(c)) {
            totalBlueX += blueCentroidC.getX();
            totalBlueY += blueCentroidC.getY();
            numBlueCentroids++;
        }
        c = new Color(img.getRGB((int) blueCentroidD.getX(), (int) blueCentroidD.getY()));
        if (isBlue(c)) {
            totalBlueX += blueCentroidD.getX();
            totalBlueY += blueCentroidD.getY();
            numBlueCentroids++;
        }
        c = new Color(img.getRGB((int) blueCentroidE.getX(), (int) blueCentroidE.getY()));
        if (isBlue(c)) {
            totalBlueX += blueCentroidE.getX();
            totalBlueY += blueCentroidE.getY();
            numBlueCentroids++;
        }

        if (numBlueCentroids == 0) {
            numBlueCentroids++;
        }

        float blueX = (float) (totalBlueX / numBlueCentroids);
        float blueY = (float) (totalBlueY / numBlueCentroids);
        Point blueCenter = new Point((int) blueX, (int) blueY);

        blueGreenPlate4Points = VisionTools.getCorners(blueGreenPlate);
        yellowGreenPlate4Points = VisionTools.getCorners(yellowGreenPlate);

        Robot blueRobot = new Robot(blueCenter, 0.0, blueGreenPlate4Points);
        Robot yellowRobot = new Robot(yellowCenter, 0.0, yellowGreenPlate4Points);
        Point ballCoords = new Point((int) redX, (int) redY);

        WorldState worldState = new WorldState(ballCoords, blueRobot, yellowRobot, img);

        return worldState;

    }

    /**
     * Check if a pixel is blue robot blue
     *
     * @param c Colour to check
     * @return True if blue
     */

    public boolean isBlue (Color c) {

        return ((c.getRed() <= blue_r) && (c.getBlue() > blue_b) && (c.getGreen() <= blue_g));
    }

    /**
     * Check if a pixel is ball red
     *
     * @param c  - Colour to check
     * @param GB - Difference between green and blue channel
     * @return True if red
     */
    public boolean isRed (Color c, int GB) {

        return ((c.getRed() > ball_r) && (c.getBlue() <= ball_b) && (c.getGreen() <= ball_g) && GB < 60);
    }

    /**
     * Check if part of green plate
     *
     * @param c  Colour to check
     * @param GB Difference in green and blue channel
     * @param RG Difference in red and green channel
     * @return True if green
     */
    public boolean isGreen (Color c, int GB, int RG) {

        return (GB > green_GB && RG > green_RG && c.getGreen() > green_g);


    }

    /**
     * Check if pixel is yellow robot
     *
     * @param c Colour to check
     * @return True if part of yellow T
     */
    public boolean isYellow (Color c) {

        System.out.println(c.toString());
        return ((c.getRed() >= yellow_r_low) && (c.getRed() <= yellow_r_high) && (c.getGreen() >= yellow_g_low) && (c.getGreen() <= yellow_g_high) && (c.getBlue() >= yellow_b_low) && (c.getBlue() <= yellow_b_high));
    }

    /**
     * Does the connected component stuff
     *
     * Use a bastardised 8 connected component method, is supposed to have clustering
     * tacked on to the end, but I just use it for reducing noise.  Currently it is this
     * method that makes the yellow T zigzaggy.  It checks a points surrounding pixels to
     * work out if it is noise or not.
     *
     * @param x - x coordinate of point
     * @param y - y coordinate of point
     * @param right To check if we go out of bounds of pitch
     * @param left To check if we go out of bounds of pitch
     * @param top To check if we go out of bounds of pitch
     * @param bottom To check if we go out of bounds of pitch
     * @param img To get the colour of the neighbouring pixels
     */

    public void setCs(int x, int y, int right, int left, int top, int bottom, BufferedImage img){
        if (x + 1 < right){
            cE = new Color(img.getRGB(x+1,y));
        }else {
            cE = c;
        }
        if (y + 1 < bottom){
            cS = new Color(img.getRGB(x,y+1));
        }else {
            cS = c;
        }
        if ((x + 1 < right) && (y - 1 > top)){
            cEN = new Color(img.getRGB(x+1,y-1));
        }else {
            cEN = c;
        }
        if ((x + 2 < right)){
            cEE = new Color(img.getRGB(x+2,y));
        }else {
            cEE = c;
        }
        if ((y + 2 < bottom)){
            cSS = new Color(img.getRGB(x,y+2));
        }else {
            cSS = c;
        }
        if ((x - 1 > left) && (y + 1 < bottom)){
            cSW = new Color(img.getRGB(x-1,y+1));
        }else {
            cSW = c;
        }
    }

    public ArrayList<Point> getBluePixels() {

        return bluePixels;
    }

    public ArrayList<Point> getYellowPixels() {

        return yellowPixels;
    }
}
