package io.zana.zapl.parser.control.predef

import io.zana.zapl.parser.Parsable
import io.zana.zapl.parser.base.Base.{opt, rep, _}
import io.zana.zapl.parser.keyword.Keyword._
import io.zana.zapl.structure.control.{Arm => Structure}

object Arm extends Parsable[Structure] {

  override def apply: Parser[Structure] =
    COND ~ DO ~ opt(rep(Arm.apply)) ~ Default.apply ~ END ^^ {
      ???
    }
}