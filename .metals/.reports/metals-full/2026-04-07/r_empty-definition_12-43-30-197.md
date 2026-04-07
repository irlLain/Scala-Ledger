error id: file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/test/scala/ledger/domain/CategorySpec.scala:map.
file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/test/scala/ledger/domain/CategorySpec.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -ledger/domain.
	 -ledger/domain#
	 -ledger/domain().
	 -ledger/ledger.
	 -ledger/ledger#
	 -ledger/ledger().
	 -scala/Predef.
	 -scala/Predef#
	 -scala/Predef().
offset: 2928
uri: file:///C:/Users/morgan.graves/Documents/Scala%20Project%201%20-%20Ledger/src/test/scala/ledger/domain/CategorySpec.scala
text:
```scala
package ledger.domain

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import ledger.domain._
import ledger.ledger._
import scala.math.BigDecimal
import java.time.{LocalDate, YearMonth}

class CategorySpec extends AnyWordSpec with Matchers {

  "Category" should {

    "exist and allow tests to run" in {
      true shouldBe true
    }

    //income and expense flags
    "Positive amounts should be flagged as income" in {
      val amount = BigDecimal(100)
      val category = Category("Salary", amount)
      category.isIncome shouldBe true
      category.isExpense shouldBe false
    }


    "Zero amounts should be flagged as income" in {
      val amount = BigDecimal(0)
      val category = Category("Zero Income", amount)
      category.isIncome shouldBe true
      category.isExpense shouldBe false
    }

    "Negative amounts should be flagged as expense" in {
      val amount = BigDecimal(-50)
      val category = Category("Groceries", amount)
      category.isIncome shouldBe false
      category.isExpense shouldBe true
    }

    "Income flag should be false for negative amounts" in {
      val amount = BigDecimal(-100)
      val category = Category("Negative Income", amount)
      category.isIncome shouldBe false
      category.isExpense shouldBe true
    }

    "Expense flag should be false for positive amounts" in {
      val amount = BigDecimal(50)
      val category = Category("Positive Expense", amount)
      category.isIncome shouldBe true
      category.isExpense shouldBe false
    }

    //Grouping transactions by category
    "Group income transactions by category" in {
      val transactions = List(
        Transaction(LocalDate.of(2024, 1, 1), Category("Salary"), Money(BigDecimal(3000))),
        Transaction(LocalDate.of(2024, 1, 15), Category("Freelance"), Money(BigDecimal(500))),
        Transaction(LocalDate.of(2024, 1, 20), Category("Groceries"), Money(BigDecimal(-150))),
        Transaction(LocalDate.of(2024, 1, 25), Category("Rent"), Money(BigDecimal(-1000)))
      )

      val categories = transactions.groupBy(_.value).map {
        case (desc, txns) =>
          val totalAmount = txns.map(_.amount).sum
          Category(desc, totalAmount)
      }.toList

      categories should contain theSameElementsAs List(
        Category("Salary", BigDecimal(3000)),
        Category("Freelance", BigDecimal(500)),
        Category("Groceries", BigDecimal(-150)),
        Category("Rent", BigDecimal(-1000))
      )
    }

    "Group expense transactions by category" in {
      val transactions = List(
        Transaction(LocalDate.of(2024, 1, 20), Category("Groceries"), Money(BigDecimal(-150))),
        Transaction(LocalDate.of(2024, 1, 25), Category("Rent"), Money(BigDecimal(-1000)))
      )

      val categories = transactions.groupBy(_.value).@@map {
        case (value, txns) =>
          val totalAmount = txns.map(_.amount).sum
          Category(value, totalAmount)
      }.toList

      categories should contain theSameElementsAs List(
        Category("Groceries", BigDecimal(-150)),
        Category("Rent", BigDecimal(-1000))
      )
    }

    "Do not mix income and expense transactions in the same category group" in {
      val transactions = List(
        Transaction(LocalDate.of(2024, 1, 1), Category("Salary"), Money(BigDecimal(3000))),
        Transaction(LocalDate.of(2024, 1, 15), Category("Freelance"), Money(BigDecimal(500))),
        Transaction(LocalDate.of(2024, 1, 20), Category("Groceries"), Money(BigDecimal(-150))),
        Transaction(LocalDate.of(2024, 1, 25), Category("Rent"), Money(BigDecimal(-1000)))
      )

      val categories = transactions.groupBy(_.description).map {
        case (desc, txns) =>
          val totalAmount = txns.map(_.amount).sum
          Category(desc, totalAmount)
      }.toList

      categories should contain theSameElementsAs List(
        Category("Salary", BigDecimal(3000)),
        Category("Freelance", BigDecimal(500)),
        Category("Groceries", BigDecimal(-150)),
        Category("Rent", BigDecimal(-1000))
      )
    }

    "Create separate category groups for income and expenses with the same name" in {
      val transactions = List(
        Transaction(LocalDate.of(2024, 1, 1), Category("Salary"), Money(BigDecimal(3000))),
        Transaction(LocalDate.of(2024, 1, 15), Category("Salary"), Money(BigDecimal(-500)))
      )

      val categories = transactions.groupBy(_.description).map {
        case (desc, txns) =>
          val totalAmount = txns.map(_.amount).sum
          Category(desc, totalAmount)
      }.toList

      categories should contain theSameElementsAs List(
        Category("Salary", BigDecimal(2500))
      )
    }

    //Ordering
    "Income categories should be ordered alphabetically" in {
      val categories = List(
        Category("Freelance", BigDecimal(500)),
        Category("Salary", BigDecimal(3000)),
        Category("Investment", BigDecimal(200))
      )

      val sortedCategories = categories.sortBy(_.value)

      sortedCategories shouldEqual List(
        Category("Freelance", BigDecimal(500)),
        Category("Investment", BigDecimal(200)),
        Category("Salary", BigDecimal(3000))
      )
    }
    
    "Expense categories should be ordered alphabetically" in {
      val categories = List(
        Category("Rent", BigDecimal(-1000)),
        Category("Groceries", BigDecimal(-150)),
        Category("Utilities", BigDecimal(-200))
      )

      val sortedCategories = categories.sortBy(_.value)

      sortedCategories shouldEqual List(
        Category("Groceries", BigDecimal(-150)),
        Category("Rent", BigDecimal(-1000)),
        Category("Utilities", BigDecimal(-200))
      )
    }

  }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 