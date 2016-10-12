import lejos.hardware.motor.Motor;
import lejos.robotics.subsumption.Behavior;


public class Aggro implements Behavior {
	private UltraSonicSensor m_pLeftSensor = null;
	private UltraSonicSensor m_pRightSensor = null;
	private boolean m_bSuppressed = false; 
	private float m_fFearDistance = 0.0f;
	private float m_fClearDistance = 0.0f;
	private boolean m_bInControl = false;

public Aggro(float a_fFearDistance, float a_fClearDistance, UltraSonicSensor a_pLeftSensor, UltraSonicSensor a_pRightSensor) { 
	//Set variables
	m_fFearDistance = a_fFearDistance;
	m_fClearDistance = a_fClearDistance;
	
	//Save a reference to the sensor
	m_pLeftSensor = a_pLeftSensor;
	m_pRightSensor = a_pRightSensor;
}

@Override
public boolean takeControl() {
	//Detect objects	
	if (!m_bInControl && (m_pLeftSensor.DetectSample() < m_fFearDistance || m_pRightSensor.DetectSample() < m_fFearDistance)) {
		//Log
		System.out.println("Object detected, initializing Aggro!");
		return true;
	} 
	//Otherwise
	return false;
}

@Override
public void suppress() {
	m_bSuppressed = true;
}

//if it detects Object on the right it turns right, vice versa with left, if object in center both motors start
@Override
public void action() {
	//Fear started
	boolean DriveLeft = false;
	boolean DriveRight = false;
	m_bSuppressed = false;
	m_bInControl = true;
	boolean FirstGo = true;
	
	//Continue driving until no longer feared or suppressed
	while( !m_bSuppressed && (DriveLeft || DriveRight || FirstGo)) {
		//Right motor (A)
		float Left = m_pLeftSensor.DetectSample();
		float Right = m_pRightSensor.DetectSample();
		FirstGo = false;
		
		//Check left side
		if(!DriveLeft && Left < m_fFearDistance) {
			Motor.A.setSpeed(180);
			Motor.A.backward();
			DriveLeft = true;
		} else if (DriveLeft && Left > m_fClearDistance) {
			Motor.A.stop();
			DriveLeft = false;
		}
		
		//Check right side
		if(!DriveRight && Right < m_fFearDistance) {
			Motor.D.setSpeed(180);
			Motor.D.backward();
			DriveRight = true;
		} else if (DriveRight && Right > m_fClearDistance) {
			Motor.D.stop();
			DriveRight = false;
		}
	 }
	 
	 //Clean up
	m_bSuppressed = false;
	m_bInControl = false;
	Motor.A.stop(true); 
	Motor.D.stop(false);
}

}
