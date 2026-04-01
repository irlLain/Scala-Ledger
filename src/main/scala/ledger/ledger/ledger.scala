package ledger.ledger

import ledger.domain.Transaction

class Ledger(transactions: List[Transaction]) {

  def all: List[Transaction] =
    transactions
}

object Ledger {
  def empty: Ledger =
    new Ledger(Nil)
}