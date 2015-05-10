public class Database{
	public static void output(){
	Writer db = new Writer ();
		db.write("import java.util.*;\n");
		db.write("import java.lang.String;\n");
		db.write("import java.io.*;\n");
		db.write("import java.lang.*;\n");
		db.write("import java.sql.*;\n");
        db.write("import java.text.DecimalFormat;\n\n");

/**
 * The Database class used to out put the code in the output file,
 * in order to make sure the output function can connect to the database
 * it also contains the function used to read the data from the database.
 */	
        db.write("class db {\n");
		db.write("\tString usr = null;	//username\n");
		db.write("\tString pwd = null;	//password\n");
		db.write("\tString url = null;	\n");
		db.write("\tResultSet rs = null;\n");
		db.write("\tboolean more = false;\n");
		db.write("\tConnection con_db = null;\n");
		db.write("\tStatement st = null;\n");

		db.write("\n\tpublic db(String iusr, String ipwd, String iurl){\n");
		db.write("\t\tusr = new String(iusr);\n");
		db.write("\t\tpwd = new String(ipwd);\n");
		db.write("\t\turl = new String(iurl);\n");
		db.write("\t}\n");

/**
 * The connect function used to load the driver.
 */
		db.write("\n\tvoid connect() {\n");
		db.write("\t\ttry {\n");
		db.write("\t\t\tClass.forName (\"org.postgresql.Driver\");\n");
		db.write("\t\t\tSystem.out.println(\"Success loading Driver!\");\n");
		db.write("\t\t} catch(Exception e) {\n");
		db.write("\t\t\tSystem.out.println(\"Fail loading Driver!\");\n");
		db.write("\t\t\te.printStackTrace();\n");
		db.write("\t\t}\n");
		db.write("\t}\n");

/**
 * The NextRecord function used to read the data from the database
 * and store the record in the DataStructure.
 */
		db.write("\n\tDataStructure NextRecord() {\n");
		db.write("\t\ttry {\n");
		db.write("\t\t\tmore = rs.next();\n");
		db.write("\t\tif(more){\n"); 
		db.write("\t\t\tDataStructure dbs = new DataStructure ();\n");
		db.write("\n\t\t\tdbs.cust = rs.getString(1);\n");	//get name of customer
		db.write("\t\t\tdbs.prod = rs.getString(2);\n");	//get name of product
		db.write("\t\t\tdbs.day = rs.getInt(3);\n");	//get number of day
		db.write("\t\t\tdbs.month = rs.getInt(4);\n");	//get number of month
		db.write("\t\t\tdbs.year = rs.getInt(5);\n");	//get number of year 
		db.write("\t\t\tdbs.state = rs.getString(6);\n");	//get name of state 
		db.write("\t\t\tdbs.quant = rs.getInt(7);\n");	//get number of quantity

		db.write("\n\t\t\treturn dbs;\n");
		db.write("\t\t}\n");
		db.write("\t\telse\n");
		db.write("\t\t\treturn null;\n");
		db.write("} catch(Exception e){\n");
		db.write("\t\t\treturn null;\n");
		db.write("\t\t}\n");
		db.write("\t}\n");
			
/**
 * The executeSql function used to execute the SQL statement.
 */
		db.write("\n\tvoid executeSql(String Sql) {\n");
		db.write("\t\ttry {\n");
		db.write("\t\t\tcon_db = DriverManager.getConnection(url, usr, pwd);\n");	//connect to the database
		db.write("\t\t\tSystem.out.println(\"Success connecting server!\");\n\n");

		db.write("\n\t\t\tst = con_db.createStatement();\n");
		db.write("\t\t\trs = st.executeQuery(Sql);\n");		//executing the required SQL statement
		db.write("\t\t} catch(SQLException e) {\n");
		db.write("\t\t\tSystem.out.println(\"Connection URL or username or password errors!\");\n");
		db.write("\t\t\te.printStackTrace();\n");
		db.write("\t\t}\n");
		db.write("\t}\n");

/**
 * The closedb function used to close the database been used.
 */
		db.write("\n\tvoid closedb() {\n");
		db.write("\t\ttry {\n");
		db.write("\t\t\trs.close();\n"); //close result set
		db.write("\t\t\tst.close();\n"); //close statement
		db.write("\t\t\tcon_db.close();\n");	//close database
		db.write("\t\t} catch(Exception e){\n");
		db.write("\t\t\tSystem.out.println(\"close db failed!\");\n");
		db.write("\t\t}\n");
		db.write("\t}\n");

		db.write("}\n\n");

		db.close();
	}

}
