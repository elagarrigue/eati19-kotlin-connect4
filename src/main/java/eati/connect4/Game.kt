package eati.connect4

private enum class Turn { RED, YELLOW }

class Game(private val gameUI: GameUI) : GameUIEventsListener {

    private val board = Board()

    private var turn = Turn.RED

    init {
        with(gameUI) {
            listener = this@Game
            showMessage("Connect 4")
            showMessage("Input: column number (0 - ${board.width - 1})")
            showBoard(board)
            showMessage("Play $turn:")
            startGame()
        }
    }

    override fun onUserInput(column: Int) {
        inGame(column)
    }

    private fun turnDisc() = when (turn) {
        Turn.RED -> Cell.RED
        Turn.YELLOW -> Cell.YELLOW
    }

    private fun changeTurn() {
        turn = when (turn) {
            Turn.RED -> Turn.YELLOW
            Turn.YELLOW -> Turn.RED
        }
    }

    private fun inGame(column: Int) {
        val status = play(turnDisc(), column)

        if (status == PlayStatus.WRONG_INPUT) {
            gameUI.showMessage("Wrong input, please try again")
        } else {
            gameUI.showBoard(board)
            if (status.endGame()) {
                gameUI.showMessage(status.toString())
                gameUI.endGame()
            } else {
                changeTurn()
                gameUI.showMessage("Play $turn:")
            }
        }
    }

    private fun play(disc: Cell, column: Int): PlayStatus {
        if (board.isFullColumn(column) || column !in (0 until board.width)) return PlayStatus.WRONG_INPUT

        board.setDisc(disc, column)

        return getNewStatus()
    }

    private fun getNewStatus(): PlayStatus {
        val winner = board.fourInLine()

        return when {
            winner != null ->
                when (winner) {
                    Cell.RED -> PlayStatus.WIN_RED
                    Cell.YELLOW -> PlayStatus.WIN_YELLOW
                    else -> PlayStatus.ERROR
                }
            board.isFull() -> PlayStatus.TIE
            else -> PlayStatus.OK
        }
    }
}

