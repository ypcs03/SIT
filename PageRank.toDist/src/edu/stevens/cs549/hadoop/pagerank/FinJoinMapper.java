package edu.stevens.cs549.hadoop.pagerank;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FinJoinMapper extends Mapper<LongWritable, Text, Text, Text> {

	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException, IllegalArgumentException {
		String line = value.toString(); // Converts Line to a String
		/*
		 * Join final output with node name
		 */
		String[] sections;
		System.out.println("************lines: " + line);
		if (line.contains(":")) {
			int index = line.indexOf(":");
			sections = new String[2];
			sections[0] = line.substring(0, index);
			sections[1] = line.substring(index + 1, line.length());
		} else {
			sections = line.split("\t"); // Splits it into two parts. Part 1: node+rank | Part 2: adj list
		}
		

		if (sections.length > 2) // Checks if the data is in the incorrect format
		{
			throw new IOException("Incorrect data format");
		}

		String[] noderank = sections[0].split("\\+");
		
		if(noderank.length == 1) {
			 //it's nodeID with its name
			context.write(new Text(noderank[0]), new Text("NAME:" + sections[1].trim()));
		}
		
		if(noderank.length == 2 && !noderank[0].equals("")) {
			// it's nodeID with its rank
			context.write(new Text(noderank[0]), new Text("RANK:" + noderank[1]));
		}
	}

}
