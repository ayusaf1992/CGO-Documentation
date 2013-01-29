import lejos.nxt.Motor;

public class localKick {

	private static boolean isKicking = false;
	
	static void main(String[] args) {

	final Motor KICKER = Motor.A;


	KICKER.smoothAcceleration(false);
        KICKER.regulateSpeed(false);

	if (isKicking) {
            return;
        }

        isKicking = true;

	KICKER.forward();
	KICKER.setSpeed(900);
        KICKER.resetTachoCount();	
	//KICKER.rotateTo(0);        
	try {
		Thread.sleep(150);
	} catch (Exception e) {}

        KICKER.backward();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    //
                }                
		KICKER.rotateTo(0);
                isKicking = false;
            }
        }).start();
    }
}
