package eati.connect4

enum class Cell { EMPTY, RED, YELLOW }

class Board {

    val height = 6
    val width = 7
    private val cells: Array<Array<Cell>> = Array(width) { Array(height) { Cell.EMPTY } }

    fun getNaturalCells() = (0 until height).map { i -> cells.map { it[i] } }

    fun isFullColumn(column: Int) = cells[column].all { it != Cell.EMPTY }

    fun isFull() = cells.flatten().all { it != Cell.EMPTY }

    fun setDisc(disc: Cell, column: Int) {
        cells[column][cells[column].indexOfLast { it == Cell.EMPTY }] = disc
    }

    fun fourInLine(): Cell? = columnWin() ?: lineWin() ?: negativeDiagonal() ?: positiveDiagonal()

    private fun List<List<Cell>>.hasWin() = this.map { it.fourEquals() }.find { it != null }

    private fun columnWin(): Cell? = cells.map { it.toList() }.toList().hasWin()

    private fun lineWin(): Cell? = getNaturalCells().hasWin()

    private fun negativeDiagonal(): Cell? =
        (-height until height).map { i ->
            (0 until width).filter { (it + i in 0 until height) }.map { j -> cells[j][j + i] }
        }.hasWin()

    private fun positiveDiagonal(): Cell? =
        (-height until height).map { i ->
            (0 until width).filter { (it + i in 0 until height) }.map { j -> cells[j][height - 1 - (j + i)] }
        }.hasWin()

    private fun List<Cell>.fourEquals(): Cell? =
            with(this.map { it.toString() }.reduce { acc, cell -> acc + cell }) {
                when {
                    this.contains(Cell.RED.toString().repeat(4)) -> Cell.RED
                    this.contains(Cell.YELLOW.toString().repeat(4)) -> Cell.YELLOW
                    else -> null
                }
            }
}