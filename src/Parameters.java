
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Parameters {
	
	public List <Integer> minMaxValues1; 
	public List <Integer> minMaxValues2; 
	public List <Integer> minMaxValues3; 
	public List <Integer> minMaxValues4; 
	public List <Integer> minMaxValues5;
	//public List <Map<Integer,Integer>> pointsList;
	public Map <String, List<Integer>> minMaxBoundryMap;
	//public Map <String, List<Integer>> weightMap;
	
	public Parameters(int numOfParams){
		// kac parametre varsa o kadarlik bir listeye puan map ini ekle.
		/*pointsList = new ArrayList<Map<Integer,Integer>>();
		
		for (int i = 0; i < numOfParams; i++) {
			pointsList.add(new HashMap<Integer,Integer>());
		}*/
		

	}
	
	public Map<String, List<Integer>> initilizeBoundries(){
		
		minMaxBoundryMap = new LinkedHashMap<String, List<Integer>>();
		
		minMaxValues1 = new ArrayList<Integer>();
		minMaxValues2 = new ArrayList<Integer>();
		minMaxValues3 = new ArrayList<Integer>();
		minMaxValues4 = new ArrayList<Integer>();
		minMaxValues5 = new ArrayList<Integer>();

		
		minMaxValues1.add(0,0);//min value
		minMaxValues1.add(1,2);//max value
		
		minMaxValues2.add(0,5);
		minMaxValues2.add(1,21);
		
		minMaxValues3.add(0,0);
		minMaxValues3.add(1,101);
		
		minMaxValues4.add(0,0);
		minMaxValues4.add(1,6);
		
		minMaxValues5.add(0,10);
		minMaxValues5.add(1,311);
		
		minMaxBoundryMap.put("param_1", minMaxValues1);
		minMaxBoundryMap.put("param_2", minMaxValues2);
		minMaxBoundryMap.put("param_3", minMaxValues3);
		minMaxBoundryMap.put("param_4", minMaxValues4);
		minMaxBoundryMap.put("param_5", minMaxValues5);

		return minMaxBoundryMap;

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
