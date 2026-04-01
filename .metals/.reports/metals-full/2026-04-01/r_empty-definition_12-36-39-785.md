error id: file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/main/scala/ledger/parsing/CsvParser.scala:
file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/main/scala/ledger/parsing/CsvParser.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -ledger/domain/amountStr.
	 -ledger/domain/amountStr#
	 -ledger/domain/amountStr().
	 -amountStr.
	 -amountStr#
	 -amountStr().
	 -scala/Predef.amountStr.
	 -scala/Predef.amountStr#
	 -scala/Predef.amountStr().
offset: 1291
uri: file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/main/scala/ledger/parsing/CsvParser.scala
text:
```scala
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
    if (fields.length != 3 || fields.exists(_.isEmpty)) {
      return Left(ParseError.InvalidFormat)
    }
    
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

            val category = Category(categoryStr)

            return Right(Transaction(date, category, Money(amount)))

            for {
                Transdate <- parseDate(dateStr)
                amount <- parseAmount(amountS@@tr)
            } yield Transaction(date, Category(categoryStr), Money(amount))
    }
  }
    Left(ParseError("Not implemented yet"))

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 