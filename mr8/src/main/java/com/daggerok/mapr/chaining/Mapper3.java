package com.daggerok.mapr.chaining;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Mapper3 extends Mapper<Text, IntWritable, Text, Text> {
    @Override
    protected void map(Text key, IntWritable value, Context context) throws IOException, InterruptedException {
        // TODO: implement me
    }
}
