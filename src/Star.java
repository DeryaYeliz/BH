import java.util.ArrayList;
import java.util.List;
import destek.Configuration;


public class Star {


	public List<Integer> parametersVector; 
	Parameters params;
	public Star() {
		params = new Parameters(Configuration.NUM_PARAMTERS);
		params.initilizeBoundries();
		generateRandomStar(params,-1);
	}
	
	public void generateRandomStar(Parameters params, int indexParamToChange){
//		Random rand = new Random();
		List<Integer> minMaxPair;

		
		if(indexParamToChange == -1){ // vektorun tamami random
			//bos yeni bir vector yarat.
			parametersVector = new ArrayList<>();
			
			//her parametre icin aralik degerleri icinde random sayi uret. Vektor olustur.
			for (int i = 0; i < params.minMaxBoundryMap.size(); i++) {
				minMaxPair = params.minMaxBoundryMap.get("param_"+(i+1));
				parametersVector.add(Population.rand.ints(1, minMaxPair.get(0), minMaxPair.get(1)).toArray()[0]);
			}
			//ilk vektor icin puanlari 1 olarak ilklendir.
			/*for (int i = 0; i < params.pointsList.size(); i++) {
				params.pointsList.get(i).put(currentSingleVector.get(i), 1);
			}*/
			
		}
		else{
			
			//eger tek tek parametreleri uretmek istersek, burayi kullanacagiz.
			//bir onceki vektoru sakla.
			/*previousSingleVector = new ArrayList<>();
			previousSingleVector.addAll(currentSingleVector);
			
			//sadece isteden parametreyi random degistir. Digerleri aynı kalsin.
			minMaxPair = params.minMaxBoundryMap.get("param_"+(indexParamToChange + 1));
			do{ //degistirilen deger random ayni denk geldiyse tekrar random olustur.
				currentSingleVector.set(indexParamToChange, rand.ints(1,minMaxPair.get(0),minMaxPair.get(1)).toArray()[0]);
				//System.out.println("Indis:" + indexParamToChange + " degistirildi!" 
				//		+ "onceki deger:" + previousSingleVector.get(indexParamToChange) +" sonraki deger:" + currentSingleVector.get(indexParamToChange));
				
			}while(previousSingleVector.get(indexParamToChange) == currentSingleVector.get(indexParamToChange));*/
		}
				
	}
	
}
