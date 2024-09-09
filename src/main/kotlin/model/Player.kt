package model

class Player(
    private val playerBoard: GameBoard,
    private val shotBoard: GameBoard,
) {

    val ships: MutableList<Ship> = mutableListOf()

    fun canPlaceShip(ship: Ship): Boolean {
        for (coordinate in ship.coordinates) {
            if (!isNewSection(coordinate)) {
                return false
            }
            if (hasNeighborShip(coordinate)) {
                return false
            }
        }
        return true
    }

    private fun hasNeighborShip(coordinate: Coordinate): Boolean {
        val directions = listOf(-1, 0 , 1)
        for (dx in directions) {
            for (dy in directions) {
                val nx = coordinate.x + dx
                val ny = coordinate.y + dy
                if (nx in 0 until playerBoard.board.size && ny in 0 until playerBoard.board.size) {
                    if (playerBoard.board[nx][ny] == Mark.SHIP_DECK) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun placeShip(ship: Ship) {
        ships.add(ship)
        for (coordinate in ship.coordinates) {
            playerBoard.board[coordinate.x][coordinate.y] = Mark.SHIP_DECK
        }
    }

    fun isNewSection(coordinate: Coordinate): Boolean {
        return playerBoard.board[coordinate.x][coordinate.y] == Mark.NEW
    }

    fun isLooser(): Boolean {
        return playerBoard.board.flatten().none { it == Mark.SHIP_DECK }
    }

    fun isNotShoted(coordinate: Coordinate): Boolean {
        return shotBoard.board[coordinate.x][coordinate.y] == Mark.NEW ||
                shotBoard.board[coordinate.x][coordinate.y] == Mark.SHIP_DECK
    }

    fun placeShot(coordinate: Coordinate) {
        if (shotBoard.board[coordinate.x][coordinate.y] == Mark.SHIP_DECK) {
            shotBoard.board[coordinate.x][coordinate.y] = Mark.DESTROYED
        } else {
            shotBoard.board[coordinate.x][coordinate.y] = Mark.SHOT
        }
    }
}