error id: file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/test/scala/ledger/domain/MonthSpec.scala:scala/math/BigDecimal.
file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/test/scala/ledger/domain/MonthSpec.scala
empty definition using pc, found symbol in pc: scala/math/BigDecimal.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -ledger/domain/BigDecimal.
	 -ledger/domain/BigDecimal#
	 -ledger/domain/BigDecimal().
	 -scala/math/BigDecimal.
	 -scala/math/BigDecimal#
	 -scala/math/BigDecimal().
	 -BigDecimal.
	 -BigDecimal#
	 -BigDecimal().
	 -scala/Predef.BigDecimal.
	 -scala/Predef.BigDecimal#
	 -scala/Predef.BigDecimal().
offset: 1280
uri: file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/test/scala/ledger/domain/MonthSpec.scala
text:
```scala
package ledger.domain

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import ledger.domain._
import scala.math.BigDecimal
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
            amount = Money(B@@igDecimal(1500))
        )

        val month =
        Month(
            yearMonth = YearMonth.of(2026, 3),
            transactions = List(transaction, transaction2)
        )

        month.total shouldBe Money(BigDecimal(2500))
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
       Ledger(List(transaction, transaction2))

       val months = ledger.months

       months should have size 2

       months.find(_.yearMonth == YearMonth.of(2026, 3)).get.total shouldBe
            Money(BigDecimal(1000))

       months.find(_.yearMonth == YearMonth.of(2026, 4)).get.total shouldBe
            Money(BigDecimal(1500))
    }

    "net income and expenses correctly within a month" in {
        true shouldBe true
    }

    "return a negative total when a month contains only expenses" in {
        true shouldBe true
    }

    "exclude months that have no transactions" in {
        true shouldBe true
    }

    "assign transactions on month boundaries to the correct month" in {
        true shouldBe true
    }

    "produce the same results regardless of transaction order" in {
        true shouldBe true
    }

    "return no monthly totals for an empty ledger" in {
        true shouldBe true
    }
  }

}
```


#### Short summary: 

empty definition using pc, found symbol in pc: scala/math/BigDecimal.