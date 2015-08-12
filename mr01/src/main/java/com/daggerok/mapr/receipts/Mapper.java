package com.daggerok.mapr.receipts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.StringTokenizer;

public class Mapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text> {
    private static final Log log = LogFactory.getLog(Mapper.class);

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        StringTokenizer iterator = new StringTokenizer(value.toString(), " ");
        String year = new String(iterator.nextToken()).toString();
        Text text = new Text();

        iterator.nextToken();
        iterator.nextToken();
        text.set(iterator.nextToken());
        log.info(new Text("summary") + " " + new Text(year + "_" + text.toString()));
        context.write(new Text("summary"), new Text(year + "_" + text.toString()));
    }
}
