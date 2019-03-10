package eati.connect4

import java.lang.Exception
import java.util.*

class TerminalUI : GameUI {
    override var listener: GameUIEventsListener? = null

    private var end = false

    private val scanner = Scanner(System.`in`)

    override fun startGame() {
        Thread {
            while (!end) {
                try {
                    val columnInput = scanner.nextInt()
                    listener?.onUserInput(columnInput)

                    // Let the UI refresh
                    Thread.sleep(100)
                } catch (e: Exception) {
                    println("Incorrect input, please try again")
                    scanner.nextLine()
                }
            }
        }.start()

    }

    override fun endGame() {
        end = true
    }

    override fun showBoard(board: Board) {
        board.getNaturalCells().forEach {
            print("|")
            it.forEach {
                print(
                    "${when (it) {
                        Cell.EMPTY -> " "
                        Cell.RED -> "R"
                        Cell.YELLOW -> "Y"
                    }}|"
                )
            }
            println()
        }
        println()
    }

    override fun showMessage(text: String) {
        println(text)
    }
}