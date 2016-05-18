package func;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Formator {
	
	/**
	 * to read the file with name(path) given line by line
	 * @param String filename
	 * @return String[] content
	 */
	public String[] getContentToString(String filename) throws FileNotFoundException{
		String[] content = new String[7];
		InputStream is = new FileInputStream (filename);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = null;
		int i=0;

		//read 2/4/6/8/10/12 line and put into config, ignore those explanatory lines
		while(i<7){
			try {
				line =reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			content[i]=line;
			i++;
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * to get list of id/version from a String
	 * @param String paraString
	 * @return List<String> paralist
	 */
	public List<String> getList(String parastring){
		List<String> paralist = new ArrayList<String>();
		int first = 0;
		int j = 0;
		String para = "";
		Boolean espace = true;
		for(first= 0; espace;first++){
			if(parastring.charAt(first) != ' '){
				espace = false;
			}
		}
		for(int i=first-1;i<parastring.length();i++){
			if(parastring.charAt(i) !=' '){
				para += parastring.charAt(i);
			}
			else{
				paralist.add(j, para);
				para = "";
				j++;
			}
		}
		paralist.add(j, para);

		return paralist;
	}
}
