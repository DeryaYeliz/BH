import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import destek.Configuration;

public class Main {
	public static void main(String[] args) throws IOException {

		BH bhObject = new BH();
		int starListeBoyu = Configuration.NUM_STARS;
		int ekStarListeBoyu = 0;
		bhObject.updateFitness(bhObject.P);

		for (int i = 0; i < Configuration.NUM_ITERATION; i++) {
			System.out.println("ITERASYON: " + (i+1));

			bhObject.moveStars(bhObject.P, bhObject.starBH);
			bhObject.updateFitness(bhObject.P);
			bhObject.R = bhObject.updateRadius(bhObject.P, bhObject.starBH);
			System.out.println("R: " + bhObject.R);
			for (int j = 0; j < starListeBoyu; j++) {
				if(bhObject.P.stars.get(j).isAlive && bhObject.starBH != bhObject.P.stars.get(j)) {
					System.out.println("dist: " + dist(bhObject.starBH, bhObject.P.stars.get(j)));
					if(dist(bhObject.starBH, bhObject.P.stars.get(j)) < bhObject.R) {
						bhObject.P.stars.get(j).isAlive = false;
						bhObject.P.stars.add(new Star());
						ekStarListeBoyu++;
						System.out.println("ekStarListeBoyu:" + ekStarListeBoyu);
					}
				}
			}
			starListeBoyu = starListeBoyu + ekStarListeBoyu;
			ekStarListeBoyu = 0;
			System.out.println("starListeBoyu:" + starListeBoyu);

			bhObject.updateFitness(bhObject.P);
			
			//TODO: if stop criteria is met
				//Break;

		}
		
		//Tum starlari ver ve komple population in coverage ini hesapla:
		bhObject.calculatePopulationsCoverage(bhObject.P);
		
		//Log yaz
		printResult(bhObject.P);
		printMissedBranchesLog(bhObject.P,Configuration.pathLog, Configuration.fileNameLog);
	}
	public static void main5(String[] args) throws IOException {
		

	}
	public static void main3(String[] args) throws IOException {
		MBH mbhObject = new MBH();
		boolean improvement = false;
		BH currentBH;
		double BHoCoverage;
		double BHnCoverage;
		for (int i = 0; i < Configuration.NUM_ITERATION; i++) {
			for (int j = 0; j < Configuration.NUM_BLACKHOLES; j++) {
				currentBH = mbhObject.BHs.get(j);
				currentBH.moveStars(currentBH.P, currentBH.starBH);
				currentBH.R = currentBH.updateRadius(currentBH.P, currentBH.starBH);
				for (int k = 0; k < Configuration.NUM_STARS; k++) {
					if(currentBH.P.stars.get(j).isAlive && currentBH.starBH != currentBH.P.stars.get(j)) {
						if(dist(currentBH.starBH, currentBH.P.stars.get(k)) < currentBH.R) {
							//replace //TODO						
							currentBH.P.stars.get(j).isAlive = false;
							currentBH.P.stars.add(new Star());
						}
					}
				}
				BHoCoverage = currentBH.starBH.coverage; 
				currentBH.updateFitness(currentBH.P);
				BHnCoverage = currentBH.starBH.coverage; 

				improvement = isImprovement(BHoCoverage,BHnCoverage);
				
				if(!improvement) {
					mbhObject.Es.set(j, mbhObject.Es.get(j) - 1 ); 
				}
				if(mbhObject.Es.get(j) < Configuration.E_THRESHOLD) {
					currentBH = new BH(); //TODO
					currentBH.updateFitness(currentBH.P);
					mbhObject.Es.set(j, Configuration.E_MAX);					
				}	
			}
						
			//TODO: if stop criteria is met
			//Break;

		}
		
		for (int i = 0; i < Configuration.NUM_BLACKHOLES; i++) {
			System.out.println("BLACKHOLE"+ (i+1) +": ");
			printResult(mbhObject.BHs.get(i).P);
			printMissedBranchesLog(mbhObject.BHs.get(i).P, Configuration.pathLog, Configuration.fileNameLog + "_MBH" + (i+1));
		}
	}
	
	
	
	
	
	
	public static double dist(Star BH, Star S) {
		//TODO: disti nasıl hesaplayacağını düşün.
		/*int sumOfSquares = 0;
		int square = 0;
		for (int i = 0; i < Configuration.NUM_PARAMTERS; i++) {
			square = (S.parametersVector.get(i) - BH.parametersVector.get(i)) * (S.parametersVector.get(i) - BH.parametersVector.get(i));
			sumOfSquares = sumOfSquares + square;
		}
		return Math.sqrt(sumOfSquares);*/

		int indexBH;
		int indexS;
		int diff = 0;
		int sumOfAllParametersSpace = 0;
		for (int i = 0; i < BH.params.inputVectorsList.size(); i++) {
			indexBH = BH.params.inputVectorsList.get(i).indexOf(BH.parametersVector.get(i));
			indexS = BH.params.inputVectorsList.get(i).indexOf(S.parametersVector.get(i));
			diff = diff + Math.abs(indexS-indexBH);
			sumOfAllParametersSpace = sumOfAllParametersSpace + BH.params.inputVectorsList.get(i).size();
		}
		return (double)diff/sumOfAllParametersSpace;
	}
	
	public static boolean isImprovement(double BHoCoverage, double BHnCoverage) {
		
		if(BHnCoverage < BHoCoverage) {
			return true;
		}
		else
			return false;
	}
	
	public static void printResult(Population P) {
		System.out.println("Solution space: ");
		for(int i = 0; i < P.stars.size(); i++) {
			System.out.print("Star" + (i+1) + ": ");
			for(int j = 0; j < Configuration.NUM_PARAMTERS; j++) {
				System.out.print(P.stars.get(i).parametersVector.get(j) + " ");
			}
			System.out.println();
		}
	}
	public static void printMissedBranchesLog(Population P, String pathLog, String fileNameLog) throws IOException {
		Path path = Paths.get(pathLog, fileNameLog);
		Files.deleteIfExists(path);
		Files.write(path, "".getBytes(), StandardOpenOption.CREATE_NEW);
		String logLine;
		for (int i = 0; i < P.stars.size(); i++) {
			logLine = "StarID: " +  String.valueOf(P.stars.get(i).id + ", IsAlive: " + P.stars.get(i).isAlive
					+ ", MissedBranches: " + P.stars.get(i).branchMissedVector.toString()
					+ "Coverage: " + 1/P.stars.get(i).coverage);
			Files.write(path, logLine.getBytes(), StandardOpenOption.APPEND );
			Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);
			Files.write(path, ("Vector:" + String.valueOf(P.stars.get(i).id) +" "+ P.stars.get(i).parametersVector.toString()).getBytes(), StandardOpenOption.APPEND );
			Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);
			Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);

		}
		Files.write(path, ("TotalCov: " + P.totalCoverage).getBytes(), StandardOpenOption.APPEND);

	}
}
