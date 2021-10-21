package com.wc

import com.wc.t1.{Task1Mapper, Task1Reducer}
import com.wc.t2.{Task2Job1Mapper, Task2Job1Reducer, Task2Job2Mapper, Task2Job2Reducer}
import com.wc.t3.{Task3Mapper, Task3Reducer}
import com.wc.t4.{Task4Mapper, Task4Reducer}

import java.lang.Iterable
import java.util.StringTokenizer
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io
import org.apache.hadoop.io.{IntWritable, Text, WritableComparable, WritableComparator}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}

import java.lang.Integer.parseInt
import scala.collection.JavaConverters._
import scala.collection.immutable.Nil.slice

class LogFileWordCounter

object LogFileWordCounter {
  def main(args: Array[String]): Unit = {
    if(args(2) == "one") {
      val configuration = new Configuration
      configuration.set("mapred.textoutputformat.separator", ",")
      val job = Job.getInstance(configuration, "JOB_1")
      job.setJarByClass(this.getClass)
      job.setMapperClass(classOf[Task1Mapper])
      job.setCombinerClass(classOf[Task1Reducer])
      job.setReducerClass(classOf[Task1Reducer])
      job.setOutputKeyClass(classOf[Text])
      job.setOutputKeyClass(classOf[Text]);
      job.setOutputValueClass(classOf[IntWritable]);
      FileInputFormat.addInputPath(job, new Path(args(0)))
      FileOutputFormat.setOutputPath(job, new Path(args(1)))
      System.exit(if (job.waitForCompletion(true)) 0 else 1)
    } else if(args.length > 3 && args(3) == "two") {
      val configuration = new Configuration
      val job = Job.getInstance(configuration, "JOB_21")
      job.setJarByClass(this.getClass)
      job.setMapperClass(classOf[Task2Job1Mapper])
      job.setCombinerClass(classOf[Task2Job1Reducer])
      job.setReducerClass(classOf[Task2Job1Reducer])
      job.setOutputKeyClass(classOf[Text])
      job.setOutputValueClass(classOf[IntWritable]);
      FileInputFormat.addInputPath(job, new Path(args(0)))
      FileOutputFormat.setOutputPath(job, new Path(args(1)))
      val success = job.waitForCompletion(true);

      if (success) {
        val conf = new Configuration
        conf.set("mapred.textoutputformat.separator", ",")
        val job2 = Job.getInstance(conf, "JOB_22")
        job2.setJarByClass(this.getClass)
        job2.setMapperClass(classOf[Task2Job2Mapper])
        job2.setReducerClass(classOf[Task2Job2Reducer])
        job2.setMapOutputKeyClass(classOf[IntWritable])
        job2.setMapOutputValueClass(classOf[Text])
        job2.setOutputKeyClass(classOf[Text])
        job2.setOutputValueClass(classOf[IntWritable]);
        FileInputFormat.addInputPath(job2, new Path(args(1)))
        FileOutputFormat.setOutputPath(job2, new Path(args(2)))
        System.exit(if (job2.waitForCompletion(true)) 0 else 1)
      }
    } else if(args(2) == "three") {
      val configuration = new Configuration
      configuration.set("mapred.textoutputformat.separator", ",")
      val job = Job.getInstance(configuration, "JOB_3")
      job.setJarByClass(this.getClass)
      job.setMapperClass(classOf[Task3Mapper])
      job.setCombinerClass(classOf[Task3Reducer])
      job.setReducerClass(classOf[Task3Reducer])
      job.setOutputKeyClass(classOf[Text])
      job.setOutputKeyClass(classOf[Text]);
      job.setOutputValueClass(classOf[IntWritable]);
      FileInputFormat.addInputPath(job, new Path(args(0)))
      FileOutputFormat.setOutputPath(job, new Path(args(1)))
      System.exit(if (job.waitForCompletion(true)) 0 else 1)
    } else if(args(2) == "four") {
      val configuration = new Configuration
      configuration.set("mapred.textoutputformat.separator", ",")
      val job = Job.getInstance(configuration, "word count")
      job.setJarByClass(this.getClass)
      job.setMapperClass(classOf[Task4Mapper])
      job.setCombinerClass(classOf[Task4Reducer])
      job.setReducerClass(classOf[Task4Reducer])
      job.setOutputKeyClass(classOf[Text])
      job.setOutputKeyClass(classOf[Text]);
      job.setOutputValueClass(classOf[IntWritable]);
      FileInputFormat.addInputPath(job, new Path(args(0)))
      FileOutputFormat.setOutputPath(job, new Path(args(1)))
      System.exit(if (job.waitForCompletion(true)) 0 else 1)
    }
  }
}
