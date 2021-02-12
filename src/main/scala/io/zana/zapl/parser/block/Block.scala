package io.zana.zapl.parser.block

import io.zana.zapl.parser.base.Base._
import io.zana.zapl.parser.call.Call._
import io.zana.zapl.parser.comment.Comment._
//  import parser.control.Control._
import io.zana.zapl.parser.expression.Expression._
import io.zana.zapl.parser.keyword.Keyword._
import io.zana.zapl.parser.variable.Variable._
import io.zana.zapl.structure.block.{Block => Structure}

object Block {

  def block: Parser[Structure] = {
    DO ~> rep(
      lineComment
        | variable
        | call
        | expression
      // todo       | control
    ) <~ END ^^ (values => Structure(values))
  }
}
