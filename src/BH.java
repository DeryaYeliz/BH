import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BH {
	
	public Star BH;
	public int indexOfBH;
	public List<Double> EP; 
	public double R;
	
	
	public BH() {
		EP = new ArrayList<Double>();
	}

	public void updateFitness(Population P){
		double coverageValue;
		for (int i = 0; i < Configuration.NUM_STARS; i++) {
			coverageValue = 50; // y(P.stars.get(i);) //TODO
			if(coverageValue != 0)
				EP.add(1/coverageValue);
			else
				EP.add(0.0);

		}
  
        double best = Collections.min(EP);
        indexOfBH = EP.indexOf(best);
        BH = P.stars.get(indexOfBH);
	}

	public double updateRadius(Population P, int indexOfBH, List<Double> EP){
		double sum = 0;
		double R;
		for (int i = 0; i < Configuration.NUM_STARS; i++) {
			sum = sum + EP.get(i);
		}
		R = EP.get(indexOfBH) / sum;
		return R;
		
	}
	public void moveStars(Population P, Star BH){
		
		int currentValue;
		int randomValue;
		int BHValue;
		int newValue;
		int maxValue;
		int minValue;
		for (int i = 0; i < Configuration.NUM_STARS; i++) {
			for (int j = 0; j < Configuration.NUM_PARAMTERS; j++) {
				currentValue = P.stars.get(i).parametersVector.get(j);
				randomValue = Population.rand.ints(1, 0, 1).toArray()[0];
				BHValue = BH.parametersVector.get(j);
				newValue = P.stars.get(i).parametersVector.get(j) 
						+ randomValue * (BHValue-currentValue);
				
				minValue = BH.params.minMaxBoundryMap.get("param_"+ (j+1)).get(0);
				maxValue = BH.params.minMaxBoundryMap.get("param_"+ (j+1)).get(1);

				//sinir kontrolu. 
				if(newValue < minValue) {
					P.stars.get(i).parametersVector.set(j, minValue)  ;
				}else if (newValue > maxValue) {
					P.stars.get(i).parametersVector.set(j, maxValue)  ;
				}else {
					P.stars.get(i).parametersVector.set(j, newValue);

				}
			}
		}
				
	}
	
}
