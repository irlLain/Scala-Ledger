error id: file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/test/scala/ledger/parsing/CsvParserSpec.scala:shouldBe.
file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/test/scala/ledger/parsing/CsvParserSpec.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -ledger/domain/shouldBe.
	 -ledger/domain/shouldBe#
	 -ledger/domain/shouldBe().
	 -shouldBe.
	 -shouldBe#
	 -shouldBe().
	 -scala/Predef.shouldBe.
	 -scala/Predef.shouldBe#
	 -scala/Predef.shouldBe().
offset: 780
uri: file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/test/scala/ledger/parsing/CsvParserSpec.scala
text:
```scala
package ledger.parsing

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import ledger.domain._
import scala.math.BigDecimal

class CsvParserSpec extends AnyWordSpec with Matchers {

  "CsvParser" should {

    "exist and allow tests to run" in {
      true shouldBe true
    }

//Path contact

 "parse a valid CSV row into a Transaction" in {
      val row = "2026-03-01,Food,-10.50"

      val result = CsvParser.parse(row)

      result shouldBe Right(
        Transaction(
          date     = MonthDay.parse("2026-03-01"),
          category = Category("Food"),
          amount   = Money(BigDecimal(-10.50))
        )
      )
    }


    "Parse an income transaction correctly" in {
      true shouldBe@@ true
    }

    "Parse an expense transaction correctly" in {
      true shouldBe true
    }

//Field parsing
    "Parse date fields correctly" in {
      true shouldBe true
    }

    "Parse category fields correctly" in {
      true shouldBe true
    }

    "Parse amount into money correctly" in {
      true shouldBe true
    }

    "Negative amounts should be parsed as expenses" in {
      true shouldBe true
    }

    "Positive amounts should be parsed as income" in {
      true shouldBe true
    }
    
    //Validation and error handling
    "Invalid CSV format returns an error" in {
      true shouldBe true
    }

    "Too few columns returns an error" in {
      true shouldBe true
    }

    "Too many columns returns an error" in {
      true shouldBe true
    }

    "Invalid date format returns an error" in {
      true shouldBe true
    }

    "Invalid amount format returns an error" in {
      true shouldBe true
    }
  }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 