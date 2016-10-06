import java.util.Random;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTUltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import lejos.*;


public class Turn implements Behavior {
	//Sample provider - Look it up ;D
	NXTUltrasonicSensor m_pSensor = null;
	private boolean suppressed = false;
	private int SampleSize = 5;
	private float TurnDistace = 0.03f;
	  
	public Turn() {
		m_pSensor = new NXTUltrasonicSensor(SensorPort.S1);
	}

	@Override
	public boolean takeControl() {
		return IsObjectBlocking();
	}

	@Override
	public void action() {
		System.out.println("Starting turning");
		suppressed = false;
		Random rand = new Random();
		int value = rand.nextInt(1);
		if(value == 0 ){
		Motor.A.setSpeed(180);
		Motor.D.setSpeed(180);
		Motor.A.backward();
		Motor.D.forward();}
		else {
			Motor.A.setSpeed(180);
		Motor.D.setSpeed(180);
		Motor.A.forward();
		Motor.D.backward();	}
		while( !suppressed && IsObjectBlocking())
			Thread.yield();
		//Clean up
		Motor.A.stop(); 
		Motor.D.stop();
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		suppressed = true;
	}
	
	private boolean IsObjectBlocking() {
		SampleProvider Provider = m_pSensor.getDistanceMode();
		float Sample[] = new float[SampleSize];
		Provider.fetchSample(Sample, 0);
		
		float Total = 0;
		for(int i=0; i<SampleSize; i++) {
			Total += Sample[i];
		}
		Total /= SampleSize;
		return Total < TurnDistace;
	}

}
