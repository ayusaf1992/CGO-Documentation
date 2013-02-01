package sdp.vision.vision;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import sdp.vision.vision.common.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: s1043077
 * Date: 29/01/13
 * Time: 14:29
 * Refactored Test.java
 * This class contains the base "get" functions, which give the values used in the tests.
 */
public class TestValues extends Vision {

    //Configuration used to convert between relative coordinates and Pixel-Range coordinates
    static ArrayList<String> filelist = new ArrayList<String>();

    //returns the text value contained within an XML element
    private static String getTextValue (Element ele, String tagName) {

        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }
        //Pretty printed linebreaks within the XML broke EVERYTHING but they are gone now
        return textVal.replace("\n", "");
    }

    //return the int value contained within an XML element after first reading it's plaintext.
    private static int getIntValue (Element ele, String tagName) {
        //in production application you would catch the exception
        return Integer.parseInt(getTextValue(ele, tagName));
    }

    //return the int value contained within an XML element after first reading it's plaintext.
    private static float getFloatValue (Element ele, String tagName) {
        //in production application you would catch the exception
        return Float.parseFloat(getTextValue(ele, tagName));
    }

    //returns a WorldState object with the data from one element of the xml file
    protected static WorldState getWorldStateFromElement (Element ws) {

        WorldState state;
        //SLIGHT XML NAVIGATION
        //image -> filename
        String filename = getTextValue(ws, "filename");
        filelist.add(filename);


        //attempting to load image from file
        BufferedImage image = null;
        try {
            //filename referenced in XML is loaded as a BufferedImage
            image = ImageIO.read(new File(filename));
        } catch (Exception e) {
            //This flags up when you FORGET TO REMOVE LEADING NEWLINES FROM FILENAMES
            System.out.print(e);
            System.out.printf("Error loading image from XML: %s\n", filename);
        }

        //XML NAVIGATION GOING DEEPER

        // image -> location-data
        Element data = (Element) ws.getElementsByTagName("location-data").item(0);

        // location-data -> ball
        Element balldata = (Element) data.getElementsByTagName("ball").item(0);

        // ball -> x and ball -> y. Passed into a Point2D.Double ready to be passed to WorldState
        Point ballpos = new Point(getIntValue(balldata, "x"), getIntValue(balldata, "y"));

        // location-data -> bluerobot
        Element bluerobotdata = (Element) data.getElementsByTagName("bluerobot").item(0);

        //defining a blue robot object and passing it bluerobot -> x, bluerobot -> y and bluerobot -> angle
        sdp.vision.vision.common.Robot bluerobot = new sdp.vision.vision.common.Robot(new Point(getIntValue(bluerobotdata, "x"), getIntValue(bluerobotdata, "y")), (double) getFloatValue(bluerobotdata, "angle"), null);

        // location-data -> yellowrobot
        Element yellowrobotdata = (Element) data.getElementsByTagName("yellowrobot").item(0);

        //defining a yellow robot object and passing it yellowrobot -> x, yellowrobot -> y and yellowrobot -> angle
        sdp.vision.vision.common.Robot yellowrobot = new sdp.vision.vision.common.Robot(new Point(getIntValue(yellowrobotdata, "x"), getIntValue(yellowrobotdata, "y")), (double) getFloatValue(yellowrobotdata, "angle"), null);

        //WorldState object is created with parsed data passed to the constructor.

        state = new WorldState(ballpos, bluerobot, yellowrobot, image);

        //WorldState object is returned to whatever called this method.
        return state;
    }
}
