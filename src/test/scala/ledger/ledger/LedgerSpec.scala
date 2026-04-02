package ledger.domain

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import ledger.domain._
import ledger.ledger._
import scala.math.BigDecimal
import java.time.{LocalDate, YearMonth}

class LedgerSpec extends AnyWordSpec with Matchers {

  "Ledger" should {

    "exist and allow tests to run" in {
      true shouldBe true
    }

    "return an empty list of months when created with no transactions" in {
        val ledger = Ledger.empty

        ledger.months shouldBe empty
    }

    "group transactions by year and month"  in {
        val transaction =
        Transaction(
            date = LocalDate.of(2026, 3, 10),
            category = Category("Salary"),
            amount = Money(BigDecimal(1000))
        )
        
        val transaction2 =
        Transaction(
            date = LocalDate.of(2026, 3, 10),
            category = Category("Salary"),
            amount = Money(BigDecimal(1500))
        )

        val ledger = new Ledger(List(transaction, transaction2))

        val months = ledger.months
        months should have size 1
        months.head.yearMonth shouldBe YearMonth.of(2026, 3)
        months.head.transactions should contain theSameElementsInOrderAs List(transaction, transaction2)
    }

    "return a single Month when all transactions occur in the same month" in {
        val transaction =
        Transaction(
            date = LocalDate.of(2026, 3, 10),
            category = Category("Salary"),
            amount = Money(BigDecimal(1000))
        )
        
        val transaction2 =
        Transaction(
            date = LocalDate.of(2026, 3, 10),
            category = Category("Rent"),
            amount = Money(BigDecimal(-500))
        )

        val ledger = new Ledger(List(transaction, transaction2))

        val months = ledger.months
        months should have size 1
        months.head.yearMonth shouldBe YearMonth.of(2026, 3)
        months.head.transactions should contain theSameElementsInOrderAs List(transaction, transaction2)
    }

    "return multiple Months when transactions span multiple months" in {
        val transaction =
        Transaction(
            date = LocalDate.of(2026, 3, 10),
            category = Category("Salary"),
            amount = Money(BigDecimal(1000))
        )

        val transaction2 =
        Transaction(
            date = LocalDate.of(2026, 4, 10),
            category = Category("Salary"),
            amount = Money(BigDecimal(1500))
        )

        val ledger = new Ledger(List(transaction, transaction2))

        val months = ledger.months
        months should have size 2

        months.find(_.yearMonth == YearMonth.of(2026, 3)).get.transactions should contain theSameElementsInOrderAs List(transaction)
        months.find(_.yearMonth == YearMonth.of(2026, 4)).get.transactions should contain theSameElementsInOrderAs List(transaction2)
    }

    "exclude months that have no transactions" in {
        val transaction =
        Transaction(
            date = LocalDate.of(2026, 3, 10),
            category = Category("Salary"),
            amount = Money(BigDecimal(1000))
        )

        val ledger = new Ledger(List(transaction))

        val months = ledger.months
        months should have size 1
        months.head.yearMonth shouldBe YearMonth.of(2026, 3)
    }

    "preserve all transactions when grouping into months" in {
        val transaction =
        Transaction(
            date = LocalDate.of(2026, 3, 10),
            category = Category("Salary"),
            amount = Money(BigDecimal(1000))
        )

        val transaction2 =
        Transaction(
            date = LocalDate.of(2026, 4, 10),
            category = Category("Salary"),
            amount = Money(BigDecimal(1500))
        )

        val ledger = new Ledger(List(transaction, transaction2))

        val months = ledger.months
        months should have size 2
    }

    "return months ordered by year and month ascending" in {
        val transaction =
        Transaction(
            date = LocalDate.of(2026, 4, 10),
            category = Category("Salary"),
            amount = Money(BigDecimal(1500))
        )

        val transaction2 =
        Transaction(
            date = LocalDate.of(2026, 3, 10),
            category = Category("Salary"),
            amount = Money(BigDecimal(1000))
        )

        val ledger = new Ledger(List(transaction, transaction2))

        val months = ledger.months
        months should have size 2

        months.head.yearMonth shouldBe YearMonth.of(2026, 3)
        months(1).yearMonth shouldBe YearMonth.of(2026, 4)
    }

    "produce deterministic results regardless of transaction order" in {
        val transaction =
        Transaction(
            date = LocalDate.of(2026, 3, 10),
            category = Category("Salary"),
            amount = Money(BigDecimal(1000))
        )

        val transaction2 =
        Transaction(
            date = LocalDate.of(2026, 3, 10),
            category = Category("Rent"),
            amount = Money(BigDecimal(-500))
        ) 
        
        val ledgerA = new Ledger(List(transaction, transaction2))
        val ledgerB = new Ledger(List(transaction2, transaction))
        
        ledgerA.months.map(m => (m.yearMonth, m.total)) shouldBe ledgerB.months.map(m => (m.yearMonth, m.total))
    }   
  }
}
