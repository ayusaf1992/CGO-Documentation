package sdp.vision.vision.examples;

import au.edu.jcu.v4l4j.V4L4JConstants;
import sdp.vision.vision.Vision;
import sdp.vision.vision.common.WorldState;
import sdp.vision.vision.common.WorldStateObserver;
import sdp.vision.vision.visualinput.CameraVisualInputProvider;
import sdp.vision.vision.visualinput.ImageVisualInputProvider;
import sdp.vision.vision.visualinput.VisualInputProvider;


/**
 * This class is an example of how to connect to the vision system and
 * receive world state updates from it in asynchronous fashion.
 * <p/>
 * As you can see, the whole setup takes X steps:
 * <p/>
 * 1) Create a Vision instance.
 * 2) Create a VisualInputProvider instance.
 * 3) Set visual input provider's callback.
 * 4) Create a WorldStateObserver.
 * 5) Start visual input capture.
 * 6) Use WorldStateObserver object to access the most recent world state.
 * <p/>
 * Note that a WorldStateObserver objects are asynchronous: they will return
 * only the most recent frame and will block if frames are consumed too fast.
 * It is also the only thing that other parts of the system need to be aware of
 * in order to interface with the vision system.
 *
 * @author Gediminas Liktaras
 */
public class VisionUsageExample implements Runnable {

    /**
     * Whether to use camera or offline inputs.
     */
    private static final boolean USE_CAMERA = false;
    /**
     * The vision subsystem object.
     */
    private Vision vision;
    /**
     * Visual input source.
     */
    private VisualInputProvider input;
    /**
     * Vision sybsystem observer.
     */
    private WorldStateObserver visionObserver;


    /**
     * The main constructor.
     */
    public VisionUsageExample () {
        // Create the new vision world state provider.
        vision = new Vision();

        // Create visual input provider.
        if (USE_CAMERA) {
            input = new CameraVisualInputProvider("/dev/video0", V4L4JConstants.STANDARD_WEBCAM, 0);
        } else {
            String filenames[] = {"data/testImages/pitch2-1.png",
                    "data/testImages/pitch2-2.png",
                    "data/testImages/pitch2-3.png"};
            input = new ImageVisualInputProvider(filenames, 25);
        }

        // Set visual input provider callback.
        // Do NOT set the vision object as a callback for multiple visual
        // input providers.
        input.setCallback(vision);

        // Create the world state observer, connect it to the world state provider.
        visionObserver = new WorldStateObserver(vision);
    }

    /**
     * The main method.
     *
     * @param args Command line arguments.
     */
    public static void main (String[] args) {

        VisionUsageExample example = new VisionUsageExample();
        (new Thread(example, "Vision example")).start();
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run () {
        // Start the vision thread.
        input.startCapture();

        while(!Thread.currentThread().isInterrupted()) {
            if (USE_CAMERA) {
                while (! Thread.interrupted()) {
                    // Get the next world state.
                    WorldState state = visionObserver.getNextState();

                    // Do something with the world state.
                    System.out.println("NEW STATE: " +
                            "Ball at (" + state.getBallCoords().x +
                            ", " + state.getBallCoords().y + "), " +
                            "Blue at (" + state.getBlueRobot().getCoords().x +
                            ", " + state.getBlueRobot().getCoords().y +
                            ", " + state.getBlueRobot().getAngle() + "), " +
                            "Yellow at (" + state.getYellowRobot().getCoords().x +
                            ", " + state.getYellowRobot().getCoords().y +
                            ", " + state.getYellowRobot().getAngle() + ").");
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    WorldState state = visionObserver.getNextState();

                    // Do something with the world state.
                    System.out.println("NEW STATE: " +
                            "Ball at (" + state.getBallCoords().x +
                            ", " + state.getBallCoords().y + "), " +
                            "Blue at (" + state.getBlueRobot().getCoords().x +
                            ", " + state.getBlueRobot().getCoords().y +
                            ", " + state.getBlueRobot().getAngle() + "), " +
                            "Yellow at (" + state.getYellowRobot().getCoords().x +
                            ", " + state.getYellowRobot().getCoords().y +
                            ", " + state.getYellowRobot().getAngle() + ").");
                }

                Thread.currentThread().interrupt();
            }
        }
    }

}
