package ledger.ledger

import java.time.YearMonth
import ledger.domain.{Month, Transaction}

class Ledger(transactions: List[Transaction]) {

  def all: List[Transaction] =
    transactions

  def months: List[Month] =
    transactions
      .groupBy(t => YearMonth.from(t.date))
      .map { case (yearMonth, txs) => Month(yearMonth, txs) }
      .toList
      .sortBy(_.yearMonth)
}

object Ledger {
  def empty: Ledger =
    new Ledger(Nil)
}