import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
// window/preferences/java/lejosev3
public class MainRobot {
	public static void main(String[] args) {
		System.out.println("I am alive!");
		
		Behavior b1 = new DriveForward();
		Behavior b2 = new Turn();
		Behavior b3 = new Fear();
	    Behavior [] bArray = {b1, b2, b3};
	    Arbitrator arby = new Arbitrator(bArray);
	    arby.start();
	}
}
// TODO if blocked go a bit to the right, check if it's clear, if yes go that way, if no, turn left, rinse and repeat