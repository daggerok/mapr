package com.daggerok.mapr.chaining;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;

public class Mapper2 extends MapReduceBase implements Mapper<Text, Text, LongWritable, Text> {
    @Override
    public void map(Text key, Text value, OutputCollector<LongWritable, Text> outputCollector, Reporter reporter)
            throws IOException {

        // TODO: Implement me
    }
}
