package defautl;

import java.util.ArrayList;
import java.util.List;
import Mode.ParameterStep;
import func.Formator;

public class DoReport {
	public static void main(String[] args) throws Exception {
		 
		if(args.length==0){
			System.out.println("Need configuration file");
			System.exit(1);
		}

		//get configuration document
		Formator configfile = new Formator();
		String[] config= configfile.getContentToString(args[0]);

		ParameterStep beginAt = new ParameterStep();
		
		//have only 1 argument(should be config), go to step1
		if(args.length==1){
			beginAt.step1(config);
		}
		else{
			//have 2 arguments(should be config.txt and mode), go to step2
			if(args.length==2){
				beginAt.step2(config, args[1]);
			}
			else{
				//have more than 2 arguments(should be config, mode, and all the rest are parameters)
				//= have all the information to create reports, go directly to step3
				List<String> paras = new ArrayList<String>();
				for(int i=2;i<args.length;i++){
					paras.add(args[i]);
				}
				beginAt.step3(config, args[1], paras);
			}
		}
	}
}
