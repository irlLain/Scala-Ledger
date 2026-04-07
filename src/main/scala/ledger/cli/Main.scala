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
  Cli.run(args.toList) match {
    case Right(path) =>
      println(s"Processing file: $path")
    case Left(error) =>
      println(error)
  }
}