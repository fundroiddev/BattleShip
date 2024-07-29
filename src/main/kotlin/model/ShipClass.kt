package model

enum class ShipClass(val size: Int) {
    CARRIER(4),
    CRUISER(3),
    DESTROYER(2),
    BOAT(1),
}