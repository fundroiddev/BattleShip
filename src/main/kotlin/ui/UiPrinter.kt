package ui

import model.GameBoard
import model.Mark

class UiPrinter {

    fun printGameBoards(playerGameBoard: GameBoard, enemyGameBoard: GameBoard, isShowShips: Boolean = false) {
        println("Ваша доска")
        printPlayerBoard(playerGameBoard)
        println()
        println("Доска противника")
        if (isShowShips) {
            printPlayerBoard(enemyGameBoard)
        } else {
            printEnemyBoard(enemyGameBoard)
        }
    }

    fun enterGameboardSize() {
        println("Введите размер доски")
    }

    fun placeShips() {
        println("Расстановка кораблей игрока")
    }

    fun shipPlacedError() {
        println("Ошибка: проверьте корректность размещения корабля корабли не могут \"пересекаться\" и находится по соседству")
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

    fun enterDirection() {
        println("Введите направление расстановки корабля. h - горизонтальное, v - вертикальное")
    }

    fun shipDestroyed() {
        println("Корабль уничтожен")
    }

    fun shipHit() {
        println("Корабль ранен")
    }

    fun yourShipDestroyed() {
        println("Ваш корабль уничтожен")
    }

    fun yourShipHit() {
        println("Ваш корабль ранен")
    }

    fun placeShip(shipSize: Int) {
        println("Установка $shipSize-палубного корабля")
        println("Введите координату первой палубы в формате a0")
    }

    private fun printEnemyBoard(enemyGameBoard: GameBoard) {
        printBoardHeader(enemyGameBoard)
        for (i in enemyGameBoard.board.indices) {
            print("|$i| ")
            for (j in enemyGameBoard.board[i]) {
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
        for (i in playerGameBoard.board.indices) {
            print("|$i| ")
            for (j in playerGameBoard.board[i]) {
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
}