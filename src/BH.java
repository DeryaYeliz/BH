import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import destek.Configuration;
import destek.Coverage;

public class BH {
	
	public Star BH;
	public int indexOfBH;
	public List<Double> EP; 
	public double R;
	Population P;
	
	
	public BH() {
		EP = new ArrayList<Double>();
		P = new Population();
		P.init();

	}

	public void updateFitness(Population P) throws IOException{
		double coverageValue;
		for (int i = 0; i < Configuration.NUM_STARS; i++) {
			//coverageValue = 50; 
			Coverage.inputListforCoverage.add(P.stars.get(i).parametersVector);
			coverageValue = Coverage.getCoverage(); 
			if(coverageValue != 0)
				EP.add(1/coverageValue);
			else
				EP.add(0.0);
			Coverage.inputListforCoverage.clear();

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
		double randomValue;
		int BHValue;
		int newValue;

		double startRand;
		double endRand;
		int BHindex;
		int currentIndex;
		
		for (int i = 0; i < Configuration.NUM_STARS; i++) {
			for (int j = 0; j < Configuration.NUM_PARAMTERS; j++) {
				currentValue = P.stars.get(i).parametersVector.get(j);
				BHValue = BH.parametersVector.get(j);
				
				//BH ve current in parametre tanimlarindaki indislerini al.
				BHindex = BH.params.inputVectorsList.get(j).indexOf(BHValue);
				currentIndex = BH.params.inputVectorsList.get(j).indexOf(currentValue);
				
				//start indisi kucuk olan olsun
				if(BHindex<currentIndex) {
					startRand = BHindex;
					endRand = currentIndex;
				}else {
					startRand = currentIndex;
					endRand = BHindex;
				} 
				//indiste random sayi uret. Parameters in o parametresini al.
				randomValue = startRand + (new Random().nextDouble() * (endRand-startRand));
				randomValue = Math.round(randomValue);//yuvarla
				
				newValue = BH.params.inputVectorsList.get(j).get((int)randomValue);
				P.stars.get(i).parametersVector.set(j, newValue);
				
			}
		}
				
	}
	
}
