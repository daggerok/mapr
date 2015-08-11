package com.daggerok.mapr.voter;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, FloatWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, total = 0, counter = 0;

        for (IntWritable value : values) {
            int curr = value.get();

            counter++;
            total += curr;
            if (curr > max) {
                max = curr;
            }
            if (curr < min) {
                min = curr;
            }
        }
        context.write(key, new FloatWritable((0.0f + total) / counter));
    }
}
