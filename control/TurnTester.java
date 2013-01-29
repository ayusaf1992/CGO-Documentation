
//import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.remote.RemoteMotor;
import static java.lang.System.out;

public class TurnTester {

	private static final int ROLL_SPEED = 500;
	private static final float ROLL_DISTANCE = 0.4f;


    public static void main(String[] args) {

        BrickController controller = new BrickController();

        boolean movingForward = false;
        int speed = ROLL_SPEED;
        float rollDistance = ROLL_DISTANCE;
        boolean hasStoppedOnce = false;
	

	while (true) {
            if (!movingForward) {
                movingForward = true;
                controller.reset();
                controller.arc(-100f);
                
            } else {
            	float distance = controller.getTravelDistance();
            	System.out.println("went "+distance+"m");
                if (distance > rollDistance) {
                	controller.stop();
                	if (!hasStoppedOnce) {
                		controller.rotate(180, 120);
                		controller.reset();
                        //controller.forward(speed);
                		controller.stop();			
                        hasStoppedOnce = true;
                	}
                	break;
                } else {
                	System.out.println("My current speed is: "+speed+" of "+controller.getMoveMaxSpeed());
                	//speed += 100;
                	//controller.setWheelSpeeds(speed,speed);
                }

            }
            try {
            	Thread.sleep(300);
            } catch (Exception e) {
            	
            }

        }

    }
}
