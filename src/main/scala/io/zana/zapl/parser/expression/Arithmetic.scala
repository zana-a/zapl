package io.zana.zapl.parser.expression

import io.zana.zapl.parser

object Arithmetic {

  import parser.Base._
  import Keyword._
  import parser.primitive.Primitive._

  def expression: Parser[Any] =
    factor ~ opt(
      rep(PLUS ~ expression
        | MINUS ~ expression
        | MULTIPLICATION ~ expression
        | DIVISION ~ expression
      )
    )

  def factor: Parser[Any] =
    constant | LEFT_PAREN ~ expression ~ RIGHT_PAREN

  def constant: Parser[Any] = integer | identifier
}