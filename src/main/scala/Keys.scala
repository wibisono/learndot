package learn
trait Keys {
  type Key
  def key(data: Key): Key
}

object Keys {

  type KeysT[T] = Keys { type Key = T }
  def keys[T](ss: List[T])(implicit k: KeysT[T]) = ss.map(k.key)

  implicit val intKeys: KeysT[Int] = new Keys {
    type Key = Int
    def key(s: Int): Key = s + s + s
  }

  implicit val stringKeys: KeysT[String] = new Keys {
    type Key = String
    def key(s: String): Key = s + s
  }
}

