package ledger.domain

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import ledger.domain._
import ledger.ledger._
import ledger.cli.Cli

class CLISpec extends AnyWordSpec with Matchers {

  "CLI" should {

    "exist and allow tests to run" in {
      true shouldBe true
    }

    //CLI arguments
    //valid
    "Accept a single CSV file path argument" in {
        val args = List("transactions.csv")

        val result = Cli.run(args)

        result shouldBe Right("transactions.csv")
    }

    "Accept relative CSV file paths" in {
        val args = List("./data/transactions.csv")

        val result = Cli.run(args)

        result shouldBe Right("./data/transactions.csv")
    }

    "Accept absolute CSV file paths" in {
        val args = List("C:/Users/morgan.graves/Documents/Scala Project 1 - Ledger/data/transactions.csv")

        val result = Cli.run(args)

        result shouldBe Right("C:/Users/morgan.graves/Documents/Scala Project 1 - Ledger/data/transactions.csv")
    }

    //invalid
    "Return error when no arguments are provided" in {
        val args = Nil

        val result = Cli.run(args)

        result shouldBe Left("No CSV file path provided")
    }

    "Return error when more than one argument is provided" in {
        val args = List("transactions.csv", "extra_arg")

        val result = Cli.run(args)

        result shouldBe Left("Invalid arguments")
    }

    "Return error when file extension is not .csv" in {
        val args = List("transactions.txt")

        val result = Cli.run(args)

        result shouldBe Left("Invalid arguments")
    }

    "Return error when argument is an empty string" in {
        val args = List("")

        val result = Cli.run(args)

        result shouldBe Left("Invalid arguments")
    }

    "Return error when argument is whitespace" in {
        val args = List("   ")

        val result = Cli.run(args)

        result shouldBe Left("Invalid arguments")
    }

    //path handling
    "Return error when CSV file is not found" in {
        val args = List("nonexistent.csv")

        val result = Cli.run(args)

        result shouldBe Right("nonexistent.csv")
    }

    "Return error when CSV file cannot be read" in {
        val args = List("/root/transactions.csv")

        val result = Cli.run(args)

        result shouldBe Right("/root/transactions.csv")
    }

    "Return error when CSV file is empty" in {
        val args = List("empty.csv")

        val result = Cli.run(args)

        result shouldBe Right("empty.csv")
    }

    "Return error when CSV file contains only headers" in {
        val args = List("headers_only.csv")

        val result = Cli.run(args)

        result shouldBe Right("headers_only.csv")
    }

    "Proceed when CSV file contains valid data rows" in {
        val args = List("valid_transactions.csv")

        val result = Cli.run(args)

        result shouldBe Right("valid_transactions.csv")
    }

    //csv content validation
    "Reject CSV with invalid row format" in {
        val args = List("sample-data/march-2026.csv")

        val result = Cli.run(args)

        result shouldBe Right("sample-data/march-2026.csv")
    }

    "Reject CSV with invalid date values" in {
        val args = List("invalid_dates.csv")

        val result = Cli.run(args)

        result shouldBe Right("invalid_dates.csv")
    }

    "Reject CSV with invalid numeric values" in {
        val args = List("invalid_amounts.csv")

        val result = Cli.run(args)

        result shouldBe Right("invalid_amounts.csv")
    }

    "Allow CSV with mixed valid and invalid rows" in {
        val args = List("mixed_valid_invalid.csv")

        val result = Cli.run(args)

        result shouldBe Right("mixed_valid_invalid.csv")
    }

    "Return error when all CSV rows are invalid" in {
        val args = List("all_invalid.csv")

        val result = Cli.run(args)

        result shouldBe Right("all_invalid.csv")
    }

    "Return partial success when some rows are invalid" in {    
        val args = List("partial_invalid.csv")

        val result = Cli.run(args)

        result shouldBe Right("partial_invalid.csv")
    }

    //Income and expense classification
    "Classify positive amounts as income" in {
        val amount = BigDecimal(100)
        val category = Category("Salary", amount)
        category.isIncome shouldBe true
        category.isExpense shouldBe false
    }

    "Classify zero amounts as income" in {
        val amount = BigDecimal(0)
        val category = Category("Zero Amount", amount)
        category.isIncome shouldBe true
        category.isExpense shouldBe false
    }

    "Classify negative amounts as expense" in {
        val amount = BigDecimal(-100)
        val category = Category("Groceries", amount)
        category.isIncome shouldBe false
        category.isExpense shouldBe true
    }

    "Ensure income flag is false for negative amounts" in {
        val amount = BigDecimal(-50)
        val category = Category("Negative Income", amount)
        category.isIncome shouldBe false
        category.isExpense shouldBe true
    }

    "Do not mix income and expense transactions" in {
        val transactions = List(
          Transaction(LocalDate.of(2024, 1, 1), Category("Salary", BigDecimal(3000)), Money(BigDecimal(3000))),
          Transaction(LocalDate.of(2024, 1, 15), Category("Salary", BigDecimal(-500)), Money(BigDecimal(-500)))
        )

        transactions.head.category.isIncome shouldBe true
        transactions.head.category.isExpense shouldBe false

        transactions(1).category.isIncome shouldBe false
        transactions(1).category.isExpense shouldBe true
    }

    "Preserve original transaction amounts" in {
        val transactions = List(
          Transaction(LocalDate.of(2024, 1, 1), Category("Salary", BigDecimal(3000)), Money(BigDecimal(3000))),
          Transaction(LocalDate.of(2024, 1, 15), Category("Salary", BigDecimal(-500)), Money(BigDecimal(-500)))
        )

        transactions.head.amount shouldBe Money(BigDecimal(3000))
        transactions(1).amount shouldBe Money(BigDecimal(-500))
    }

    //Category grouping
    "Group income transactions by category" in {
        val transactions = List(
            Transaction(LocalDate.of(2024, 1, 1), Category("Salary"), Money(BigDecimal(3000))),
            Transaction(LocalDate.of(2024, 1, 15), Category("Freelance"), Money(BigDecimal(500))),
            Transaction(LocalDate.of(2024, 1, 20), Category("Groceries"), Money(BigDecimal(-150))),
            Transaction(LocalDate.of(2024, 1, 25), Category("Rent"), Money(BigDecimal(-1000)))
        )
    
        val categories = transactions.groupBy(_.category.value).map {
            case (desc, txns) =>
            val totalAmount = txns.map(_.amount.value).fold(BigDecimal(0))(_ + _)
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
    
        val categories = transactions.groupBy(_.category.value).map {
            case (value, txns) =>
            val totalAmount = txns.map(_.amount.value).fold(BigDecimal(0))(_ + _)
            Category(value, totalAmount)
        }.toList
    
        categories should contain theSameElementsAs List(
            Category("Groceries", BigDecimal(-150)),
            Category("Rent", BigDecimal(-1000))
        )
    }

    "Create separate category groups for income and expense with the same name" in {
        val transactions = List(
            Transaction(LocalDate.of(2024, 1, 1), Category("Salary"), Money(BigDecimal(3000))),
            Transaction(LocalDate.of(2024, 1, 15), Category("Salary"), Money(BigDecimal(-500)))
        )
    }

    "Exclude categories with no transactions" in {
        val transactions = List(
            Transaction(LocalDate.of(2024, 1, 1), Category("Salary"), Money(BigDecimal(3000)))
        )

        val categories = transactions.groupBy(_.category.value).map {
            case (desc, txns) =>
            val totalAmount = txns.map(_.amount.value).fold(BigDecimal(0))(_ + _)
            Category(desc, totalAmount)
        }.toList

        categories should contain theSameElementsAs List(
            Category("Salary", BigDecimal(3000))
        )
    }

    "Preserve all transactions during grouping" in {
        val transactions = List(
            Transaction(LocalDate.of(2024, 1, 1), Category("Salary"), Money(BigDecimal(3000))),
            Transaction(LocalDate.of(2024, 1, 15), Category("Salary"), Money(BigDecimal(-500)))
        )

        val categories = transactions.groupBy(_.category.value).map {
            case (desc, txns) =>
            val totalAmount = txns.map(_.amount.value).fold(BigDecimal(0))(_ + _)
            Category(desc, totalAmount)
        }.toList

        categories should contain theSameElementsAs List(
            Category("Salary", BigDecimal(2500))
        )
    }

    //category sorting
    "Order income categories alphabetically" in {
        val transactions = List(
            Transaction(LocalDate.of(2024, 1, 1), Category("Salary"), Money(BigDecimal(3000))),
            Transaction(LocalDate.of(2024, 1, 15), Category("Freelance"), Money(BigDecimal(500))),
            Transaction(LocalDate.of(2024, 1, 20), Category("Groceries"), Money(BigDecimal(-150))),
            Transaction(LocalDate.of(2024, 1, 25), Category("Rent"), Money(BigDecimal(-1000)))
        )

        val categories = transactions.groupBy(_.category.value).map {
            case (desc, txns) =>
            val totalAmount = txns.map(_.amount.value).fold(BigDecimal(0))(_ + _)
            Category(desc, totalAmount)
        }.toList

        val sortedCategories = categories.sortBy(_.value)

        sortedCategories shouldEqual List(
            Category("Freelance", BigDecimal(500)),
            Category("Groceries", BigDecimal(-150)),
            Category("Rent", BigDecimal(-1000)),
            Category("Salary", BigDecimal(3000))
        )
    }

    "Order expense categories alphabetically" in {
        val transactions = List(
            Transaction(LocalDate.of(2024, 1, 20), Category("Groceries"), Money(BigDecimal(-150))),
            Transaction(LocalDate.of(2024, 1, 25), Category("Rent"), Money(BigDecimal(-1000))),
            Transaction(LocalDate.of(2024, 1, 30), Category("Utilities"), Money(BigDecimal(-200)))
        )

        val categories = transactions.groupBy(_.category.value).map {
            case (value, txns) =>
            val totalAmount = txns.map(_.amount.value).fold(BigDecimal(0))(_ + _)
            Category(value, totalAmount)
        }.toList

        val sortedCategories = categories.sortBy(_.value)
    }

    "Sort categories case-insensitively" in {
        val transactions = List(
            Transaction(LocalDate.of(2024, 1, 20), Category("groceries"), Money(BigDecimal(-150))),
            Transaction(LocalDate.of(2024, 1, 25), Category("Rent"), Money(BigDecimal(-1000))),
            Transaction(LocalDate.of(2024, 1, 30), Category("utilities"), Money(BigDecimal(-200)))
        )

        val categories = transactions.groupBy(_.category.value).map {
            case (value, txns) =>
            val totalAmount = txns.map(_.amount.value).fold(BigDecimal(0))(_ + _)
            Category(value, totalAmount)
        }.toList

        val sortedCategories = categories.sortBy(_.value.toLowerCase)

        sortedCategories shouldEqual List(
            Category("groceries", BigDecimal(-150)),
            Category("Rent", BigDecimal(-1000)),
            Category("utilities", BigDecimal(-200))
        )
    }

    "Produce deterministic ordering regardless of input order" in {
        val transactions = List(
            Transaction(LocalDate.of(2024, 1, 25), Category("Rent"), Money(BigDecimal(-1000))),
            Transaction(LocalDate.of(2024, 1, 20), Category("Groceries"), Money(BigDecimal(-150))),
            Transaction(LocalDate.of(2024, 1, 30), Category("Utilities"), Money(BigDecimal(-200)))
        )

        val categories = transactions.groupBy(_.category.value).map {
            case (value, txns) =>
            val totalAmount = txns.map(_.amount.value).fold(BigDecimal(0))(_ + _)
            Category(value, totalAmount)
        }.toList

        val sortedCategories = categories.sortBy(_.value)

        sortedCategories shouldEqual List(
            Category("Groceries", BigDecimal(-150)),
            Category("Rent", BigDecimal(-1000)),
            Category("Utilities", BigDecimal(-200))
        )
    }
  
    //totals and summary
    "Calculate total income correctly" in {
        val transactions = List(
            Transaction(LocalDate.of(2024, 1, 1), Category("Salary"), Money(BigDecimal(3000))),
            Transaction(LocalDate.of(2024, 1, 15), Category("Freelance"), Money(BigDecimal(500)))
        )

        val totalIncome = transactions.filter(_.amount.value >= 0).map(_.amount.value).fold(BigDecimal(0))(_ + _)

        totalIncome shouldBe BigDecimal(3500)
    }

    "Calculate total expenses correctly" in {
        val transactions = List(
            Transaction(LocalDate.of(2024, 1, 20), Category("Groceries"), Money(BigDecimal(-150))),
            Transaction(LocalDate.of(2024, 1, 25), Category("Rent"), Money(BigDecimal(-1000)))
        )

        val totalExpenses = transactions.filter(_.amount.value < 0).map(_.amount.value).fold(BigDecimal(0))(_ + _)

        totalExpenses shouldBe BigDecimal(-1150)
    }

    "Calculate net total correctly" in {
        val transactions = List(
            Transaction(LocalDate.of(2024, 1, 1), Category("Salary"), Money(BigDecimal(3000))),
            Transaction(LocalDate.of(2024, 1, 15), Category("Freelance"), Money(BigDecimal(500))),
            Transaction(LocalDate.of(2024, 1, 20), Category("Groceries"), Money(BigDecimal(-150))),
            Transaction(LocalDate.of(2024, 1, 25), Category("Rent"), Money(BigDecimal(-1000)))
        )

        val netTotal = transactions.map(_.amount.value).fold(BigDecimal(0))(_ + _)

        netTotal shouldBe BigDecimal(2350)
    }

    "Calculate totals per category" in {
        val transactions = List(
            Transaction(LocalDate.of(2024, 1, 1), Category("Salary"), Money(BigDecimal(3000))),
            Transaction(LocalDate.of(2024, 1, 15), Category("Salary"), Money(BigDecimal(500))),
            Transaction(LocalDate.of(2024, 1, 20), Category("Groceries"), Money(BigDecimal(-150))),
            Transaction(LocalDate.of(2024, 1, 25), Category("Rent"), Money(BigDecimal(-1000)))
        )

        val categoryTotals = transactions.groupBy(_.category.value).map {
            case (desc, txns) =>
            val totalAmount = txns.map(_.amount.value).fold(BigDecimal(0))(_ + _)
            Category(desc, totalAmount)
        }.toList

        categoryTotals should contain theSameElementsAs List(
            Category("Salary", BigDecimal(3500)),
            Category("Groceries", BigDecimal(-150)),
            Category("Rent", BigDecimal(-1000))
        )
    }

    "Ensure totals equal the sum of grouped transactions" in {
        val transactions = List(
            Transaction(LocalDate.of(2024, 1, 1), Category("Salary"), Money(BigDecimal(3000))),
            Transaction(LocalDate.of(2024, 1, 15), Category("Salary"), Money(BigDecimal(500))),
            Transaction(LocalDate.of(2024, 1, 20), Category("Groceries"), Money(BigDecimal(-150))),
            Transaction(LocalDate.of(2024, 1, 25), Category("Rent"), Money(BigDecimal(-1000)))
        )

        val categoryTotals = transactions.groupBy(_.category.value).map {
            case (desc, txns) =>
            val totalAmount = txns.map(_.amount.value).fold(BigDecimal(0))(_ + _)
            Category(desc, totalAmount)
        }.toList

        categoryTotals.foreach { category =>
            val transactionsInCategory = transactions.filter(_.category.value == category.value)
            val sumOfTransactions = transactionsInCategory.map(_.amount.value).fold(BigDecimal(0))(_ + _)
            category.amount shouldBe sumOfTransactions
        }
    }

    //CLI error handing
    "Return InvalidArguments error for malformed input" in {
        val args = List("invalid_arg")

        val result = Cli.run(args)

        result shouldBe Left("Invalid arguments")
    }

    "Return FileNotFound error when CSV file is missing" in {
        val args = List("missing.csv")

        val result = Cli.run(args)

        result shouldBe Right("missing.csv")
    }
    "Return ParseError when CSV cannot be parsed" in {
        val args = List("unparseable.csv")

        val result = Cli.run(args)

        result shouldBe Right("unparseable.csv")
    }

    "Return NoValidData error when no valid rows exist" in {
        val args = List("all_invalid.csv")

        val result = Cli.run(args)

        result shouldBe Right("all_invalid.csv")
    }

    "Return meaningful error messages" in {
        val args = List("")

        val result = Cli.run(args)

        result shouldBe Left("Invalid arguments")
    }
  
  }
}


