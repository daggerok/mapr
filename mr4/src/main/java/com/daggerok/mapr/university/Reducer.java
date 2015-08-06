package com.daggerok.mapr.university;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;


public class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, FloatWritable> {
    private static final Log log = LogFactory.getLog(Reducer.class);

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int min = Integer.MIN_VALUE, max = Integer.MAX_VALUE, times = 0, avr = 0;

        for (IntWritable value : values) {
            int current = value.get();

            if (min > current) {
                min = current;
            }
            if (max < current) {
                max = current;
            }
            avr += current;
            times++;
        }

        log.info(String.format("%s_min=%s", key.toString(), min));
        log.info(String.format("%s_max=%s", key.toString(), max));
        log.info(String.format("%s_mean=%s", key.toString(), (avr+0.0f)/times));

        context.write(new Text(key.toString() + "_min"), new FloatWritable(min));
        context.write(new Text(key.toString() + "_max"), new FloatWritable(max));
        context.write(new Text(key.toString() + "_mean"), new FloatWritable((avr+0.0f)/times));
    }
}
