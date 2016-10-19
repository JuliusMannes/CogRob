import lejos.hardware.motor.*;
import lejos.robotics.subsumption.*;

public class DriveForward  implements Behavior {
	//Variables
   private boolean m_bSuppressed = false;
   
   public boolean takeControl() {
      return true;
   }

   public void suppress() {
	 m_bSuppressed = true;
   }

   public void action() {
	 //Log
	 System.out.println("Starting forward");
	 
	 //Set motor speed and direction
     Motor.A.setSpeed(270);
	 Motor.D.setSpeed(270);
     Motor.A.backward();
     Motor.D.backward();
     
     //Loop while not suppressed
     while( !m_bSuppressed )
        Thread.yield();
     
     //Stop the motors
     m_bSuppressed = false;
     Motor.A.stop(true); 
     Motor.D.stop(false);
   }
}
