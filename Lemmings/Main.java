import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;


public class Main {
	public static void main(String[] args) {
		//Create the sensors
		UltraSonicSensor TopSensor = new UltraSonicSensor(SensorPort.S1, 1);
		UltraSonicSensor BottomSensor = new UltraSonicSensor(SensorPort.S2, 1);
		
		//Create and start behavior
		LemmingBehavior Behavior = new LemmingBehavior(TopSensor, BottomSensor, Motor.A, Motor.D );
		Behavior.ExecuteBehavior();
	}
}
