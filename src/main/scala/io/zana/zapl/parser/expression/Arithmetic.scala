package io.zana.zapl.parser.expression

import io.zana.zapl.{parser, structure}

object Arithmetic {

  import parser.Base._
  import Keyword._
  import parser.primitive.Primitive._

  def expression: Parser[structure.expression.Expression] = {

    def expr: Parser[Any] = term ~ rep(MINUS ~ term | PLUS ~ term)

    def factor: Parser[Any] = integer | LEFT_PAREN ~ expr ~ RIGHT_PAREN

    def term: Parser[Any] =
      factor ~ rep(MULTIPLICATION ~ factor | DIVISION ~ factor)

    expr ^^ {
      _ => structure.expression.Expression("dummy")
    }
  }
}
