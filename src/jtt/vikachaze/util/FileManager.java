package jtt.vikachaze.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileManager {
	public static void writeToFile(String filename, String data) {
		File file = new File(filename);
		
		try {
			if (!file.exists()) { file.createNewFile(); }
			
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(data + "\n");
			
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void cleanFile(String filename) {
		File file = new File(filename);
		if (file.exists()) {
			file.delete();
		}
	}
	
	public static List<String> readFromFile(String filename) {
		File file = new File(filename);
		
		if (!file.exists()) {
			return null;
		}
		
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			List<String> dataLines = new LinkedList<String>();
			
			String line;
			while ((line = br.readLine()) != null ) {
				dataLines.add(line);
			}
			
			br.close();
			fr.close();
			return dataLines;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
