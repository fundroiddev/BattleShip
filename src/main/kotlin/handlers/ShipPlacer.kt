package handlers

import model.GameBoard
import model.Mark

class ShipPlacer(
    val playerBoard: GameBoard,
    val shotBoard: GameBoard,
) {

    fun placeShip(x: Int, y: Int) {
        playerBoard.board[x][y] = Mark.SHIP_DECK
    }

    fun placeShot(x: Int, y: Int) {
        shotBoard.board[x][y] = Mark.SHOOTED
    }
}