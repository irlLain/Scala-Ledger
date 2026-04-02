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

//Income category tests
    "Parse an income transaction correctly"
    "Positive amounts should be classified as income"
    "Zero amounts should be classified as income"
    "Income transactions should retain the original category"
    "Income transactions should contribute to total income"
    "Multiple income transactions should be summed correctly"
    "Income transactions should be grouped by category"
    "Income transactions with the same category should be grouped together"
    "Income transactions with different categories should be kept separate"

    //Expense category tests
    "Parse an expense transaction correctly"
    "Negative amounts should be classified as expenses"
    "Expense transactions should retain the original category"
    "Expense transactions should contribute to total expenses"
    "Multiple expense transactions should be summed correctly"
    "Expense transactions should be grouped by category"
    "Expense transactions with the same category should be grouped together"
    "Expense transactions with different categories should be kept separate"
    "Expense amounts should be stored as negative values"

    //Category name tests
    "Group transactions by category"
    "Group income and expenses under the same category"
    "Categories should contain both income and expense transactions"
    "Categories with no transactions should not appear in results"
    "Transactions should be grouped even if amounts differ"
    "Grouping should preserve all transactions"
    "Grouping should not alter transaction amounts"
    "Grouping should not alter transaction dates"
    "Grouping should be order-independent"
    "Grouping should handle a single transaction"
    "Grouping should handle multiple categories"
  }
}