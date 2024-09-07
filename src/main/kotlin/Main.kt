import handlers.CoordinateConverter
import model.GameBoard
import model.Player
import ui.UiPrinter
import kotlin.random.Random

fun main() {
    val uiPrinter = UiPrinter()
    uiPrinter.enterShipNumber()
    val shipsForPlayer = readln().toInt()

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

    for (i in 1..shipsForPlayer) {
        playerPlaceMark(
            uiPrinter,
            coordinateConverter,
            player::isNewSection,
            player::placeShip,
            uiPrinter::shipAlreadyPlacedError
        )

        enemyPlaceMark(random, boardSize, enemy::isNewSection, enemy::placeShip)
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

private fun enemyPlaceMark(
    random: Random.Default,
    boardSize: Int,
    checkCoordinateMethod: (x: Int, y: Int) -> Boolean,
    placeMarkMethod: (x: Int, y: Int) -> Unit,
) {
    var x: Int
    var y: Int
    do {
        x = random.nextInt(boardSize)
        y = random.nextInt(boardSize)
    } while (!checkCoordinateMethod(x, y))
    placeMarkMethod(x, y)
}

private fun playerPlaceMark(
    uiPrinter: UiPrinter,
    coordinateConverter: CoordinateConverter,
    checkCoordinateMethod: (x: Int, y: Int) -> Boolean,
    placeMarkMethod: (x: Int, y: Int) -> Unit,
    errorMethod: () -> Unit,
) {
    var validInput = false
    while (!validInput) {
        uiPrinter.enterCoordinates()
        val shipCoordinate = readln()
        if (coordinateConverter.isValidCoordinate(shipCoordinate)) {
            val y = coordinateConverter.symbolToDigit(shipCoordinate[0])
            val x = shipCoordinate[1].digitToInt()
            if (checkCoordinateMethod(x, y)) {
                placeMarkMethod(x, y)
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
