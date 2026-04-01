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

  def parse(row: String): Either[ParseError, Transaction] =
    Left(ParseError("Not implemented yet"))

}
