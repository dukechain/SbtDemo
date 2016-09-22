package com.demo.dynamicloader

import java.io._
import scala.reflect._
import scala.reflect.runtime.{universe => ru}

/**
 * Created by chenxu on 08.09.16.
 *
 * Reference: http://stackoverflow.com/questions/8867766/scala-dynamic-object-class-loading
 *
 * http://docs.scala-lang.org/overviews/reflection/overview.html
 */
class JarLoader {

  def loader(jarName: String, classname: String, methodname:String, parameterTypes: Class[_], parameters: Object):Unit= {
    var classLoader = new java.net.URLClassLoader(
      Array(new File(jarName).toURI.toURL),
      /*
     * need to specify parent, so we have all class instances
     * in current context
     */
      this.getClass.getClassLoader)

    /*
   * please note that the suffix "$" is for Scala "object",
   * it's a trick
   */
    val clazzExModule = classLoader.loadClass(classname)

    val mm = clazzExModule.getDeclaredMethod(methodname, parameterTypes)


    //println(clazzExModule.isInstanceOf[JarLoader])


    mm.invoke(clazzExModule.newInstance(), parameters)

  }


  def loader(jarName: String, userdefined_class_name: String, expected_class_name:Class[_]): Class[_] = {
    var classLoader = new java.net.URLClassLoader(
      Array(new File(jarName).toURI.toURL),
      /*
     * need to specify parent, so we have all class instances
     * in current context
     */
   this.getClass.getClassLoader)

    /*
   * please note that the suffix "$" is for Scala "object",
   * it's a trick
   */
    val loaded_class = classLoader.loadClass(userdefined_class_name)
    //val expected = this.getClass.loadClass(expected_class_name)


    def m[T: ru.TypeTag, S: ru.TypeTag](x: T, y: S): Boolean = {
      val leftTag = ru.typeTag[T]
      val rightTag = ru.typeTag[S]
      leftTag.tpe <:< rightTag.tpe
    }

    val d = loaded_class.newInstance()
    val c = expected_class_name.newInstance()

    if (m(loaded_class,loaded_class)) {
      loaded_class
    }
    else null
  }
}
