import handlers.CoordinateConverter
import handlers.UiPrinter
import model.GameBoard
import model.Player
import kotlin.random.Random

fun main() {
    val uiPrinter = UiPrinter()
    uiPrinter.enterShipNumber()
    val shipsForPlayer = readln().toInt()

    uiPrinter.enterGameboardSize()
    val boardSize = readln().toInt()
    val playerBoard = GameBoard(boardSize)
    val enemyBoard = GameBoard(boardSize)

    val player = Player(
        playerBoard,
        enemyBoard,
    )

    val enemy = Player(
        enemyBoard,
        playerBoard,
    )

    val coordinateConverter = CoordinateConverter(boardSize)
    val random = Random.Default

    uiPrinter.placeShips()
    uiPrinter.printGameBoards(playerBoard, enemyBoard)
    placePlayerShips(shipsForPlayer, uiPrinter, coordinateConverter, player)

    placeEnemyShips(shipsForPlayer, random, boardSize, enemy)
    uiPrinter.printGameBoards(playerBoard, enemyBoard)

    while (gameOver(player, enemy)) {
        playerShot(uiPrinter, coordinateConverter, player)
        enemyShot(random, boardSize, enemy, uiPrinter)
        uiPrinter.printGameBoards(playerBoard, enemyBoard)
    }

    gameOver(uiPrinter, playerBoard, enemyBoard, player)
}

private fun gameOver(
    uiPrinter: UiPrinter,
    playerBoard: GameBoard,
    enemyBoard: GameBoard,
    player: Player
) {
    uiPrinter.gameOver()
    uiPrinter.printAllBoards(playerBoard, enemyBoard)
    if (player.isLooser()) {
        uiPrinter.printEnemyWin()
    } else {
        uiPrinter.printPlayerWin()
    }
}

private fun gameOver(player: Player, enemy: Player) = !player.isLooser() && !enemy.isLooser()

private fun enemyShot(
    random: Random.Default,
    boardSize: Int,
    enemy: Player,
    uiPrinter: UiPrinter
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

private fun playerShot(
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
                uiPrinter.alreadyShoted()
            }
        } else {
            uiPrinter.wrongFormatError()
        }
    }
}

private fun placeEnemyShips(
    shipsForPlayer: Int,
    random: Random.Default,
    boardSize: Int,
    enemy: Player
) {
    for (i in 1..shipsForPlayer) {
        var x: Int
        var y: Int
        do {
            x = random.nextInt(boardSize)
            y = random.nextInt(boardSize)
        } while (!enemy.isEmpty(x, y))
        enemy.placeShip(x, y)
    }
}

private fun placePlayerShips(
    shipsForPlayer: Int,
    uiPrinter: UiPrinter,
    coordinateConverter: CoordinateConverter,
    player: Player
) {
    for (i in 1..shipsForPlayer) {
        var validInput = false
        while (!validInput) {
            uiPrinter.enterCoordinatesShip()
            val shipCoordinate = readln()
            if (coordinateConverter.isValidCoordinate(shipCoordinate)) {
                val y = coordinateConverter.symbolToDigit(shipCoordinate[0])
                val x = shipCoordinate[1].digitToInt()
                if (player.isEmpty(x, y)) {
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
}