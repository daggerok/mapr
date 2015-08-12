package com.daggerok.mapr.twojobs;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.Iterator;

public class Reduce2 extends MapReduceBase implements Reducer<Text, Text, Text, FloatWritable> {
    @Override
    public void reduce(Text key, Iterator<Text> iterator, OutputCollector<Text, FloatWritable> outputCollector
            , Reporter reporter) throws IOException {

        // TODO: Implement me
    }
}
