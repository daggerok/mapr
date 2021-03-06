package com.daggerok.mapr.voter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class Driver extends Configured implements Tool {
    private static final Log log = LogFactory.getLog(Driver.class);

    public static void runJob(String[] args) throws Exception {
        System.exit(ToolRunner.run(new Configuration(), new Driver(), args));
    }

    @Override
    public int run(String[] args) throws Exception {
        if (null == args || args.length != 2) {
            log.error("usage:\n java -jar $jarFile $inputFile $outputDir");
            System.exit(-1);
        }

        String input = args[0];

        if (Files.notExists(Paths.get(input))) {
            log.error("input file wasn't found.");
            System.exit(-2);
        }

        String output = args[1];

        if (Files.exists(Paths.get(output))) {
            output += new Date().getTime();
            log.warn("output dir is exists. using: " + output);
        }
        return job(input, output);
    }

    private int job(String input, String output) throws IOException, ClassNotFoundException, InterruptedException {
        // setup delimeter
        getConf().set("textinputformat.record.delimiter", "\n");
        Job job = new Job(getConf(), Driver.class.getPackage().getName());
        // set map-reduce
        job.setJarByClass(Driver.class);
        job.setCombinerClass(Combiner.class);
        job.setMapperClass(Mapper.class);
        job.setReducerClass(Reducer.class);
        // input format
        job.setInputFormatClass(TextInputFormat.class);
        // mapper key-value
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        // reducer key-value
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FloatWritable.class);
        // input and output paths
        FileInputFormat.addInputPath(job, new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output));
        // launch job synchronously
        return job.waitForCompletion(true) ? 0 : 1;
    }
}
