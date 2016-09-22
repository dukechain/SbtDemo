/**
 * Demonstrate the message passing between processes
 * Created by chenxu on 08.09.16.
 */

package com.demo.messagepassing

import akka.actor._
import akka.util.Timeout
import akka.pattern.ask
import com.typesafe.config.ConfigFactory
import scala.concurrent._
import scala.concurrent.Await
import scala.concurrent.duration._
import Server._



class Client extends Actor {
  var remoteActor : ActorSelection = null

  var localActor : akka.actor.ActorRef = null

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    remoteActor = context.actorSelection("akka.tcp://123@127.0.0.1:2555/user/server")
    println("remote address: " + remoteActor)
  }

  override def receive: Receive = {
    case msg: AkkaMessage => {
      println("client send messages: " + msg)
      this.localActor = sender()
      remoteActor ! msg
    }

    case res: Response => {
      localActor ! res
    }

    case _ => println("unsupported message at Client")
  }
}

object Client {
  def main (args: Array[String] ): Unit = {
    val clientSystem = ActorSystem("ClientSystem", ConfigFactory.parseString(
      """akka {
         actor {
            provider = "akka.remote.RemoteActorRefProvider"
         }
      }
      """) )

    var client = clientSystem.actorOf(Props[Client])
    var msgs = Array[AkkaMessage](AkkaMessage("message1"),AkkaMessage("message2"),
      AkkaMessage("message3"), AkkaMessage("message4"))

    implicit val timeout = Timeout(3 seconds)

//    msgs.foreach { x =>
//      client ! x
//    }

    msgs.foreach { x =>
      val future = client ? x
      val result = Await.result(future, timeout.duration).asInstanceOf[Response]
      println("received feedback: " + result)
    }

    clientSystem.shutdown()
  }
}

