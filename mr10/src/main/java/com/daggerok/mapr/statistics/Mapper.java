package com.daggerok.mapr.statistics;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Mapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, IntWritable> {
    public static int mean1;
    public static int mean2;
    public static String var1 = null;
    public static String var2 = null;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        Configuration conf = context.getConfiguration();

        var1 = context.getConfiguration().get("var1");
        var2 = context.getConfiguration().get("var2");

        Path[] localFiles = DistributedCache.getLocalCacheFiles(conf);

        // we only have 1 file in the cache (location: localFiles[0])
        // it looks like this:
        // math_min        325
        // math_max        780
        // math_mean       592
        // verbal_min      300
        // verbal_max      700
        // verbal_mean     539

        try (BufferedReader cacheReader = new BufferedReader(new FileReader(localFiles[0].toString()))) {
            String line = null;

            while ((line = cacheReader.readLine()) != null) {
                if (line.contains(var1 + "_mean")) {                // find var1_mean
                    mean1 = Integer.parseInt(line.split("\\t")[1]);
                } else if (line.contains(var2 + "_mean")) {         // find var2_mean
                    mean2 = Integer.parseInt(line.split("\\t")[1]);
                }
            }
        }
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // create iterator over record assuming line-separated fields
        String inputs = value.toString();

        if (inputs.contains("%") || !inputs.contains(var1) || !inputs.contains(var2)) {
            return;
        }

        StringTokenizer iterator = new StringTokenizer(inputs, "\n");

        String first = null;
        String current = null;
        // pull out line for firstVar
        while (iterator.hasMoreTokens()) {
            current = iterator.nextToken().toString();
            if (current.contains(var1)) {
                first = var1;
                break;
            } else if (current.contains(var2)) {
                first = var2;
                break;
            }
        }
        // pull out value from line, pull right parens from value
        current = current.split("\\s+")[3].split("\\)")[0];

        int firstVal = Integer.parseInt(current);

        // pull out line for var2
        String second = null;
        while (iterator.hasMoreTokens()) {
            current = iterator.nextToken().toString();
            if (current.contains(var1)) {
                second = var1;
                break;
            } else if (current.contains(var2)) {
                second = var2;
                break;
            }
        }
        // pull out value from line, pull right parens from value
        current = current.split("\\s+")[3].split("\\)")[0];

        int secondVal = Integer.parseInt(current);

        int int1 = var1 == first ? firstVal : secondVal;
        int int2 = var1 == first ? secondVal : firstVal;
        // compute deltas
        int delta1 = mean1 - int1;
        int delta2 = mean2 - int2;
        // compute product
        int productInt = delta1 * delta2;
        // emit key-value as
        context.write(new Text(var1 + "_delta"), new IntWritable(delta1));
        context.write(new Text(var2 + "_delta"), new IntWritable(delta2));
        context.write(new Text("product"), new IntWritable(productInt));
    }
}
