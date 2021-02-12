package io.zana.zapl.parser.program

import io.zana.zapl.parser.base.Base._
import io.zana.zapl.parser.comment.LineComment
import io.zana.zapl.parser.module.Module._
import io.zana.zapl.structure.program.{Program => Structure}

object Program {

  def program: Parser[Structure] = rep(
    LineComment.apply
      | module
      | call
      | function
      | variable
      | control
  ) ^^ (statements => Structure(statements))
}
