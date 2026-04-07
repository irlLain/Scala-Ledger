package ledger.domain

case class Category(value: String, amount: BigDecimal = BigDecimal(0)) {
  def isIncome: Boolean = amount >= 0
  def isExpense: Boolean = amount < 0
}