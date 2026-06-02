package ledger.cli

import java.nio.file.Files
import java.nio.file.Paths
import ledger.parsing.CsvParser
import scala.jdk.CollectionConverters._
import scala.io.StdIn

object Cli {
  def run(args: List[String]): Either[String, String] =
    args match {
      case path :: Nil if path.endsWith(".csv") =>
        val filePath = Paths.get(path)
        if (!Files.exists(filePath)) {
          Left(s"File not found: $path")
        } else if (!Files.isReadable(filePath)) {
          Left(s"Cannot read file: $path")
        } else {
          try {
            val nonEmptyLines = Files.readAllLines(filePath).asScala.toList.map(_.trim).filter(_.nonEmpty)

            if (nonEmptyLines.isEmpty) {
              Left("CSV file is empty")
            } else {
              val hasHeader = nonEmptyLines.head.toLowerCase.startsWith("date,")
              val dataRows = if (hasHeader) nonEmptyLines.tail else nonEmptyLines

              if (dataRows.isEmpty) {
                Left("CSV file contains no data rows")
              } else {
                val parsedRows = dataRows.map(CsvParser.parse)
                val hasValidRows = parsedRows.exists(_.isRight)

                if (!hasValidRows) Left("No valid data rows found")
                else Right(path)
              }
            }
          } catch {
            case _: Exception => Left(s"Cannot read file: $path")
          }
        }
      case "--help" :: _ =>
        Left(
          """
          Help!
          Process the specified CSV file: ledger <path-to-csv> 
          Show this help message: --help
          """.stripMargin
        )
      case Nil =>
        Left("No CSV file path provided")
      case _ =>
        Left("Invalid arguments")
    }
}

object Main extends App {
  def printMenu(): Unit = {
    println(
      """
      Welcome to Ledger: the personal finance tool.
      To get started, provide the path to your CSV file using the command:
        ledger <path-to-csv>

      Need help? Type: ledger --help
      
      Type 'exit' to quit.""".stripMargin
    )
  }

  def mainLoop(): Unit = {
    printMenu()
    var continue = true
    
    while (continue) {
      println("Enter command or CSV path (e.g., ledger transactions.csv):")
      val typedInput = StdIn.readLine().trim

      if (typedInput.toLowerCase == "exit") {
        println("Goodbye!")
        continue = false
      } else {
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
        println() // Add blank line before returning to menu
      }
    }
  }

  mainLoop()
}