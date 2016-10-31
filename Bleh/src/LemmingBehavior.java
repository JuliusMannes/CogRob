import java.io.File;
import java.util.Random;

import lejos.hardware.Sound;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.utility.Delay;


public class LemmingBehavior {
	//Variables
	private UltraSonicSensor m_pTopSensor = null;
	private UltraSonicSensor m_pBottomSensor = null;
	private ColorSensor m_pColorSensor = null;
	private NXTRegulatedMotor m_pLeftWheel = null;
	private NXTRegulatedMotor m_pRightWheel = null;
	private Random m_pRandom = null;
	
	public LemmingBehavior(UltraSonicSensor a_pTop, UltraSonicSensor a_pBottom, ColorSensor a_pColorSensor, NXTRegulatedMotor a_pLeftWheel, NXTRegulatedMotor a_pRightWheel) {
		//Initialize the variables
		m_pTopSensor = a_pTop;
		m_pBottomSensor = a_pBottom;
		m_pColorSensor = a_pColorSensor;
		m_pLeftWheel = a_pLeftWheel;
		m_pRightWheel = a_pRightWheel;
		
		//Initialize random generator
		m_pRandom = new Random();
	}
	
	public void ExecuteBehavior() {
		//Initialize behavior
		Wander();
		File pR2D2 = new File("R2D2a.wav");
		if(pR2D2.isFile() == false) {
			System.out.println("File not found");
		}
		
		//Start behavioral loop
		boolean Running = true;
		while(Running) {
			//Get Detector Samples
			float TopSample = m_pTopSensor.DetectSample();
			float BottomSample = m_pBottomSensor.DetectSample();
			float fColorSample = m_pColorSensor.DetectSample();
			int ColorSample = (int)fColorSample;
			
			//Check for wall detection
			if(TopSample < 0.25f) {
				Sound.beep();
				if( ColorSample == 2) {
					//BLUE
					TurnLeft();
				} else if(ColorSample == 0) {
					//RED
					TurnRight();
				} else {
					//Without a block in the gripper random turn left or right
					int RandomRoll = m_pRandom.nextInt(2);
					if (RandomRoll == 1) {
						TurnLeft();
					} else {
						TurnRight();
					}
				}
			}
			//Check for block detection
			else if(BottomSample < 0.15f) {
      				if(pR2D2.isFile()) {
					Sound.playSample(pR2D2);
				} else {
					Sound.twoBeeps();
				}
				if( ColorSample == 2) {
					//Nothing
				} else if(ColorSample == 0) {
					//RED
					TurnLeft();
				} else {
					//No block
					Straight();
				}
			} else {
				Wander();
			}
		}
	}
	
	private void Wander() {
		//Speed difference should cause a slight right curve
		m_pLeftWheel.setSpeed(360);
		m_pRightWheel.setSpeed(320);
		m_pLeftWheel.forward();
		m_pRightWheel.forward();
	}
	
	private void Straight() {
		m_pLeftWheel.setSpeed(360);
		m_pRightWheel.setSpeed(360);
		m_pLeftWheel.forward();
		m_pRightWheel.forward();
	}
	
	private void TurnLeft() {
		//Execute a left turn
		System.out.println("Turning Left");
		m_pLeftWheel.setSpeed(200);
		m_pRightWheel.setSpeed(200);
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
		m_pLeftWheel.setSpeed(200);
		m_pRightWheel.setSpeed(200);
		m_pLeftWheel.forward();
		m_pRightWheel.backward();
		
		//Delay to complete the turn
		Delay.msDelay(1000);
		
		//Return to wander
		Wander();
	}
}
