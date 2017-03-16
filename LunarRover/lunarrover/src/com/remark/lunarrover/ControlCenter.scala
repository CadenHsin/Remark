package com.remark.lunarrover

import scala.actors.Actor

/**
  * Created by xinruyi on 2017/3/15.
  */
/**
  * control center for manipulating rovers
  */
object ControlCenter extends Actor{

  override def act(): Unit = {
    while (true){
//      Thread.sleep(500)
      receive{
        case MessageCoorCorrect(roverIdx, start, end, time) =>{
          printf("%s 【LunarRover-%s Coordinate_Correct】 Received Position [%s:%s]  Target Position [%s:%s]  \n",
            time, roverIdx, start.x, start.y, end.x, end.y)
        }
        case MessageStart(roverIdx, start, nowPosition, end, speed, time) =>{
          val prePosition = Utils.forecast(nowPosition, speed)
          printf("%s 【LunarRover-%s Start】 Received Position [%s:%s] Predicted Position [%s:%s] Target Position [%s:%s] Speed:%s \n",
            time, roverIdx, nowPosition.x, nowPosition.y, prePosition.x, prePosition.y, end.x, end.y, speed)
        }
        case MessageMove(roverIdx, start, nowPosition, end, speed, angle, time) =>{
          val prePosition = Utils.forecast(nowPosition, speed, angle)
          printf("%s 【LunarRover-%s Move】 Received Position [%s:%s] Predicted Position [%s:%s] Target Position [%s:%s] Speed:%s \n",
            time, roverIdx, nowPosition.x, nowPosition.y, prePosition.x, prePosition.y, end.x, end.y, speed)
        }
        case MessageTurn(roverIdx, start, nowPosition, end, speed, angle, time) =>{
          val prePosition = Utils.forecast(nowPosition, speed)
          printf("%s 【LunarRover-%s Turn】 Received Position [%s:%s] Predicted Position [%s:%s] Target Position [%s:%s] Speed:%s \n",
            time, roverIdx, nowPosition.x, nowPosition.y, prePosition.x, prePosition.y, end.x, end.y, speed)
        }
        case MessageStop(roverIdx, start, nowPosition, end, time) =>{
          val prePosition = Utils.forecast(nowPosition, 0)
          printf("%s 【LunarRover-%s Stop】 Received Position [%s:%s] Predicted Position [%s:%s] Target Position [%s:%s] Speed:%s \n",
            time, roverIdx, nowPosition.x, nowPosition.y, prePosition.x, prePosition.y, end.x, end.y, 0)
        }
      }
    }
  }
}
