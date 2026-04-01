package ledger.domain

import java.time.LocalDate

case class Transaction(date: LocalDate, category: Category, amount: Money)