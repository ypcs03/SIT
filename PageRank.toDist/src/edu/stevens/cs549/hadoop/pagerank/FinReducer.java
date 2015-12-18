package edu.stevens.cs549.hadoop.pagerank;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FinReducer extends Reducer<DoubleWritable, Text, Text, Text> {

	public void reduce(DoubleWritable key, Iterable<Text> values, Context context) throws IOException,
			InterruptedException {
		/* 
		 * TODO: For each value, emit: key:value, value:-rank
		 */
		Iterator<Text> ite = values.iterator();
		String node;
		
		while(ite.hasNext()){
			node = ite.next().toString();
			
			context.write(new Text(node), new Text(String.valueOf(0 - key.get())));
		}
	}
}
