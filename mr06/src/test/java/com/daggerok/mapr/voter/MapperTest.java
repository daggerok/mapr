package com.daggerok.mapr.voter;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

public class MapperTest {
    private static MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;

    @Before
    public void setUp() throws Exception {
        mapDriver = MapDriver.newMapDriver(new Mapper());
    }

    @Test
    public void testMap() throws Exception {
        mapDriver
                .withInput(new LongWritable(0), new Text("1000000,priscilla zipper,40,libertarian,862.71,7137"))
                .withOutput(new Text("libertarian"), new IntWritable(40))
                .runTest();
    }

    @Test
    public void testWrongNumberCounter() throws Exception {
        mapDriver
                .withInput(new LongWritable(0), new Text("priscilla zipper,40,libertarian,862.71,7137"))
                .withCounter("voter", "wrong_fields_number", 1)
                .runTest();
    }

    @Test
    public void testBadAgeCounter() throws Exception {
        mapDriver
                .withInput(new LongWritable(0), new Text("1000000,priscilla zipper,-40,libertarian,862.71,7137"))
                .withCounter("voter", "wrong_age", 1)
                .runTest();
    }
}