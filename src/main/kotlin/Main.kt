import handlers.CoordinateConverter
import handlers.UiPrinter
import model.GameBoard
import model.Mark
import model.Player
import kotlin.random.Random

fun main() {
    println("Введите количество кораблей")
    val shipsForPlayer = readln().toInt()
    println("Введите размер доски")
    val boardSize = readln().toInt()

    val playerBoard = GameBoard(boardSize)

    val enemyBoard = GameBoard(boardSize)

    val player = Player(
        playerBoard,
        enemyBoard,
        shipsForPlayer,
    )

    val enemy = Player(
        enemyBoard,
        playerBoard,
        shipsForPlayer,
    )

    val uiPrinter = UiPrinter(player.playerBoard, player.shotBoard)
    uiPrinter.printGameBoards()

    val coordinateConverter = CoordinateConverter()

    println("Расстановка кораблей игрока")
    for (i in 1..shipsForPlayer) {
        println("Введите координаты корабля в формате a1")
        val shipCoordinate = readln()
        val y = coordinateConverter.symbolToDigit(shipCoordinate[0])
        val x = shipCoordinate[1].digitToInt()
        player.placeShip(x, y)
    }

    println("Расстановка кораблей противника")
    val random = Random.Default
    for (i in 1..shipsForPlayer) {
        var x = 0
        var y = 0
        do {
            x = random.nextInt(boardSize)
            y = random.nextInt(boardSize)
        } while (!enemy.isEmpty(x, y))
        enemy.placeShip(x,y)
    }
    uiPrinter.printGameBoards()
    while (!player.shotBoard.board.all { it.all { mark -> mark == Mark.SHOOTED } }) {
        println("Введите координаты выстрела в формате a1")
        val shipCoordinate = readln()
        val y = coordinateConverter.symbolToDigit(shipCoordinate[0])
        val x = shipCoordinate[1].digitToInt()
        player.placeShot(x, y)
        uiPrinter.printGameBoards()
    }
    println("Чистая победа")
}