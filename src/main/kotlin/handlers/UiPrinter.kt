package handlers

import model.GameBoard

class UiPrinter {

    fun enterShipNumber() {
        println("Введите количество кораблей")
    }

    fun enterGameboardSize() {
        println("Введите размер доски")
    }

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
        println("Доска противника с кораблями")
        printPlayerBoard(enemyGameBoard)
    }

    private fun printPlayerBoard(board: GameBoard) {
        printBoardHeader(board)
        for (i in board.board.indices) {
            print("|${i}| ")
            for (j in board.board[i]) {
                print("${j.symbol} ")
            }
            println()
        }
    }

    private fun printEnemyBoard(board: GameBoard) {
        printBoardHeader(board)
        for (i in board.board.indices) {
            print("|${i}| ")
            for (j in board.board[i]) {
                print(
                    when (j) {
                        model.Mark.SHIP_DECK -> model.Mark.NEW.symbol
                        else -> j.symbol
                    } + " "
                )
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

    fun placeShips() {
        println("Расстановка кораблей игрока")
    }

    fun enterCoordinatesShip() {
        println("Введите координаты корабля в формате a0")
    }

    fun shipAlreadyPlacedError() {
        println("Ошибка: здесь уже стоит корабль. Попробуйте еще раз.")
    }

    fun wrongFormatError() {
        println("Ошибка: неверный формат координат. Попробуйте еще раз.")
    }

    fun alreadyShoted() {
        println("Ошибка: по этим координатам уже был произведен выстрел. Попробуйте еще раз.")
    }

    fun enterShot() {
        println("Введите координаты выстрела в формате a0")
    }

    fun gameOver() {
        println("Игра окончена!")
        println("Так выглядит сражение в финале")
    }

    fun printPlayerWin() {
        println("Вы победили!")
    }

    fun printEnemyWin() {
        println("Вы проиграли")
    }

    fun enemyShot(x: Int, y: Int) {
        println("Противник стреляет в $y, $x")
    }
}