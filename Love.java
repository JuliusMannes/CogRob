import java.io.IOException;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;


public class Love implements Behavior {
	private NXTLightSensor ls = null;
	private NXTLightSensor rs = null;
	private boolean suppressed = false;
	private int SampleSize = 5;
	private float LightIntensity = 0.1f;
	private float SpeedWeight = 2000.0f;

public Love() {
	ls = new NXTLightSensor(SensorPort.S3);
	rs = new NXTLightSensor(SensorPort.S2);
}



@Override
public boolean takeControl() {
	//if light source above treshold - take action
	float Left = detectLight_left();
	float Right = detectLight_right();
	System.out.println("Left: "+Left+"; Right:"+Right);
	return (Right > LightIntensity || Left > LightIntensity );
}

//if it detects light on the right it turns right, vice versa with left, if light in center both motors start
@Override
public void action() {
	//Right motor (A)
	float LeftDetection = detectLight_left();
	if (LeftDetection > LightIntensity) {
		Motor.A.forward();
		Motor.A.setSpeed(Math.max(0,LeftDetection * SpeedWeight));
	}
	//Left motor (D)
	float RightDetection = detectLight_right();
	if (RightDetection > LightIntensity) {
		Motor.D.forward();
		Motor.D.setSpeed(Math.max(0,RightDetection * SpeedWeight));
	}
}

@Override
public void suppress() {
	suppressed = true;
}
	 
private float detectLight_right() {
	SampleProvider Provider_r = rs.getAmbientMode();
	float Sample[] = new float[SampleSize];
	Provider_r.fetchSample(Sample,0);
	
	float Total = 0;
	for(int i=0; i<SampleSize; i++) {
		Total += Sample[i];
	}
	Total /= SampleSize;
	
	return Total;
}
private float detectLight_left() {
	SampleProvider Provider_l = ls.getAmbientMode();
	float Sample[] = new float[SampleSize];
	Provider_l.fetchSample(Sample,0);
	
	float Total = 0;
	for(int i=0; i<SampleSize; i++) {
		Total += Sample[i];
	}
	Total /= SampleSize;
	
	return Total;
}

}