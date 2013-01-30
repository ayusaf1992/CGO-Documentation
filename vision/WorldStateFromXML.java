package sdp.vision.vision;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sdp.vision.vision.common.WorldState;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: s1043077
 * Date: 29/01/13
 * Time: 15:18
 * Refactored Test.java
 * This class contains methods for retrieving the WorldState from XML.
 */
public class WorldStateFromXML {
    //gets the document from the xml file
    private static Document getDocumentFromXML (String filename) {
        //Something about factories.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document dom = null;
        try {
            //getting  an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            //parse using builder to get  document representation of the XML file
            dom = db.parse(filename);
            System.out.println("Loaded XML file.");
            //In the unlikely event that something breaks
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return dom;
    }

    //return an array list with the elements required from the xml document
    private static ArrayList<WorldState> getWorldStateFromDocument (Document dom) {
        //An ArrayList of WordStates is created and then states can be added as they are parsed
        ArrayList<WorldState> states = new ArrayList<WorldState>();
        //get the root element from the document
        Element annotations = dom.getDocumentElement();
        //get a nodelist of  elements
        NodeList nl = annotations.getElementsByTagName("image");
        //if there are elements then iterate through them to extract each state
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                //get the individual state data node
                Element ws = (Element) nl.item(i);
                //Element passed to method for parsing individual elements
                WorldState annotated = TestValues.getWorldStateFromElement(ws);
                //parsed state added to ArrayList
                states.add(annotated);
            }
        }
        //Debug
        System.out.print(states.size());
        System.out.println(" world states extracted from XML file.");
        //ArrayList of parsed states returned to whatever called method
        return states;
    }

    public static ArrayList<WorldState> getWorldStateFromXML (String filename){
        return getWorldStateFromDocument(getDocumentFromXML(filename));
    }
}
