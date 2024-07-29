package handlers

class CoordinateConverter {

    fun symbolToDigit(symbol: Char): Int {
        return when(symbol.lowercaseChar()) {
            'a' -> 0
            'b' -> 1
            'c' -> 2
            'd' -> 3
            'e' -> 4
            'f' -> 5
            'g' -> 6
            'h' -> 7
            else -> throw IllegalArgumentException("There is no this letter at coordinate field")
        }   
    }
}