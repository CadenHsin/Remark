package com.remark.lunarrover

import java.util

import com.sun.deploy.util.StringUtils

import scala.collection.immutable.Range.Double
import scala.io.Source
import scala.util.parsing.json.JSON

/**
  * Created by xinruyi on 2017/3/15.
  */
object Main {
  def main(args: Array[String]): Unit = {
    /**
      * 1.start ControlCenter
      */
      ControlCenter.start()

    /**
      * 2.start Lunar Rovers
      */

    val text:String = Source.fromFile("lunarrover/linesInfo.txt").mkString
    val jsonTxt = JSON.parseFull(text)
    jsonTxt match {
      case Some(map1:Map[String,Any])=> {
        map1.values.map(ta=> Option(ta) match {
          case Some(map2:Map[String,Any])=>{
            val idx = map2("idx").toString
            val startPosition = map2("start").toString
            val endPosition = map2("end").toString
            val (startX,startY) = Utils.listStrPosition2Tuple(startPosition)
            val (endX,endY) = Utils.listStrPosition2Tuple(endPosition)
            val speed = map2("speed").toString.toDouble
            (new LunarRover(idx,new Position(startX,startY),new Position(startX,startY),new Position(endX,endY), speed)).start()
          }
          case None=>println("parse failed inner")
        })
      }
      case None =>println("parse failed outer")
    }


  }
}
