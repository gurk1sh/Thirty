package se.umu.cs.guth0028.myapplication

import android.util.Log

class ScoreCalculator() {
    companion object {
        fun calculateScore (selectedItem: String, listOfPairs: MutableList<Int>) : Int {
            var score = 0
            when (selectedItem) {
               "low" -> for (pair in listOfPairs) {
                   if (pair < 3) {
                       score = score + pair
                   }
               }

                "4" -> for (i in 0..3) {
                    if (listOfPairs[i] + listOfPairs[i+1] == 4) {
                        score = score + 4
                    }
                }
                "5" -> score = 3
                "6" -> score = 4
                "7" -> score = 5
                "8" -> score = 6
                "9" -> score = 7
                "10" -> score = 8
                "11" -> score = 9
                "12" -> score = 10
            }
            return score
        }
    }
}