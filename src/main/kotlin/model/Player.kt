package model

class Player(
    private val playerBoard: GameBoard,
    private val shotBoard: GameBoard,
) {
    private val ships: MutableList<Ship> = mutableListOf()

    fun canPlaceShip(ship: Ship): Boolean {
        for (coordinate in ship.coordinates) {
            if(!isInBorder(coordinate)) {
                return false
            }
            if (!isNewSection(coordinate)) {
                return false
            }
            if (hasNeigbourShip(coordinate)) {
                return false
            }
        }
        return true
    }

    fun placeShip(ship: Ship) {
        ships.add(ship)
        for (coordinate in ship.coordinates) {
            playerBoard.board[coordinate.x][coordinate.y] = Mark.SHIP_DECK
        }
    }

    fun isLooser(): Boolean {
        return playerBoard.board.flatten().none { it == Mark.SHIP_DECK }
    }

    fun isNotShoted(coordinate: Coordinate): Boolean {
        return shotBoard.board[coordinate.x][coordinate.y] == Mark.NEW || shotBoard.board[coordinate.x][coordinate.y] == Mark.SHIP_DECK
    }

    fun placeShot(x:Int, y:Int): Boolean {
        return if (shotBoard.board[x][y] == Mark.SHIP_DECK) {
            shotBoard.board[x][y] = Mark.DESTROYED
            true
        } else {
            shotBoard.board[x][y] = Mark.SHOT
            false
        }
    }

    fun isShipDestroyed(ship: Ship): Boolean {
        return ship.coordinates.all { playerBoard.board[it.x][it.y] == Mark.DESTROYED }
    }

    fun findShipByCoordinate(x: Int, y:Int): Ship? {
        return ships.firstOrNull { ship -> ship.coordinates.any { it.x == x && it.y == y} }
    }

    private fun isNewSection(coordinate: Coordinate): Boolean {
        return playerBoard.board[coordinate.x][coordinate.y] == Mark.NEW
    }

    private fun isInBorder(coordinate: Coordinate): Boolean {
        val nx = coordinate.x
        val ny = coordinate.y
        return nx in 0 until playerBoard.board.size && ny in 0 until playerBoard.board.size
    }

    private fun hasNeigbourShip(coordinate: Coordinate): Boolean {
        val directions = listOf(-1,0,1)
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
}