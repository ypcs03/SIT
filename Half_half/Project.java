import java.io.*;
import java.util.*;

public class Project{
    public void begin(File file){
        String user = new String();
        String pwd = new String();
        String url = new String();        
        try{    
            //Create the data structure
            //Create the EMF structure
            Database.output();
            DataStructure create_data_struct = new DataStructure();
            create_data_struct.output();
            //Write the database class including: connection, next, close, execute
            EMFStructure emf_struct = new EMFStructure(file);

            InputReader input = new InputReader(file);
            //Scan zero
            ArrayList<String> data_type = new ArrayList<String>();


            //Create specific db object
            Writer java = new Writer();
            java.write("\npublic class EMF_QUERY{\n");
            java.write("\tpublic static void main(String []args){\n");
            java.write("\t\tdb database = new db(\"xthury\",\"677131\",\"jdbc:postgresql://localhost:5432/Database-562\");\n");
            java.write("\t\tdatabase.connect();\n");
            java.write("\t\tDataStructure record = new DataStructure();\n");
            java.close();
            
            Scan_0 zero_scan = new Scan_0(input.getV(), input.getGlobal(),input.getW());
            //First solve the dependency in the such that clasue(mainly the aggregation)
            Dependency such_dependency = new Dependency(input, create_data_struct);
            such_dependency.cal_all();

            //Replace the dependency in the such that clasue
            //        FinalScan scan = new FinalScan();
            //        scan.replace(input.getFunc(), input.getV(), value_dependency_agg, input.getC());

            //Apply the having clause
            java = new Writer();
            String having_clause = input.getG();
            java.write("/********** Applying the having clause ************/\n");
            java.write("\tfor(int i = 0; i < emf_array.size(); i ++){\n");


            //Format the having clause
            ArrayList<String> Attributes = create_data_struct.getStrName();

            having_clause = having_clause.replaceAll("and", "&&");
            having_clause = having_clause.replaceAll("or", "||");
            having_clause = having_clause.replaceAll("sum", "emf_array.get(i).sum");
            having_clause = having_clause.replaceAll("max", "emf_array.get(i).max");
            having_clause = having_clause.replaceAll("min", "emf_array.get(i).min");
            having_clause = having_clause.replaceAll("avg", "emf_array.get(i).avg");
            having_clause = having_clause.replaceAll("count", "emf_array.get(i).count");
            
            if(having_clause == null || having_clause.length() == 0)
                having_clause = new String("true");
            java.write("\t\tif(!(" + having_clause + ")){\n");
            java.write("\t\t\temf_array.remove(i);\t//remove the emf_struct that don't meet the having clause\n\t\t\ti --;\n");
            java.write("\t\t}\n\t}\n");

            //output the result
            ArrayList<String> temp = input.getS();
           
            //Debug output
            System.out.println("output:");
            for(int s = 0; s < temp.size(); s ++)
                System.out.println(temp.get(s));

            java.write("\t\t/*************************Output the result, that is the remain emf_structure************/\n");
            java.write("\t\tDecimalFormat DF = new DecimalFormat(\".00\");\n");
            java.write("\t\tfor(int i = 0; i < emf_array.size(); i ++){\n");
            java.write("\t\t\tSystem.out.println(");
            for(int i = 0; i < temp.size(); i ++){
                boolean flag = temp.get(i).matches(".*quant.*");
                
                if(flag)
                    java.write("DF.format(");

                if(temp.get(i).contains("/")){
                    String mathmetic[] = temp.get(i).split("/");
                    if(mathmetic[1].matches("\\s*[0-9]*\\s*"))

                    java.write(mathmetic[1] + " - 0.0 < 0.0001 ? -1: emf_array.get(i)." + mathmetic[0] + "/" +  mathmetic[1]);
                        
                    else
                    java.write("emf_array.get(i)." + mathmetic[1] + " - 0.0 < 0.0001 ? -1: emf_array.get(i)." + mathmetic[0] + "/" + " emf_array.get(i)." + mathmetic[1]);
//                    java.write("emf_array.get(i)." + mathmetic[0] + "/" + "emf_array.get(i)." + mathmetic[1]);       

                    




                }else if(temp.get(i).contains("*")){
                    String mathmetic[] = temp.get(i).split("*");
                    java.write("emf_array.get(i)." + mathmetic[0] + "*" + "emf_array.get(i)." + mathmetic[1]);       
                }
                else
                    java.write("emf_array.get(i)."+temp.get(i));
                
                if(flag)
                    java.write(") +\"\\t\" + ");
                else
                    java.write(" + \"\\t\" + ");

            }
            java.write("\"\");\n");
            
            java.write("\t\t}\ndatabase.closedb();\n\t}\n}");
            java.close();
            
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
    }
}
