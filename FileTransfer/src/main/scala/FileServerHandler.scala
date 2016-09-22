package com.demo.filetransfer

import io.netty.buffer.ByteBuf
import io.netty.channel._
import java.io._

import org.jboss.netty.channel.MessageEvent

/**
 * Created by chenxu on 20.09.16.
 *
 * http://www.coderli.com/netty-file-transfer/
 *
 * Changes of netty 4
 * http://wangxinyue1130.blog.163.com/blog/static/23439507020145910106375/
 */

class FileServerHandler extends ChannelInboundHandlerAdapter{
  private var file:File = null
  private var fos: FileOutputStream = null

  def this(path:String) {
    this()
    try {
      file = new File(path)

      if (!file.exists()) {
        file.createNewFile()
      } else {
        file.delete()
        file.createNewFile()
      }
      fos = new FileOutputStream(file);
    } catch {
      case e: IOException => e.printStackTrace();
    }
  }

  override def channelRead(ctx:ChannelHandlerContext, e:Object) :Unit ={

    try {
      var buffer = e.asInstanceOf[ByteBuf]
      var length = buffer.readableBytes()
      buffer.readBytes(fos, length)
      fos.flush()
      buffer.clear()
    } catch {
      case e:Exception => throw e
    }
  }
}
