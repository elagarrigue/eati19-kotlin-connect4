package eati.connect4

enum class PlayStatus {

    OK, WRONG_INPUT, WIN_RED, WIN_YELLOW, TIE, ERROR;

    override fun toString(): String =
        when (this) {
            PlayStatus.WIN_RED -> "Player RED wins!"
            PlayStatus.WIN_YELLOW -> "Player YELLOW wins!"
            PlayStatus.TIE -> "Tie!"
            else -> "Game error"
        }

    fun endGame() =
        this in setOf(PlayStatus.WIN_RED, PlayStatus.WIN_YELLOW, PlayStatus.TIE, PlayStatus.ERROR)

}