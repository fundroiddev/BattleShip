package ui

import model.GameBoard
import model.Mark

class UiPrinter {

    fun printGameBoards(playerGameBoard: GameBoard, enemyGameBoard: GameBoard) {
        println("Ваша доска")
        printPlayerBoard(playerGameBoard)
        println()
        println("Доска противника")
        printEnemyBoard(enemyGameBoard)
    }

    fun printAllBoards(playerGameBoard: GameBoard, enemyGameBoard: GameBoard) {
        println("Ваша доска")
        printPlayerBoard(playerGameBoard)
        println()
        println("Доска противника")
        printPlayerBoard(enemyGameBoard)
    }

    private fun printEnemyBoard(enemyGameBoard: GameBoard) {
        printBoardHeader(enemyGameBoard)
        for(i in enemyGameBoard.board.indices) {
            print("|$i|")
            for(j in enemyGameBoard.board[i]) {
                print(
                    when (j) {
                        Mark.SHIP_DECK -> Mark.NEW.symbol
                        else -> j.symbol
                    } + " "
                )
            }
            println()
        }
    }

    private fun printPlayerBoard(playerGameBoard: GameBoard) {
        printBoardHeader(playerGameBoard)
        for(i in playerGameBoard.board.indices) {
            print("|$i| ")
            for(j in playerGameBoard.board[i]) {
                print("${j.symbol} ")
            }
            println()
        }
    }

    private fun printBoardHeader(playerGameBoard: GameBoard) {
        print("   ")
        for (i in playerGameBoard.board.indices) {
            print("|${'a' + i}")
        }
        print("|")
        println()
    }

    fun enterShipNumber() {
        println("Введите количество кораблей")
    }

    fun enterGameboardSize() {
        println("Введите размер доски")
    }

    fun placeShips() {
        println("Расстановка кораблей игрока")
    }

    fun enterCoordinateShip() {
        println("Введите координаты корабля в формате a0")
    }

    fun shipAlreadyPlacedError() {
        println("Ошибка: в данной позиции уже установлен корабль")
    }

    fun wrongFormatError() {
        println("Ошибка: введены некорректные координаты")
    }

    fun enterShot() {
        println("Введите координаты выстрела в формате a0")
    }

    fun alreadyShotedError() {
        println("Ошибка: по этим координатам уже был произведен выстрел. Попробуйте еще раз")
    }

    fun enemyShot(x: Int, y: Int) {
        println("Противник стреляет в $y, $x")
    }

    fun gameOver() {
        println("Игра окончена")
        println("Так выглядит поле боя в финале")
    }

    fun printEnemyWins() {
        println("Вы проиграли(")
    }

    fun printPlayerWin() {
        println("Поздравляю! Ты победил и Пистолетов обнимает тебя!!!11")
    }
}