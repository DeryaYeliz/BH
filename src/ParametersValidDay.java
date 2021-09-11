
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParametersValidDay {
	
	public List <Integer> input1; 
	public List <Integer> input2; 
	public List <Integer> input3; 
	

	//public List <Map<Integer,Integer>> pointsList;
	public List <List<Integer>> inputVectorsList;
	//public Map <String, List<Integer>> weightMap;
	
	public ParametersValidDay(int numOfParams){
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
		

		//day
		input1.add(0);
		input1.add(30);
		input1.add(31);
		input1.add(15);		
		
		//month
		input2.add(0);
		input2.add(5);
		input2.add(9);
		input2.add(10);
		input2.add(120);
		
		//year
		input3.add(0);
		input3.add(1981);
		input3.add(1994);
		input3.add(2021);

		
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
