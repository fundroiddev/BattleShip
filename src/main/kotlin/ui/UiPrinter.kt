package ui

import model.GameBoard
import model.Mark

class UiPrinter {

    fun printGameBoards(playerGameBoard: GameBoard, enemyGameBoard: GameBoard, showEnemyShips: Boolean = false) {
        println("Ваша доска")
        printPlayerBoard(playerGameBoard)
        println()
        println("Доска противника")
        if (showEnemyShips) {
            printPlayerBoard(enemyGameBoard)
        } else {
            printEnemyBoard(enemyGameBoard)
        }
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

    fun placeShots() {
        println("Начинается бой, следуйте инструкциям")
    }

    fun shipAlreadyPlacedError() {
        println("Ошибка: в данной позиции уже установлен корабль")
    }

    fun wrongFormatError() {
        println("Ошибка: введены некорректные координаты")
    }

    fun enterCoordinates() {
        println("Введите координаты в формате a0")
    }

    fun alreadyShotedError() {
        println("Ошибка: по этим координатам уже был произведен выстрел. Попробуйте еще раз")
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