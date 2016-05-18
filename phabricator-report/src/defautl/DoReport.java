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
		if(args.length==1){
			beginAt.step0(config);
		}
		else{
			if(args.length==2){
				beginAt.step1(config, args[1]);
			}
			else{
				List<String> paras = new ArrayList<String>();
				for(int i=2;i<args.length;i++){
					paras.add(args[i]);
				}
				beginAt.step2(config, args[1], paras);
			}
		}
	}
}
