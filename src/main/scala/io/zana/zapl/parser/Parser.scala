package io.zana.zapl.parser

import io.zana.zapl.ast._

import scala.util.parsing.combinator._

/**
 * A static object that extends the
 * [[JavaTokenParsers]] to parse a set of
 * predifined EBNF.
 *
 * @example Parser.parseAll(program, Source.fromFile("path.zapl"))
 * @author zana-a
 */
object Parser extends JavaTokenParsers {

  /**
   * Parses either the keyword true or false.
   *
   * @return parser of encapsulated [[scala.Boolean]] object in [[Type.Bool]]
   */
  def bool: Parser[Type.Bool] = {

    def t: Parser[Type.Bool] = "true" ^^ {
      result => Type.Bool(result.toBoolean)
    }

    def f: Parser[Type.Bool] = "false" ^^ {
      result => Type.Bool(result.toBoolean)
    }

    t | f
  }

  /**
   * Parses a list starting with "[" and ending with "]".
   * Comma seperated values of type [[Type.Type]].
   * Supports 0, 1 or more values and multi-dimentional lists.
   *
   * @return parser of encapsulated [[scala.List object in [[Type.List]]
   */
  def list: Parser[Type.List] = {
    "[" ~> repsep(`type`, ",") <~ "]" ^^ {
      case result => Type.List(result)
    }
  }

  /**
   * Inherits [[stringLiteral]] from the [[JavaTokenParsers]].
   *
   * @return parser of encapsulated [[String]] object in [[Type.String]]
   */
  def string: Parser[Type.String] = {
    super.stringLiteral ^^ { result => Type.String(result) }
  }

  /**
   * Inherits the [[wholeNumber]] from the [[JavaTokenParsers]].
   *
   * @return parser of encapsulated [[scala.Int]] object in [[Type.Integer]]
   */
  def integer: Parser[Type.Integer] = {
    super.wholeNumber ^^ { result => Type.Integer(result.toInt) }
  }

  /**
   * Returns a [[`type`]] based on what it matches.
   *
   * @return a parser of
   *         [[Type.Integer]],
   *         [[Type.Bool]],
   *         [[Type.String]],
   *         [[Type.List]].
   */
  def `type`: Parser[Type.Type] = integer | bool | string | list

  /**
   * A regex matching a module identifier name that start with a capital letter
   * followed by one or more letters or numbers.
   *
   * @return a parser of a matched string.
   */
  def moduleIdent: Parser[String] = """^([A-Z])(\w?)+(\d?)+""".r

  /**
   * Matches a module based on following syntax.
   *
   * {{{
   * mod A do
   *
   * end
   * }}}
   *
   * where A is a [[moduleIdent]].
   *
   * @note the do-block in modules only accept functions in the body.
   * @return TODO: add return
   */
  def module: Parser[Any] = {
    // TODO: Static type change & return result as something more useful
    "mod" ~ moduleIdent ~ "do" ~ opt(rep(function)) ~ "end" ^^ {
      case _ ~ name ~ _ ~ body ~ _ =>
        println(s"module of ${name}: ${body}")
    }
  }

  /**
   * Parses 0, 1 or more of either a [[variable]], [[function]] or [[block]].
   *
   * @return a parser of either
   *         [[variable]],
   *         [[function]] or
   *         [[block]].
   */
  def blockBody: Parser[Any] = {
    // TODO: Static type change
    opt(rep(variable | function | block))
  }

  /**
   * Parses [[blockBody]] within a "do" and "end" tokens.
   *
   * @return TODO: add return
   */
  def block: Parser[Any] = {
    "do" ~ blockBody ~ "end"
  }

  /**
   * Parses a variable binding using the [[ident]] from the inherited
   * [[JavaTokenParsers]].
   *
   * Any of [[`type`]] is accepted.
   *
   * @return TODO: add return
   */
  def variable: Parser[Any] = {
    ident ~ "=" ~ `type`
  }

  /**
   * Parses a function with a name, a list of parameters and a block body using
   * [[block]].
   *
   * @return TODO: add return
   */
  def function: Parser[Any] = {
    "def" ~ ident ~ "(" ~ opt(repsep(ident, ",")) ~ ")" ~ block
  }

  /**
   * The entry point of the parser in accordance to the project's EBNF.
   *
   * @return TODO: add return
   */
  def program: Parser[Any] = {
    opt(rep(module | function | block | variable))
  }
}
