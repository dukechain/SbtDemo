package com.demo.dynamicloader.example

/**
 * Created by chenxu on 08.09.16.
 */

import com.demo.dynamicloader.Example

class UDFExample extends Example{
  def printer(str: String):Unit ={
    println(str)
  }
}

//object ExModule extends Module {}
