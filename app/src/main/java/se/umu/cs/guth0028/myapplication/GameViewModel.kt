package se.umu.cs.guth0028.myapplication

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    var listOfModes = arrayListOf<String>()
    var listOfResult = arrayListOf<Int>()
    var listOfPairs = arrayListOf<Dice>()
    var dicesPaired: Boolean = false
    var round = 0
    var throws = 0
    var score = 0
    val gameRoundThrows = 3

    var gameModes = arrayListOf(
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

    var whiteDices = arrayListOf(
        Dice(1, false, false, R.drawable.white1, false),
        Dice(2, false, false, R.drawable.white2,false),
        Dice(3, false, false, R.drawable.white3,false),
        Dice(4, false, false, R.drawable.white4,false),
        Dice(5, false, false, R.drawable.white5,false),
        Dice(6, false, false, R.drawable.white6,false),
    )

    var greyDices = arrayListOf(
        Dice(1, false, false,R.drawable.grey1,false),
        Dice(2, false, false,R.drawable.grey2,false),
        Dice(3, false, false,R.drawable.grey3,false),
        Dice(4, false, false,R.drawable.grey4,false),
        Dice(5, false, false,R.drawable.grey5,false),
        Dice(6, false, false,R.drawable.grey6,false),
    )

    var redDices = arrayListOf(
        Dice(1, false, false,R.drawable.red1,false),
        Dice(2, false, false,R.drawable.red2,false),
        Dice(3, false, false,R.drawable.red3,false),
        Dice(4, false, false,R.drawable.red4,false),
        Dice(5, false, false,R.drawable.red5,false),
        Dice(6, false, false,R.drawable.red6,false),
    )
}