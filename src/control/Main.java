package control;

import model.DummyLocalizer;
import model.Robot;
import model.RobotLocalizer;
import model.Sensor;
import view.RobotLocalizationViewer;

public class Main {
	/*
	 * build your own if you like, this is just an example of how to start the viewer
	 * ...
	 */
	
	public static void main( String[] args) {
		
		/*
		 * generate you own localiser / estimator wrapper here to plug it into the 
		 * graphics class.
		 */
		//EstimatorInterface l = new DummyLocalizer( 1, 1, 1);
		Robot r = new Robot(5);
		EstimatorInterface l = new RobotLocalizer(r, new Sensor(r), r.getRoom());

		RobotLocalizationViewer viewer = new RobotLocalizationViewer( l);


		/*
		 * this thread controls the continuous update. If it is not started, 
		 * you can only click through your localisation stepwise
		 */
		new LocalizationDriver( 500, viewer).start();
	}
}	