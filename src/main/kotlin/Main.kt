import handlers.CoordinateConverter
import handlers.UiPrinter
import model.GameBoard
import model.Mark
import model.Player

fun main() {
    println("Введите количество кораблей")
    val shipsForPlayer = readln().toInt()
    println("Введите размер доски")
    val boardSize = readln().toInt()

    val player = Player(
        GameBoard(boardSize),
        GameBoard(boardSize),
        shipsForPlayer,
    )
    val coordinateConverter = CoordinateConverter()

    println("Расстановка кораблей игрока")
    for (i in 1..shipsForPlayer) {
        println("Введите координаты корабля в формате a1")
        val shipCoordinate = readln()
        val y = coordinateConverter.symbolToDigit(shipCoordinate[0])
        val x = shipCoordinate[1].digitToInt()
        player.placeShip(x, y)
    }
    val uiPrinter = UiPrinter(player.playerBoard, player.shotBoard)
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