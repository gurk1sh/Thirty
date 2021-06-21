package se.umu.cs.guth0028.myapplication

class Game {
    var round = 0
    var throws = 0
    var score = 0
    val gameRoundThrows = 3


    /*var gameModes = mutableListOf( //testing purposes only
        "low",
    )*/

    var gameModes = mutableListOf(
        "low",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12"
    )

    val gameRounds = gameModes.size

    val greyDices = listOf(
        Dice(1, false, false, R.drawable.grey1),
        Dice(2, false, false, R.drawable.grey2),
        Dice(3, false, false,R.drawable.grey3),
        Dice(4, false, false,R.drawable.grey4),
        Dice(5, false, false,R.drawable.grey5),
        Dice(6, false, false,R.drawable.grey6),
    )

    val redDices = listOf(
        Dice(1, false, false,R.drawable.red1),
        Dice(2, false, false,R.drawable.red2),
        Dice(3, false, false, R.drawable.red3),
        Dice(4, false, false,R.drawable.red4),
        Dice(5, false, false,R.drawable.red5),
        Dice(6, false, false,R.drawable.red6),
    )
}

