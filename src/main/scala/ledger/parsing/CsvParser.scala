package ledger.parsing

import ledger.domain._

sealed trait ParseError

object ParseError {
  case object InvalidFormat extends ParseError
  case object InvalidDate extends ParseError
  case object InvalidAmount extends ParseError
  case object UnexplainedError extends ParseError

  def apply(message: String): ParseError = UnexplainedError
}

object CsvParser {
  def parse(row: String): Either[ParseError, Transaction] = {
    val fields = row.split(",").map(_.trim)

    //checks field length
    if (fields.length != 3 || fields.exists(_.isEmpty)) {
      return Left(ParseError.InvalidFormat)
    }

    //checks field types and parses
    fields match {
      case Array(dateStr, categoryStr, amountStr) =>
        val date = try {
          MonthDay.parse(dateStr)
        } catch {
          case _: Exception => return Left(ParseError.InvalidDate)
        }

        val amount = try {
          BigDecimal(amountStr)
        } catch {
          case _: Exception => return Left(ParseError.InvalidAmount)
        }

        //creates transaction
        val category = Category(categoryStr)
        Right(Transaction(date, category, Money(amount)))

      case _ =>
        Left(ParseError.InvalidFormat)
    }
  }
}
