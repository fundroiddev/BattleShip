import handlers.CoordinateConverter
import model.Coordinate
import model.GameBoard
import model.Player
import model.Ship
import ui.UiPrinter
import kotlin.random.Random
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction1

fun main() {
    val uiPrinter = UiPrinter()
    val fleet = listOf(1, 1, 1, 1, 2, 2, 2, 3, 3, 4)
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

    uiPrinter.printGameBoards(playerBoard, enemyBoard)
    uiPrinter.placeShips()

    for (shipSize in fleet) {
        playerPlaceShip(
            uiPrinter,
            coordinateConverter,
            player::canPlaceShip,
            player::placeShip,
            uiPrinter::shipAlreadyPlacedError,
            shipSize,
        )

        enemyPlaceShip(random, boardSize, enemy::isNewSection, enemy::placeShip)
    }

    uiPrinter.printGameBoards(playerBoard, enemyBoard)
    uiPrinter.placeShots()

    while (gameContinue(player, enemy)) {
        playerPlaceMark(
            uiPrinter,
            coordinateConverter,
            player::isNotShoted,
            player::placeShot,
            uiPrinter::alreadyShotedError
        )
        uiPrinter.printGameBoards(playerBoard, enemyBoard)

        enemyPlaceMark(random, boardSize, enemy::isNotShoted, enemy::placeShot)

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

fun enemyPlaceShip(
    random: Random.Default,
    boardSize: Int,
    checkCoordinateMethod: (ship: Ship) -> Boolean,
    placeShipMethod: (ship: Ship) -> Unit,
    shipSize: Int,
) {
    var validInput = false
    while (!validInput) {
        val startX = random.nextInt(boardSize)
        val startY = random.nextInt(boardSize)
        val isHorizontal = random.nextBoolean()

        val coordinates = mutableListOf<Coordinate>()

        for (i in 0 until shipSize) {
            val coordinate = if (isHorizontal) {
                Coordinate(startX, startY + i)
            } else {
                Coordinate(startX + i, startY)
            }
            coordinates.add(coordinate)
        }

        val ship = Ship(size = shipSize, coordinates = coordinates)
        if (checkCoordinateMethod(ship)) {
            placeShipMethod(ship)
            validInput = true
        }
    }
}

fun playerPlaceShip(
    uiPrinter: UiPrinter,
    coordinateConverter: CoordinateConverter,
    checkCoordinateMethod: (ship: Ship) -> Boolean,
    placeMarkMethod: (ship: Ship) -> Unit,
    errorMethod: () -> Unit,
    shipSize: Int
) {
    val validInput = false
    while (!validInput) {
        uiPrinter.enterCoordinates()
        val startCoordinate = readln()

        uiPrinter.enterShipdirection()
        val direction = readln()

        val startX = startCoordinate[0].digitToInt()
        val startY = coordinateConverter.symbolToDigit(startCoordinate[0])
        val coordinates = mutableListOf<Coordinate>()
        for (i in 0 until shipSize) {
            val coordinate = if (direction == "h") {
                Coordinate(startX, startY + i)
            } else {
                Coordinate(startX + i, startY)
            }
            coordinates.add(coordinate)
        }

        val ship = Ship(size = shipSize, coordinates = coordinates)
        if (checkCoordinateMethod(ship)) {
            placeMarkMethod(ship)
            validInput = true
        } else {
            errorMethod()
        }
    }
}

private fun enemyPlaceMark(
    random: Random.Default,
    boardSize: Int,
    checkCoordinateMethod: (coordinate: Coordinate) -> Boolean,
    placeMarkMethod: (coordinate: Coordinate) -> Unit,
) {
    var x: Int
    var y: Int
    do {
        x = random.nextInt(boardSize)
        y = random.nextInt(boardSize)
    } while (!checkCoordinateMethod(Coordinate(x, y)))
    placeMarkMethod(Coordinate(x, y))
}

private fun playerPlaceMark(
    uiPrinter: UiPrinter,
    coordinateConverter: CoordinateConverter,
    checkCoordinateMethod: (coordinate: Coordinate) -> Boolean,
    placeMarkMethod: (coordinate: Coordinate) -> Unit,
    errorMethod: () -> Unit,
) {
    var validInput = false
    while (!validInput) {
        uiPrinter.enterCoordinates()
        val shipCoordinate = readln()
        if (coordinateConverter.isValidCoordinate(shipCoordinate)) {
            val y = coordinateConverter.symbolToDigit(shipCoordinate[0])
            val x = shipCoordinate[1].digitToInt()
            if (checkCoordinateMethod(Coordinate(x, y))) {
                placeMarkMethod(Coordinate(x, y))
                validInput = true
            } else {
                errorMethod()
            }
        } else {
            uiPrinter.wrongFormatError()
        }
    }
}

fun gameContinue(player: Player, enemy: Player): Boolean {
    return !player.isLooser() && !enemy.isLooser()
}
