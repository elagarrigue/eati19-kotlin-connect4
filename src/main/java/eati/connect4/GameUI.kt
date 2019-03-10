package eati.connect4

interface GameUI {

    var listener: GameUIEventsListener?

    fun showBoard(board: Board)

    fun showMessage(text: String)

    fun startGame()

    fun endGame()
}

interface GameUIEventsListener {

    fun onUserInput(column: Int)
}