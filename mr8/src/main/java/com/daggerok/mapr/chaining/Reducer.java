package com.daggerok.mapr.chaining;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.Iterator;

public class Reducer extends MapReduceBase implements org.apache.hadoop.mapred.Reducer<LongWritable, Text, Text, IntWritable> {
    @Override
    public void reduce(LongWritable key, Iterator<Text> iterator, OutputCollector<Text, IntWritable> outputCollector
            , Reporter reporter) throws IOException {

        // TODO: implement me
    }
}
