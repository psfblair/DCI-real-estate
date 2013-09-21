package net.phobot.realestate.util

class OptionExtensions[OptionType](val option: Option[OptionType]) {

  def setOnlyOnce[T <: AnyRef](value: T) : Option[T] = option match {
    case None => Some(value)
    case _ => throw new IllegalStateException
  }
}

object OptionExtensions {

  implicit def asOptionExtension[T](option: Option[T]) : OptionExtensions[T] = {
    OptionExtensions(option)
  }
  def apply[T](option: Option[T]) : OptionExtensions[T] = new OptionExtensions(option)
}
