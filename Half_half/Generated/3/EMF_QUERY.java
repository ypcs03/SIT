import java.util.*;
import java.lang.String;
import java.io.*;
import java.lang.*;
import java.sql.*;
import java.text.DecimalFormat;

class db {
	String usr = null;	//username
	String pwd = null;	//password
	String url = null;	
	ResultSet rs = null;
	boolean more = false;
	Connection con_db = null;
	Statement st = null;

	public db(String iusr, String ipwd, String iurl){
		usr = new String(iusr);
		pwd = new String(ipwd);
		url = new String(iurl);
	}

	void connect() {
		try {
			Class.forName ("org.postgresql.Driver");
			System.out.println("Success loading Driver!");
		} catch(Exception e) {
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
		}
	}

	DataStructure NextRecord() {
		try {
			more = rs.next();
		if(more){
			DataStructure dbs = new DataStructure ();

			dbs.cust = rs.getString(1);
			dbs.prod = rs.getString(2);
			dbs.day = rs.getInt(3);
			dbs.month = rs.getInt(4);
			dbs.year = rs.getInt(5);
			dbs.state = rs.getString(6);
			dbs.quant = rs.getInt(7);

			return dbs;
		}
		else
			return null;
} catch(Exception e){
			return null;
		}
	}

	void executeSql(String Sql) {
		try {
			con_db = DriverManager.getConnection(url, usr, pwd);
			System.out.println("Success connecting server!");


			st = con_db.createStatement();
			rs = st.executeQuery(Sql);
		} catch(SQLException e) {
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}
	}

	void closedb() {
		try {
			rs.close();
			st.close();
			con_db.close();
		} catch(Exception e){
			System.out.println("close db failed!");
		}
	}
}

/**
 * Data_Structure used to store the variables we scaned from the Database
 */
class DataStructure{
	String cust=null;
	String prod=null;
	int day=0;
	int month=0;
	int year=0;
	String state=null;
	int quant=0;
}

/**
 * EMF_Structure with the group attributes and all the aggregate functions in it
 * Also when we get avg aggregate functions we will add a sum and count func to it
 */
class EMFStructure{
	String cust;
	int month;
	double avg_quant=0;
	double sum_avg=0.0;
	int count_avg=0;
	double avg_quant_1;
	double sum_avg_1;
	int count_avg_1;
	double avg_quant_2;
	double sum_avg_2;
	int count_avg_2;

	//Generator for the EMFStructure
	public EMFStructure(){
	String cust;
	int month;
		double avg_quant_1=0.0;
		double sum_avg_1=0.0;
		int count_avg_1=0;
		double avg_quant_2=0.0;
		double sum_avg_2=0.0;
		int count_avg_2=0;
	}
}

public class EMF_QUERY{
	public static void main(String []args){
		db database = new db("xthury","677131","jdbc:postgresql://localhost:5432/Database-562");
		database.connect();
		DataStructure record = new DataStructure();

/*******************************Scan Zero***************************************/
	 database.executeSql("SELECT * FROM sales"); //retrieve data from database
	 ArrayList<EMFStructure> emf_array=new ArrayList<EMFStructure>(); //create all the combinations of grouping attributes
	 record=database.NextRecord();//get one tuple
	 while(record != null){
		 boolean exist=false;
			if( record.year == 1997){
			 for(int i=0;i<emf_array.size();i++){
				 if(emf_array.get(i).cust.equals(record.cust)
				 && emf_array.get(i).month == record.month){ //if the combination exists, then update the global aggregation functions
					 exist=true;
					 emf_array.get(i).sum_avg += record.quant;
					 emf_array.get(i).count_avg++;
					 break;
				 }
			 }
			 if(!exist){ //if the combination doesn't exist, then create a new entry and add it to emf_array
				 EMFStructure emf=new EMFStructure();
				 emf.cust = record.cust;
				 emf.month = record.month;
				 emf.sum_avg = record.quant;
				 emf.count_avg=1;
				 emf_array.add(emf);
			 }
		}
	 record=database.NextRecord();
	 }
	 for(int i=0;i<emf_array.size();i++){
		 emf_array.get(i).avg_quant=emf_array.get(i).sum_avg/emf_array.get(i).count_avg;
	}
/************Calculate the dependecy appeared in such that clause************/
		database.executeSql("SELECT * FROM sales");

		while((record = database.NextRecord()) != null){
			if( record.year == 1997)
			for(int i = 0; i < emf_array.size(); i ++){
/***************Update the emf_struct********************/
				if( record.cust.equals(emf_array.get(i).cust) && record.month < emf_array.get(i).month  ){
					emf_array.get(i).count_avg_1 ++;
					emf_array.get(i).sum_avg_1 += record.quant; 
					emf_array.get(i).avg_quant_1 = emf_array.get(i).sum_avg_1/emf_array.get(i).count_avg_1;
				}
/***************Update the emf_struct********************/
				if( record.cust.equals(emf_array.get(i).cust) && record.month > emf_array.get(i).month  ){
					emf_array.get(i).count_avg_2 ++;
					emf_array.get(i).sum_avg_2 += record.quant; 
					emf_array.get(i).avg_quant_2 = emf_array.get(i).sum_avg_2/emf_array.get(i).count_avg_2;
				}
			}
		}
/********** Applying the having clause ************/
	for(int i = 0; i < emf_array.size(); i ++){
		if(!(true)){
			emf_array.remove(i);	//remove the emf_struct that don't meet the having clause
			i --;
		}
	}
		/*************************Output the result, that is the remain emf_structure************/
		DecimalFormat DF = new DecimalFormat(".00");
		for(int i = 0; i < emf_array.size(); i ++){
			System.out.println(emf_array.get(i).cust + "\t" + emf_array.get(i).month + "\t" + DF.format(emf_array.get(i).avg_quant_1) +"\t" + DF.format(emf_array.get(i).avg_quant) +"\t" + DF.format(emf_array.get(i).avg_quant_2) +"\t" + "");
		}
database.closedb();
	}
}