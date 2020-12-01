package io.zana.zapl.generator

import io.zana.zapl.ast.Expression.{String, _}
import io.zana.zapl.ast.Type

object Generator {

  object Extract {

    def function(i: Type.Type): Any = {
      i match {
        case Type.Integer(value) => value
        case Type.Bool(value) => value
        case Type.String(value) => "\"" + value + "\""
        case Type.List(values) => for {v <- values} yield function(v)
      }
    }
  }

  object Generate {
    def definition(definition: Definition): String = {
      val result =
        definition.value match {
          case Type.Integer(i) => (
            Int
              .getClass
              .getName.
              toCharArray
              .dropRight(1)
              .mkString,
            i
          )
          case Type.Bool(i) => (
            Boolean
              .getClass
              .getName.
              toCharArray
              .dropRight(1)
              .mkString,
            i
          )
          case Type.String(i) => (
            "java.lang.String",
            "\"" + i + "\""
          )
          case Type.List(i) => (
            List
              .getClass
              .getName
              .toCharArray
              .dropRight(1)
              .concat("[Any]")
              .mkString,
            for {
              item <- i
            } yield Extract.function(item)
          )
        }

      s"var ${definition.identifier}: ${result._1} = ${result._2}"
    }
  }

}
