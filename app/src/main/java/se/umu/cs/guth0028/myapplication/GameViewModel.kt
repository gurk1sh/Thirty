package se.umu.cs.guth0028.myapplication

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    var listOfModes = arrayListOf<String>()
    var listOfResult = arrayListOf<Int>()
    var listOfPairs = mutableListOf<Int>()
    var dicesPaired: Boolean = false
    var round = 0
    var throws = 0
    var score = 0
    val gameRoundThrows = 3

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

    val gameRounds = gameModes.size //game consists of a round for each gamemode

    var greyDices = arrayListOf(
        Dice(1, false, false,R.drawable.grey1),
        Dice(2, false, false,R.drawable.grey2),
        Dice(3, false, false,R.drawable.grey3),
        Dice(4, false, false,R.drawable.grey4),
        Dice(5, false, false,R.drawable.grey5),
        Dice(6, false, false,R.drawable.grey6),
    )

    var redDices = arrayListOf(
        Dice(1, false, false,R.drawable.red1),
        Dice(2, false, false,R.drawable.red2),
        Dice(3, false, false,R.drawable.red3),
        Dice(4, false, false,R.drawable.red4),
        Dice(5, false, false,R.drawable.red5),
        Dice(6, false, false,R.drawable.red6),
    )
}