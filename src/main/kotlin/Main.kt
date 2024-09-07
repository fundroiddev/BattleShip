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
    uiPrinter.printGameBoards(playerBoard, enemyBoard)

    for (i in 1..shipsForPlayer) {
        var x: Int
        var y: Int
        do {
            x = random.nextInt(boardSize)
            y = random.nextInt(boardSize)
        } while (!enemy.isNewSection(x, y))
        enemy.placeShip(x, y)
    }

    while (gameContinue(player, enemy)) {
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
        uiPrinter.printGameBoards(playerBoard, enemyBoard)

        var x: Int
        var y: Int
        do {
            x = random.nextInt(boardSize)
            y = random.nextInt(boardSize)
        } while (!enemy.isNotShoted(x, y))
        enemy.placeShot(x, y)
        uiPrinter.enemyShot(x, y)
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

fun gameContinue(player: Player, enemy: Player): Boolean {
    return !player.isLooser() && !enemy.isLooser()
}
