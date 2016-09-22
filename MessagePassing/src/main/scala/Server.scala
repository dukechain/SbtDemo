/**
 * Created by chenxu on 08.09.16.
 */
package com.demo.messagepassing

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import com.typesafe.config.ConfigFactory

case class AkkaMessage(message: Any)
case class Response(response: Any)

class Server extends Actor {
  override def receive: Receive = {
    case msg: AkkaMessage => {
      println("message received at Server " + msg.message)
      sender ! Response("response_" + msg.message)
    }
    case _ => println("unsupported message type")
  }
}

object Server {
  def main (args: Array[String]):Unit = {
    val serverSystem = ActorSystem("123", ConfigFactory.parseString(
      """akka {
         actor {
             provider = "akka.remote.RemoteActorRefProvider"
         }
         remote {
           enabled-transports = ["akka.remote.netty.tcp"]
           netty.tcp {
             hostname = "127.0.0.1"
             port = 2555
           }
         }
      }
      """))

    serverSystem.actorOf(Props[Server], "server")
  }
}
