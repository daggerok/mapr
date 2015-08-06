package com.daggerok.mapr.university;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;

import java.io.IOException;
import java.util.StringTokenizer;



public class UniversityMapper  extends Mapper <LongWritable,Text,Text,IntWritable> {
   public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
      // create iterator over record assuming line-separated fields 
      String valueString = value.toString();
      //if(! valueString.contains("percent-financial-aid") || ! valueString.contains("quality-of-life")) {
         //context.write(new Text("ISF"), new IntWritable(0));
         //context.write(new Text("ISF"), new Text("ISF")); 

      //if(valueString.contains("percent-financial-aid") &&  valueString.contains("social")) {
      if(valueString.contains("sat verbal") &&  valueString.contains("sat math")) {
      StringTokenizer iterator = new StringTokenizer(valueString,"\n");
      //int numTokens = iterator.countTokens();
      //if (numTokens != 0) { 
      
      String[] valueStringArray;
      String tempString=null;
      
      // sample record:
    /* (def-instance Adelphi
   (state newyork)
   (control private)
   (no-of-students thous:5-10)
   (male:female ratio:30:70)
   (student:faculty ratio:15:1)
   (sat verbal 500)
   (sat math 475)
   (expenses thous$:7-10)
   (percent-financial-aid 60)
   (no-applicants thous:4-7)
   (percent-admittance 70)
   (percent-enrolled 40)
   (academics scale:1-5 2)
   (social scale:1-5 2)
   (quality-of-life scale:1-5 2)
   (academic-emphasis business-administration)
   (academic-emphasis biology*/

      // pull out line for percent-financial-aid from record
      while(iterator.hasMoreTokens()) {
         tempString = iterator.nextToken().toString();
         //if (tempString.contains("percent-financial-aid"))
         if (tempString.contains("verbal"))
            break;
      }

      //(percent-financial-aid 20)
      // pull out value from line
      valueStringArray = tempString.split("\\s+");
      tempString = valueStringArray[3];
      // pull right parens from value
      valueStringArray = tempString.split("\\)"); 
      tempString = valueStringArray[0];
      //int faInt = (new Integer(tempString)).intValue();
      int satVerbal = (new Integer(tempString)).intValue();
      

      // pull out line for quality-of-life  from record      
      while(iterator.hasMoreTokens()) {
         tempString = iterator.nextToken().toString();
         //if (tempString.contains("quality-of-life"))
         if (tempString.contains("math"))
            break;
      }
      //(quality-of-life scale:1-5 5)
      //(social scale:1-5 2)
      // pull out value from line
      valueStringArray = tempString.split("\\s+");
      tempString = valueStringArray[3];
      // pull right parens from value
      valueStringArray = tempString.split("\\)"); 
      tempString = valueStringArray[0];
      //int qolInt = (new Integer(tempString)).intValue();
      //int sInt = (new Integer(tempString)).intValue();
      int satMath = (new Integer(tempString)).intValue();
      


      // emit key-value as  
      //context.write(new Text("fa"), new IntWritable(faInt)); 
      //context.write(new Text("qol"), new IntWritable(qolInt)); 
      //context.write(new Text("s"), new IntWritable(sInt)); 
      context.write(new Text("satv"), new IntWritable(satVerbal)); 
      context.write(new Text("satm"), new IntWritable(satMath)); 
      }
   }
}
