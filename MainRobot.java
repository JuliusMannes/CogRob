import lejos.hardware.port.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
// window/preferences/java/lejosev3
public class MainRobot {
	public static void main(String[] args) {
		//Log
		System.out.println("Main function called");
		
		//Create Sensors
		UltraSonicSensor USS_Right = new UltraSonicSensor(SensorPort.S1, 1);
		UltraSonicSensor USS_Left = new UltraSonicSensor(SensorPort.S4, 1);
		
		Behavior b1 = new DriveForward();
		//Behavior b2 = new Fear(0.2f, 0.25f, USS_Left, USS_Right);
		//Behavior b2 = new Fear(0.25f, 0.3f, USS_Right, USS_Left); //Aggro
		//Behavior b2 = new Love(0.25f, 0.3f, USS_Left, USS_Right, 1000.0f);
		Behavior b2 = new Love(0.10f, 0.15f, USS_Right, USS_Left, 1000.0f); //Explore
		//Behavior b2 = new Wanderer(0.15f, 0.3f, USS_Left, USS_Right, 500.0f);
		
		//Put them in an array and create the Arbitrator
		System.out.println("Behaviors created, creating Arbitrator");
	    Behavior [] bArray = {b1, b2};
	    Arbitrator arby = new Arbitrator(bArray);
	    System.out.println("Starting Arbitrator");
	    arby.start();
	    System.out.println("Ended");
	}
}
// TODO if blocked go a bit to the right, check if it's clear, if yes go that way, if no, turn left, rinse and repeat