import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;


public class Main {
	public static void main(String[] args) {
		//Create the sensors
		UltraSonicSensor TopSensor = new UltraSonicSensor(SensorPort.S1, 5);
		UltraSonicSensor BottomSensor = new UltraSonicSensor(SensorPort.S4, 1);
		ColorSensor pColorSensor = new ColorSensor(SensorPort.S3, 1);
		
		//Create and start behavior
		LemmingBehavior Behavior = new LemmingBehavior(TopSensor, BottomSensor, pColorSensor, Motor.A, Motor.D );
		Behavior.ExecuteBehavior();
	}
}
