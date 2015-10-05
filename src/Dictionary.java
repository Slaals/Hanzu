import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Dictionary {
	
	private static Dictionary instance = null;
	
	private File file;
	
	private Dictionary() {
		file = new File("dictionary.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public BufferedReader getReader() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return br;
	}
	
	public boolean isWordExists(String word) {
		String line;
		try {
			BufferedReader br = getReader();
			while((line = br.readLine()) != null) {
				if(line.equals(word)) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void addEntry(String hanzi) {
		try {
			FileWriter fr = new FileWriter(file.getName(), true);
			BufferedWriter bw = new BufferedWriter(fr);
			bw.append(hanzi);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Dictionary getInstance() {
		if(instance == null) {
			return new Dictionary();
		} else {
			return instance;
		}
	}
}
