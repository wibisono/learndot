package learn

trait Monoid {
  type Val
  def empty : Val
  def combine(a : Val, b:Val) : Val
}

object Monoid {

  type MonoidT[T] = Monoid { type Val = T }
  type Monoidal[T] = implicit MonoidT[T] =>  T 

  def im[T](implicit m : MonoidT[T]) = m  

  def fold[T](ss : List[T]) : Monoidal[T] = ss match {
      case Nil => im.empty 
      case h :: rest =>  im.combine(h, fold(rest))
  }

  implicit val monoidInt : MonoidT[Int] = new Monoid {
    type Val = Int 
    def empty = 0
    def combine(a : Int, b:Int) = a + b
  }

  implicit val monoidString : MonoidT[String] = new Monoid {
    type Val = String 
    def empty = "" 
    def combine(a : String, b:String) = a + b
  }
}


