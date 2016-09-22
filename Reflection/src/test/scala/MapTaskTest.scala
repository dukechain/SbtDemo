package com.demo.reflection

import java.io.File

import scala.collection.mutable.ListBuffer

/**
 * Created by chenxu on 08.09.16.
 */
class UserMapFunction extends MapFunction {

  def map(in: Pair[Int, String]): List[Pair[String, Int]] = {
    var out = ListBuffer.empty[Pair[String, Int]]

    def mapandappend(s: String): Unit = {
      var item = new Pair[String, Int](s, 1)
      out += item
    }

    in.value.split(" ").foreach {
      s => mapandappend(s)
    }

    out.toList
  }

  def map(input:Object):List[Object] = {
    var out = ListBuffer.empty[Record[String, Int]]

    def mapandappend(s: String): Unit = {
      var item = new Record[String, Int](s, 1)
      out += item
    }

    input.asInstanceOf[Record[Int,String]].value.split(" ").foreach {
      s => mapandappend(s)
    }

    out.toList
  }

  /*def map(input:Record[Int,String]):List[Record[String,Int]] = {
    var out = ListBuffer.empty[Record[String, Int]]

    def mapandappend(s: String): Unit = {
      var item = new Record[String, Int](s, 1)
      out += item
    }

    input.value.split(" ").foreach {
      s => mapandappend(s)
    }

    out.toList
  }*/

  /*def map(in: RecordFormat[Int, String]): List[RecordFormat[String, Int]] = {
    var out = ListBuffer.empty[Pair[String, Int]]

    def mapandappend(s: String): Unit = {
      var item = new Pair[String, Int](s, 1)
      out += item
    }

    in.value.split(" ").foreach {
      s => mapandappend(s)
    }

    out.toList
  }*/
}


object MapTaskTest {
  def main(args: Array[String]): Unit = {

    System.out.println(System.getProperty("user.dir"))

//    new MapTask().mapper(new File("./text.txt"), new File("./output.txt"), new UserMapFunction)

    new MapTask().mapper(new File("./text.txt"), new TextFormat,
    new File("./output.txt"), new UserMapFunction)
  }
}
