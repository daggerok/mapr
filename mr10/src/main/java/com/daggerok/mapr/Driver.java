package com.daggerok.mapr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.BufferedReader;
import java.io.FileReader;
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
        if (null == args || args.length != 3) {
            //log.error("usage:\n java -cp $(hbase classpath):$jarFile $inputFile $outputDir");
            log.error("usage:\n hadoop jar $jarFile -D var1=\"verbal\" -D var2=\"math\" $inputFile $outputDir1 $outputDir2");
            System.exit(-1);
        }

        String input = args[0];
        if (Files.notExists(Paths.get(input))) {
            log.error("input file wasn't found.");
            System.exit(-2);
        }

        String output1 = args[1];
        try {
            Files.deleteIfExists(Paths.get(output1));
        } catch (IOException e) {
            if (Files.exists(Paths.get(output1))) {
                output1 += new Date().getTime();
                log.warn("output1 dir is exists. using: " + output1);
            }
        }

        String output2 = args[2];
        try {
            Files.deleteIfExists(Paths.get(output2));
        } catch (IOException e) {
            if (Files.exists(Paths.get(output2))) {
                output2 += new Date().getTime();
                log.warn("output2 dir is exists. using: " + output2);
            }
        }

        return job(input, output1, output2);
    }

    private int job(String input, String output1, String output2) throws IOException, ClassNotFoundException, InterruptedException {
        // setup the first job (university: com.daggerok.mapr.university.Driver.java)
        getConf().set("textinputformat.record.delimiter", "))");

        Job job = new Job(getConf(), "university" + System.getProperty("user.name"));

        job.setJarByClass(Driver.class);
        job.setMapperClass(com.daggerok.mapr.university.Mapper.class);
        job.setReducerClass(com.daggerok.mapr.university.Reducer.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        // setup input and output paths for first job
        FileInputFormat.addInputPath(job, new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output1));
        // run first job
        boolean university = job.waitForCompletion(true);
        // put results from first job in distributed cache
        FileSystem fs = FileSystem.get(getConf());
        Path resultsFile = new Path(output1 + "/part-r-00000");
        fs.copyFromLocalFile(resultsFile, new Path("/user/user01/stats.txt")); // hardcoded... better is get from args
        DistributedCache.addCacheFile(resultsFile.toUri(), getConf());

        // setup the second job (statistics: com.daggerok.mapr.statistics.Driver.java)
        getConf().set("textinputformat.record.delimiter", "))");
        job = new Job(getConf(), "stats" + System.getProperty("user.name"));
        job.setJarByClass(Driver.class);
        job.setMapperClass(com.daggerok.mapr.statistics.Mapper.class);
        job.setReducerClass(com.daggerok.mapr.statistics.Reducer.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        // setup input and output paths for second job
        FileInputFormat.addInputPath(job, new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output2));
        // run second job
        boolean statistics = job.waitForCompletion(true);

        String var1 = getConf().get("var1");
        String var2 = getConf().get("var2");
        double squares1 = 0;
        double squares2 = 0;
        double products = 0;

        try (BufferedReader cacheReader = new BufferedReader(new FileReader(output1 + "/part-r-00000"))) {
            String line = null;

            while ((line = cacheReader.readLine()) != null) {
                if (line.contains(var1)) {              // find firstVar_sumofsquares
                    squares1 = Double.parseDouble(line.split("\\s+")[1]);
                } else if (line.contains(var2)) {       // find secondVar_sumofsquares
                    squares2 = Double.parseDouble(line.split("\\s+")[1]);
                } else if (line.contains("product")) {  // find product_sumofsquares
                    products = Double.parseDouble(line.split("\\s+")[1]);
                }
            }
        }

        double coefficient = products / Math.sqrt(squares1 * squares2);

        log.info("product_sumofsquares is " + products);
        log.info("var1_sumofsquares is " + squares1);
        log.info("var2_sumofsquares is " + squares2);
        log.info("spearman's coefficient is " + coefficient);

        return university && statistics ? 0 : 1;
    }
}
