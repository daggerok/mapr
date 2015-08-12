package com.daggerok.mapr.voter;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ReducerTest {
    private static ReduceDriver<Text, IntWritable, Text, FloatWritable> reduceDriver;

    @Before
    public void setUp() throws Exception {
        reduceDriver = reduceDriver.newReduceDriver(new Reducer());
    }

    @Test
    public void testReduce() throws Exception {
        Text key = new Text("democrat");

        reduceDriver
                .withInput(key, Arrays.asList(new IntWritable(1), new IntWritable(2), new IntWritable(3)))
                .withOutput(key, new FloatWritable((1+2+3)/3.0f))
                .runTest();
    }
}
