import lejos.hardware.motor.*;
import lejos.robotics.subsumption.*;

public class DriveForward  implements Behavior {
   private boolean suppressed = false;
   
   public boolean takeControl() {
      return true;
   }

   public void suppress() {
      suppressed = true;
   }

   public void action() {
	 System.out.println("Starting forward");
     suppressed = false;
     Motor.A.setSpeed(180);
	 Motor.D.setSpeed(180);
     Motor.A.backward();
     Motor.D.backward();
     while( !suppressed )
        Thread.yield();
     //Clean up
     Motor.A.stop(); 
     Motor.D.stop();
   }
}
