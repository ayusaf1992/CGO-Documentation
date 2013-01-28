package sdp.vision.vision;

import sdp.vision.vision.common.WorldState;
import sdp.vision.vision.common.WorldStateProvider;
import sdp.vision.vision.processing.NewImageProcessor;
import sdp.vision.vision.visualinput.VisualInputCallback;

import java.awt.image.BufferedImage;

/**
 * The main vision subsystem class.
 * <p/>
 * For the love of FSM, do not set an instance of this class as a callback to
 * multiple VisualInputProviders.
 *
 * @author Gediminas Liktaras
 */
public class Vision extends WorldStateProvider implements VisualInputCallback {

    /**
     * Image processor.
     */
    NewImageProcessor imageProcessor;


    /**
     * The main constructor.
     */
    public Vision () {

        imageProcessor = new NewImageProcessor();
    }

    public WorldState quietNextFrame (BufferedImage frame) {

        WorldState nextState = imageProcessor.process(frame, this.getCurrentState());
        return nextState;

    }

    /* (non-Javadoc)
    * @see sdp.vision.VisualInputCallback#nextFrame(java.awt.image.BufferedImage)
    */
    @Override
    public void nextFrame (BufferedImage frame) {

        WorldState nextState = imageProcessor.process(frame, this.getCurrentState());
        setChanged();
        notifyObservers(nextState);
    }

}