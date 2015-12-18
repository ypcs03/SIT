package edu.stevens.cs549.hadoop.pagerank;

import java.io.*;
import java.util.Iterator;

import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.io.*;

public class DiffRed2 extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		double diff_max = 0.0; // sets diff_max to a default value
		/* 
		 * TODO: Compute and emit the maximum of the differences
		 */
		Iterator<Text> ite = values.iterator();
		while(ite.hasNext()){
			double temp = Double.valueOf(ite.next().toString());
			
			if(diff_max < temp)
				diff_max = temp;
		}
		
		context.write(new Text(""), new Text(String.valueOf(diff_max)));
	}
}
