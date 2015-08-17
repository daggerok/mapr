package com.daggerok.mapr.cloudera;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

public class Mapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, IntWritable> {
	private static final IntWritable one = new IntWritable(1);
	
	@Override
	protected void map(LongWritable key, Text value, org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		
		StringTokenizer stringTokenizer = new StringTokenizer(value.toString());
		
		while (stringTokenizer.hasMoreElements()) {
			context.write(new Text(stringTokenizer.nextToken()), one);
		}
	}
}
