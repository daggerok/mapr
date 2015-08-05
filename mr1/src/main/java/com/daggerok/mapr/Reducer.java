package com.daggerok.mapr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, FloatWritable> {
    private static final Log log = LogFactory.getLog(Reducer.class);

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        long max = Long.MIN_VALUE;
        long min = Long.MAX_VALUE;
        Text minYear = new Text();
        Text maxYear = new Text();
        Text keyMin = new Text();
        Text keyMax = new Text();
        FloatWritable valMin, valMax;

        for (Text value : values) {
            String[] keyString = value.toString().split("_");
            Text tempYear = new Text(keyString[0]);
            long tempValue = new Long(keyString[1]).longValue();

            if (tempValue < min) {
                min = tempValue;
                minYear = tempYear;
            }
            if (tempValue > max) {
                max = tempValue;
                maxYear = tempYear;
            }
        }

        keyMin.set("min" + "(" + minYear.toString() + "): ");
        valMin = new FloatWritable(min);
        log.info(keyMin + ":" + valMin);
        context.write(keyMin, valMin);

        keyMax.set("max" + "(" + maxYear.toString() + "): ");
        valMax = new FloatWritable(max);
        log.info(keyMax + ":" + valMax);
        context.write(keyMax, valMax);
    }
}