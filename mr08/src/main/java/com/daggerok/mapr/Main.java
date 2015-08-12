package com.daggerok.mapr;

import com.daggerok.mapr.chaining.Mapper1;
import com.daggerok.mapr.chaining.Mapper2;
import com.daggerok.mapr.chaining.Reducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.lib.ChainMapper;
import org.apache.hadoop.mapred.lib.ChainReducer;

/*
map-reduce chaining pattern: [map + reduce map*]
    map task                reduce task
Mapper1 -> Mapper2 -->
                        Reducer --> Mapper3
Mapper1 -> Mapper2 -->
                        Reducer --> Mapper3
Mapper1 -> Mapper2 -->
 */
public class Main {
    public static void main(String[] args) throws Exception {
        JobConf conf = new JobConf(true);

        JobConf mapper1Conf = new JobConf(false);
        ChainMapper.addMapper(conf, Mapper1.class, LongWritable.class, Text.class, Text.class, Text.class, true,
                mapper1Conf);

        JobConf mapper2Conf = new JobConf(false);
        ChainMapper.addMapper(conf, Mapper2.class, Text.class, Text.class, LongWritable.class, Text.class, true,
                mapper2Conf);

        JobConf reducerConf = new JobConf(false);
        ChainReducer.setReducer(conf, Reducer.class, LongWritable.class, Text.class, Text.class, IntWritable.class,
                true, reducerConf);

        //JobConf mapper3Conf = new JobConf(false);
        JobClient.runJob(conf);
    }
}
