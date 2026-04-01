package ledger.domain

import java.time.YearMonth

final case class Month(
  yearMonth: YearMonth,
  transactions: List[Transaction]
) {

  /** Total amount for the month (income + expenses) */
  def total: BigDecimal =
    ???  

  /** Transactions grouped by category */
  def byCategory: Map[Category, List[Transaction]] =
    ???

  /** Total amount per category for the month */
  def totalsByCategory: Map[Category, BigDecimal] =
    ???

}
