package com.daggerok.mapr.statistics;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, LongWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

        String var1 = context.getConfiguration().get("var1");
        String var2 = context.getConfiguration().get("var2");

        long squares1 = 0L;
        long squares2 = 0L;
        long products = 0L;

        if (key.toString().contains(var1 + "_delta")) {
            for (IntWritable value : values) {
                int current = value.get();

                squares1 += current * current;
            }
            context.write(new Text(key.toString() + "_sumofsquares"), new LongWritable(squares1));
        } else if (key.toString().contains(var2 + "_delta")) {
            for (IntWritable value : values) {
                int current = value.get();

                squares2 += current * current;
            }
            context.write(new Text(key.toString() + "_sumofsquares"), new LongWritable(squares2));
        } else if (key.toString().contains("product")) {
            for (IntWritable value : values) {
                products += value.get();
            }
            context.write(new Text(key.toString() + "_sumofproducts"), new LongWritable(products));
        }
    }
}
