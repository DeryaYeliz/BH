import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import destek.Configuration;
import destek.Coverage;

public class BH {
	
	public Star starBH;
	//public int indexOfBH;
	//public List<Double> EP; 
	public double R;
	Population P;
	
	public BH() {
		//EP = new ArrayList<Double>();
		P = new Population();
		P.init();

	}

	public void updateFitness(Population P) throws IOException{
		double coverageValue;
		double minCoverage = 1;
		for (int i = 0; i < P.stars.size(); i++) {
			if(P.stars.get(i).isAlive) {
				if(P.stars.get(i).isNew) {
					Coverage.inputListforCoverage.add(P.stars.get(i).parametersVector);
					coverageValue = Coverage.getCoverage(); 
					if(coverageValue != 0)
						P.stars.get(i).coverage = (1/coverageValue);
					else
						P.stars.get(i).coverage = 0.0;
					
					Coverage.readHtmlMissedBranches(P.stars.get(i).branchMissedVector);
					Coverage.inputListforCoverage.clear();	
					
					P.stars.get(i).isNew = false;
				}
				if(P.stars.get(i).coverage < minCoverage) {
					minCoverage = P.stars.get(i).coverage;
			        starBH = P.stars.get(i);
				}
				
			}
				
			
			
		}

	}
	public void calculatePopulationsCoverage(Population P) throws IOException {
		double coverageValue;
		for (int i = 0; i < P.stars.size(); i++) {
			if(P.stars.get(i).isAlive) {
				Coverage.inputListforCoverage.add(P.stars.get(i).parametersVector);		
			}
		}
		coverageValue = Coverage.getCoverage(); 
		P.totalCoverage = coverageValue;
		Coverage.inputListforCoverage.clear();
	}
	public double updateRadius(Population P, Star bh){
		double sum = 0;
		double R;
		for (int i = 0; i < P.stars.size(); i++) {
			if(P.stars.get(i).isAlive)
				sum = sum + P.stars.get(i).coverage;
		}
		R = bh.coverage / sum;
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
		
		for (int i = 0; i < P.stars.size(); i++) {
			if(P.stars.get(i).isAlive) {
				for (int j = 0; j < Configuration.NUM_PARAMTERS; j++) {
					currentValue = P.stars.get(i).parametersVector.get(j);
					BHValue = BH.parametersVector.get(j);
					
					//BH ve current in parametre tanimlarindaki indislerini al.
					BHindex = BH.params.inputVectorsList.get(j).indexOf(BHValue);
					currentIndex = BH.params.inputVectorsList.get(j).indexOf(currentValue);
					if(BHValue != currentValue) {
						//start indisi kucuk olan olsun
						if(BHindex<currentIndex) {
							startRand = BHindex;
							endRand = currentIndex - 1;
						}else {
							startRand = currentIndex + 1;
							endRand = BHindex;
						} 
						//indiste random sayi uret. Parameters in o parametresini al.
						randomValue = startRand + (new Random().nextDouble() * (endRand-startRand));
						randomValue = Math.round(randomValue);//yuvarla
						
						newValue = BH.params.inputVectorsList.get(j).get((int)randomValue);
						P.stars.get(i).parametersVector.set(j, newValue);
					}
					P.stars.get(i).isNew = true;
	
				}
			}
			
		}
				
	}
	
}
