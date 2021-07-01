package se.umu.cs.guth0028.myapplication

class ScoreCalculator {
    companion object {
        fun calculateScore (selectedItem: String, listOfPairs: MutableList<Dice>) : Int {

            /*
            This class takes a list of "pairs" consisting of 1-6 different values, for example 1,3,4
            Then it calculates the score based on a gamemode, (gamemode 8 with values 1,3,4 will result in score = 8).
            A gameround can lead to this class being used more than once because the user can submit a maximum of 6 different "pairs" (one for each dice)
            */

            var sum = 0
            var score = 0

            when (selectedItem) {
               "low" -> for (pair in listOfPairs) {
                   if (pair.value <= 3) {
                       score += pair.value
                   }
               }

                "4" -> { for (pair in (listOfPairs)) {
                            sum += pair.value
                    }
                    if (sum == 4) {
                        score += 4
                    }
                }

                "5" -> { for (pair in (listOfPairs)) {
                    sum += pair.value
                }
                    if (sum == 5) {
                        score += 5
                    }
                }
                "6" -> { for (pair in (listOfPairs)) {
                    sum += pair.value
                }
                    if (sum == 6) {
                        score += 6
                    }
                }
                "7" -> { for (pair in (listOfPairs)) {
                    sum += pair.value
                }
                    if (sum == 7) {
                        score += 7
                    }
                }
                "8" -> { for (pair in (listOfPairs)) {
                    sum += pair.value
                }
                    if (sum == 8) {
                        score += 8
                    }
                }
                "9" -> { for (pair in (listOfPairs)) {
                    sum += pair.value
                }
                    if (sum == 9) {
                        score += 9
                    }
                }
                "10" -> { for (pair in (listOfPairs)) {
                    sum += pair.value
                }
                    if (sum == 10) {
                        score += 10
                    }
                }
                "11" -> { for (pair in (listOfPairs)) {
                    sum += pair.value
                }
                    if (sum == 11) {
                        score += 11
                    }
                }
                "12" -> { for (pair in (listOfPairs)) {
                    sum += pair.value
                }
                    if (sum == 12) {
                        score += 12
                    }
                }
            }
            return score
        }
    }
}