package com.remark.lunarrover

import java.util.Date

import scala.util.Random

/**
  * Created by xinruyi on 2017/3/15.
  */
/**
  * utils for lunar rover application
  */
object Utils {
  /**
    * used to simulate whether an obstacle exists or not
    * @return if obstacle exists return true and vice versa
    */
  def hasObstacle: Boolean={
    Random.nextBoolean()
  }

  /**
    * randomly generate obstacle
    * @return a random obstacle
    */
  def generateObstacle: Obstacle={
    Obstacle(Random.nextInt(5), Random.nextInt(5))
  }

  /**
    * get now time
    * @return
    */
  def getNowTime: Date={
    new Date
  }

  /**
    * correct the two position in Y-axis, and set nowPostion be Postion(0,0)
    * @param nowPostion
    * @param toPostion
    * @return corrected postions includeing beginPosition and endPosition
    */
  def correctCoordinate(nowPostion:Position, toPostion:Position): (Position, Position) ={
    val distance: Double =math.sqrt(math.pow(toPostion.x - nowPostion.x,2)+math.pow(toPostion.y - nowPostion.y,2))
    val beginPosition:Position = new Position(0,0)
    val endPosition:Position = new Position(0,math.sqrt(distance))
    (beginPosition,endPosition)
  }

  def listStrPosition2Tuple(position:String): (Double,Double) ={
    val pairPos = position.split(",")
    val posX = pairPos(0).toString.substring(5)
    val posY = pairPos(1).toString.substring(0,pairPos(1).toString.length-1)
    (posX.toDouble,posY.toDouble)
  }
  /**
    * predict position for each  lunar rover
    * @param nowPosition
    * @param speed
    * @return predicted position
    */
  def forecast(nowPosition: Position, speed: Double, angle:Int=0): Position = {
    if(angle/90==0 || angle/90==4){
      return Position(nowPosition.x,nowPosition.y+speed)
    }else if(angle/90==1){
      return Position(nowPosition.x+speed,nowPosition.y)
    }else{
      if(nowPosition.x <= speed){
        return Position(0,nowPosition.y+speed)
      }
      return Position(nowPosition.x-speed,nowPosition.y)
    }
  }

}
