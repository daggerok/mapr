package com.daggerok.mapr.university;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.StringTokenizer;

public class Mapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String var1 = context.getConfiguration().get("var1");
        String var2 = context.getConfiguration().get("var2");
        String firstKey = null;
        String secondKey = null;

        // create iterator over record assuming line-separated fields
        String inputs = value.toString();
        if (inputs.contains("%") || !inputs.contains(var1) || !inputs.contains(var2)) {
            return;
        }

        StringTokenizer iterator = new StringTokenizer(inputs, "\n");
        String input = null;

        // pull out line for firstVar
        while (iterator.hasMoreTokens()) {
            input = iterator.nextToken().toString();

            if (input.contains(var1)) {
                firstKey = var1;
                break;
            } else if (input.contains(var2)) {
                firstKey = var2;
                break;
            }
        }
        // pull out value for first, pull right parens from value
        input = input.split("\\s+")[3].split("\\)")[0];
        int firstVal = Integer.parseInt(input);

        // pull out line for secondVar
        while (iterator.hasMoreTokens()) {
            input = iterator.nextToken().toString();
            if (input.contains(var1)) {
                secondKey = var1;
                break;
            } else if (input.contains(var2)) {
                secondKey = var2;
                break;
            }
        }
        // pull out value for second, pull right parens from value
        input = input.split("\\s+")[3].split("\\)")[0];
        int secondVal = Integer.parseInt(input);

        // emit key-value as
        context.write(new Text(firstKey), new IntWritable(firstVal));
        context.write(new Text(secondKey), new IntWritable(secondVal));
    }
}
