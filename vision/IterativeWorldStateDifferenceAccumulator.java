package sdp.vision.vision;

import sdp.vision.vision.common.WorldState;

import java.util.ArrayList;

public class IterativeWorldStateDifferenceAccumulator {

    int ballerror, blueerror, yellowerror;

    //ArrayLists used to record error magnitude on a per-iteration basis;
    public ArrayList<Integer> balllist, bluelist, yellowlist;

    //The constructor with no errors initially
    public IterativeWorldStateDifferenceAccumulator () {

        ballerror = 0;
        balllist = new ArrayList<Integer>();
        blueerror = 0;
        bluelist = new ArrayList<Integer>();
        yellowerror = 0;
        yellowlist = new ArrayList<Integer>();
    }

    //Each iteration, two WorldStates are passed to the class and their respective errors are incremented to the class variables.
    public void iteration (WorldState manual, WorldState vision) {

        //If an object is not detected it is given negative coordinates and therefore should have the maximum error.
        Integer error;
        if (vision.getBallCoords().x > 0 && vision.getBallCoords().y > 0) {
            error = (int) Math.sqrt(Math.pow(manual.getBallCoords().x - vision.getBallCoords().x, 2) + Math.pow(manual.getBallCoords().y - vision.getBallCoords().y, 2));
        } else {
            error = 640;
        }

        //error added for the ball
        ballerror += error;
        balllist.add(error);

        if (vision.getBlueRobot().getCoords().x > 0 && vision.getBlueRobot().getCoords().y > 0) {
            error = (int) Math.sqrt(Math.pow(manual.getBlueRobot().getCoords().x - vision.getBlueRobot().getCoords().x, 2) + Math.pow(manual.getBlueRobot().getCoords().y - vision.getBlueRobot().getCoords().y, 2));
        } else {
            error = 640;
        }

        //error added for the blue robot
        blueerror += error;
        bluelist.add(error);

        if (vision.getYellowRobot().getCoords().x > 0 && vision.getYellowRobot().getCoords().y > 0) {
            error = (int) Math.sqrt(Math.pow(manual.getYellowRobot().getCoords().x - vision.getYellowRobot().getCoords().x, 2) + Math.pow(manual.getYellowRobot().getCoords().y - vision.getYellowRobot().getCoords().y, 2));
        } else {
            error = 640;
        }

        //error added for the yellow robot
        yellowerror += error;
        yellowlist.add(error);


    }

    public int averageBallError (int iterations) {

        return ballerror / iterations;
    }

    public int averageBlueError (int iterations) {

        return blueerror / iterations;
    }

    public int averageYellowError (int iterations) {

        return yellowerror / iterations;
    }
}
