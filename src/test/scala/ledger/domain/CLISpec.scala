package ledger.domain

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import ledger.domain._
import ledger.ledger._

class CLISpec extends AnyWordSpec with Matchers {

  "CLI" should {

    "exist and allow tests to run" in {
      true shouldBe true
    }

    //CLI arguments
    //valid
    "Accept a single CSV file path argument"
    "Accept relative CSV file paths"
    "Accept absolute CSV file paths"

    //invalid
    "Return error when no arguments are provided"
    "Return error when more than one argument is provided"
    "Return error when file extension is not .csv"
    "Return error when argument is an empty string"
    "Return error when argument is whitespace"

    //path handling
    "Return error when CSV file is not found"
    "Return error when CSV file cannot be read"
    "Return error when CSV file is empty"
    "Return error when CSV file contains only headers"
    "Proceed when CSV file contains valid data rows"

    //csv content validation
    "Reject CSV with invalid row format"
    "Reject CSV with invalid date values"
    "Reject CSV with invalid numeric values"
    "Allow CSV with mixed valid and invalid rows"
    "Return error when all CSV rows are invalid"
    "Return partial success when some rows are invalid"

    //Income and expense classification
    "Classify positive amounts as income"
    "Classify zero amounts as income"
    "Classify negative amounts as expense"
    "Do not mix income and expense transactions"
    "Preserve original transaction amounts"

    //Category grouping
    "Group income transactions by category"
    "Group expense transactions by category"
    "Create separate category groups for income and expense with the same name"
    "Exclude categories with no transactions"
    "Preserve all transactions during grouping"

    //category sorting
    "Order income categories alphabetically"
    "Order expense categories alphabetically"
    "Sort categories case-insensitively"
    "Produce deterministic ordering regardless of input order"
  
    //totals and summary
    "Calculate total income correctly"
    "Calculate total expenses correctly"
    "Calculate net total correctly"
    "Calculate totals per category"
    "Ensure totals equal the sum of grouped transactions"

    //CLI error handing
    "Return InvalidArguments error for malformed input"
    "Return FileNotFound error when CSV file is missing"
    "Return ParseError when CSV cannot be parsed"
    "Return NoValidData error when no valid rows exist"
    "Return meaningful error messages"
  
  }

}
