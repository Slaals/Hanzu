import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class Practice {
	
	private final static String API_URL = "https://glosbe.com/gapi/translate?from=cmn&dest=eng&format=json&phrase=";
	
	public int currentIndex = -1;
	
	private String hanzi;
	private String translate;
	private String desc;
	
	public LinkedList<String> selectedWords;
	
	public Practice() {
		selectedWords = new LinkedList<String>();
		
		selectWords();
	}
	
	private void selectWords() {
		String line;
		try {
			BufferedReader br = Dictionary.getInstance().getReader();
			while((line = br.readLine()) != null) {
				selectedWords.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.shuffle(selectedWords);
	}
	
	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	        sb.append((char) cp);
	    }
	    return sb.toString();
	}
	
	private void setData() {
		String sURL = API_URL + hanzi;
		try {
			InputStream is = new URL(sURL).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		    
		    Object obj = JSONValue.parse(readAll(br));
		    JSONObject jsonObject = (JSONObject) obj;
		    
		    JSONArray info = (JSONArray) ((JSONArray) jsonObject.get("tuc"));

		    if(info.size() > 0) {
		    	JSONObject translateObj = (JSONObject) info.get(0);
		    	
		    	if(translateObj.containsKey("phrase")) {
		    		translate = (String) ((JSONObject) translateObj.get("phrase")).get("text");
		    	} else if(translateObj.containsKey("text")) {
		    		translate = (String) translateObj.get("text");
		    	} else {
		    		translate = "Pfff";
		    	}
		    	
		    	if(((JSONObject) info.get(0)).get("meanings") != null) {
		    		desc = (String) ((JSONObject)((JSONArray) ((JSONObject) info.get(0)).get("meanings")).get(0)).get("text");
		    	}
		    	
		    }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
	
	public String getDesc() {
		return desc;
	}
	
	public String getTranslate() {
		return translate;
	}

	public String getHanzi() {
		return hanzi;
	}
	
	public void next() {
		currentIndex += 1;
		
		if(currentIndex >= selectedWords.size()) {
			currentIndex = 0;
		}
		
		if(selectedWords.size() > 0) {
			hanzi = selectedWords.get(currentIndex);
			desc = "";
			translate = "";
			
			setData();
		}
	}

}
