package handlers

import model.GameBoard

class UiPrinter(
    val playerGameboard: GameBoard,
    val enemyGameBoard: GameBoard,
) {

    fun printGameBoards() {
        println("Ваша доска")
        printGameBoard(playerGameboard)
        println()
        println("Доска противника")
        printGameBoard(enemyGameBoard)
    }

    private fun printGameBoard(board: GameBoard) {
        printBoardHeader(board)
        for (i in board.board.indices) {
            print("|${i}| ")
            for (j in board.board[i]) {
                print("${j.symbol} ")
            }
            println()
        }
    }

    private fun printBoardHeader(board: GameBoard) {
        print("   ")
        for (i in board.board.indices) {
            print("|${'a' + i}")
        }
        print("|")
        println()
    }
}