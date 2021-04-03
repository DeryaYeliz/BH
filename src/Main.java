
public class Main {
	public static void main(String[] args) {

		BH bhOject = new BH();
		Population P = new Population();
		P.init();
		for (int i = 0; i < Configuration.NUM_ITERATION; i++) {
			
			bhOject.updateFitness(P);
			bhOject.moveStars(P, bhOject.BH);
			bhOject.updateFitness(P);
			bhOject.R = bhOject.updateRadius(P, bhOject.indexOfBH, bhOject.EP);
			
			for (int j = 0; j < Configuration.NUM_STARS; j++) {
				if(dist(bhOject.BH, P.stars.get(i))<bhOject.R) {
					//replace
				}
			}
			bhOject.updateFitness(P);
			
			//TODO: if stop criteria is met
				//Break;

		}
		printResult(P);
		
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
	
	
	public static void printResult(Population P) {
		System.out.println("Solution space is: ");
		for(int i = 0; i < Configuration.NUM_STARS; i++) {
			System.out.print("Star" + i + ": ");
			for(int j = 0; j < Configuration.NUM_PARAMTERS; i++) {
				System.out.print(P.stars.get(i).parametersVector.get(j) + " ");

			}
			System.out.println();
		}
	}
}
