import java.util.ArrayList;

public class Scan_0 {
	private ArrayList<String> groupingAttr=new ArrayList<String>();
	private ArrayList<String> global=new ArrayList<String>();
	private String where="";
	int groupingAttrSize=0;
	int globalSize=0;	
	DataStructure table = new DataStructure();
	
	public Scan_0(ArrayList<String> gAttr,ArrayList<String> glo,String wh){
		groupingAttr=gAttr;
		global=glo;
		where=wh;
	    groupingAttrSize=groupingAttr.size();
		globalSize=global.size();
		Writer file=new Writer();
		
		file.write("\n/*******************************Scan Zero***************************************/\n");
        file.write("\t database.executeSql(\"SELECT * FROM sales\"); //retrieve data from database\n");	
	    file.write("\t ArrayList<EMFStructure> emf_array=new ArrayList<EMFStructure>(); //create all the combinations of grouping attributes\n");
	    file.write("\t record=database.NextRecord();//get one tuple\n");
	    file.write("\t while(record != null){\n"	    		
	    		+  "\t\t boolean exist=false;\n");
	    
	    if(where.length() != 0){
            ArrayList<String> Attributes = table.getStrName();

            where = where.replaceAll("and", "&&");
            where = where.replaceAll("or", "||");
            //replace the quant == 231 to record.quant == 231
            for(int j = 0; j < Attributes.size(); j ++){
                where = where.replaceAll(Attributes.get(j) + ".equals", "record." + Attributes.get(j) + "equals");
                where = where.replaceAll(Attributes.get(j) + "[\\s]*<", "record." + Attributes.get(j) + " <");
                where = where.replaceAll(Attributes.get(j) + "[\\s]*<=", "record." + Attributes.get(j) + " <=");
                where = where.replaceAll(Attributes.get(j) + "[\\s]*>=", "record." + Attributes.get(j) + " >=");
                where = where.replaceAll(Attributes.get(j) + "[\\s]*>", "record." + Attributes.get(j) + " >");
                where = where.replaceAll(Attributes.get(j) + "[\\s]*==", "record." + Attributes.get(j) + " ==");
            }

            file.write("\t\t\tif( " +  where + "){\n");
        }
	    
	    file.write("\t\t\t for(int i=0;i<emf_array.size();i++){\n");
	    String type="";
	    for(int i=0;i<table.getStrData().size();i++){
	    	if(table.getStrData().get(i).contains(groupingAttr.get(0))){
	    		String[] tokens=table.getStrData().get(i).split("[ ]");
	    		type=tokens[0];
	    		break;
	    	}
	    }
	    if(type.equals("int"))
	    	file.write("\t\t\t\t if(emf_array.get(i)."+groupingAttr.get(0)+" == record."+groupingAttr.get(0));
	    else
	    	file.write("\t\t\t\t if(emf_array.get(i)."+groupingAttr.get(0)+".equals(record."+groupingAttr.get(0)+")");
	    int j=1;
	    while(j<groupingAttrSize){
	    	for(int i=0;i<table.getStrData().size();i++){
		    	if(table.getStrData().get(i).contains(groupingAttr.get(j))){
		    		String[] tokens=table.getStrData().get(i).split("[ ]");
		    		type=tokens[0];
		    		break;
		    	}
		    }
	    	if(type.equals("int"))
		    	file.write("\n\t\t\t\t && emf_array.get(i)."+groupingAttr.get(j)+" == record."+groupingAttr.get(j));
		    else
		    	file.write("\n\t\t\t\t && emf_array.get(i)."+groupingAttr.get(j)+".equals(record."+groupingAttr.get(j)+")");
	    	j++;
	    }
	    file.write("){ //if the combination exists, then update the global aggregation functions\n");
	    file.write("\t\t\t\t\t exist=true;\n");
	    int m=0;
	    boolean avg=false;
	    while(m<globalSize){
	    	switch(global.get(m).substring(0, 3).toUpperCase()){
	    	case "SUM":
	    		file.write("\t\t\t\t\t emf_array.get(i)."+global.get(m)+" += record.quant;\n");
	    	    break;
	    	case "MAX":
	    		file.write("\t\t\t\t\t emf_array.get(i)."+global.get(m)+" = Math.max(emf_array.get(i)."+global.get(m)+",record.quant);\n");
	    		break;
	    	case "MIN":
	    		file.write("\t\t\t\t\t emf_array.get(i)."+global.get(m)+" = Math.min(emf_array.get(i)."+global.get(m)+",record.quant);\n");
	    		break;
	    	case "COU":
	    		file.write("\t\t\t\t\t emf_array.get(i)."+global.get(m)+"++;\n");
	    		break;
	    	case "AVG":
	    		file.write("\t\t\t\t\t emf_array.get(i).sum_avg"+" += record.quant;\n");
	    		file.write("\t\t\t\t\t emf_array.get(i).count_avg++;\n");
	    		avg=true;
	    		break;
	    	}
	    	m++;
	    }
	    		
	    file.write("\t\t\t\t\t break;\n"
	    		+  "\t\t\t\t }\n"
	    		+  "\t\t\t }\n");
	    
	    file.write("\t\t\t if(!exist){ //if the combination doesn't exist, then create a new entry and add it to emf_array\n"
	    		+  "\t\t\t\t EMFStructure emf=new EMFStructure();");
	    j=0;
	    while(j<groupingAttrSize){
	    	file.write("\n\t\t\t\t emf."+groupingAttr.get(j)+" = record."+groupingAttr.get(j)+";");
	    	j++;
	    }
	    file.write("\n");
	    m=0;	    
	    while(m<globalSize){
	    	switch(global.get(m).substring(0, 3).toUpperCase()){
	    	case "SUM":
	    		file.write("\t\t\t\t emf."+global.get(m)+" = record.quant;\n");
	    	    break;
	    	case "MAX":
	    		file.write("\t\t\t\t emf."+global.get(m)+" = record.quant;\n");
	    		break;
	    	case "MIN":
	    		file.write("\t\t\t\t emf."+global.get(m)+" = record.quant;\n");
	    		break;
	    	case "COU":
	    		file.write("\t\t\t\t emf."+global.get(m)+"=1;\n");
	    		break;
	    	case "AVG":
	    		file.write("\t\t\t\t emf.sum_avg = record.quant;\n");
	    		file.write("\t\t\t\t emf.count_avg=1;\n");
	    		break;
	    	}
	    	m++;
	    }   	
	    		    	
	    file.write("\t\t\t\t emf_array.add(emf);\n"
	    		+  "\t\t\t }\n");
	    if(where.length()!=0)
	    	file.write("\t\t}\n");
	    file.write("\t record=database.NextRecord();\n"
	    		+  "\t }\n"); 
	    if(avg){
	    	file.write("\t for(int i=0;i<emf_array.size();i++){\n"
	    			+  "\t\t emf_array.get(i).avg_quant=emf_array.get(i).sum_avg/emf_array.get(i).count_avg;\n"
	    			+  "\t}\n");
	    }
	      	         			
	   file.close();
	   
	}
	
/*
	public static void main(String[] args){
		ArrayList<String> groupingAtt = new ArrayList<String>();
		groupingAtt.add("cust");
		groupingAtt.add("prod");
		
		ArrayList<String> globa = new ArrayList<String>();
		globa.add("sum_quant");
		globa.add("avg_quant");
		
		String wher="rs.getInt(\"year\")==1997";
		Scan_0 s=new Scan_0(groupingAtt,globa,wher);	
	}
	
*/	
}