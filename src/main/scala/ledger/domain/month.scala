package ledger.domain

import java.time.YearMonth

final case class Month(
  yearMonth: YearMonth,
  transactions: List[Transaction]
) {

  def total: BigDecimal =
    transactions.map(_.amount.value).fold(BigDecimal(0))(_ + _)  

}
