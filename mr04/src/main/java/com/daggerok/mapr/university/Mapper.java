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
    private static final String satVerbal = "sat verbal";
    private static final String satMath = "sat math";

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String record = value.toString();

        if (record.contains(satVerbal)) {
            int verbal = find(satVerbal, record);

            log.info(satVerbal + ": " + verbal);
            context.write(new Text("satv"), new IntWritable(verbal));
        }

        if (record.contains(satMath)) {
            int math = find(satMath, record);

            log.info(satMath + ": " + math);
            context.write(new Text("satm"), new IntWritable(math));
        }
    }

    private static int find(String what, String where) {
        String line = "";

        for (StringTokenizer lineTokenizer = new StringTokenizer(where, "\n");
             lineTokenizer.hasMoreTokens() && !line.contains(what);
             line = lineTokenizer.nextToken().toString());
        return parse(line);
    }

    private static int parse(String string) {
        return new Integer(string.split("\\s+")[3].split("\\)")[0]).intValue();
    }
}
