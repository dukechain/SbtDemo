package com.demo.reflection
/**
 * Created by chenxu on 14.09.16.
 */
class Record[K, V](val key: K, val value: V){
  def print() {
    println(key+ "," + value)
  }
}
