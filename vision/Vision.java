package sdp.vision.vision;

import sdp.vision.vision.processing.OldImageProcessor;
import sdp.vision.vision.common.WorldState;
import sdp.vision.vision.common.WorldStateProvider;
import sdp.vision.vision.visualinput.VisualInputCallback;

import java.awt.image.BufferedImage;


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
	

	/* (non-Javadoc)
	 * @see sdp.vision.VisualInputCallback#nextFrame(java.awt.image.BufferedImage)
	 */
	@Override
	public void nextFrame(BufferedImage frame) {
		imageProcessor.process(frame);
		WorldState state = imageProcessor.worldState;
		
		setChanged();
		notifyObservers(state);
	}
	
}