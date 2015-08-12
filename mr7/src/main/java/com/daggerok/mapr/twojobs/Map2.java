package com.daggerok.mapr.twojobs;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;

public class Map2 extends MapReduceBase implements Mapper<Text, Text, Text, IntWritable> {
    @Override
    public void map(Text key, Text value, OutputCollector<Text, IntWritable> outputCollector, Reporter reporter)
            throws IOException {

        // TODO: Implement me
    }
}
