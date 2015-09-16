import java.util.*;

/*Such that clause are stored as ArrayList<String>:
  "1.cust == cust && 1.prod == banana && sum_quant_1"
  "2.cust == cust && 2.month = 12"
  ...
*/

/*The function with dependency stored as ArrayList<String>:
* sum_quant_1 avg_quant_1
* sum_quant_2
*/

public class Dependency{
    Writer java = null;
    DataStructure table = null;
    InputReader input = null;
    String where = null;                //where clause
    ArrayList<ArrayList<String>> Such_agg = null;  //Functions in such that clausei
    ArrayList<String> Such_clause = null;

    /***Constructor:
    ****Initialize the InputReader, which is used to get content from the sql_query file
    ****Initialize the Such_agg, which stores the aggregation function appeared in Such that clause
    **/
    public Dependency(InputReader in, DataStructure db){
        java = new Writer();
        table = db;
        input = in;
        where = input.getW();
        Such_agg = new ArrayList<ArrayList<String>>();
        Such_clause = input.getC();
    }

    String replace(String str){
        ArrayList<String> Attributes = table.getStrName();
       
        //Debug output 
        System.out.print("All the Attributes: ");
        for(int i = 0; i < Attributes.size(); i ++)
            System.out.print(Attributes.get(i) + "\t");
        System.out.println("");
        System.out.println("String before:" + str);


        //replace the 1.quant == quant to emf_array.get(i).quant == record.quant
        for(int i = 0; i < Attributes.size(); i ++){
            str = str.replaceAll("and", "&&");
            str = str.replaceAll("or", "||");
            str = str.replaceAll("equals\\([\\s]*" + Attributes.get(i), "equals(emf_array.get(i)." + Attributes.get(i));
            str = str.replaceAll("<[\\s]*" + Attributes.get(i), "< emf_array.get(i)." + Attributes.get(i));
            str = str.replaceAll("<=[\\s]*" + Attributes.get(i), "<= emf_array.get(i)." + Attributes.get(i));
            str = str.replaceAll(">=[\\s]*" + Attributes.get(i), ">= emf_array.get(i)." + Attributes.get(i));
            str = str.replaceAll(">[\\s]*" + Attributes.get(i), "> emf_array.get(i)." + Attributes.get(i));
            str = str.replaceAll("==[\\s]*" + Attributes.get(i), "== emf_array.get(i)." + Attributes.get(i));

            System.out.println(i + str);
        }

           str = str.replaceAll("sum", " emf_array.get(i).sum");
           str = str.replaceAll("max", " emf_array.get(i).max");
           str = str.replaceAll("avg", " emf_array.get(i).avg");
           str = str.replaceAll("min", " emf_array.get(i).min");
           str = str.replaceAll("count", " emf_array.get(i).count");



        System.out.println("String after: " + str);
        return str;
    }

    void getfunctions(ArrayList<ArrayList<Integer>> func){
        //this is like sum_quant_1
        ArrayList<String> functions = new ArrayList<String>();
        ArrayList<ArrayList<String>> t = input.getFunc();

        for(int i = 0; i < t.size(); i ++)
            for(int j = 0; j < t.get(i).size(); j ++)
                functions.add(t.get(i).get(j));

        //Debug output
        System.out.println("all functions");
        for(int i = 0; i < functions.size(); i ++)
            System.out.print(functions.get(i) + "\t");
        System.out.println("");

        if(functions == null || func == null)
            return ;
        /*The input is an arraylist<arraylist<integer>>:
          1
          2,3
          4
          ...
        */
        for(int i = 0; i < func.size(); i ++){
            ArrayList<String> temp = new ArrayList<String>();
            for(int j = 0; j < func.get(i).size(); j ++){
                for(int m = 0; m < functions.size(); m ++)
                    if(functions.get(m).endsWith(func.get(i).get(j).toString()))
                        temp.add(functions.get(m));
                
            }
            Such_agg.add(temp);
        }
    }
    /*This function is used to output the aggregation calculation part to the file
    **  After calculation, replace it in the condition array
    **  The condition string should be output to the file
    */

    //Database variable: db, 
    //record variable, record
    public void cal_all(){                  
        //Output the sentence to read from the sql database
        java.write("/************Calculate the dependecy appeared in such that clause************/\n");
      

        //Debug output
        System.out.println("**********************get priority***********************");
        ArrayList<ArrayList<Integer>> priority = input.getPriority();
        for(int i = 0; i < priority.size(); i ++){
            for(int j = 0; j < priority.get(i).size(); j ++)
                System.out.print(priority.get(i).get(j) + "\t");
            System.out.println("");
        }



        getfunctions(input.getPriority());
        if(Such_agg == null)
            return;

        //Debug output
        System.out.println("Such aggregations:");
        for(int i = 0; i < Such_agg.size(); i ++){
            for(int j = 0; j < Such_agg.get(i).size(); j ++)
                System.out.print(Such_agg.get(i).get(j) + "\t");
            System.out.println("");
        }

        //Debug output
        System.out.println("Such clause");
        for(int i = 0; i < Such_clause.size(); i ++)
            System.out.println(Such_clause.get(i));

        ArrayList<String> Attributes = table.getStrName();

        where = where.replaceAll("and", " && ");
        where = where.replaceAll("or", " || ");

        //replace the quant == 231 to record.quant == 231
        for(int j = 0; j < Attributes.size(); j ++){
            where = where.replaceAll(Attributes.get(j) + ".equals", "record." + Attributes.get(j) + "equals");
            where = where.replaceAll(Attributes.get(j) + "[\\s]*<", "record." + Attributes.get(j) + " <");
            where = where.replaceAll(Attributes.get(j) + "[\\s]*<=", "record." + Attributes.get(j) + " <=");
            where = where.replaceAll(Attributes.get(j) + "[\\s]*>=", "record." + Attributes.get(j) + " >=");
            where = where.replaceAll(Attributes.get(j) + "[\\s]*>", "record." + Attributes.get(j) + " >");
            where = where.replaceAll(Attributes.get(j) + "[\\s]*==", "record." + Attributes.get(j) + " ==");
        }

        for(int i = 0; i < Such_agg.size(); i ++){
            java.write("\t\tdatabase.executeSql(\"SELECT * FROM sales\");\n\n");
            java.write("\t\twhile((record = database.NextRecord()) != null){\n");
            if(where.length() != 0){
                java.write("\t\t\tif( " +  where + ")\n");
            }
            java.write("\t\t\tfor(int i = 0; i < emf_array.size(); i ++){\n");            

            for(int m = 0; m < Such_agg.get(i).size(); m ++){
                String []args = Such_agg.get(i).get(m).split("_");
                int index_gb;

                if(args.length == 2)
                    index_gb = Integer.valueOf(args[1]);
                else
                    index_gb = Integer.valueOf(args[2]);

                String con_gb = new String("true");
                java.write("/***************Update the emf_struct********************/\n");

                //Find the corresponding such that clause
                for(int j = 0; j < Such_clause.size(); j ++){
                    if((Such_clause.get(j).charAt(0) - '0') == index_gb || Such_clause.get(j).charAt(1) - '0' == index_gb){
                        con_gb = Such_clause.get(j);
                        break;
                    }
                }

                //Debug output
                System.out.println("Where clause:" + where);
                System.out.println("such condition:" + con_gb);

                //change 1.prod = prod to record.pro = emf_array.get(i).prod
                con_gb = replace(con_gb);                    
                con_gb = con_gb.replaceAll(Integer.toString(index_gb)+"\\.", "record.");

                java.write("\t\t\t\tif( " + con_gb + " ){\n");

                switch(args[0].toUpperCase()){
                    case "SUM":
                        //efm_array.get(i).sum_quant_1 += record.quant;
                        java.write("\t\t\t\t\temf_array.get(i)." + Such_agg.get(i).get(m) + " += record." + args[1] + ";\n");
                        break;
                    case "COUNT":
                        //emf_array.get(i).count_1 ++;
                        java.write("\t\t\t\t\temf_array.get(i)." + Such_agg.get(i).get(m) + " ++;\n");
                        break;
                    case "MAX":
                        //emf_array.get(i).max_quant_1 = Math.max(emf_array.get(i).max_quant_1, record.quant);
                        java.write("\t\t\t\t\temf_array.get(i)." + Such_agg.get(i).get(m) + " = Math.max(emf_array.get(i)." + Such_agg.get(i).get(m) + ", record." + args[1] + ");\n");
                        break;
                    case "MIN":
                        //emf...
                        java.write("\t\t\t\t\temf_array.get(i)." + Such_agg.get(i).get(m) + " = Math.min(emf_array.get(i)." + Such_agg.get(i).get(m) + ", record." + args[1] + ");\n");
                        break;
                    case "AVG":
                        java.write("\t\t\t\t\temf_array.get(i).count_avg_" + args[2] + " ++;\n");
                        java.write("\t\t\t\t\temf_array.get(i).sum_avg_" + args[2] + " += record." + args[1] + "; \n");
                        java.write("\t\t\t\t\temf_array.get(i)." + Such_agg.get(i).get(m) + " = emf_array.get(i).sum_avg_" + args[2] + "/emf_array.get(i).count_avg_" + args[2] + ";\n");
                        break;
                    default:
                        System.out.println("Incorrect output");
                        System.exit(1);
                }
                java.write("\t\t\t\t}\n");
            }
            java.write("\t\t\t}\n\t\t}\n");

        }

        java.close();
    }

    /*    enum Function{
          SUM, COUNT, MAX, MIN, AVG, NOVALUE;

          public static Function ToFunc(String str){
          try{
          return valueOf(str);
          }catch(Exception e){
          System.out.println("The input is incorrect");
          return NOVALUE;
          }
          }
          }*/

}
