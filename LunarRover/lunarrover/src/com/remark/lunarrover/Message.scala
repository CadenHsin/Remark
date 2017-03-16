package com.remark.lunarrover

import java.util.Date

/**
  * Created by xinruyi on 2017/3/15.
  */
/**
  * actions of lunar rovers
  * @param roverIdx   id of each lunar rover
  * @param start      start position of each lunar rover
  * @param end        end position of each lunar rover
  * @param speed      speed of each lunar rover
  * @param time       current time
  */

case class MessageStart(roverIdx:String, start:Position, nowPosition:Position, end:Position, speed:Double, time:Date)
case class MessageMove(roverIdx:String, start:Position, nowPosition:Position, end:Position, speed:Double, angle:Int, time:Date)
case class MessageTurn(roverIdx:String, start:Position, nowPosition:Position, end:Position, speed:Double, angle:Int, time:Date)
case class MessageStop(roverIdx:String, start:Position, nowPostion:Position, end:Position, time:Date)
case class MessageCoorCorrect(roverIdx:String, start:Position, end:Position, time:Date)