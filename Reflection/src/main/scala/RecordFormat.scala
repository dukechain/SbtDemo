package com.demo.reflection

import java.io.File

/**
 * Created by chenxu on 09.09.16.
 */


abstract class RecordFormat [K,V]{
  /*
  constructor
   */
  //protected val data_collection:List[Record[K,V]] = null

  // functions
  //def load(inputfile: File): Unit

  def isEmpty():Boolean

  def getData(inputfile: File):List[Record[K,V]]

}
