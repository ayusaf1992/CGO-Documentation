package sdp.vision.vision;

import java.awt.image.BufferedImage;

import sdp.vision.vision.common.WorldState;
import sdp.vision.vision.common.WorldStateProvider;
import sdp.vision.vision.processing.OldImageProcessor;
import sdp.vision.vision.visualinput.VisualInputCallback;

/**
 * The main vision subsystem class.
 *
 * For the love of FSM, do not set an instance of this class as a callback to
 * multiple VisualInputProviders.
 *
 * @author Gediminas Liktaras
 */
public class Vision extends WorldStateProvider implements VisualInputCallback {

    /** Image processor. */
    OldImageProcessor imageProcessor;


    /**
     * The main constructor.
     */
    public Vision() {
        imageProcessor = new OldImageProcessor();
    }


    public WorldState worldImageData(BufferedImage frame){
        imageProcessor.process(frame);
        WorldState nextState = imageProcessor.worldState;
        return nextState;

    }

    /* (non-Javadoc)
    * @see sdp.vision.VisualInputCallback#nextFrame(java.awt.image.BufferedImage)
    */
    @Override
    public void nextFrame(BufferedImage frame) {
        imageProcessor.process(frame);
        WorldState nextState = imageProcessor.worldState;
        setChanged();
        notifyObservers(nextState);
    }

}