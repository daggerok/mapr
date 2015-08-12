package com.daggerok.mapr.university;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DriverTest {
    private static MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;

    @Before
    public void setUp() throws Exception {
        mapDriver = MapDriver.newMapDriver(new Mapper());
    }

    /*"(def-instance Adelphi\n" +                       //0
                "   (state newyork)\n" +
                "   (control private)\n" +
                "   (no-of-students thous:5-10)\n" +
                "   (male:female ratio:30:70)\n" +
                "   (student:faculty ratio:15:1)\n" +
                "   (sat verbal 500)\n" +               //6
                "   (sat math 475)\n" +                 //7
                "   (expenses thous$:7-10)\n" +
                "   (percent-financial-aid 60)\n" +
                "   (no-applicants thous:4-7)\n" +
                "   (percent-admittance 70)\n" +
                "   (percent-enrolled 40)\n" +
                "   (academics scale:1-5 2)\n" +
                "   (social scale:1-5 2)\n" +
                "   (quality-of-life scale:1-5 2)\n" +
                "   (academic-emphasis business-administration)\n" +
                "   (academic-emphasis biology))";*/

    @Test
    public void testMapperNothing() throws Exception {
        mapDriver
                .withInput(new LongWritable(0), new Text("(def-instance Adelphi\n"))
                .runTest();
    }

    @Test(expected = AssertionError.class)
    public void testMapperNothingNegative() throws Exception {
        mapDriver
                .withInput(new LongWritable(6), new Text("   (sat verbal 500)\n"))
                .runTest();
    }

    @Test
    public void testMapperSatv() throws Exception {
        mapDriver
                .withInput(new LongWritable(6), new Text("   (sat verbal 500)\n"))
                .withOutput(new Text("satv"), new IntWritable(500))
                .runTest();
    }

    @Test
    public void testMapperMatv() throws Exception {
        mapDriver
                .withInput(new LongWritable(7), new Text("   (sat math 475)\\n"))
                .withOutput(new Text("satm"), new IntWritable(475))
                .runTest();
    }
}
