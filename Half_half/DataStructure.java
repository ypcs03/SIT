import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DataStructure used to generate a DataStructure Class in the output file
 */
public class DataStructure {
	private ArrayList<String> strData = new ArrayList<String>();  //attribute data list
	private ArrayList<String> strName = new ArrayList<String>();  // attribute data name
	private ArrayList<String> strType = new ArrayList<String>();   //attribute data type
	
	public DataStructure(){
		//Connect to the Database
		String usr ="xthury"; 
		String pwd ="677131"; 
		String url ="jdbc:postgresql://localhost:5432/Database-562"; 
		try { 
			Class.forName("org.postgresql.Driver"); 
			System.out.println("Success loading Driver!"); } 
		catch(Exception e) { 
			System.out.println("Fail loading Driver!");
			e.printStackTrace(); 
		}
		try {
			Connection db = DriverManager.getConnection(url, usr, pwd);
			System.out.println("Success connecting server!");
			
			Statement st = db.createStatement();
			ResultSet rs;
			rs = st.executeQuery("select * from information_schema.columns where table_name='sales'");
			
			while(rs.next()) {
				 String name = rs.getString(4);         //get attribute name
			 	 String type = rs.getString(8);         //get the data type, integer or character
			 	 if(type.equals("integer")){           //if attribute is integer
			 	  	String temp = "int"+" "+name;
			 	   	strData.add(temp);
			 	   	strName.add(name);
			 	   	strType.add("int");
			 	 }
			 	 else{                         //if attribute is character/string
			 	   	String temp = "String"+" "+name;
			 	   	strData.add(temp);
			 	   	strName.add(name);
			 	   	strType.add("String");
			 	 }
			}

            db.close();
		} catch(SQLException e) {
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}
    }
	
	/**
	 * Get the attribute data list
	 * @return Attribute data list
	 */
	public ArrayList<String> getStrData(){
		return strData;
	}
	
	/**
	 * Get the attribute data name
	 * @return Attribute data name
	 */
	public ArrayList<String> getStrName(){
		return strName;
	}
	
	/**
	 * Get the attribute data type
	 * @return Attribute data type
	 */
	public ArrayList<String> getStrType(){
		return strType;
	}
	
	//Output the code to the output file with comment on it 
	public void output() {
		Writer java = new Writer();
		DataStructure ds = new DataStructure();
		
		java.write("/**\n");
		java.write(" * Data_Structure used to store the variables we scaned from the Database\n");
		java.write(" */\n");
		java.write("class DataStructure{\n");
        for(int i=0;i<ds.getStrData().size();i++){
        	if(ds.getStrType().get(i).equals("int")){
        		java.write("\t"+ds.getStrData().get(i)+"=0;\n");
        	}else{
        		java.write("\t"+ds.getStrData().get(i)+"=null;\n");
        	}	
        }
        java.write("}\n\n");
        
        java.close();
	}
}
