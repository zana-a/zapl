package io.zana.zapl.parser

import io.zana.zapl.parser.base.Base._

trait Parsable[T] {

  def apply: Parser[T]
}