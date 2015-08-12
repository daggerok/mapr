package com.daggerok.mapr.voter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.StringTokenizer;

public class Mapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, IntWritable> {
    private static final Log log = LogFactory.getLog(Mapper.class);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        StringTokenizer input = new StringTokenizer(value.toString(), ",");

        if (6 != input.countTokens()) {
            log.warn("input has wrong (not " + 6 + ") fields number: " + input.countTokens());
            StringBuilder details = new StringBuilder();
            for (int i = 0; input.hasMoreTokens(); i++) {
                details.append(i).append(":").append(input.nextToken()).append(";");
            }
            log.warn(details.toString());
            context.getCounter("voter", "wrong_fields_number").increment(1L);
            return;
        }

        input.nextToken();
        input.nextToken();
        String ageString = input.nextToken();

        //log.info("ageString: " + ageString);

        int age = Integer.valueOf(ageString).intValue();

        if (age < 1) {
            log.warn("input has wrong age: " + age);
            context.getCounter("voter", "wrong_age").increment(1L);
            return;
        }
        context.write(new Text(input.nextToken()), new IntWritable(age));
    }
}
