package learn

trait IO[A] {
  def pure[T](t: T): IO[T]
  def flatMap[A, B](ia: IO[A])(f: A => IO[B]): IO[B]

  def map[A, B](ia: IO[A])(f: A => B): IO[B] = flatMap(ia)(a => pure(f(a)))

  val unit : IO[Unit] = pure(()) 

}



