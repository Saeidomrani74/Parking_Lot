package parking

data class ParkingLot(val size: Int) {
    data class Car(val plate: String, val color: String) {
        override fun toString() = "$plate $color"
    }

    data class Spot(val spotNumber: Int, val isFree: Boolean, val car: Car?) {
        override fun toString() = "$spotNumber $car"
    }

    private val parkingData = Array(size) { Spot(it + 1, true, null) }

    init {
        if (size != 0) println("Created a parking lot with $size spots.")
    }

    fun park(car: Car) {
        if (size == 0) {
            println("Sorry, a parking lot has not been created.")
            return
        }
        val firstFree = parkingData.firstOrNull { it.isFree }
        if (firstFree != null) {
            parkingData[firstFree.spotNumber - 1] = Spot(firstFree.spotNumber, false, car)
            println(car.color + " car parked in spot " + firstFree.spotNumber + ".")
        } else
            println("Sorry, the parking lot is full.")
    }

    fun leave(spotNumber: Int) {
        if (size == 0) {
            println("Sorry, a parking lot has not been created.")
            return
        }
        if (parkingData[spotNumber - 1].isFree)
            println("There is no car in spot $spotNumber.")
        else {
            println("Spot $spotNumber is free.")
            parkingData[spotNumber - 1] = Spot(spotNumber, true, null)
        }
    }

    override fun toString(): String {
        val str = parkingData.filter { !it.isFree }.joinToString("\n") { it.toString() }
        return when {
            size == 0 -> "Sorry, a parking lot has not been created."
            str.isEmpty() -> "Parking lot is empty."
            else -> str
        }
    }

    fun sbc(color: String) {
        val str = parkingData.filter { it.car?.color?.contains(color,true) ?: false }.joinToString(", ") { it.spotNumber.toString() }
        println( when {
            size == 0 -> "Sorry, a parking lot has not been created."
            str.isEmpty() -> "No cars with color $color were found."
            else -> str
        })
    }

    fun sbr(reg: String) {
        val str = parkingData.filter { it.car?.plate == reg }.joinToString(", ") { it.spotNumber.toString() }
        println( when {
            size == 0 -> "Sorry, a parking lot has not been created."
            str.isEmpty() -> "No cars with registration number $reg were found."
            else -> str
        })
    }

    fun rbc(color: String) {
        val str = parkingData.filter { it.car?.color?.contains(color,true) ?: false }.joinToString(", ") { it.car?.plate.toString() }
        println( when {
            size == 0 -> "Sorry, a parking lot has not been created."
            str.isEmpty() -> "No cars with color $color were found."
            else -> str
        })
    }
}

fun main() {
    var parkingLot = ParkingLot(0)
    while (true) {
        val command = readLine()!!.split(" ")
        when (command[0]) {
            "create"        -> parkingLot = ParkingLot(command[1].toInt())
            "park"          -> parkingLot.park(ParkingLot.Car(command[1], command[2]))
            "leave"         -> parkingLot.leave(command[1].toInt())
            "spot_by_color" -> parkingLot.sbc(command[1])
            "spot_by_reg"   -> parkingLot.sbr(command[1])
            "reg_by_color"  -> parkingLot.rbc(command[1])
            "status"        -> println(parkingLot)
            "exit" -> return
        }
    }
}