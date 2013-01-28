import lejos.nxt.Motor;

public class localKick {

	public final Motor KICKER = Motor.A;

	private volatile boolean isKicking = false;
	
	public void kickControl() {

	KICKER.smoothAcceleration(false);
        KICKER.regulateSpeed(false);

	if (isKicking) {
            return;
        }

        isKicking = true;

	KICKER.forward();	
	//KICKER.rotateTo(0);        
	try {
		Thread.sleep(80);
	} catch (Exception e) {}
	KICKER.setSpeed(900);
        KICKER.resetTachoCount();
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
