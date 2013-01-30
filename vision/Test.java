package sdp.vision.vision;

import sdp.vision.vision.common.WorldState;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

// Refactored to only include methods which carry out actual tests.

public class Test extends Vision {

    //Configuration used to convert between relative coordinates and Pixel-Range coordinates
    static ArrayList<String> filelist = new ArrayList<String>();

    public static void printMarginsOfError (IterativeWorldStateDifferenceAccumulator difference, ArrayList<WorldState> annotations) {
        //Error details are generated
        String ballerror = "Average ball error is " + ((int) difference.averageBallError(annotations.size())) + " pixels.";
        String blueerror = "Average blue robot error is " + ((int) difference.averageBlueError(annotations.size())) + " pixels.";
        String yellowerror = "Average yellow robot error is " + ((int) difference.averageYellowError(annotations.size())) + " pixels.";

        //And output to the terminal
        System.out.println(ballerror);
        System.out.println(blueerror);
        System.out.println(yellowerror);

        //Also written to a file that can be pushed to the repository for metric tracking over time.
        try {
            FileWriter fw = new FileWriter("metrics.txt");
            BufferedWriter out = new BufferedWriter(fw);

            //Number of pixels difference to ignore
            int tolerance = 20;
            out.append("Error tolerance is set to " + tolerance + " pixels.\n\n");
            //Filenames in order so that the cause of high error rates can be investigated
            out.append("Files used in tests:\n");
            int index = 0;
            for (String file : filelist) {
                index++;
                out.append(index + ": ");
                out.append(file + "\n");
            }
            out.append("\n");
            out.append(ballerror + "\n");
            index = 0;
            for (int error : difference.balllist) {
                index++;
                out.append(index + ": ");
                if (error > tolerance) {
                    out.append("[ABOVE TOLERANCE] ");
                }
                out.append(error + "\n");
            }
            out.append("\n");

            out.append(blueerror + "\n");
            index = 0;
            for (int error : difference.bluelist) {
                index++;
                out.append(index + ": ");
                if (error > tolerance) {
                    out.append("[ABOVE TOLERANCE] ");
                }
                out.append(error + "\n");
            }
            out.append("\n");

            out.append(yellowerror + "\n");
            index = 0;
            for (int error : difference.yellowlist) {
                index++;
                out.append(index + ": ");
                if (error > tolerance) {
                    out.append("[ABOVE TOLERANCE] ");
                }
                out.append(error + "\n");
            }
            out.append("\n");

            //File closed and write finalised
            out.close();
            System.out.println("Metrics written to metrics.txt");
        } catch (Exception e) {

        }
    }

    public static void main (String[] args) throws InterruptedException {

        //Test constructor for methods
        Test test = new Test();

        //delay in ms between slides being shown.
        int delay = 0;

        //if visual output should be shown when iterating
        boolean visoutput = true;

        //The xml file (currently hard coded location) is parsed by the above voodoo and stored in an ArrayList<WorldState>

        // Removed this during refactoring - is the refactoring ok?
        //ArrayList<WorldState> annotations = getWorldStateFromDocument(getDocumentFromXML("vision/imagedata.xml"));
        ArrayList<WorldState> annotations = WorldStateFromXML.getWorldStateFromXML("vision/imagedata.xml");

        //Init display if showing output
        JFrame frame = null;
        Viewer base = new Viewer(null, null, null);
        if (visoutput) {
            frame = new JFrame("Image Display");
            frame.setSize(640, 480);
            frame.getContentPane().add(base);
            frame.setVisible(true);
        }

        //Class that accumulates the difference between WorldStates is initialised.
        IterativeWorldStateDifferenceAccumulator difference = new IterativeWorldStateDifferenceAccumulator();

        //For each state documented in the XML
        for (WorldState state : annotations) {

            //Copy of the manual image is made so that the original is not overwritten.
            sdp.vision.vision.common.Utilities utility = new sdp.vision.vision.common.Utilities();
            BufferedImage manualimage = utility.deepBufferedImageCopy(state.getWorldImage());

            //The comparative WorldState object that the vision system will construct.
            WorldState visionimage;

            //The vision system is passed the image from the annotation and generates
            //a WorldState to be compared with the human perception
            visionimage = test.quietNextFrame(utility.deepBufferedImageCopy(state.getWorldImage()));

            //Update visual output if showing
            if (visoutput) {
                base.updateImageAndState(manualimage, state, visionimage);
            }

            //Sleep if delay is configured
            Thread.sleep(delay);

            //Differences between the WorldStates are calculated
            difference.iteration(state, visionimage);
        }

        //Closes display if was open
        if (visoutput) {
            frame.setVisible(false);
        }

        //Average errors over iterations are printed
        printMarginsOfError(difference, annotations);
    }
}
