package com.daggerok.mapr.university;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
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
            log.error("usage:\n java -cp $(hbase classpath):$jarFile $inputFile $outputDir");
            System.exit(-1);
        }

        String input = args[0];
        if (Files.notExists(Paths.get(input))) {
            log.error("input file wasn't found.");
            System.exit(-2);
        }

        String output = args[1];
        try {
            Files.deleteIfExists(Paths.get(output));
        } catch (IOException e) {
            if (Files.exists(Paths.get(output))) {
                output += new Date().getTime();
                log.warn("output dir is exists. using: " + output);
            }
        }

        return job(input, output);
    }

    private int job(String input, String output) throws IOException, ClassNotFoundException, InterruptedException {
        // setup job
        getConf().set("textinputformat.record.delimiter", "))");

        Job job = new Job(getConf(), "university" + System.getProperty("user.name"));

        job.setJarByClass(Driver.class);
        job.setMapperClass(Mapper.class);
        job.setReducerClass(Reducer.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        // setup input and output paths
        FileInputFormat.addInputPath(job, new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output));
        // log vars
        String var1 = getConf().get("var1");
        String var2 = getConf().get("var2");
        log.info("var1 is " + var1);
        log.info("var2 is " + var2);
        // run job synchronously
        return job.waitForCompletion(true) ? 0 : 1;
    }
}
