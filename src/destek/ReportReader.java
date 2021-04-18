package destek;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReportReader {

	public static double csvParser(Path path) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(path.toString()));
		sc.useDelimiter(",");
		List<String> csvElements = new ArrayList<String>();
		
		while(sc.hasNext()) {
			csvElements.add(sc.next());
		}
		sc.close();		
		double missedBranches = Integer.valueOf(csvElements.get(17));
		double coveredBranches = Integer.valueOf(csvElements.get(18));
		double coverage = (coveredBranches / (coveredBranches + missedBranches))*100;
		System.out.println("missedBranches: " +missedBranches);
		System.out.println("coveredBranches: " +coveredBranches);

		System.out.println("Coverage: " +coverage);

		return coverage;
	}
	
}
