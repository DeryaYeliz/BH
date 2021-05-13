
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Parameters {
	
	public List <Integer> input1; 
	public List <Integer> input2; 
	public List <Integer> input3; 
	public List <Integer> input4; 
	public List <Integer> input5;
	public List <Integer> input6;
	public List <Integer> input7;
	public List <Integer> input8;
	public List <Integer> input9;
	public List <Integer> input10;
	public List <Integer> input11;
	public List <Integer> input12;

	//public List <Map<Integer,Integer>> pointsList;
	public List <List<Integer>> inputVectorsList;
	//public Map <String, List<Integer>> weightMap;
	
	public Parameters(int numOfParams){
		// kac parametre varsa o kadarlik bir listeye puan map ini ekle.
		/*pointsList = new ArrayList<Map<Integer,Integer>>();
		
		for (int i = 0; i < numOfParams; i++) {
			pointsList.add(new HashMap<Integer,Integer>());
		}*/
		

	}
	
	public void initilizeBoundries(){
		inputVectorsList = new ArrayList<>();
		
		input1 = new ArrayList<Integer>();
		input2 = new ArrayList<Integer>();
		input3 = new ArrayList<Integer>();
		input4 = new ArrayList<Integer>();
		input5 = new ArrayList<Integer>();
		input6 = new ArrayList<Integer>();
		input7 = new ArrayList<Integer>();
		input8 = new ArrayList<Integer>();
		input9 = new ArrayList<Integer>();
		input10 = new ArrayList<Integer>();
		input11 = new ArrayList<Integer>();
		input12 = new ArrayList<Integer>();

		//dXPos
		input1.add(0);
		input1.add(20);
		input1.add(50);
		input1.add(75);
		input1.add(100);
		input1.add(200);

		//dYPos
		input2.add(0);
		input2.add(20);
		input2.add(50);
		input2.add(75);
		input2.add(100);
		input2.add(200);
		
		//dzPos
		input3.add(0);
		input3.add(20);
		input3.add(50);
		input3.add(75);
		input3.add(100);
		input3.add(200);
		
		//dVx
		input4.add(-50);
		input4.add(0);
		input4.add(50);
		
		//dVy
		input5.add(-50);
		input5.add(0);
		input5.add(50);
		
		//dhoStatus
		input6.add(0);
		input6.add(1);
		
		//izTipi
		input7.add(0);
		input7.add(1);
		
		//dpMu[0]
		input8.add(1);
		input8.add(2);
		
		//dpMu[1]
		input9.add(1);
		input9.add(2);
				
		//dpMu[2]
		input10.add(1);
		input10.add(4);
		input10.add(8);
		
		//iMuhtSinif
		for (int i = 0; i < 4; i++) {
			input11.add((i+1));
		}
		
		//dGercekHiz
		input12.add(10);
		input12.add(270);
		input12.add(500);
		
		
		inputVectorsList.add(input1);
		inputVectorsList.add(input2);
		inputVectorsList.add(input3);
		inputVectorsList.add(input4);
		inputVectorsList.add(input5);
		inputVectorsList.add(input6);
		inputVectorsList.add(input7);
		inputVectorsList.add(input8);
		inputVectorsList.add(input9);
		inputVectorsList.add(input10);
		inputVectorsList.add(input11);
		inputVectorsList.add(input12);

	}
	
	/*public void giveWeightToParameters(){
		
		int currentMajority;
		
		for (Map.Entry<String,List<Integer>> entry : minMaxBoundryMap.entrySet()) {
			//min-max deger farkini al.
			currentMajority = minMaxBoundryMap.get(entry.getKey()).get(1) - minMaxBoundryMap.get(entry.getKey()).get(0);
			minMaxBoundryMap.get(entry.getKey()).add(currentMajority*10000);// 0:minvalue, 1:maxvalue, 2: weight
		}
		
		weightMap = new LinkedHashMap<String, List<Integer>>();
		weightMap.putAll(minMaxBoundryMap);
		
	}
	
	public int sayIndex(){
		int maxMajority = 0;
		int currentMajority = 0;
		int maxIndex = 0;
		
		for (Map.Entry<String,List<Integer>> entry : weightMap.entrySet()) {
			currentMajority = minMaxBoundryMap.get(entry.getKey()).get(2);
			if(currentMajority > maxMajority){
				maxMajority = currentMajority;
				maxIndex = Integer.valueOf(entry.getKey().split("_")[1]);
			}	
		 }
		
		
		//agirligi 1 azalt.
		weightMap.get("param_"+maxIndex).set(2, weightMap.get("param_"+maxIndex).get(2)-1);
		//eger agirlik bittiyse o parametreyi sil.
		if(weightMap.get("param_"+ maxIndex).get(2) == 0){
			weightMap.remove("param_"+maxIndex);
		}
		if(weightMap.isEmpty())
			return -1;
		
		return maxIndex - 1;
	}*/
}
