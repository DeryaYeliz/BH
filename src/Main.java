import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.net.ssl.SNIHostName;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import destek.Configuration;
import destek.Coverage;

public class Main {
	public static long startTime;
	public static long endTime;
	public static void main(String[] args) throws IOException {
		 startTime = System.currentTimeMillis();
	
		
		BH bhObject = new BH();
		BH bhObjectBestSnap = new BH();
		int starListeBoyu = Configuration.NUM_STARS;
		int ekStarListeBoyu = 0;
		double ratio;
		bhObject.updateFitness(bhObject.P);

		//Starlar icin coverage history tut
		for (int i = 0; i < starListeBoyu; i++) {			
			bhObject.P.stars.get(i).coverageHistory.add(Configuration.timeStamp);			
			if(bhObject.P.stars.get(i).isAlive) {
				bhObject.P.stars.get(i).coverageHistory.add(1/bhObject.P.stars.get(i).coverage);
			}else {
				bhObject.P.stars.get(i).coverageHistory.add(0.0);
			}
		}
		//Total Coverege History tut
		bhObject.calculatePopulationsCoverage(bhObject.P);
		bhObject.P.totalCoverageHistory.add(Configuration.timeStamp);
		bhObject.P.totalCoverageHistory.add(bhObject.P.totalCoverage);
		Configuration.timeStamp ++;	
		keepMaxTotalCoverageSnap(bhObject, bhObjectBestSnap); //**

		for (int i = 0; i < Configuration.NUM_ITERATION; i++) {
			System.out.println("ITERASYON: " + (i+1));

			bhObject.moveStars(bhObject.P, bhObject.starBH);
			bhObject.updateFitness(bhObject.P);
			bhObject.R = bhObject.updateRadius(bhObject.P, bhObject.starBH);
			
			for (int j = 0; j < starListeBoyu; j++) {
				if(bhObject.P.stars.get(j).isAlive && bhObject.starBH != bhObject.P.stars.get(j)) {
					//System.out.println("dist: " + dist(bhObject.starBH, bhObject.P.stars.get(j)));
					if(dist(bhObject.starBH, bhObject.P.stars.get(j)) < bhObject.R) {
						bhObject.P.stars.get(j).isAlive = false;
						bhObject.P.stars.add(new Star());
						ekStarListeBoyu++;
						//System.out.println("ekStarListeBoyu:" + ekStarListeBoyu);
					}
					/*ratio = calculateMissedBranchRatio(bhObject.starBH,bhObject.P.stars.get(j));
					System.out.print("Ratio: " + ratio);
					if(!isBestInAtLeastOneBranch(bhObject.P, bhObject.P.stars.get(j))) {
						if(ratio < 1.0) {
							System.out.println("elendi: ");
							bhObject.P.stars.get(j).isAlive = false;
							//bhObject.P.stars.get(j).coverage = 0.0;
							Star newBornStar = new Star();
							while(true) {
								if(isDifferentFromBH(newBornStar, bhObject.starBH)) {
									bhObject.P.stars.add(newBornStar);
									ekStarListeBoyu++;
									break;
								}
								newBornStar = new Star();
							}					
						}		
					}*/
				}
				
			}

			
			//Yeni eklenen star icin
			bhObject.updateFitness(bhObject.P);	
			
			//Total Coverege History tut
			bhObject.calculatePopulationsCoverage(bhObject.P);
			bhObject.P.totalCoverageHistory.add(Configuration.timeStamp);
			bhObject.P.totalCoverageHistory.add(bhObject.P.totalCoverage);
			
			//**keepMaxTotalCoverageSnap(bhObject, bhObjectBestSnap); //**

			starListeBoyu = starListeBoyu + ekStarListeBoyu;
			ekStarListeBoyu = 0;
			System.out.println("starListeBoyu:" + starListeBoyu);

			
			
			//Starlar icin coverage history tut
			for (int j = 0; j < starListeBoyu; j++) {			
				bhObject.P.stars.get(j).coverageHistory.add(Configuration.timeStamp);			
				if(bhObject.P.stars.get(j).isAlive) {
					bhObject.P.stars.get(j).coverageHistory.add(1/bhObject.P.stars.get(j).coverage);
				}else {
					bhObject.P.stars.get(j).coverageHistory.add(0.0);
				}
			}
			Configuration.timeStamp ++;	

		}
		//Log yaz
		printResult(bhObject.P);
		//**bhObject.calculatePopulationsCoverage(bhObjectBestSnap.P);
		bhObject.calculatePopulationsCoverage(bhObject.P);

		//**sonDarbe(bhObject.P, bhObjectBestSnap.starBH,bhObjectBestSnap.P);
		//Total Coverege History tut
		//**bhObject.calculatePopulationsCoverage(bhObjectBestSnap.P);
		bhObject.calculatePopulationsCoverage(bhObject.P);
		bhObject.P.totalCoverageHistory.add(Configuration.timeStamp);
		//**bhObject.P.totalCoverageHistory.add(bhObjectBestSnap.P.totalCoverage);
		bhObject.P.totalCoverageHistory.add(bhObject.P.totalCoverage);
		
		//Starlar icin coverage history tut bura biraz anlamsiz oldu
		for (int j = 0; j < starListeBoyu; j++) {			
			bhObject.P.stars.get(j).coverageHistory.add(Configuration.timeStamp);			
			if(bhObject.P.stars.get(j).isAlive) {
				bhObject.P.stars.get(j).coverageHistory.add(1/bhObject.P.stars.get(j).coverage);
			}else {
				bhObject.P.stars.get(j).coverageHistory.add(0.0);
			}
		}
		
		endTime = System.currentTimeMillis();
		//**offlineAnalyze(bhObjectBestSnap.P, bhObjectBestSnap.starBH, Configuration.pathLog, Configuration.fileNameLog, Configuration.fileNameGraph);
		offlineAnalyze(bhObject.P, bhObject.starBH, Configuration.pathLog, Configuration.fileNameLog, Configuration.fileNameGraph);

		sketchGraph(bhObject.P, bhObject.starBH, Configuration.pathLog, Configuration.fileNameLog, Configuration.fileNameGraph);
		System.out.println("DURATION: "+ (endTime-startTime)/60000.0); //dakika

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
			offlineAnalyze(mbhObject.BHs.get(i).P, mbhObject.BHs.get(i).starBH, Configuration.pathLog, Configuration.fileNameLog + "_MBH" + (i+1), Configuration.fileNameGraph + "_MHB" + (i+1));
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
	public static void offlineAnalyze(Population P, Star starBH, String pathLog, String fileNameLog, String fileNameGrap) throws IOException {
		Path path = Paths.get(pathLog, fileNameLog);
		Files.deleteIfExists(path);
		Files.write(path, "".getBytes(), StandardOpenOption.CREATE_NEW);
		String logLine;
		List<Double> diffMissedBranchVector = new ArrayList<Double>(); 

		for (int i = 0; i < P.stars.size(); i++) {
			logLine = "StarID: " +  String.valueOf(P.stars.get(i).id + ", IsAlive: " + P.stars.get(i).isAlive
					+ ",\nMissedBranches: " + P.stars.get(i).branchMissedVector.toString()
					+ "\nCoverage: " + 1/P.stars.get(i).coverage);
			Files.write(path, logLine.getBytes(), StandardOpenOption.APPEND );
			Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);
			Files.write(path, ("StarVector:" + String.valueOf(P.stars.get(i).id) +" "+ P.stars.get(i).parametersVector.toString()).getBytes(), StandardOpenOption.APPEND );
			Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);
			
			if(!P.stars.get(i).isAlive) {
				for (int j = 0; j < P.stars.size(); j++) {
					if(P.stars.get(j).isAlive) {
						for (int k = 0; k < P.stars.get(i).branchMissedVector.size(); k++) {
							diffMissedBranchVector.add(P.stars.get(i).branchMissedVector.get(k) - P.stars.get(j).branchMissedVector.get(k));
						}
						Files.write(path, ("DiffVector(false-true): trueStarID:" +P.stars.get(j).id+ " "+ diffMissedBranchVector.toString()).getBytes(), StandardOpenOption.APPEND);
						Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);
						diffMissedBranchVector.clear();
					}
				}
			}
			Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);
			Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);

		}
		Files.write(path, ("BHStar ID: " + starBH.id).getBytes(), StandardOpenOption.APPEND);
		Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);
		Files.write(path, ("TotalCov: " + P.totalCoverage).getBytes(), StandardOpenOption.APPEND);
		Files.write(path, ("TotalDuration: " + (endTime-startTime)/60000.0).getBytes(), StandardOpenOption.APPEND);

		
		
		
		
	}
	public static void sketchGraph(Population P, Star starBH, String pathLog, String fileNameLog, String fileNameGrap) throws IOException {
		//sketch graph
				Path path2 = Paths.get(pathLog, fileNameGrap);
				Files.deleteIfExists(path2);
				Files.write(path2, "".getBytes(), StandardOpenOption.CREATE_NEW);
				
				Files.write(path2, P.totalCoverageHistory.toString().getBytes(), StandardOpenOption.APPEND );
				Files.write(path2, "\n".getBytes(), StandardOpenOption.APPEND );

				for (int i = 0; i < P.stars.size(); i++) {
					Files.write(path2, P.stars.get(i).coverageHistory.toString().getBytes(), StandardOpenOption.APPEND );
					Files.write(path2, "\n".getBytes(), StandardOpenOption.APPEND );
				}
	}

	
	public static double calculateMissedBranchRatio(Star starBH, Star S) {
		double SMinusStarBH;
		double starIsBetter = 0;
		double starIsWorse = 0;
		double starAndBHSame = 0;
		double betterOverWorse;
		for (int i = 0; i < starBH.branchMissedVector.size(); i++) {
			SMinusStarBH = S.branchMissedVector.get(i) - starBH.branchMissedVector.get(i);
			if(SMinusStarBH < 0) {
				starIsBetter = starIsBetter + Math.abs(SMinusStarBH);
			}
			else if(SMinusStarBH > 0) {
				starIsWorse = starIsWorse + SMinusStarBH;
			}
			else {// 0 ise - yani BH ile aynı ise, yani BH ye cok yaklasmissa
				starAndBHSame ++;
			}
			
		}
		//starIsWorse = starIsWorse + (starAndBHSame/2);
		if(starAndBHSame == starBH.branchMissedVector.size()) {
			return -1;
		}
		
		if(starIsWorse == 0) {  //kaldir
			betterOverWorse = 100;
		}else {
			betterOverWorse = (3.0)*starIsBetter/starIsWorse;
		}
		return betterOverWorse;
	}
	
	public static boolean isBestInAtLeastOneBranch(Population P, Star S) {
		
		boolean sIsBest= true;
		for (int j = 0; j < P.stars.get(0).branchMissedVector.size(); j++) { // her bir branch icin
			for (int i = 0; i < P.stars.size(); i++) {
				if(S.id != P.stars.get(i).id ) {
					if(S.branchMissedVector.get(j) >= P.stars.get(i).branchMissedVector.get(j)) { 
						sIsBest = false;	
						break;
					}
				}
				if(sIsBest) {
					return true;
				}
				sIsBest = true;
				}					
		}
		return false;
	}
	public static void sonDarbe(Population P, Star BH, Population PbestSnap) {
//		List <Star> additionalStars = new ArrayList<Star>();
		double minValue = 1000;
		boolean thereIsBetterThanBH = false;
		
		//son yidizsayisi-1 yildizi false yap.
//		for (int i = 0; i < P.stars.size(); i++) {
//			if(P.stars.get(i).id != BH.id) {
//				P.stars.get(i).isAlive = false;
//			}
//		}
		for (int j = 0; j < P.stars.get(0).branchMissedVector.size(); j++) {
			for (int i = 0; i < P.stars.size(); i++) {
				if(P.stars.get(i).id != BH.id) { //BH disindakilere bak
					if(P.stars.get(i).branchMissedVector.get(j) < BH.branchMissedVector.get(j)){
						if(P.stars.get(i).branchMissedVector.get(j) < minValue) {
							minValue = P.stars.get(i).branchMissedVector.get(j);
							//candidateStar = P.stars.get(i);
							thereIsBetterThanBH = true;
							System.out.println("Better star");
						}
					}
				}
			}
			
			if(thereIsBetterThanBH) {
				for (int k = 0; k <  P.stars.size(); k++) {
					if(P.stars.get(k).branchMissedVector.get(j).equals(minValue)) {
						Star candidateStar = P.stars.get(k);
						System.out.println("Canlanan star: "+ candidateStar.id);
						candidateStar.isAlive = true;
						PbestSnap.stars.add(candidateStar);
					}
				}	
				minValue = 1000;
				thereIsBetterThanBH = false;
			}
	}
	
	System.out.print(false);
	}
	public static boolean isDifferentFromBH(Star newBorn, Star BH) {
		boolean isDifferent = false;
		
		for (int i = 0; i < BH.parametersVector.size(); i++) {
			if(!BH.parametersVector.get(i).equals(newBorn.parametersVector.get(i))) {
				isDifferent = true;
				break;
			}
		}
		return isDifferent;
	}
	public static void keepMaxTotalCoverageSnap(BH bhObject, BH bhObjectBestSnap) {
		
		if(bhObject.P.totalCoverage > bhObject.maxPCoverage) {
			bhObject.maxPCoverage = bhObject.P.totalCoverage;
			bhObjectBestSnap.P.totalCoverageHistory.clear();
			bhObjectBestSnap.P.stars.clear();
			
			
			//starBH kopyala
			Star s_bh = new Star();
			s_bh.parametersVector.clear(); //default initilize ettigi icin temizlemem lazim.
			s_bh.coverage = bhObject.starBH.coverage;
			s_bh.id = bhObject.starBH.id;
			s_bh.isAlive = bhObject.starBH.isAlive;
			s_bh.isNew = bhObject.starBH.isNew;
			for (int j = 0; j < bhObject.starBH.branchMissedVector.size(); j++) {
				s_bh.branchMissedVector.add(bhObject.starBH.branchMissedVector.get(j));
			}
			for (int j = 0; j < bhObject.starBH.coverageHistory.size(); j++) {
				s_bh.coverageHistory.add(bhObject.starBH.coverageHistory.get(j));
			}
			for (int j = 0; j < bhObject.starBH.parametersVector.size(); j++) {
				s_bh.parametersVector.add(bhObject.starBH.parametersVector.get(j));
			}
			bhObjectBestSnap.starBH = s_bh;
			
			//P kopyala
			bhObjectBestSnap.P.totalCoverage = bhObject.P.totalCoverage; 
			for (int i = 0; i < bhObject.P.stars.size(); i++) {
				Star s = new Star();
				s.parametersVector.clear(); //default initilize ettigi icin temizlemem lazim.
				s.coverage = bhObject.P.stars.get(i).coverage;
				s.id = bhObject.P.stars.get(i).id;
				s.isAlive = bhObject.P.stars.get(i).isAlive;
				s.isNew = bhObject.P.stars.get(i).isNew;
				for (int j = 0; j < bhObject.P.stars.get(i).branchMissedVector.size(); j++) {
					s.branchMissedVector.add(bhObject.P.stars.get(i).branchMissedVector.get(j));
				}
				for (int j = 0; j < bhObject.P.stars.get(i).coverageHistory.size(); j++) {
					s.coverageHistory.add(bhObject.P.stars.get(i).coverageHistory.get(j));
				}
				for (int j = 0; j < bhObject.P.stars.get(i).parametersVector.size(); j++) {
					s.parametersVector.add(bhObject.P.stars.get(i).parametersVector.get(j));
				}
				bhObjectBestSnap.P.stars.add(s);
			}
			for (int i = 0; i < bhObject.P.totalCoverageHistory.size(); i++) {
				bhObjectBestSnap.P.totalCoverageHistory.add(bhObject.P.totalCoverageHistory.get(i));
			}
			
		}
	}
}
