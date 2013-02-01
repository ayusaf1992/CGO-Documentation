
//import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.remote.RemoteMotor;
import static java.lang.System.out;

public class kickTest {  

   public static void main(String[] args) {

        BrickController controller = new BrickController();

	controller.kick();
	controller.stop();

	}

}
