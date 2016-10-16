import lejos.hardware.motor.Motor;
import lejos.robotics.subsumption.Behavior;


public class Turn implements Behavior {
	private UltraSonicSensor m_pLeftSensor = null;
	private UltraSonicSensor m_pRightSensor = null;
	private boolean m_bSuppressed = false; 
	private float m_fTurnDistance = 0.0f;
	private float m_fClearDistance = 0.0f;
	private boolean m_bInControl = false;

public Turn(float a_fTurnDistance, float a_fClearDistance, UltraSonicSensor a_pLeftSensor, UltraSonicSensor a_pRightSensor) { 
	//Set variables
	m_fTurnDistance = a_fTurnDistance;
	m_fClearDistance = a_fClearDistance;
	
	//Save a reference to the sensor
	m_pLeftSensor = a_pLeftSensor;
	m_pRightSensor = a_pRightSensor;
}

@Override
public boolean takeControl() {
	//Detect objects	
	if (!m_bInControl && (m_pLeftSensor.DetectSample() < m_fTurnDistance || m_pRightSensor.DetectSample() < m_fTurnDistance)) {
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
		if(Left < m_fTurnDistance || Right < m_fTurnDistance) {
			Motor.D.setSpeed(180);
			Motor.D.backward();
		} 
	 }
	 
	 //Clean up
	m_bSuppressed = false;
	m_bInControl = false;
	Motor.A.stop(true); 
	Motor.D.stop(false);
}

}
