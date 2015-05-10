import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;


public class InputReader {
	
	private ArrayList<String> s;   //select attribute(s)
	private int n;                // number of grouping variables
	private ArrayList<String> v;  // grouping attribute
	private ArrayList<ArrayList<String>> f;   //aggregate function
	private ArrayList<ArrayList<String>> func;   //aggregate function relocated the number to the end
	private ArrayList<String> c;   //select condition 
	private ArrayList<ArrayList<String>> c1; //support variable for c 
	private String g;   //having condition
	private String w;	//where condition
	private ArrayList<ArrayList<Integer>> priority;	//priority of the group variable
	private ArrayList<String> global;	//global aggregate function
	
	/**
	 * Assign the variables to the elements in InputReader by reading
	 * the Input File line by line
	 * @param inFile Input File we need
	 * @throws IOException
	 */
	public InputReader(File inFile) throws IOException{
		s = new ArrayList<String>();
		n = 0;
		v = new ArrayList<String>();
		f = new ArrayList<ArrayList<String>>();
		func = new ArrayList<ArrayList<String>>();
		c = new ArrayList<String>();
		c1 = new ArrayList<ArrayList<String>>();
		g = new String();
		w = new String();
		global = new ArrayList<String>();
		priority = new ArrayList<ArrayList<Integer>>();
		
		//Create BufferedReader to read the file line by line
		BufferedReader br = new BufferedReader(new FileReader(inFile));
		
		int flag = 0; // Flag to show that we entered the first line of SELECT CONDITION 
		String line = null;
		while((line = br.readLine()) != null){
			
			String subLine = line.substring(0, 8);
			
			if(subLine.equalsIgnoreCase("select a")){ //Read the line of SELECT ATTRIBUTES
				line = br.readLine();
				StringTokenizer st;     
				st= new StringTokenizer(line,", ");    
				while(st.hasMoreTokens()){
					String token = st.nextToken();
					if(token.charAt(1) == '_'){
	        			String n = token.substring(0, 1);
	            		token = (token.substring(2)+"_").concat(n);
			    	}
					s.add(token);
				}
			}else if(subLine.equalsIgnoreCase("number o")){ //Read the line of NUMBER OF GROUPING VARIABLES
				line = br.readLine();
				n = Integer.parseInt(line);
			}else if(subLine.equalsIgnoreCase("grouping")){ //Read the line of GROUPING ATTRIBUTES
				line = br.readLine();
				StringTokenizer st;     
				st= new StringTokenizer(line,", ");    
				while(st.hasMoreTokens()){
					String token = st.nextToken();
					v.add(token);
				}
			}else if(subLine.equalsIgnoreCase("func-vec")){ //Read the line of FUNC-VECT
				line = br.readLine();
				StringTokenizer st;
				st= new StringTokenizer(line,", ");
				char c = '0';
				ArrayList<String> func = new ArrayList<String>(); 
				while(st.hasMoreTokens()){
					String token = st.nextToken();
					if( c == '0'){
						c = token.charAt(0);
						func.add(token);
					}else if(token.charAt(0) == c){
						func.add(token);
					}else{
						
						ArrayList<String> temp = new ArrayList<String>(); 
						for(int i=0;i<func.size();i++){
							temp.add(func.get(i));
						}
						f.add(temp);
						
						
						func.clear();
						c = token.charAt(0);
						func.add(token);
					}
					
					if(!st.hasMoreTokens()){
						ArrayList<String> temp = new ArrayList<String>(); 
						for(int i=0;i<func.size();i++){
							temp.add(func.get(i));
						}
						f.add(temp);
					}
				}
			}else if(subLine.equalsIgnoreCase("select c")){ //Read the line of SELECT CONDITION
				flag = 1; //Change FLAG to 1 when we enter the SELECTION CONDITION
				
				line = br.readLine();
				ArrayList<String> cond = new ArrayList<String>(); 
				String[] result = line.split(" ");
				
				for(int i = 0; i < result.length; i ++){
					String[] agg_unit = result[i].split("_");
			
					if(agg_unit.length == 3){ //1_avg_quant existed
						StringBuilder new_str = new StringBuilder();
						new_str.append(agg_unit[1]);
						new_str.append("_");
						new_str.append(agg_unit[2]);
						new_str.append("_");
						new_str.append(agg_unit[0]);
						result[i] = new_str.toString();

                        System.out.println("***************modified---------------" + result[i]);
					}
				}
				
				StringBuilder modified = new StringBuilder();
				for(int i = 0; i < result.length; i ++)
				    modified.append(result[i] + " ");
				c.add(modified.toString()); //Add it to the String ArrayList   	
                result = line.split("(and)|(or)");
                for(int i = 0; i < result.length; i ++)
                    cond.add(result[i].replaceAll(" ",""));                
                
			    c1.add(cond); //Add it to ArrayList of String ArrayList
				
			}else if(subLine.equalsIgnoreCase("having c")){ //Read the line of HAVING CONDITION
				
				line = br.readLine();
				g = line;
				
				
			}else if(subLine.equalsIgnoreCase("where co")){ //Read the line of WHERE CONDITION
				line = br.readLine();
				w = line;
			}else if(flag == 1){ //Read the next several lines of SELECT CONDITION after read the first line of it
				ArrayList<String> cond = new ArrayList<String>(); 
				String[] result = line.split(" ");
				
				for(int i = 0; i < result.length; i ++){
					String[] agg_unit = result[i].split("_");
			
				    System.out.println("length: " + agg_unit.length);	
					
					if(agg_unit.length == 3){ //1_avg_quant existed
						StringBuilder new_str = new StringBuilder();
						new_str.append(agg_unit[1]);
						new_str.append("_");
						new_str.append(agg_unit[2]);
						new_str.append("_");
						new_str.append(agg_unit[0]);
						result[i] = new_str.toString();
					    
                        System.out.println("***********modified: " + result[i]);
                    }
				}
				
				StringBuilder modified = new StringBuilder();
				for(int i = 0; i < result.length; i ++)
				    modified.append(result[i] + " ");
				c.add(modified.toString()); //Add it to the String ArrayList   	
			    
                result = line.split("(and)|(or)");
                for(int i = 0; i < result.length; i ++)
                    cond.add(result[i].replaceAll(" ",""));                
                
                c1.add(cond); //Add it to ArrayList of String ArrayList
			}
		}
		
		//Using the changed Topological Algorithm to find the priority of the Grouping Variables 
		int[][] dep = new int[n][n]; //Create a 2D array to store the dependency between the grouping variables
		for(int i=0;i<c1.size();i++){
			for(int j=0;j<c1.get(i).size();j++){
				String temp = c1.get(i).get(j);
				String[] result = temp.split("<|>|=");
				if(result.length>1){
					if(result[0].contains("_") && result[0].matches("[0-9]+.*")){
						int a = Integer.parseInt(result[0].replaceAll(" ","").substring(0, 1));
						int b = Integer.parseInt(result[1].replaceAll(" ","").substring(0, 1));
						dep[a-1][b-1] = 1;
					}			
					else if(result[1].contains("_") && result[1].matches("[0-9]+.*")){
						int a = Integer.parseInt(result[0].replaceAll(" ","").substring(0, 1));
						int b = Integer.parseInt(result[1].replaceAll(" ","").substring(0, 1));
						dep[b-1][a-1] = 1;
					}
				}
				
			}
		}
		ArrayList<Integer> base = new ArrayList<Integer>();
		for(int i=0;i<n;i++){
			int parent = 0;
			for(int j=0;j<n;j++){
				if(dep[i][j] == 1){
					parent = 1;
				}
			}
			if(parent == 0){
				base.add(i+1);
			}
		}
		ArrayList<Integer> tempO = new ArrayList<Integer>();
		for(int i=0;i<base.size();i++){
			tempO.add(base.get(i));
		}
		priority.add(tempO);
		ArrayList<Integer> removed = new ArrayList<Integer>();
		while((removed.size()+base.size()) != n){
			
			for(int i=0;i<base.size();i++){
				int remove = base.get(i)-1;
				removed.add(remove);
				for(int j=0;j<n;j++){
					dep[j][remove] = 0;
				}
			}
			
			base.clear();
			for(int i=0;i<n;i++){
				if(!removed.contains(i)){
					int parent = 0;
					for(int j=0;j<n;j++){
						if(dep[i][j] == 1){
							parent = 1;
						}
					}
					if(parent == 0){
						base.add(i+1);
					}
				}
				
			}
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for(int i=0;i<base.size();i++){
				temp.add(base.get(i));
			}
			priority.add(temp);
		}
		Collections.reverse(priority); //Reverse the ArrayList to get the priority we want
		
		//Relocate the number in the functions to the end of it and store them in func
		//Also find the Global Aggregate functions and store them in global
		for(int i=0;i<f.size();i++){
			for(int j=0;j<f.get(i).size();j++){
				String add = f.get(i).get(j);
				if(add.charAt(1) == '_'){
        			String n = add.substring(0, 1);
            		add = (add.substring(2)+"_").concat(n);
            		if(func.size() <= i){
            			ArrayList<String> temp = new ArrayList<String>();
            			temp.add(add);
            			func.add(temp);
            		}else if(func.get(i).size() == 0){
            			ArrayList<String> temp = new ArrayList<String>();
            			temp.add(add);
            			func.add(temp);
            		}else{
            			func.get(i).add(add);
            		}

        		}else{
        			if(func.size() <= i){
            			ArrayList<String> temp = new ArrayList<String>();
            			temp.add(f.get(i).get(j));
            			func.add(temp);
            		}else if(func.get(i).size() == 0){
            			ArrayList<String> temp = new ArrayList<String>();
            			temp.add(f.get(i).get(j));
            			func.add(temp);
            		}else{
            			func.get(i).add(f.get(i).get(j));
            		}
					global.add(f.get(i).get(j));
				}
			}
		}

	}
	
	/**
	 * Get the Select Attributes in String ArrayList
	 * @return The Select Attributes
	 */
	public ArrayList<String> getS(){
		return s;
	}
	
	/**
	 * Get the Number of Grouping Variables
	 * @return The Number of Grouping Variables
	 */
	public int getN(){
		return n;
	}
	
	/**
	 * Get the Grouping Attributes in String ArrayList
	 * @return Grouping Attributes
	 */
	public ArrayList<String> getV(){
		return v;
	}
	
	/**
	 * Get the Aggregate Functions in a ArrayList of String ArrayList
	 * @return Aggregate Functions
	 */
	public ArrayList<ArrayList<String>> getF(){
		return f;
	}
	
	/**
	 * Get the Aggregate Functions in a ArrayList of String ArrayList with the number at the end
	 * @return Aggregate Functions with the numbers at the end
	 */
	public ArrayList<ArrayList<String>> getFunc(){
		return func;
	}
	
	/**
	 * Get the Select Conditions in a String ArrayList
	 * @return Select Conditions
	 */
	public ArrayList<String> getC(){
		return c;
	}
	
	/**
	 * Get the Having Conditions in String
	 * @return Having Conditions
	 */
	public String getG(){
		return g;
	}
	
	/**
	 * Get the Where Conditions in String
	 * @return Where Conditions
	 */
	public String getW(){
		return w;
	}
	
	/**
	 * Get the Priorities in a ArrayList of String ArrayList
	 * For example if the List is {{1},{2,3}}
	 * We will do the full scan for Group 1 first and then 
	 * We will do query for the Group 2 and 3 together 
	 * @return Aggregate Functions with the numbers at the end
	 */
	public ArrayList<ArrayList<Integer>> getPriority(){
		
		return priority;
	}
	
	/**
	 * Get the Global Functions in a String ArrayList
	 * @return Global Functions
	 */
	public ArrayList<String> getGlobal(){
		
		return global;
	}


}
