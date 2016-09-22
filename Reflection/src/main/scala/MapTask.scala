package com.demo.reflection

import java.io._


import scala.io.Source

/**
 * Created by chenxu on 08.09.16.
 */
class MapTask {

  def mapper(inputfile: File, outputfile: File, mapFunction: MapFunction): Unit = {

    val bw = new BufferedWriter(new FileWriter(outputfile))

    var i = 0

    for (line <- Source.fromFile(inputfile).getLines) {
      println(line)
      i += 1

      val in: Pair[Int, String] = new Pair[Int, String](i, line)
      var out = mapFunction.map(in)

      out.foreach(s => bw.write(s.key.toString + " " + s.value.toString + System.getProperty("line.separator")))

    }

    bw.close()
  }


  def mapper[K,V](inputfile: File, inputFormat: RecordFormat[K,V], outputfile: File, mapFunction: Object): Unit = {

    val bw = new BufferedWriter(new FileWriter(outputfile))

    //val rf = inputFormat.asInstanceOf[RecordFormat]

    //inputFormat.load(inputfile)

    inputFormat.getData(inputfile).foreach( x => {
      var out = mapFunction.asInstanceOf[MapFunction].map(x)

      out.foreach(s =>
        bw.write( s.asInstanceOf[Record[AnyVal,AnyVal]].key.toString + " " +
          s.asInstanceOf[Record[AnyVal,AnyVal]].value.toString +
          System.getProperty("line.separator")))
    }
    )


    bw.close()
  }
}


