error id: file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/test/scala/ledger/domain/MonthSpec.scala:BigDecimal.
file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/test/scala/ledger/domain/MonthSpec.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -ledger/domain/BigDecimal.
	 -ledger/domain/BigDecimal#
	 -ledger/domain/BigDecimal().
	 -ledger/ledger/BigDecimal.
	 -ledger/ledger/BigDecimal#
	 -ledger/ledger/BigDecimal().
	 -scala/math/BigDecimal.
	 -scala/math/BigDecimal#
	 -scala/math/BigDecimal().
	 -BigDecimal.
	 -BigDecimal#
	 -BigDecimal().
	 -scala/Predef.BigDecimal.
	 -scala/Predef.BigDecimal#
	 -scala/Predef.BigDecimal().
offset: 191
uri: file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/test/scala/ledger/domain/MonthSpec.scala
text:
```scala
package ledger.domain

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import ledger.domain._
import ledger.ledger._
import scala.math.BigDecimal@@
import java.time.{LocalDate, YearMonth}

class MonthSpec extends AnyWordSpec with Matchers {

  "Month" should {

    "exist and allow tests to run" in {
      true shouldBe true
    }

    "return the correct total for a single transaction in a single month" in {
        val transaction =
        Transaction(
            date = LocalDate.of(2026, 3, 10),
            category = Category("Salary"),
            amount = Money(BigDecimal(1000))
        )

        val month =
        Month(
            yearMonth = YearMonth.of(2026, 3),
            transactions = List(transaction)
        )

        month.total shouldBe BigDecimal(1000)
    }

    "sum multiple transactions within the same month" in {
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

        val month =
        Month(
            yearMonth = YearMonth.of(2026, 3),
            transactions = List(transaction, transaction2)
        )

        month.total shouldBe BigDecimal(2500)
    }

    "group transactions into separate months" in {
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

       val ledger =
       new Ledger(List(transaction, transaction2))

       val months = ledger.months

       months should have size 2

       months.find(_.yearMonth == YearMonth.of(2026, 3)).get.total shouldBe
            (BigDecimal(1000))

       months.find(_.yearMonth == YearMonth.of(2026, 4)).get.total shouldBe
            (BigDecimal(1500))
    }

    "net income and expenses correctly within a month" in {
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

        val month =
        Month(
            yearMonth = YearMonth.of(2026, 3),
            transactions = List(transaction, transaction2)
        )

        month.total shouldBe (BigDecimal(500))
    }

    "return a negative total when a month contains only expenses" in {
        val transaction =
        Transaction(
            date = LocalDate.of(2026, 3, 10),
            category = Category("Rent"),
            amount = Money(BigDecimal(-500))
        )

        val month =
        Month(
            yearMonth = YearMonth.of(2026, 3),
            transactions = List(transaction)
        )

        month.total shouldBe (BigDecimal(-500))
    }

    "exclude months that have no transactions" in {
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

       val ledger =
       new Ledger(List(transaction, transaction2))

       val months = ledger.months

       months should have size 1

       months.find(_.yearMonth == YearMonth.of(2026, 3)).get.total shouldBe
            (BigDecimal(2500))

       months.find(_.yearMonth == YearMonth.of(2026, 4)) shouldBe empty
    }

    "produce the same results regardless of transaction order" in {
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
        
        ledgerA.months shouldBe ledgerB.months

    }

    "return no monthly totals for an empty ledger" in {
        val ledger = new Ledger(Nil)
        val months = ledger.months
        months shouldBe empty
    }
  }

}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 