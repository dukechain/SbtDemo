package com.demo.reflection

import java.io.File
import scala.collection.mutable.ListBuffer
import scala.io.Source

/**
 * Created by chenxu on 09.09.16.
 */
class TextFormat extends RecordFormat [Int,String] {

  private var data_collection:List[Record[Int,String]] = null

  private def load(inputfile: File): Unit = {
    var buffer = ListBuffer.empty[Record[Int, String]]
    for (line <- Source.fromFile(inputfile).getLines()) {

      val record = new Record(0, line)


      buffer += record
    }

    data_collection = buffer.toList
  }

//  override def getData():List[Record[Int,String]] = {
//    data_collection
//  }

  def isEmpty():Boolean = {
    data_collection.isEmpty
  }

  def getData(inputfile: File):List[Record[Int,String]] = {
    if(data_collection == null ){
      load(inputfile)
    }

    data_collection
  }
}

