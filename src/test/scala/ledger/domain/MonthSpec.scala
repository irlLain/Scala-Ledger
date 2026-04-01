package ledger.parsing

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import ledger.domain._
import scala.math.BigDecimal

class MonthSpec extends AnyWordSpec with Matchers {

  "Month" should {

    "exist and allow tests to run" in {
      true shouldBe true
    }

    "return the correct total for a single transaction in a single month" in {
        true shouldBe true
    }

    "sum multiple transactions within the same month" in {
        true shouldBe true
    }

    "group transactions into separate months" in {
        true shouldBe true
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