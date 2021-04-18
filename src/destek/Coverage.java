package destek;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Coverage {
	public static double coverage;
	public static Path path;
	public static List<List<Integer>> inputListforCoverage = new ArrayList<>();
	
	public static double getCoverage() throws IOException {
		
		path = Paths.get(Configuration.directoryInputVector, Configuration.fileNameInputVector);
		//Input vectorlerini dosyaya yaz		
		if(VectorWriter.writeVector2File(inputListforCoverage, path)) {
			System.out.println("Input vector dosyaya yazildi");
		}
				
		//Terminalden maven ile algoyu cagir ve coverage raporu olusmasini sagla.					
		String command = "/usr/local/bin/mvn test";
		String output = executeCommand(command, null, new File(Configuration.pathAlgo));
		System.out.println(output);
		
		//Coverage raporu oku
		
		path = Paths.get(Configuration.directoryCovReport, Configuration.fileNameCovReport);
		coverage = ReportReader.csvParser(path);
		
		return coverage;
	}
		
	public static String executeCommand(String command, String[] env, File dir) {
		StringBuffer output = new StringBuffer();
		Process p;
		
		try {
			p = Runtime.getRuntime().exec(command,env,dir);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output.toString();
	}	
	
	public static void readHtmlMissedBranches(List<Integer> branchMissedVector) throws IOException {
		File input = new File(Configuration.pathBranchFile);
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		Elements elements1 = doc.getElementsByClass("pc bpc");
		Elements elements2 = doc.getElementsByClass("nc bnc");
		Elements elements3 = doc.getElementsByClass("fc bfc");
		String line;
		String [] words;
		int totalBranch = 0;
		int missedBranch = 0;
		 for (Element e : elements1) {
			 line = e.attributes().get("title");
			 words = line.split(" ");	 
			 	if(!words[0].equals("All")) {
					 totalBranch = Integer.valueOf(words[2]);
					 missedBranch = Integer.valueOf(words[0]);
				 }else {
					 totalBranch = Integer.valueOf(words[1]);
					 if(words[3] == "missed.") {
						 missedBranch = totalBranch;
					 }else {
						 missedBranch = 0;
					 }
				 }
			 	branchMissedVector.add(missedBranch);
		     //System.out.println( e.attributes().get("title"));
		 }
		 for (Element e : elements2) {
			 line = e.attributes().get("title");
			 words = line.split(" ");	 
			 	if(!words[0].equals("All")) {
					 totalBranch = Integer.valueOf(words[2]);
					 missedBranch = Integer.valueOf(words[0]);
				 }else {
					 totalBranch = Integer.valueOf(words[1]);
					 if(words[3] == "missed.") {
						 missedBranch = totalBranch;
					 }else {
						 missedBranch = 0;
					 }
				 }
		     //System.out.println( e.attributes().get("title"));
			 	branchMissedVector.add(missedBranch);
		}
		 for (Element e : elements3) {
			 line = e.attributes().get("title");
			 words = line.split(" ");	 
			 	if(!words[0].equals("All")) {
					 totalBranch = Integer.valueOf(words[2]);
					 missedBranch = Integer.valueOf(words[0]);
				 }else {
					 totalBranch = Integer.valueOf(words[1]);
					 if(words[3] == "missed.") {
						 missedBranch = totalBranch;
					 }else {
						 missedBranch = 0;
					 }
				 }
		     //System.out.println( e.attributes().get("title"));
			 	branchMissedVector.add(missedBranch);
		 }
	}
	

}
