package destek;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
	
	

}
