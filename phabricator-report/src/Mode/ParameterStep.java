package Mode;

import java.util.List;
import java.util.Scanner;

import func.Formator;
import net.sf.jasperreports.engine.JRException;

public class ParameterStep {
	public void step0(String[] config) throws JRException{
		Scanner sc = new Scanner(System.in);
		String message = "Choose the mode :  1.by version  2.by report id";
		System.out.println(message);
		String mode = sc.nextLine();
		this.step1(config, mode);
		sc.close();
	}

	public void step1(String[] config, String mode) throws JRException{
		/*
		 *  if cannot recognize the mode (enter other things), should give a message and ask again
		 */
		if(!mode.equals("1")&&!mode.equals("2")){
			System.out.println("Cannot understand '" + mode +"'");
			this.step0(config);
		}

		else{
			Scanner sc = new Scanner(System.in);
			Formator getparas = new Formator();
			/*
			 * if choose 1-by version, go to execute DoReportBy.byVersion
			 */
			if(mode.equals("1")){
				System.out.println("Please enter the version(s) you want");
				String versionstring = sc.nextLine();
				List<String> versions= getparas.getList(versionstring);
				this.step2(config, mode, versions);				
			}

			/*
			 *  if choose 2-by report id, go to execute DeReportBy.byID
			 */
			if(mode.equals("2")){
				System.out.println("Please enter the report id(s) you want");
				String idstring= sc.nextLine();
				List<String> ids= getparas.getList(idstring);
				this.step2(config, mode, ids);
			}
			sc.close();
		}
	}

	public void step2(String[] config, String mode, List<String> paras) throws JRException{
		if(!mode.equals("1")&&!mode.equals("2")){
			System.out.println("Cannot understand '" + mode +"'");
			this.step0(config);
		}
		else{
			DoReportBy doInMode = new DoReportBy(config);
			if(mode.equals("1")){
				doInMode.byVersion(paras);
			}
			if(mode.equals("2")){
				doInMode.byID(paras);
			}
		}
	}
}
