package com.wc

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.{Mapper, Reducer}
import java.lang.Integer.parseInt
import java.util.StringTokenizer
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

class t2 {

}
object t2 {
  class Task2Job1Mapper extends Mapper[Object, Text, Text, IntWritable] {
    val word = new Text()
    val LOGS = Array("ERROR")
    override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
      val itr = new StringTokenizer(value.toString)
      val timeStamp = itr.nextToken()
      while (itr.hasMoreTokens()) {
        val keyword: String = itr.nextToken()
        if(LOGS.contains(keyword)) {
          val timeStampSeconds = timeStamp.split('.')(0)
          word.set(timeStampSeconds+ "__" +keyword)
          context.write(word,  new IntWritable(1))
        }
      }
    }
  }

  class Task2Job1Reducer extends Reducer[Text, IntWritable, Text, IntWritable] {
    override def reduce(key: Text, values: Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
      val sum = values.asScala.foldLeft(0)(_ + _.get)
      context.write(key, new IntWritable(sum))
    }
  }

  class Task2Job2Mapper extends Mapper[Object, Text, IntWritable, Text] {
    override def map(key: Object, value: Text, context: Mapper[Object, Text, IntWritable, Text]#Context): Unit = {
      val fields = value.toString.split("\t")
      val key = -1 * parseInt(fields(1))
      context.write(new IntWritable(key), new Text(fields(0)))
    }
  }

  class Task2Job2Reducer extends Reducer[IntWritable, Text, Text, IntWritable] {
    override def reduce(key: IntWritable, values: Iterable[Text], context: Reducer[IntWritable, Text, Text, IntWritable]#Context): Unit = {
      val value = values.asScala.head
      val newKey = -1 * key.get()
      context.write(value, new IntWritable(newKey))
    }
  }
}