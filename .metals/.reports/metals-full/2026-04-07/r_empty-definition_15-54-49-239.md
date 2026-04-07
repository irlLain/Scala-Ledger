error id: file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/main/scala/ledger/cli/Main.scala:scala/Predef.println().
file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/main/scala/ledger/cli/Main.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -println.
	 -println#
	 -println().
	 -scala/Predef.println.
	 -scala/Predef.println#
	 -scala/Predef.println().
offset: 339
uri: file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/main/scala/ledger/cli/Main.scala
text:
```scala
package ledger.cli

object Cli {
  def run(args: List[String]): Either[String, String] =
    args match {
      case path :: Nil if path.endsWith(".csv") =>
        Right(path)
      case Nil =>
        Left("No CSV file path provided")
      case _ =>
        Left("Invalid arguments")
    }
}

object Main extends App {
  @@println()
  Cli.run(args.toList) match {
    case Right(path) =>
      println(s"Processing file: $path")
    case Left(error) =>
      println(error)
  }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 