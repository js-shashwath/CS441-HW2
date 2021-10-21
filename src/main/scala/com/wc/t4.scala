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

class t4 {

}
object t4 {
  class Task4Mapper extends Mapper[Object, Text, Text, IntWritable] {
    val word = new Text()
    val LOGS = Array("WARN", "INFO", "DEBUG", "ERROR");
    override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
      val itr = new StringTokenizer(value.toString)
      while (itr.hasMoreTokens()) {
        val keyword: String = itr.nextToken()
        if(LOGS.contains(keyword)) {
          word.set(keyword)
        }
        if(!itr.hasMoreTokens()){
          val charCount =  new IntWritable(keyword.length);
          context.write(word, charCount)
        }
      }
    }
  }

  class Task4Reducer extends Reducer[Text, IntWritable, Text, IntWritable] {
    override def reduce(key: Text, values: Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
      var maxval = values.asScala.reduceLeft((a, b) => {
        if(a.get() > b.get()) {
          a
        }
        b
      })
      context.write(key, maxval)
    }
  }
}