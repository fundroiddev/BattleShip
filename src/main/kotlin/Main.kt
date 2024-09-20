import handlers.CoordinateConverter
import model.Coordinate
import model.GameBoard
import model.Player
import model.Ship
import ui.UiPrinter
import kotlin.random.Random

fun main() {
    val uiPrinter = UiPrinter()

    uiPrinter.enterGameboardSize()
    val boardSize = readln().toInt()
    val playerBoard = GameBoard(size = boardSize)
    val enemyBoard = GameBoard(size = boardSize)

    val player = Player(
        playerBoard,
        enemyBoard
    )

    val enemy = Player(
        enemyBoard,
        playerBoard
    )

    val random = Random.Default
    val coordinateConverter = CoordinateConverter(boardSize)

    val fleet = listOf(1, 2)

    uiPrinter.printGameBoards(playerBoard, enemyBoard)
    uiPrinter.placeShips()
    fleet.forEach { ship ->
        playerPlaceShip(uiPrinter, coordinateConverter, player, ship)
        uiPrinter.printGameBoards(playerBoard, enemyBoard)
        enemyPlaceShip(random, boardSize, enemy, ship)
    }
    uiPrinter.printGameBoards(playerBoard, enemyBoard)

    while (gameContinue(player, enemy)) {
        playerPlaceShot(uiPrinter, coordinateConverter, player, enemy)
        uiPrinter.printGameBoards(playerBoard, enemyBoard)

        enemyPlaceShot(random, boardSize, enemy, player, uiPrinter)
        uiPrinter.printGameBoards(playerBoard, enemyBoard)
    }

    uiPrinter.gameOver()
    uiPrinter.printGameBoards(playerBoard, enemyBoard, true)
    if (player.isLooser()) {
        uiPrinter.printEnemyWins()
    } else {
        uiPrinter.printPlayerWin()
    }
}

private fun playerPlaceShip(
    uiPrinter: UiPrinter,
    coordinateConverter: CoordinateConverter,
    player: Player,
    shipSize: Int,
) {
    var validInput = false
    while (!validInput) {
        uiPrinter.placeShip(shipSize)
        val startCoordinate = readln()
        if (!coordinateConverter.isValidCoordinate(startCoordinate)) {
            uiPrinter.wrongFormatError()
            continue
        }

        val startX = startCoordinate[1].digitToInt()
        val startY = coordinateConverter.symbolToDigit(startCoordinate[0])

        val coordinates = mutableListOf<Coordinate>()

        if (shipSize == 1) {
            coordinates.add(Coordinate(startX, startY))
        } else {
            uiPrinter.enterDirection()
            val direction = readln()

            createCoordinatesForShip(shipSize, direction == HORIZONTAL, startX, startY, coordinates)
        }

        val ship = Ship(size = shipSize, coordinates = coordinates)

        if (player.canPlaceShip(ship)) {
            player.placeShip(ship)
            validInput = true
        } else {
            uiPrinter.shipPlacedError()
        }
    }
}

private fun createCoordinatesForShip(
    shipSize: Int,
    isHorizontal: Boolean,
    startX: Int,
    startY: Int,
    coordinates: MutableList<Coordinate>
) {
    for (i in 0 until shipSize) {
        val coordinate = if (isHorizontal) {
            Coordinate(startX, startY + i)
        } else {
            Coordinate(startX + i, startY)
        }
        coordinates.add(coordinate)
    }
}

private fun enemyPlaceShip(
    random: Random.Default,
    boardSize: Int,
    enemy: Player,
    shipSize: Int,
) {
    var placed = false
    while (!placed) {
        val x = random.nextInt(boardSize)
        val y = random.nextInt(boardSize)
        val isHorizontal = random.nextBoolean()
        val coordinates = mutableListOf<Coordinate>()

        createCoordinatesForShip(shipSize, isHorizontal, x, y, coordinates)

        val ship = Ship(size = shipSize, coordinates = coordinates)

        if (enemy.canPlaceShip(ship)) {
            enemy.placeShip(ship)
            placed = true
        }
    }
}

private fun enemyPlaceShot(
    random: Random.Default,
    boardSize: Int,
    enemy: Player,
    player: Player,
    uiPrinter: UiPrinter,
) {
    var x: Int
    var y: Int

    do {
        x = random.nextInt(boardSize)
        y = random.nextInt(boardSize)
    } while (!enemy.isNotShoted(Coordinate(x, y)))

    val isHit = enemy.placeShot(x,y)
    if (isHit) {
        val ship = player.findShipByCoordinate(x,y)
        if (ship != null && player.isShipDestroyed(ship)) {
            uiPrinter.yourShipDestroyed()
        } else {
            uiPrinter.yourShipHit()
        }
    }
}

private fun playerPlaceShot(
    uiPrinter: UiPrinter,
    coordinateConverter: CoordinateConverter,
    player: Player,
    enemy: Player
) {
    var validInput = false
    while (!validInput) {
        uiPrinter.enterShot()
        val shotCoordinate = readln()
        if (coordinateConverter.isValidCoordinate(shotCoordinate)) {
            val y = coordinateConverter.symbolToDigit(shotCoordinate[0])
            val x = shotCoordinate[1].digitToInt()

            if (player.isNotShoted(Coordinate(x,y))) {
                val isHit = player.placeShot(x,y)
                if (isHit) {
                    val ship = enemy.findShipByCoordinate(x, y)
                    if (ship != null && enemy.isShipDestroyed(ship)) {
                        uiPrinter.shipDestroyed()
                    } else {
                        uiPrinter.shipHit()
                    }
                }
                validInput = true
            } else {
                uiPrinter.alreadyShotedError()
            }
        } else {
            uiPrinter.wrongFormatError()
        }
    }
}

fun gameContinue(player: Player, enemy: Player): Boolean {
    return !player.isLooser() && !enemy.isLooser()
}

private const val HORIZONTAL = "h"