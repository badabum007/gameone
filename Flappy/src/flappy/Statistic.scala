package flappy

import scala.collection.JavaConversions
import scala.collection.mutable.ArrayBuffer


class Statistic {

  def getStatistic(info: Array[SaveInfo]): java.util.List[Integer] = {
    val statistic: ArrayBuffer[Int] = ArrayBuffer()
    var i = 0
    var less20 = 0
    var less50 = 0
    var less100 = 0
    var great = 0
    while (i < info.length) {
      if (info(i).getScore < 20) {
        less20 += 1;
      }
      if (info(i).getScore > 20 && info(i).getScore < 50) {
        less50 += 1;
      }
      if (info(i).getScore > 50 && info(i).getScore < 100) {
        less100 += 1;
      }
      if (info(i).getScore > 100) {
        great += 1;
      }
      statistic += info(i).getScore
      i += 1
    }
    val gameStat: ArrayBuffer[Integer] = ArrayBuffer()
    if (statistic.size != 0) {
      gameStat += Integer.valueOf(statistic.sum / statistic.size)
      gameStat += Integer.valueOf(statistic.max)
    } else {
      gameStat += Integer.valueOf(0)
      gameStat += Integer.valueOf(0)
    }
    gameStat += less20
    gameStat += less50
    gameStat += less100
    gameStat += great
    JavaConversions.seqAsJavaList(gameStat)
  }
}
