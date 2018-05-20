package learn 

import util.Try
import concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Faulty extends App {
  
  def getMaybe [I, O](i : I, f : I => O ) : Option[O]         = Try(f(i)).toOption
  def getFuture[I, O](i : I, f : I => O ) : Future[O]         = Try(f(i)).toEither.fold(Future.failed, Future.successful)  
  def getEither[I, O](i : I, f : I => O ) : Either[String, O] = Try(f(i)).toEither.fold(t => Left(t.toString), Right(_))

  type Faulty[T] = Option[T] | Either[String, T] | Future[T]

  def faultyF[T](ot : Faulty[T]) : Future[T] = ot match {
      case Some(value) => Future.successful(value)
      case Right(o)    => Future.successful(o)
      case None        => Future.failed(new RuntimeException("Sorry, no luck this time"))
      case Left(e)     => Future.failed(new RuntimeException(e)) 
      case f:Future[T] => f
  } 

  (for {
    a <- faultyF(getMaybe (5, _ / 1)) 
    b <- faultyF(getEither(6, _ / 2))
    c <- faultyF(getFuture(3, _ / 3))
  } yield (a+b+c))
  .map(_.toString)
  .recover{ case e => e.getMessage }
  .foreach( x => println(s"Result : $x"))

}

