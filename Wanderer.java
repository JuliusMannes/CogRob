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
	return true;/*
	if (!m_bInControl && (m_pLeftSensor.DetectSample() < m_fClearDistance || m_pRightSensor.DetectSample() < m_fClearDistance)) {
		//Log
		System.out.println("Object detected, initializing Turn!");
		return true;
	} 
	//Otherwise
	return false;*/
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
	//boolean FirstGo = true;
	
	//Continue driving until no longer feared or suppressed
	while( !m_bSuppressed && m_bInControl) {
		//Right motor (A)
		float Left = m_pLeftSensor.DetectSample();
		float Right = m_pRightSensor.DetectSample();
		
		//Right
		if(Right < m_fClearDistance) {
			Motor.A.setSpeed(Math.max(0.0f,(Right-m_fTurnDistance) * m_fSpeedModifier));
			Motor.A.backward();
		} else {
			Motor.A.setSpeed(270);
			Motor.A.backward();
		}
		
		//Left
		if(Left < m_fClearDistance) {
			Motor.D.setSpeed(Math.max(0.0f,(Left-m_fTurnDistance) * m_fSpeedModifier));
			Motor.D.backward();
		} else {
			Motor.D.setSpeed(270);
			Motor.D.backward();
		}
		
		//Turn around on stuck
		if (Left < m_fTurnDistance && Right < m_fTurnDistance) {
			System.out.println("TURNING");
			
			Motor.D.setSpeed(270);
			Motor.D.backward();
			Motor.A.setSpeed(270);
			Motor.A.forward();
			//Motor.A.rotateTo(-360, false);
			
			try {
				Thread.sleep((long) (1.8f * 1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Right:"+Right+" Left:"+Left);
	 }
	 
	 //Clean up
	m_bSuppressed = false;
	m_bInControl = false;
	Motor.A.stop(true); 
	Motor.D.stop(false);
}

}
