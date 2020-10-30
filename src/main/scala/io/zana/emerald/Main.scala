package io.zana.emerald

import scala.util.parsing.combinator._

case class WordFreq(word: String, count: Int) {
  override def toString = s"Word <$word> occurs with frequency $count"
}

class SimpleParser extends RegexParsers {
  def word: Parser[String] =
    """[a-z]+""".r ^^ {
      _.toString
    }

  def number: Parser[Int] =
    """(0|[1-9]\d*)""".r ^^ {
      _.toInt
    }

  def freq: Parser[WordFreq] = word ~ number ^^ { case wd ~ fr => WordFreq(wd, fr) }
}

object Main extends SimpleParser {
  def main(args: Array[String]) = {
    parse(freq, "word 121") match {
      case Success(matched, _) => println(matched)
      case Failure(msg, _) => println(s"FAILURE: $msg")
      case Error(msg, _) => println(s"ERROR: $msg")
    }
  }
}


// a = [1, 2, 3]

// result of AST
//  program ->
//    identifier ->
//      name -> "a"
//        values -> [
//          number -> 1
//          number -> 2
//        ]
