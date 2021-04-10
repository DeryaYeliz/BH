import java.io.IOException;
import java.util.List;
import destek.Configuration;

public class Main {
	public static void main2(String[] args) throws IOException {

		BH bhObject = new BH();

		
		for (int i = 0; i < Configuration.NUM_ITERATION; i++) {
			
			bhObject.updateFitness(bhObject.P);
			bhObject.moveStars(bhObject.P, bhObject.BH);
			bhObject.updateFitness(bhObject.P);
			bhObject.R = bhObject.updateRadius(bhObject.P, bhObject.indexOfBH, bhObject.EP);
			
			for (int j = 0; j < Configuration.NUM_STARS; j++) {
				if(dist(bhObject.BH, bhObject.P.stars.get(j)) < bhObject.R) {
					//replace //TODO
					bhObject.P.init();					
				}
			}
			bhObject.updateFitness(bhObject.P);
			
			//TODO: if stop criteria is met
				//Break;

		}
		printResult(bhObject.P);
		
	}
	
	
	public static void main(String[] args) throws IOException {
		MBH mbhObject = new MBH();
		boolean improvement = false;
		BH currentBH;
		int indexOfBHo;
		int indexOfBHn;
		for (int i = 0; i < Configuration.NUM_ITERATION; i++) {
			for (int j = 0; j < Configuration.NUM_BLACKHOLES; j++) {
				currentBH = mbhObject.BHs.get(j);
				currentBH.moveStars(currentBH.P, currentBH.BH);
				currentBH.R = currentBH.updateRadius(currentBH.P, currentBH.indexOfBH, currentBH.EP);
				for (int k = 0; k < Configuration.NUM_STARS; k++) {
					if(dist(currentBH.BH, currentBH.P.stars.get(k)) < currentBH.R) {
						//replace //TODO
						currentBH.P.init();					
					}
				}
				indexOfBHo = currentBH.indexOfBH;
				currentBH.updateFitness(currentBH.P);
				indexOfBHn = currentBH.indexOfBH;

				improvement = isImprovement(indexOfBHo,indexOfBHn, currentBH.EP);
				
				if(!improvement) {
					mbhObject.Es.set(j, mbhObject.Es.get(j) - 1 ); 
				}
				if(mbhObject.Es.get(j) < Configuration.E_THRESHOLD) {
					currentBH = new BH(); //TODO
					currentBH.updateFitness(currentBH.P);
					mbhObject.Es.set(j, Configuration.E_MAX);					
				}	
			}
						
			//TODO: if stop criteria is met
			//Break;

		}
		
		for (int i = 0; i < Configuration.NUM_BLACKHOLES; i++) {
			System.out.println("BH Loop"+ (i+1) +": ");
			printResult(mbhObject.BHs.get(i).P);
		}
	}
	
	
	
	
	
	
	public static double dist(Star BH, Star S) {
		//TODO: disti nasıl hesaplayacağını düşün.
		int sumOfSquares = 0;
		int square = 0;
		for (int i = 0; i < Configuration.NUM_PARAMTERS; i++) {
			square = (S.parametersVector.get(i) - BH.parametersVector.get(i)) * (S.parametersVector.get(i) - BH.parametersVector.get(i));
			sumOfSquares = sumOfSquares + square;
		}
		
		return Math.sqrt(sumOfSquares);
	}
	
	public static boolean isImprovement(int indexOfBHo, int indexOfBHn, List<Double> EP) {
		
		if(EP.get(indexOfBHn) < EP.get(indexOfBHo)) {
			return true;
		}
		else
			return false;
	}
	
	public static void printResult(Population P) {
		System.out.println("Solution space: ");
		for(int i = 0; i < Configuration.NUM_STARS; i++) {
			System.out.print("Star" + (i+1) + ": ");
			for(int j = 0; j < Configuration.NUM_PARAMTERS; j++) {
				System.out.print(P.stars.get(i).parametersVector.get(j) + " ");

			}
			System.out.println();
		}
	}
}
