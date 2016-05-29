package flappy

object Parser {

  def parseNotation(obj: Any): Unit = {
    obj match {
      case number: Int if number < 800 => println("Score in this game " + number.toString() + ".")
      case number: Int if number >= 800 => println("After " + (number - 450).toString() +
        " meters will marshmallow.")
      case text: String =>println("File " + text + " loaded.")
      case _ =>  println("Error. Sample not found.")
    }
  }
}
