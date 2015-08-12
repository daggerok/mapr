package com.daggerok.mapr.university;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int total = 0, count = 0;

        for (IntWritable value: values) {
            int current = value.get();

            count++;
            total += current;
            if(current < min) {
                min = current;
            }
            if(current > max) {
                max = current;
            }
        }

        context.write(new Text(key.toString() + "_" + "min"), new IntWritable(min));
        context.write(new Text(key.toString() + "_" + "max"), new IntWritable(max));
        context.write(new Text(key.toString() + "_" + "mean"), new IntWritable(total / count));
    }
}
