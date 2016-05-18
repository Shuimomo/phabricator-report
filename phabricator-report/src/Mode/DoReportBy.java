package Mode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import func.JdbcConnection;
import func.ReportInDB;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class DoReportBy {
	private InputStream jrxml;
	private JdbcConnection con;
	private ReportInDB report;
	@SuppressWarnings("rawtypes")
	private Map parameters = new HashMap();
	private JasperDesign jasperDesign;
	private JasperReport jasperReport;
	private String chemin;

	/**
	 * Constructor(prepare jdbc connection&compile jrxml)
	 * @param String[] config
	 */
	@SuppressWarnings("unchecked")
	public DoReportBy(String[] config){
		//get jrxml;
		try {
			jrxml = new FileInputStream(config[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//set connection to DB
		con = new JdbcConnection(config[1], config[2], config[3]);
		//pass connection to ReportInDB, prepare for getting result of request
		report = new ReportInDB(con);
		//get client and project name
		String client = config[4];
		String projet = config[5];

		try {
			jasperDesign = JRXmlLoader.load(jrxml);
		} catch (JRException e1) {
			System.out.println("Cannot find " + config[0]);
			System.exit(1);
		}
		try {
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
		} catch (JRException e) {
			e.printStackTrace();
		}

		parameters.put("client", client);
		parameters.put("projet",projet);

		chemin = config[6];

		System.out.println("Ready");
	}

	/**
	 * create report(s) of id(s) given
	 * @param String idset
	 * @throws JRException
	 */
	public void byID(List<String> idlist) throws JRException{
		Connection connection = con.openConnection();
		List<String> allID = report.getAllID();
		List<String> idset = new ArrayList<String>();
		/*
		 * get all the ids that can be found in DB, put into a list
		 */
		for(int j=0;j<idlist.size();j++){
			String id = idlist.get(j);
			if(allID.contains(id)){
				idset.add(id);
			}
			//if cannot find this id, send a message and go to the next
			else{
				System.out.println("Cannot find revision" + id);
				continue;
			}
		}
		if(idset.size()!=0){
			this.doReport(idset, connection);
		}
		con.close();
	}

	/**
	 * create ths reports of all the revisions in version(s) given
	 * @param List<String> version
	 * @throws JRException
	 */
	public void byVersion(List<String> versions) throws JRException{
		Connection connection = con.openConnection();
		for(int i = 0;i<versions.size();i++){
			String version = versions.get(i);
			System.out.println("Version "+version+" :");
			String checkversion = version +" ";
			List<String> idlist = new ArrayList<String>();
			//to get the ids of all the revisions in this version 
			idlist = report.getIDsOfVersion(checkversion);

			//if cannot find any revision, send a message
			if(idlist.size()==0){
				System.out.println("Cannot find any revision of version " + version);
				continue;
			}
			else{
				if(idlist.size()==1){
					System.out.println(idlist.size() + " revision found");
				}
				else{
					System.out.println(idlist.size() + " revisions found");
				}
				this.doReport(idlist, connection);
			}
		}
		con.close();
	}

	/**
	 * every report id in the list refer to a revision(always can be found), for every revision, fill and export its report
	 * @param List<String> idset, Connection connection
	 */
	@SuppressWarnings("unchecked")
	private void doReport(List<String> idset, Connection connection) throws JRException {
		for(int i=0;i<idset.size();i++){
			String id = idset.get(i);
			long getid = Long.parseLong(id);
			parameters.put("idreport", getid);

			//create report
			JasperPrint jasperPrint = null;
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

			// set report locate&name
			report.getTiltleOf(id);
			String foldername = report.getFolderName();
			String name = report.getFileName();
			String filepath=null;

			//if the title is not in good form so that we cannot get the version or name,
			//give a default name
			if(name == null||foldername == null){
				System.out.println("The title of revision " + id + " is not in right form");
				if(name==null||name == " "){
					name = "D" + id;
				}
			}

			//if cannot get version, put in the default folder
			if(foldername == null||foldername == " "){
				filepath = chemin +name +".pdf";
			}
			//for each time create a new folder for this version
			else{
				new File(chemin+foldername).mkdir();
				filepath = chemin+foldername + "/" +name+".pdf";
			}

			//export the report in format pdf
			JasperExportManager.exportReportToPdfFile(jasperPrint,filepath);
			System.out.println("Your revision D" + id +" is placed in " + chemin +foldername + ", with name '" +name+"'");
		}
	}
}
