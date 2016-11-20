package pt.isel.ccd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class EntropiaCalculo {
	static double log(double x) {
		return (Math.log(x) / Math.log(2));
	}

	private final static String path = "/Users/Mig/Dropbox/Mestrado Eng. Informática/1ºAno - 1ºSemestre/Compressão e Codificação de Dados/ficheiros/";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File file = new File(path + "alice29.txt");

		byte[] bFile = new byte[(int) file.length()];

		HashMap<String, Double> pMap = readFileMem(file, bFile);

		// Gravar ficheiro
		/*
		 * try { writeFile(bFile, pMap);
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

	private static HashMap<String, Double> readFile(File file, byte[] bFile) {
		FileInputStream fileInputStream;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		HashMap<String, Double> pMap = new HashMap<String, Double>();

		try {
			// convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

			for (int i = 0; i < bFile.length; i++) {
				Integer cur = map.get(String.valueOf(bFile[i] & 0xFF));
				if (cur == null) {
					map.put(String.valueOf(bFile[i] & 0xFF), 1);
				} else {
					map.put(String.valueOf(bFile[i] & 0xFF), cur + 1);
				}

			}

			double H = calcH(bFile, map, pMap);

			System.out.println(H);

			System.out.println("Done " + bFile.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pMap;
	}

	private static double calcH(byte[] bFile, HashMap<String, Integer> map,
			HashMap<String, Double> pMap) {
		double H = 0;

		for (int h = 0; h <= 255; h++) {
			Integer simblo = map.get(String.valueOf(h));
			if (simblo != null) {
				double Px = (simblo.doubleValue() / bFile.length);
				double log = log(1 / Px);
				H = H + (Px * log);
				System.out.println(h + "," + Px + "\n");

				pMap.put(String.valueOf(h), Px);
				System.out.println("index = " + h + " prob= "
						+ pMap.get(String.valueOf(h)));

			} else {
				// System.out.println(h + "," + 0+"\n");
			}
		}
		return H;
	}

	private static HashMap<String,HashMap<String,Integer>> readFileMem(File file, byte[] bFile) {
		FileInputStream fileInputStream;
		HashMap<String, HashMap<String, Integer>> map = new HashMap<String, HashMap<String, Integer>>();
		
		HashMap<String,HashMap<String,Double>> pMap = new HashMap<String, HashMap<String, Double>>();
	
		HashMap<String,Double> occurMap = new HashMap<String, Double>();


		try {
			// convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

			for (int i = 1; i < bFile.length; i++) {
				HashMap<String, Integer> cur = map.get(String.valueOf(bFile[i]& 0xFF));
				if (cur == null) {
					HashMap<String, Integer> secMap = new HashMap<String, Integer>();
					secMap.put(String.valueOf(bFile[i-1]& 0xFF), 1);
					map.put(String.valueOf(bFile[i-1]& 0xFF), secMap);
					System.out.println(i);
				} else {
					Integer lastValue = cur.get(String.valueOf(bFile[i-1]& 0xFF));
					if(lastValue == null){
						lastValue = 0;
					}
					cur.put(String.valueOf(bFile[i-1] & 0xFF), lastValue + 1);
				}
			
			}

			for (Entry<String, HashMap<String, Integer>> entry : map.entrySet()) {
				HashMap<String, Integer> cur = entry.getValue();
				HashMap<String,Double> pCur=pMap.get(entry.getKey());
				for (Entry<String, Integer> b : cur.entrySet()) {
					System.out.println("["+entry.getKey() +","+ b.getKey()+"] = " + b.getValue());
					if(pCur == null){
						pCur = new HashMap<String, Double>();
						b.getValue()
						pCur.put(entry.get)
					}
				}
			}
			
//			System.out.println(H);
//
//			System.out.println("Done " + bFile.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pMap;
	}

	private static void writeFile(byte[] bFile, HashMap<String, Double> pMap)
			throws FileNotFoundException, IOException {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < bFile.length; i++) {

			builder.append((char) (getSym(pMap)));

		}

		FileOutputStream out = new FileOutputStream(
				new File(
						"/Users/Mig/Dropbox/Mestrado Eng. Informática/1ºAno - 1ºSemestre/Compressão e Codificação de Dados/ficheiros/alice29New.txt"));
		out.write(builder.toString().getBytes());
		out.close();
	}

	private static int getSym(HashMap<String, Double> pMap) {
		Random rand = new Random();
		Double valRand = rand.nextDouble();
		Double sumEntry = 0.0;

		for (Map.Entry<String, Double> entry : pMap.entrySet()) {
			sumEntry += entry.getValue();
			if (sumEntry >= valRand) {
				return Integer.valueOf(entry.getKey());
			}

		}

		return 0;
	}

}
