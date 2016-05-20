package Mode;

import java.util.List;
import java.util.Scanner;

import func.Formator;
import net.sf.jasperreports.engine.JRException;

public class ParameterStep {
	
	/**
	 * Only know info of configuration, should begin by asking the mode
	 * @param String[] config
	 * @throws JRException
	 */
	public void step1(String[] config) throws JRException{
		Scanner sc = new Scanner(System.in);
		String message = "Choose the mode :  1.by version  2.by report id";
		System.out.println(message);
		String mode = sc.nextLine();
		//when we have got the mode, go to next step
		this.step2(config, mode);
		sc.close();
	}

	/**
	 * Know info of configuration and mode, need to ask for the parameters(ids or versions)
	 * @param String[] config
	 * @param String mode
	 * @throws JRException
	 */
	public void step2(String[] config, String mode) throws JRException{
		/*
		 *  if cannot recognize the mode (enter other things), should give a message and ask again
		 */
		if(!mode.equals("1")&&!mode.equals("2")){
			System.out.println("Invalid option '" + mode +"'");
			this.step1(config);
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
				this.step3(config, mode, versions);				
			}

			/*
			 *  if choose 2-by report id, go to execute DeReportBy.byID
			 */
			if(mode.equals("2")){
				System.out.println("Please enter the report id(s) you want");
				String idstring= sc.nextLine();
				List<String> ids= getparas.getList(idstring);
				this.step3(config, mode, ids);
			}
			sc.close();
		}
	}

	/**
	 * Have all the info : config,mode and parameters, begin to do report
	 * @param String[] config
	 * @param String mode
	 * @param List<String >paras
	 * @throws JRException
	 */
	public void step3(String[] config, String mode, List<String> paras) throws JRException{
		if(!mode.equals("1")&&!mode.equals("2")){
			System.out.println("Cannot understand '" + mode +"'");
			this.step1(config);
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
		System.exit(1);
	}
}