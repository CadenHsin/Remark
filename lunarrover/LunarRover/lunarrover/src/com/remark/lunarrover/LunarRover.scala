package com.remark.lunarrover

import scala.actors.Actor
import scala.util.control.Breaks

/**
  * Created by xinruyi on 2017/3/15.
  */
abstract class BaseRover(idx:String, startPosition:Position, nowPosition:Position, toPosition:Position, var speed:Double=1) extends Actor{
  /**
    * move right along x-axis
    */
  def moveXPlus(width:Double)
  /**
    * move back to y-axis after crossing obstacle
    */
  def moveXMinus(width:Double)
  /**
    * move right along y-axis
    */
  def moveY(height:Double=0)

  /**
    * rover starts to work
    */
  def sendMessageStart
  /**
    * execute move action,send message to CE
    * @param angle angle presents the direction
    */
  def sendMessageMove(angle:Int)
  /**
    * turn 90 degree clockwise,send message to CE
    */
  def sendMessageTurn
  /**
    * finish task ,end,send message to CE
    */
  def sendMessageStop

  /**
    * execute turn action for crossing obstacle
    */
  def turn(obs:Obstacle)
}

class LunarRover(idx:String, var startPosition:Position, var nowPosition:Position, var toPosition:Position, speed:Double) extends BaseRover(idx,startPosition,nowPosition,toPosition,speed){
  /**
    * execute turn action for crossing obstacle
    */
  override def turn (obs: Obstacle):  Unit={
    sendMessageTurn
    moveXPlus(obs.width)

    sendMessageTurn
    sendMessageTurn
    sendMessageTurn
    moveY(obs.height)

    sendMessageTurn
    sendMessageTurn
    sendMessageTurn
    moveXMinus(obs.width)

    sendMessageTurn
  }
  override def act(): Unit = {
    //1.firstly,correct coordinate
    var (startP,endP) = Utils.correctCoordinate(this.startPosition,this.toPosition)
    this.sendMessageCoorCorrect   //send correct raw message
    Thread.sleep(1000)
    this.startPosition.x = startP.x
    this.startPosition.y = startP.y
    this.nowPosition = startP
    this.toPosition = endP
    //create Breaks object
    val loop = new Breaks
    loop.breakable({
      this.sendMessageStart   //start to work
      while (true){
        if (this.nowPosition.y>=this.toPosition.y){
          this.sendMessageStop
          this.exit()
          loop.break()
        }
        this.sendMessageMove(0)    //move action
        if(Utils.hasObstacle){
          val obs = Utils.generateObstacle  //simulate obstacle
          this.turn(obs)    //cross obstacle
        }
        this.moveY()
      }
    })
  }

  override def moveXPlus(width:Double): Unit = {
    var w=width
    while (w>0){
      Thread.sleep(1000)    //sleep 1 second
      this.nowPosition.x = this.nowPosition.x+this.speed
      this.sendMessageMove(90)
      w-=this.speed
    }
  }

  override def moveXMinus(width:Double): Unit = {
    var w=width
    while (w>0){
      Thread.sleep(1000)
      this.nowPosition.x = this.nowPosition.x-this.speed
      this.sendMessageMove(270)
      w-=this.speed
    }
  }

  override def moveY(height:Double): Unit = {
    if (height==0){
      Thread.sleep(1000)
      this.nowPosition.y = this.nowPosition.y+this.speed
      this.sendMessageMove(0)
    } else {    //if obstacle exists,then here
      var h=height
      while (h>0){
        Thread.sleep(1000)
        this.nowPosition.y = this.nowPosition.y+this.speed
        this.sendMessageMove(0)
        h-=this.speed
      }
    }
  }
  /**
    * rover starts to work
    */
  override def sendMessageStart: Unit = {
    ControlCenter ! new MessageStart(this.idx,this.startPosition.copy(),this.nowPosition.copy(),this.toPosition.copy(),this.speed,Utils.getNowTime)
  }
  override def sendMessageMove(angle:Int): Unit = {
    ControlCenter ! new MessageMove(this.idx,this.startPosition.copy(),this.nowPosition.copy(),this.toPosition.copy(),this.speed,angle,Utils.getNowTime)
  }
  override def sendMessageTurn: Unit = {
    ControlCenter ! new MessageTurn(this.idx,this.startPosition.copy(),this.nowPosition.copy(),this.toPosition.copy(),this.speed,90,Utils.getNowTime)
  }
  override def sendMessageStop: Unit = {
    ControlCenter ! new MessageStop(this.idx,this.startPosition.copy(),this.nowPosition.copy(),this.toPosition.copy(),Utils.getNowTime)
  }
  def sendMessageCoorCorrect: Unit = {
    ControlCenter ! new MessageCoorCorrect(this.idx,this.startPosition,this.toPosition,Utils.getNowTime)
  }
}
