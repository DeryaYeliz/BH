import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MBH {
	
	public List<Integer> Es; 	
	public List<BH> BHs;
	
	public MBH() {
		BHs = new ArrayList<BH>();
		Es = new ArrayList<Integer>();
		for (int i = 0; i < Configuration.NUM_BLACKHOLES; i++) {
			BHs.add(new BH());
			Es.add(Configuration.E_MAX);
			BHs.get(i).updateFitness(BHs.get(i).P);
		}
	}	
}
