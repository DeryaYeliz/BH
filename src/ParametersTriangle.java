
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParametersTriangle {
	
	public List <Integer> input1; 
	public List <Integer> input2; 
	public List <Integer> input3; 
	

	//public List <Map<Integer,Integer>> pointsList;
	public List <List<Integer>> inputVectorsList;
	//public Map <String, List<Integer>> weightMap;
	
	public ParametersTriangle(int numOfParams){
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
		

		//edge1
		input1.add(6);
		input1.add(8);
		input1.add(10);
		input1.add(11);
		
		//edge2
		input2.add(6);
		input2.add(8);
		input2.add(9);
		input2.add(10);
		input2.add(11);

		//edge3
		input3.add(6);
		input3.add(8);

		
		
		inputVectorsList.add(input1);
		inputVectorsList.add(input2);
		inputVectorsList.add(input3);
		

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
