package actors

import akka.actor._
import java.net.{InetAddress, UnknownHostException}


//object AnalysisActor extends Actor {
//  def receive() {
//    for (i <- 1 to 10) {
//      println("I'm acting!")
//      Thread.sleep(1000)
//    }
//  }
//}
//import scala.actors._
//
//
//object SeriousActor extends Actor {
//  def act() {
//    for (i <- 1 to 5) {
//      println("To be or not to be.")
//      Thread.sleep(1000)
//    }
//  }
//}
//
//import Actor._
//
//object EchoActor {
//  val echoActor = actor {
//    while (true) {
//      receive {
//        case msg =>
//          println("received message: "+ msg)
//      }
//    }
//  }
//}
//
//
//object NameResolver extends Actor {
//  import java.net.{InetAddress, UnknownHostException}
//
//  def act() {
//    react {
//      case (name: String, actor: Actor) =>
//        actor ! getIp(name)
//        act()
//      case "EXIT" =>
//        println("Name resolver exiting.")
//      // quit
//      case msg =>
//        println("Unhandled message: "+ msg)
//        act()
//    }
//  }
//
//  def getIp(name: String): Option[InetAddress] = {
//    try {
//      Some(InetAddress.getByName(name))
//    } catch {
//      case _:UnknownHostException => None
//    }
//  }
//}
//
//object NameResolverLoop extends Actor {
//  import java.net.{InetAddress, UnknownHostException}
//  import NameResolver.getIp
//
//  def act() {
//    loop {
//      react {
//        case (name: String, actor: Actor) =>
//          actor ! getIp(name)
//        case msg =>
//          println("Unhandled message: " + msg)
//      }
//    }
//  }
//}
//
//object SillyActor2 {
//  val sillyActor2 = actor {
//    def emoteLater() {
//      val mainActor = self
//      actor {
//        Thread.sleep(1000)
//        mainActor ! "Emote"
//      }
//    }
//
//    var emoted = 0
//    emoteLater()
//
//    loop {
//      react {
//        case "Emote" =>
//          println("I'm acting!")
//          emoted += 1
//          if (emoted < 5)
//            emoteLater()
//        case msg =>
//          println("Received: "+ msg)
//      }
//    }
//  }
//}
//
//object NameResolver2 {
//  import NameResolver.getIp
//
//  import scala.actors.Actor._
//  import java.net.{InetAddress, UnknownHostException}
//
//  case class LookupIP(name: String, respondTo: Actor)
//  case class LookupResult(
//                           name: String,
//                           address: Option[InetAddress]
//                           )
//
//  object NameResolver2 extends Actor {
//
//    def act() {
//      loop {
//        react {
//          case LookupIP(name, actor) =>
//            actor ! LookupResult(name, getIp(name))
//        }
//      }
//    }
//
//    def getIp(name: String): Option[InetAddress] = {
//      // As before (in Listing 30.3)
//      try {
//        Some(InetAddress.getByName(name))
//      } catch {
//        case _:UnknownHostException => None
//      }
//    }
//  }
//}