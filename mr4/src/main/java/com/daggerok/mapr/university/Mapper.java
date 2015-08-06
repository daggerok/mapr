package com.daggerok.mapr.university;

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
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String record = value.toString();

        if (!record.contains("sat math") || !record.contains("sat verbal")) {
            return;
        }

        StringTokenizer lineTokenizer = new StringTokenizer(record, "\n");
        String line = null;

        while (lineTokenizer.hasMoreTokens()) {
            line = lineTokenizer.nextToken().toString();
            if (line.contains("verbal")) {
                break;
            }
        }

        int verbal = new Integer(parse(line)).intValue();

        log.info("verbal:"+verbal);

        while (lineTokenizer.hasMoreTokens()) {
            line = lineTokenizer.nextToken().toString();
            if (line.contains("math")) {
                break;
            }
        }

        int math = new Integer(parse(line)).intValue();

        log.info("math:"+math);

        context.write(new Text("satv"), new IntWritable(verbal));
        context.write(new Text("satm"), new IntWritable(math));
    }

    private static String parse(String line) {
        return line.split("\\s+")[3].split("\\)")[0];
    }
}
