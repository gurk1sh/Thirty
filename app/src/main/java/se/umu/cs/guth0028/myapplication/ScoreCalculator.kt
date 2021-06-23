package se.umu.cs.guth0028.myapplication

class ScoreCalculator {
    companion object {
        fun calculateScore (selectedItem: String, listOfPairs: MutableList<Int>) : Int {
            var sum = 0
            var score = 0

            when (selectedItem) {
               "low" -> for (pair in listOfPairs) {
                   if (pair < 3) {
                       score = score + pair
                   }
               }

                "4" -> { for (pair in (listOfPairs)) {
                            sum += pair
                    }
                    if (sum == 4) {
                        score += 4
                    }
                }

                "5" -> { for (pair in (listOfPairs)) {
                    sum += pair
                }
                    if (sum == 5) {
                        score += 5
                    }
                }
                "6" -> { for (pair in (listOfPairs)) {
                    sum += pair
                }
                    if (sum == 6) {
                        score += 6
                    }
                }
                "7" -> { for (pair in (listOfPairs)) {
                    sum += pair
                }
                    if (sum == 7) {
                        score += 7
                    }
                }
                "8" -> { for (pair in (listOfPairs)) {
                    sum += pair
                }
                    if (sum == 8) {
                        score += 8
                    }
                }
                "9" -> { for (pair in (listOfPairs)) {
                    sum += pair
                }
                    if (sum == 9) {
                        score += 9
                    }
                }
                "10" -> { for (pair in (listOfPairs)) {
                    sum += pair
                }
                    if (sum == 10) {
                        score += 10
                    }
                }
                "11" -> { for (pair in (listOfPairs)) {
                    sum += pair
                }
                    if (sum == 11) {
                        score += 11
                    }
                }
                "12" -> { for (pair in (listOfPairs)) {
                    sum += pair
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