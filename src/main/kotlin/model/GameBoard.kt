package model

class GameBoard(private val size: Int) {

    val board: MutableList<MutableList<Mark>> = MutableList(size) { MutableList(size) { Mark.NEW } }
}