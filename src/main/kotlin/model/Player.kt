package model

class Player(
    private val playerBoard: GameBoard,
    private val shotBoard: GameBoard,
) {

    fun placeShip(x: Int, y: Int) {
        playerBoard.board[x][y] = Mark.SHIP_DECK
    }

    fun isNewSection(x: Int, y: Int): Boolean {
        return playerBoard.board[x][y] == Mark.NEW
    }

    fun isLooser(): Boolean {
        return playerBoard.board.all { markList ->
            markList.all { mark -> mark != Mark.SHIP_DECK }
        }
    }

    fun isNotShoted(x: Int, y: Int): Boolean {
        return shotBoard.board[x][y] == Mark.NEW || shotBoard.board[x][y] == Mark.SHIP_DECK
    }

    fun placeShot(x:Int, y:Int) {
        if (shotBoard.board[x][y] == Mark.SHIP_DECK) {
            shotBoard.board[x][y] = Mark.DESTROYED
        } else {
            shotBoard.board[x][y] = Mark.SHOT
        }
    }
}