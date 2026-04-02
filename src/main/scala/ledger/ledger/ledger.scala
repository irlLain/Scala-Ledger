package ledger.ledger

import ledger.domain.{Month, Transaction}

class Ledger(transactions: List[Transaction]) {

  def all: List[Transaction] =
    transactions

  def months: List[Month] =
    ???
}

object Ledger {
  def empty: Ledger =
    new Ledger(Nil)
}