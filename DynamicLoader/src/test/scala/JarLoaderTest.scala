package com.demo.dynamicloader


/**
 * Created by chenxu on 09.09.16.
 */
object JarLoaderTest {
  def main(args: Array[String]) {
    new JarLoader().loader("./out/artifacts/SbtDemo_jar",
      "com.demo.dynamicloader.example.UDFExample",
      "printer",
    classOf[String],
    "hello")

    /*val cla = new JarLoader().loader("./out/artifacts/SbtDemo_jar",
      "com.demo.dynamicloader.example.UDFExample",
      classOf[Example])

    //val clazzExModule = cla.asInstanceOf[Class[_]]
    val clazzExModule = cla

    val mm = clazzExModule.getDeclaredMethod("printer", classOf[String])
    mm.invoke(clazzExModule.newInstance(), "hi")*/
  }
}
