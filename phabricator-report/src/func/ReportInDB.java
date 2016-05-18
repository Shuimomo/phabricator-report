package func;

import java.util.ArrayList;
import java.util.List;

public class ReportInDB {
	private String title = null;
	private JdbcConnection con;

	public ReportInDB(JdbcConnection con){
		this.con = con;
	}
	
	/**
	 * get the title of report from DB and set ReportInDB.title
	 * @param String id(the report id which we want to create the pdf)
	 */
	public void getTiltleOf(String id){
		String request = "select phabricator_differential.differential_revision.title  from phabricator_differential.differential_revision where phabricator_differential.differential_revision.id = "+id;
		try {
			title = con.execute(request).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * get the version as the name of new folder from title
	 * @return String
	 */
	public String getFolderName(){
		String version = null;
		int nbTi = 0;
		int begin=0;
		int fin=0;
		for(int i=0;i<title.length()&&(nbTi < 3);i++){
			if(title.charAt(i) ==  '-'){
				nbTi ++;
			}
			if(title.charAt(i) ==  '-'&&(nbTi == 1)){
				begin = i+2;
			}
			if(title.charAt(i)=='-'&&(nbTi ==2)){
				fin = i-1;
			}
		}
		if(begin != 0 && fin!=0 && begin<fin){
		version = title.substring(begin, fin);
		}
		return version;
	}

	/**
	 * get the "fourniture" as the name of pdf(DMxxxxxx) from title
	 * @return String
	 */
	public String getFileName(){
		String fourniture = null;
		int nbTi = 0;
		int begin=0;
		int fin=0;
		for(int i=0;i<title.length()&&(nbTi < 3);i++){
			if(title.charAt(i) ==  '-'){
				nbTi ++;
			}
			if(title.charAt(i) ==  '-'&&(nbTi == 2)){
				begin = i+2;
			}
			if(title.charAt(i)=='-'&&(nbTi ==3)){
				fin = i-1;
			}
		}
		if(begin != 0 && fin!=0 && begin<fin){
		fourniture = title.substring(begin, fin);
		}
		return fourniture;
	}
	
	/**
	 * get the list of id of certain version
	 * @param String version
	 * @return List<String> idlist
	 */
	public List<String> getIDsOfVersion(String version){
		String request = "select phabricator_differential.differential_revision.id  from phabricator_differential.differential_revision where phabricator_differential.differential_revision.title like '%"+version +"%'";
		List<String> idlist = new ArrayList<String>();
		try {
			idlist = con.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idlist;
	}
	
	/**
	 * get the list of id of all reports
	 * @return List<String> allIDs
	 */
	public List<String> getAllID(){
		String request = "select phabricator_differential.differential_revision.id  from phabricator_differential.differential_revision";
		List<String> allIDs = new ArrayList<String>();
		try {
			allIDs = con.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allIDs;
	}
}
