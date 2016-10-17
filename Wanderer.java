import lejos.hardware.motor.Motor;
import lejos.robotics.subsumption.Behavior;


public class Wanderer implements Behavior {
	private UltraSonicSensor m_pLeftSensor = null;
	private UltraSonicSensor m_pRightSensor = null;
	private boolean m_bSuppressed = false; 
	private float m_fTurnDistance = 0.0f;
	private float m_fClearDistance = 0.0f;
	private boolean m_bInControl = false;
	private float m_fSpeedModifier = 0.0f;

public Wanderer(float a_fTurnDistance, float a_fClearDistance, UltraSonicSensor a_pLeftSensor, UltraSonicSensor a_pRightSensor, float a_fSpeedModifier) { 
	//Set variables
	m_fTurnDistance = a_fTurnDistance;
	m_fClearDistance = a_fClearDistance;
	
	//Save a reference to the sensor
	m_pLeftSensor = a_pLeftSensor;
	m_pRightSensor = a_pRightSensor;
	m_fSpeedModifier = a_fSpeedModifier;
}

@Override
public boolean takeControl() {
	//Detect objects	
	if (!m_bInControl && (m_pLeftSensor.DetectSample() < m_fClearDistance || m_pRightSensor.DetectSample() < m_fClearDistance)) {
		//Log
		System.out.println("Object detected, initializing Turn!");
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
	m_bSuppressed = false;
	m_bInControl = true;
	
	//Continue driving until no longer feared or suppressed
	while( !m_bSuppressed) {
		float Left = m_pLeftSensor.DetectSample();
		float Right = m_pRightSensor.DetectSample();
		
		//Turn
		if(Left < m_fTurnDistance && Right < m_fTurnDistance) {
			Motor.D.rotateTo(360, true);
			Motor.A.rotateTo(-360, true);
			
			while(Motor.A.isMoving() || Motor.D.isMoving()) {
				Thread.yield();
			}
		} else if (Left < m_fClearDistance){
			Motor.D.setSpeed(Left * m_fSpeedModifier);
			Motor.D.backward();
			Motor.A.stop();
		} else if (Right < m_fClearDistance){
			Motor.A.setSpeed(Right * m_fSpeedModifier);
			Motor.A.backward();
			Motor.D.stop();
		} 
	 }
	 
	 //Clean up
	m_bSuppressed = false;
	m_bInControl = false;
	Motor.A.stop(true); 
	Motor.D.stop(false);
}

}
