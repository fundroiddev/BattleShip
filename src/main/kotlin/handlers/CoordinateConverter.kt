package handlers

class CoordinateConverter(private val boardSize: Int) {

    fun symbolToDigit(symbol: Char): Int {
        val lowerSymbol = symbol.lowercaseChar()
        val digit = lowerSymbol - 'a'
        if (digit in 0 until boardSize) {
            return digit
        } else {
            throw IllegalArgumentException("Некорректная буква : $symbol. " +
                    "Введите букву от 'a' до '${'a' + boardSize - 1}'.")
        }
    }

    fun isValidCoordinate(shipCoordinate: String): Boolean {
        if (shipCoordinate.length != 2) return false
        val symbol = shipCoordinate[0].lowercaseChar()
        val digit = shipCoordinate[1].digitToIntOrNull() ?: return false
        return symbol in 'a' until 'a' + boardSize && digit in 0 until boardSize
    }
}