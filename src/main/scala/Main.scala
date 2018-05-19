package learn

object Main {

  def main(args: Array[String]): Unit = {

    import Keys._
    println(keys(List(1, 2, 3)))
    println(keys(List("A", "B")))

    import Monoid._
    println(fold(List(1, 2, 3, 4, 5)))
    println(fold(List(33, 22, 11).map(_.toString)))
  }
}
