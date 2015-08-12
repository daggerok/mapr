package com.daggerok.mapr.twojobs;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

public class Driver extends Configured {
    public static void runJobs(String[] args) throws Exception {
        JobConf jobConf1 = new JobConf(Driver.class);
        jobConf1.setMapperClass(Map1.class);
        jobConf1.setReducerClass(Reduce1.class);
        JobClient.runJob(jobConf1);

        JobConf jobConf2 = new JobConf(Driver.class);
        jobConf2.setMapperClass(Map2.class);
        jobConf2.setReducerClass(Reduce2.class);
        JobClient.runJob(jobConf2);
    }
}
