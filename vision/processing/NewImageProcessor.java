package sdp.vision.vision.processing;

import sdp.vision.vision.common.WorldState;

import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: s0954240
 * Date: 26/01/13
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
public class NewImageProcessor {

    Preprocessing preprocessor = new Preprocessing();
    Locator locator = new Locator();
    Postprocessing postprocessor = new Postprocessing();

    public WorldState process(BufferedImage image, WorldState previousState) {

        BufferedImage newImage = preprocessor.removeBarrelDistortion(image, 0, 640, 0, 480);
        WorldState nextState = locator.locate(previousState, newImage, 0, 640, 0, 480);
        nextState = postprocessor.postProcess(nextState, newImage, locator.getBluePixels(), locator.getYellowPixels());

        if (previousState == null) {

            BufferedImage secondNewImage = preprocessor.removeBarrelDistortion(image, 0, 640, 0, 480);
            nextState = locator.locate(nextState, newImage, 0, 640, 0, 480);
            nextState = postprocessor.postProcess(nextState, secondNewImage, locator.getBluePixels(), locator.getYellowPixels());

        }

        return nextState;
    }
}
