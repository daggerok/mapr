package com.daggerok.mapr.voter;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Combiner extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        // optimize your performance before reducer here...
    }
}
