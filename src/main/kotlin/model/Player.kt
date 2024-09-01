package model

class Player(
    val playerBoard: GameBoard,
    val shotBoard: GameBoard,
    val fleetSize: Int,
) {
    val fleet: MutableList<Ship> = mutableListOf()
    val shotList: MutableList<Ship> = mutableListOf()

    fun placeShips() {
        for (ship in fleet) {
            playerBoard.board[ship.x][ship.y] = ship.status
        }
    }

    fun placeShots() {
        for (shot in shotList) {
            shotBoard.board[shot.x][shot.y] = shot.status
        }
    }

    fun placeShot(x: Int, y: Int) {
        shotBoard.board[x][y] = Mark.SHOOTED
    }

    fun placeShip(x: Int, y: Int) {
        playerBoard.board[x][y] = Mark.SHIP_DECK
    }

    fun isEmpty(x: Int, y: Int): Boolean {
        return playerBoard.board[x][y] == Mark.NEW
    }

    fun printGameBoards() {
        println("Ваша доска")
        printGameBoard(playerBoard)
        println()
        println("Доска противника")
        printGameBoard(shotBoard)
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