import java.util.Random;

import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.utility.Delay;


public class LemmingBehavior {
	//Variables
	private UltraSonicSensor m_pTopSensor = null;
	private UltraSonicSensor m_pBottomSensor = null;
	private NXTRegulatedMotor m_pLeftWheel = null;
	private NXTRegulatedMotor m_pRightWheel = null;
	private Random m_pRandom = null;
	
	public LemmingBehavior(UltraSonicSensor a_pTop, UltraSonicSensor a_pBottom, NXTRegulatedMotor a_pLeftWheel, NXTRegulatedMotor a_pRightWheel) {
		//Initialize the variables
		m_pTopSensor = a_pTop;
		m_pBottomSensor = a_pBottom;
		m_pLeftWheel = a_pLeftWheel;
		m_pRightWheel = a_pRightWheel;
		
		//Initialize random generator
		m_pRandom = new Random();
	}
	
	public void ExecuteBehavior() {
		//Initialize behavior
		Wander();
		
		//Start behavioral loop
		boolean Running = true;
		while(Running) {
			//Get Detector Samples
			float TopSample = m_pTopSensor.DetectSample();
			//float BottomSample = m_pBottomSensor.DetectSample();
			
			//Check for wall detection
			if(TopSample < 0.16f) {
				//Without a block in the gripper random turn left or right
				int RandomRoll = m_pRandom.nextInt(2);
				if (RandomRoll == 1) {
					TurnLeft();
				} else {
					TurnRight();
				}
			}
			
			
			//Test Case
			//Delay.msDelay(10000);
			//Running = false;
		}
	}
	
	private void Wander() {
		//Speed difference should cause a slight right curve
		System.out.println("Starting Wandering");
		m_pLeftWheel.setSpeed(360);
		m_pRightWheel.setSpeed(320);
		m_pLeftWheel.forward();
		m_pRightWheel.forward();
	}
	
	private void TurnLeft() {
		//Execute a left turn
		System.out.println("Turning Left");
		m_pLeftWheel.setSpeed(240);
		m_pRightWheel.setSpeed(240);
		m_pLeftWheel.backward();
		m_pRightWheel.forward();
		
		//Delay to complete the turn
		Delay.msDelay(1000);
		
		//Return to wander
		Wander();
	}
	
	private void TurnRight() {
		//Execute a right turn
		System.out.println("Turning Right");
		m_pLeftWheel.setSpeed(240);
		m_pRightWheel.setSpeed(240);
		m_pLeftWheel.forward();
		m_pRightWheel.backward();
		
		//Delay to complete the turn
		Delay.msDelay(1000);
		
		//Return to wander
		Wander();
	}
}
