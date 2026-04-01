package ledger.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter

case class Month(year: Int, month: Int)

object Month {
  def from(year: Int, month: Int): Month =
    Month(year, month)
}

object MonthDay {
  private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  def parse(s: String): LocalDate = LocalDate.parse(s, formatter)
}
