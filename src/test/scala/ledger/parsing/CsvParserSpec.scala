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
          category = Category("Income"),
          amount   = Money(BigDecimal("10.50"))
        )
      )
    }


    "Parse an income transaction correctly" in {
      val row = "2026-03-01,Salary,2000.00"

      val result = CsvParser.parse(row)

      result shouldBe Right(
        Transaction(
          date     = MonthDay.parse("2026-03-01"),
          category = Category("Salary"),
          amount   = Money(BigDecimal("2000.00"))
        )
      )

    }

    "Parse an expense transaction correctly" in {
        val row = "2026-03-01,Rent,-1200.00"
    
        val result = CsvParser.parse(row)
    
        result shouldBe Right(
            Transaction(
            date     = MonthDay.parse("2026-03-01"),
            category = Category("Rent"),
            amount   = Money(BigDecimal("-1200.00"))
            )
        )
    }

//Field parsing
"Parse date fields correctly" in {
  val row = "2026-03-15,Food,-25.00"

  val result = CsvParser.parse(row)

  result match {
    case Right(transaction) =>
      transaction.date shouldBe MonthDay.parse("2026-03-15")

    case Left(error) =>
      fail(s"Expected successful parse, got error: $error")
  }
}

    "Parse category fields correctly" in {
        val row = "2026-03-01,Utilities,-150.00"
    
        val result = CsvParser.parse(row)
    
        result match {
            case Right(transaction) =>
            transaction.category shouldBe Category("Utilities")
    
            case Left(error) =>
            fail(s"Expected successful parse, got error: $error")
        }
    }

    "Parse amount into money correctly" in {
        val row = "2026-03-01,Entertainment,50.00"

        val result = CsvParser.parse(row)

        result match {
            case Right(transaction) =>
            transaction.amount shouldBe Money(BigDecimal("50.00"))
    
            case Left(error) =>
            fail(s"Expected successful parse, got error: $error")
        }
    }

    "Negative amounts should be parsed as expenses" in {
        val row = "2026-03-01,Entertainment,-50.00"

        val result = CsvParser.parse(row)

        result match {
            case Right(transaction) =>
            transaction.amount shouldBe Money(BigDecimal("-50.00"))
    
            case Left(error) =>
            fail(s"Expected successful parse, got error: $error")
        }
    }

    "Positive amounts should be parsed as income" in {
        val row = "2026-03-01,Salary,3000.00"

        val result = CsvParser.parse(row)

        result match {
            case Right(transaction) =>
            transaction.amount shouldBe Money(BigDecimal("3000.00"))
    
            case Left(error) =>
            fail(s"Expected successful parse, got error: $error")
        }
    }
    
    //Validation and error handling
    "Invalid CSV format returns an error" in {
        val row = "2026-03-01,Food"

        val result = CsvParser.parse(row)

        result shouldBe Left(ParseError.InvalidFormat)
    }

    "Too few columns returns an error" in {
        val row = "2026-03-01"
    
        val result = CsvParser.parse(row)
    
        result shouldBe Left(ParseError.InvalidFormat)
    }

    "Too many columns returns an error" in {
        val row = "2026-03-01,Food,50.00,ExtraColumn"
    
        val result = CsvParser.parse(row)
    
        result shouldBe Left(ParseError.InvalidFormat)
    }

    "Invalid date format returns an error" in {
      val row = "invalid-date,Food,50.00"

      val result = CsvParser.parse(row)

      result shouldBe Left(ParseError.InvalidDate)
    }

    "Invalid amount format returns an error" in {
      val row = "2026-03-01,Food,invalid-amount"

      val result = CsvParser.parse(row)

      result shouldBe Left(ParseError.InvalidAmount)
    }
  }
}