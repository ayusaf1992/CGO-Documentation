
//import lejos.nxt.LCD;
import lejos.nxt.remote.NXTCommand;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.remote.RemoteMotor;
import static java.lang.System.out;

public class RollThroughField {

	private static final int ROLL_SPEED = 300;
	private static final float ROLL_DISTANCE = 1f;

    /* private static void drawMessage(String message) {
        LCD.clear();
        LCD.drawString(message, 0, 0);
        LCD.refresh();
    } */

    public static void main(String[] args) {

        BrickController controller = new BrickController();
        //TouchSensor sensorLeft = new TouchSensor(SensorPort.S1);
        //TouchSensor sensorRight = new TouchSensor(SensorPort.S2);

        boolean movingForward = false;
        boolean firstTime = true;
        int speed = ROLL_SPEED;
        float rollDistance = ROLL_DISTANCE;
        boolean hasStoppedOnce = false;
	

	while (true) {
            /*if (sensorLeft.isPressed() || sensorRight.isPressed()) {
                //drawMessage("Whoops, wall!");
                controller.stop();
                controller.setWheelSpeeds(-controller.getMaximumWheelSpeed(),
                        -controller.getMaximumWheelSpeed());
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    //drawMessage(";/");
                    break;
                }
                controller.rotate(180, 120);
                break;
            } */
            if (!movingForward) {
                movingForward = true;
                controller.reset();
                controller.setWheelSpeeds(speed,speed);
                try {
                	Thread.sleep(200);
                } catch (Exception e) {
                	
                }
            } else {
            	if(firstTime) {
            		speed = 900;
            		controller.setWheelSpeeds(speed,speed);
            		firstTime = false;
            	}
            	float distance = controller.getTravelDistance();
            	System.out.println("went "+distance+"m");
                if (distance > rollDistance) {
                	controller.stop();
                	if (!hasStoppedOnce) {
                		//controller.rotate(180, 120);
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
            	Thread.sleep(80);
            } catch (Exception e) {
            	
            }

        }

    }
}
