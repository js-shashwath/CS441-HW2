package com.wc

import java.lang.Iterable
import java.util.StringTokenizer

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}
import scala.collection.JavaConverters._
class t1 {

}
object t1 {
  class Task1Mapper extends Mapper[Object, Text, Text, IntWritable] {
    val word = new Text()
    val LOGS = Array("WARN", "INFO", "DEBUG", "ERROR")
    override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
      val itr = new StringTokenizer(value.toString)
      val timeStamp = itr.nextToken()
      while (itr.hasMoreTokens()) {
        val keyword: String = itr.nextToken()
        if(LOGS.contains(keyword)) {
          val timeStampSeconds = timeStamp.split('.')(0)
          word.set(timeStampSeconds+ "__" +keyword)
        }
        if(!itr.hasMoreTokens()){
          context.write(word,  new IntWritable(1))
        }
      }
    }
  }

  class Task1Reducer extends Reducer[Text, IntWritable, Text, IntWritable] {
    override def reduce(key: Text, values: Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
      val sum = values.asScala.foldLeft(0)(_ + _.get)
      context.write(key, new IntWritable(sum))
    }
  }
}