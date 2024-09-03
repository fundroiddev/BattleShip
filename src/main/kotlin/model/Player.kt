package model

class Player(
    val playerBoard: GameBoard,
    val shotBoard: GameBoard,
) {

    fun placeShot(x: Int, y: Int) {
        if (shotBoard.board[x][y] == Mark.SHIP_DECK) {
            shotBoard.board[x][y] = Mark.DESTROYED
        } else {
            shotBoard.board[x][y] = Mark.SHOOTED
        }
    }

    fun placeShip(x: Int, y: Int) {
        playerBoard.board[x][y] = Mark.SHIP_DECK
    }

    fun isEmpty(x: Int, y: Int): Boolean {
        return playerBoard.board[x][y] == Mark.NEW
    }

    fun isNotShoted(x: Int, y: Int): Boolean {
        return shotBoard.board[x][y] == Mark.NEW || shotBoard.board[x][y] == Mark.SHIP_DECK
    }

    fun isLooser(): Boolean {
        return playerBoard.board.all { markList -> markList.all { mark -> mark != Mark.SHIP_DECK } }
    }
}