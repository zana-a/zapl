package io.zana.zapl.structure.module

import io.zana.zapl.structure.common.Identifier

// TODO: Change Any to Specific Type: Restrict to function and mods
case class Module(identifier: Identifier, body: List[Any])