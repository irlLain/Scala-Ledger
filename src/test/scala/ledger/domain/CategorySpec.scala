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
    "Positive amounts should be flagged as income"
    "Zero amounts should be flagged as income"
    "Negative amounts should be flagged as expense"
    "Income flag should be false for negative amounts"
    "Expense flag should be false for positive amounts"

    //Grouping transactions by category
    "Group income transactions by category"
    "Group expense transactions by category"
    "Do not mix income and expense transactions in the same category group"
    "Create separate category groups for income and expenses with the same name"

    //Ordering
    "Income categories should be ordered alphabetically"
    "Expense categories should be ordered alphabetically"

  }
}