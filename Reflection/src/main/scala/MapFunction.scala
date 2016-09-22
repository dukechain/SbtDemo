package com.demo.reflection
/**
 * Created by chenxu on 08.09.16.
 */


abstract class MapFunction {

  def map(in: Pair[Int, String]): List[Pair[String, Int]]

  def map(input:Object):List[Object]
}
