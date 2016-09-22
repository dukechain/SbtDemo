package com.demo.filetransfer


import io.netty.buffer.Unpooled
import io.netty.channel._
import java.io._

/**
 * Created by chenxu on 20.09.16.
 */
class FileClientHandler extends ChannelInboundHandlerAdapter {

  private val readLength = 8
  private var filepath:String = null

  def this(filepath:String){
    this()
    this.filepath = filepath
  }

  override def channelRegistered( ctx:ChannelHandlerContext):Unit={
     // 发送文件
    sendFile(ctx.channel())
  }

  private def sendFile(channel: Channel) :Unit = {
    var file = new File(filepath)
    var fis = new FileInputStream(file)
    var count = 0
    var bis = new BufferedInputStream(fis)
    while (true) {
      var bytes = new Array[Byte](readLength)
      var readNum = bis.read(bytes, 0, readLength)
      if (readNum == -1) {
        return
      }
      sendToServer(bytes, channel, readNum)
      count += 1
      System.out.println("Send count: " + count)
    }

  }

  private def sendToServer( bytes: Array[Byte], channel:Channel, length:Int):Unit= {
    var buffer = Unpooled.copiedBuffer(bytes, 0, length)
    channel.write(buffer)
  }



}
