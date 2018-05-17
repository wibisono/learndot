trait Keys {
  type Key 
  def key(data : String) : Key
}
object HashKeys extends Keys {
  type Key = Int
  def key(s : String) : Key = s.hashCode()
}

object IdKeys extends Keys {
  type Key = String 
  def key(s : String) : Key = s+s
}

trait Mo {
  type Val
  def empty : Val
}

object Main {

  def main(args: Array[String]): Unit = {
    println("Hello world!")
    println(msg)

    def mapKeys(k : Keys, ss : List[String]) = ss.map(k.key)
    println(mapKeys(IdKeys, List("A","b")))

    def getKeys(ss : List[String])(implicit k : Keys) = ss.map(k.key)
    def impid = {
      implicit val implId = IdKeys 
      println(getKeys(List("A","B")))
    }
    def impHash  = {
      implicit val implId = HashKeys 
      println(getKeys(List("A","B")))
    }
    impHash
    impid
    impid
  }

  def msg = "I was compiled by dotty :)"
}
