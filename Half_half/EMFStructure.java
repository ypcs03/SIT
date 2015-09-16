import java.io.File;
import java.io.IOException;

/**
 * EMFStructure used to generate a EMFStructrue Class in the output file
 */
public class EMFStructure {
	/**
	 * Using the input file and generate a EMFStructure Class
	 * @param file Input File
	 * @throws IOException
	 */
	public EMFStructure(File file) throws IOException{
		InputReader ir = new InputReader(file);
		Writer java = new Writer();
		DataStructure ds = new DataStructure();
        
		java.write("/**\n");
		java.write(" * EMF_Structure with the group attributes and all the aggregate functions in it\n");
		java.write(" * Also when we get avg aggregate functions we will add a sum and count func to it\n");
		java.write(" */\n");
		java.write("class EMFStructure{\n");
		
		//Assign the grouping attributes in the EMFStructure
		for(int i=0;i<ir.getV().size();i++){
			for(int j=0;j<ds.getStrName().size();j++){
				if(ir.getV().get(i).equals(ds.getStrName().get(j))){
					java.write("\t"+ ds.getStrType().get(j)+" "+ir.getV().get(i)+";\n");
				}
			}
        	
        }
		
		//Assign the functions in the EMFStructure with initial value
        for(int i=0;i<ir.getFunc().size();i++){
        	for(int j=0;j<ir.getFunc().get(i).size();j++){
        		String temp = ir.getF().get(i).get(j);
        		String temp1 = ir.getFunc().get(i).get(j);
        		if (temp.charAt(1) != '_'){
        			java.write("\tdouble "+temp1+"=0;\n");
        			if(temp.contains("avg")){
        				java.write("\tdouble sum_avg=0.0;\n");
            			java.write("\tint count_avg=0;\n");
        			}
        		}else if(temp.contains("avg")){ //When we get the avg function we will add a sum and count to it
        			java.write("\tdouble "+temp1+";\n");
        			java.write("\tdouble sum_avg_"+temp.substring(0, 1)+";\n");
        			java.write("\tint count_avg_"+temp.substring(0, 1)+";\n");
        		}else if(temp.contains("count")){
        			java.write("\tint "+temp1+";\n");
        		}else{
        			java.write("\tdouble "+temp1+";\n");
        		}		
        	}
        }
        
        java.write("\n\t//Generator for the EMFStructure");
        java.write("\n\tpublic EMFStructure(){\n");
        
        for(int i=0;i<ir.getV().size();i++){
			for(int j=0;j<ds.getStrName().size();j++){
				if(ir.getV().get(i).equals(ds.getStrName().get(j))){
					java.write("\t"+ ds.getStrType().get(j)+" "+ir.getV().get(i)+";\n");
				}
			}
        	
        }
        for(int i=0;i<ir.getFunc().size();i++){
        	for(int j=0;j<ir.getFunc().get(i).size();j++){
        		String temp = ir.getF().get(i).get(j);
        		String temp1 = ir.getFunc().get(i).get(j);
        		if (temp.charAt(1) != '_'){
        			java.write("");
        		}else if(temp.contains("avg")){
        			java.write("\t\tdouble "+temp1+"=0.0;\n");
        			java.write("\t\tdouble sum_avg_"+temp.substring(0, 1)+"=0.0;\n");
        			java.write("\t\tint count_avg_"+temp.substring(0, 1)+"=0;\n");
        		}else if(temp.contains("count")){
        			java.write("\tint "+temp1+"=0;\n");
        		}else{
        			java.write("\t\tdouble "+temp1+"=0.0;\n");
        		}
        		
        		
        	}
        }
        java.write("\t}\n");
        java.write("}\n");
    
        java.close();
    }
}
