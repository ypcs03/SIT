package edu.stevens.cs549.hadoop.pagerank;

import java.io.*;
import java.util.Iterator;

import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.io.*;

public class DiffRed1 extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		double[] ranks = new double[2];
		/* 
		 * TODO: The list of values should contain two ranks.  Compute and output their difference.
		 */
		Iterator<Text> ite = values.iterator();
		double difference = 0.0;
		
		if(ite.hasNext())
			ranks[0] = Double.valueOf(ite.next().toString());
		if(ite.hasNext())
			ranks[1] = Double.valueOf(ite.next().toString());
		
		difference = Math.abs(ranks[0] - ranks[1]);
		context.write(key, new Text(String.valueOf(difference)));
	}
}
