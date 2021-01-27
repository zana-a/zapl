package io.zana.zapl.parser

import io.zana.zapl.parser.Base._
import io.zana.zapl.parser.Keyword._
import io.zana.zapl.structure.primitive

object Primitive {

  def string: Parser[primitive.String] = {
    stringLiteral ^^ {
      case result => primitive.String(result)
    }
  }

  def boolean: Parser[primitive.Boolean] = {
    def t: Parser[primitive.Boolean] = TRUE ^^ {
      result => primitive.Boolean(result.toBoolean)
    }

    def f: Parser[primitive.Boolean] = FALSE ^^ {
      result => primitive.Boolean(result.toBoolean)
    }

    phrase(t | f) ^^ (result => result)
  }

  def list: Parser[primitive.List] = "[" ~> repsep(`type`, ",") <~ "]" ^^ {
    result => primitive.List(result)
  }

  def integer: Parser[primitive.Integer] = wholeNumber ^^ {
    result => primitive.Integer(result.toInt)
  }

  def `type`: Parser[primitive.Type] = string | integer | list | boolean
}
