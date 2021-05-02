import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import destek.Configuration;

public class Population {
	
	public List<Star> stars;
	public static Random rand;
	double totalCoverage;
	public List<Double> totalCoverageHistory;

	
	public Population() {
		this.stars = new ArrayList<Star>();
		totalCoverageHistory = new ArrayList<Double>();
		rand = new Random();
	}

	public void init() {
		
		for (int i = 0; i < Configuration.NUM_STARS; i++) {			
			stars.add(new Star());
			//stars.get(i).id = i;
		}
	}
				

}
