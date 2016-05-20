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
		else{
			if(!args[0].equals("-config")){
				System.out.println("Invalid option: "+ args[0]+". Try -config");
				System.exit(1);
			}
			else{
				ParameterStep beginAt = new ParameterStep();

				if(args.length>1){
					//get configuration document
					Formator configfile = new Formator();
					String[] config= configfile.getContentToString(args[1]);

					if(args.length>2){
						String mode = args[2];
						if(args[2].equals("-version")){
							mode = "1";
						}
						if(args[2].equals("-id")){
							mode = "2";
						}
						if(args.length>3){
							//have more than 2 arguments(should be config, mode, and all the rest are parameters)
							//= have all the information to create reports, go directly to step3
							List<String> paras = new ArrayList<String>();
							for(int i=3;i<args.length;i++){
								paras.add(args[i]);
							}
							beginAt.step3(config, mode, paras);
						}
						//if don't have more arguments = only have config and mode, go to step2
						beginAt.step2(config, mode);
					}
					//if don't have more than 2 arguments = only have config, go to step1
					beginAt.step1(config);
				}
				System.out.println("Need configuration file");
			}
		}
	}
}