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
        playerPlaceShip(uiPrinter, coordinateConverter, player)

        enemyPlaceShip(random, boardSize, enemy)
    }
    uiPrinter.printGameBoards(playerBoard, enemyBoard)

    while (gameContinue(player, enemy)) {
        playerPlaceShot(uiPrinter, coordinateConverter, player)
        uiPrinter.printGameBoards(playerBoard, enemyBoard)

        enemyPlaceShot(random, boardSize, enemy, uiPrinter)
        uiPrinter.printGameBoards(playerBoard, enemyBoard)
    }

    uiPrinter.gameOver()
    uiPrinter.printAllBoards(playerBoard, enemyBoard)
    if (player.isLooser()) {
        uiPrinter.printEnemyWins()
    } else {
        uiPrinter.printPlayerWin()
    }
}

private fun enemyPlaceShip(
    random: Random.Default,
    boardSize: Int,
    enemy: Player,
) {
    var x: Int
    var y: Int
    do {
        x = random.nextInt(boardSize)
        y = random.nextInt(boardSize)
    } while (!enemy.isNewSection(x, y))
    enemy.placeShip(x, y)
}

private fun enemyPlaceShot(
    random: Random.Default,
    boardSize: Int,
    enemy: Player,
    uiPrinter: UiPrinter,
) {
    var x: Int
    var y: Int
    do {
        x = random.nextInt(boardSize)
        y = random.nextInt(boardSize)
    } while (!enemy.isNotShoted(x, y))
    enemy.placeShot(x, y)
    uiPrinter.enemyShot(x, y)
}

private fun playerPlaceShot(
    uiPrinter: UiPrinter,
    coordinateConverter: CoordinateConverter,
    player: Player
) {
    var validInput = false
    while (!validInput) {
        uiPrinter.enterShot()
        val shipCoordinate = readln()
        if (coordinateConverter.isValidCoordinate(shipCoordinate)) {
            val y = coordinateConverter.symbolToDigit(shipCoordinate[0])
            val x = shipCoordinate[1].digitToInt()
            if (player.isNotShoted(x, y)) {
                player.placeShot(x, y)
                validInput = true
            } else {
                uiPrinter.alreadyShotedError()
            }
        } else {
            uiPrinter.wrongFormatError()
        }
    }
}

private fun playerPlaceShip(
    uiPrinter: UiPrinter,
    coordinateConverter: CoordinateConverter,
    player: Player
) {
    var validInput = false
    while (!validInput) {
        uiPrinter.enterCoordinateShip()
        val shipCoordinate = readln()
        if (coordinateConverter.isValidCoordinate(shipCoordinate)) {
            val y = coordinateConverter.symbolToDigit(shipCoordinate[0])
            val x = shipCoordinate[1].digitToInt()
            if (player.isNewSection(x, y)) {
                player.placeShip(x, y)
                validInput = true
            } else {
                uiPrinter.shipAlreadyPlacedError()
            }
        } else {
            uiPrinter.wrongFormatError()
        }
    }
}

fun gameContinue(player: Player, enemy: Player): Boolean {
    return !player.isLooser() && !enemy.isLooser()
}
