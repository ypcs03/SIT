package edu.stevens.cs549.hadoop.pagerank;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FinJoinReducer extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException, IllegalArgumentException {

		/*
		 * Join final output with linked name
		 * output: key: nodeId : names, value: rank
		 */
		Iterator<Text> iterator = values.iterator();
		String nodeName = "";
		String rank  = "";
		while(iterator.hasNext()) {
			String tmp = iterator.next().toString();
			if(tmp.startsWith("NAME:")) {
				nodeName = tmp.replaceAll("NAME:", "");
			} 
			if(tmp.startsWith("RANK:")) {
				rank = tmp.replaceAll("RANK:", "");
			} 
		}
		
		context.write(new Text(key + " : " + nodeName) , new Text(rank));
		
	}

}
