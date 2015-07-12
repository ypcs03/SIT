In the Linux environment, to run the program:
1. Add the classpath of the postgresql driver by:
	Run command in the terminal: export CLASSPATH="The path of the file postgresql-8.3-604.jdbc4.jar" + ":."
		eg: export CLASSPATH=/media/peng/Half_half/postgresql-8.3-604.jdbc4.jar:.
	Or add the command into the file .bashrc

2. In the terminal, use command 'cd' to the destination folder where the source code stored.
	eg: cd /media/peng/Half_half/Half_half/

3. Before running the source code, you should change the postgresql username, password and database in the file:
	DataStructure.java at line: 18,19,20
	Project.java at line : 27

3. Run the command in the terminal:
	rm ./*.class      #delete the previously compiled file
	rm EMF_QUERY.java #delete the previous generated code also you can click the delete button after run the program
	javac ./*.java    #compile all the source code
	java Generator    #Run the program

4. After running the program, choose the input file where the EMF stores, and click GenerateCode button

5. Copy the generated file "EMF_QUERY.java" to another folder, and then run:
	javac EMF_QUERY.java 
	JAVA EMF_QUERY

6. As a convenience, we also provided the EMF query text and the response sql file in the "TestCase" folder:
	To check whether the output of the response sql, just run the command:
		psql -d "your database name" -a -f sqlfile
		eg: psql -d Database-562 -a -f sql_2

7. The all generated file located in the Generated folder, for each example