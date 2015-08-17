package com.daggerok.mapr.cloudera;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

public class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, LongWritable> {
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values, org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, LongWritable>.Context context)
			throws IOException, InterruptedException {
		
		long total = 0L;
		
		for (IntWritable value: values) {
			total += value.get();
		}
		context.write(key, new LongWritable(total));
	}
}
