trait Keys {
  type Key 
  def key(data : Key) : Key
}

object Keys {

    type KeysT[T] = Keys {type Key = T}
    def keys[T](ss : List[T])(implicit k : KeysT[T]) = ss.map(k.key)

    implicit val intKeys : KeysT[Int] = new Keys {
      type Key = Int
      def key(s : Int) : Key = s+s+s
    }

    implicit val stringKeys : KeysT[String]= new Keys { 
      type Key = String 
      def key(s : String) : Key = s+s
    }
}

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

object Main {

  def main(args: Array[String]): Unit = {
   
    import Keys._ 
    println(keys(List(1,2,3)))
    println(keys(List("A","B")))

    import Monoid._
    println(fold(List(1,2,3,4,5)))
    println(fold(List(33,22,11).map(_.toString)))
  }
}
