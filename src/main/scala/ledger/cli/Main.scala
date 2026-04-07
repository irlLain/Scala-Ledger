package ledger.cli

import java.nio.file.Files
import java.nio.file.Paths
import scala.io.StdIn

object Cli {
  def run(args: List[String]): Either[String, String] =
    args match {
      case path :: Nil if path.endsWith(".csv") =>
        if (Files.exists(Paths.get(path))) Right(path)
        else Left(s"File not found: $path")
      case Nil =>
        Left("No CSV file path provided")
      case _ =>
        Left("Invalid arguments")
    }
}

object Main extends App {
  println(
  """Welcome to Ledger: the personal finance tool.
    |
    |To get started, provide the path to your CSV file:
    |  ledger <path-to-csv>
    |
    |Need help?
    |  ledger --help
    |
    |Example:
    |  ledger transactions.csv
    |""".stripMargin
)
  println("Enter command or CSV path (e.g., ledger transactions.csv):")
  val typedInput = StdIn.readLine().trim

  val effectiveArgs =
    if (args.nonEmpty) {
      args.toList
    } else if (typedInput.isEmpty) {
      Nil
    } else {
      val tokens = typedInput.split("\\s+").toList
      tokens match {
        case "ledger" :: rest => rest
        case other => other
      }
    }

  Cli.run(effectiveArgs) match {
    case Right(path) =>
      println(s"Processing file: $path")
    case Left(error) =>
      println(error)
  }
}