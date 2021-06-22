package se.umu.cs.guth0028.myapplication

class ScoreCalculator {
    companion object {
        fun calculateScore (selectedItem: String, listOfPairs: MutableList<Int>) : Int {
            var score = 0

            when (selectedItem) {
               "low" -> for (pair in listOfPairs) {
                   if (pair < 3) {
                       score = score + pair
                   }
               }

                "4" -> for (pair in getPairs(listOfPairs)) {
                        if (pair == 4) {
                            score += pair
                        }
                    }

                "5" -> for (pair in getPairs(listOfPairs)) {
                    if (pair == 5) {
                        score += pair
                    }
                }
                "6" -> for (pair in getPairs(listOfPairs)) {
                    if (pair == 6) {
                        score += pair
                    }
                }
                "7" -> for (pair in getPairs(listOfPairs)) {
                    if (pair == 7) {
                        score += pair
                    }
                }
                "8" -> for (pair in getPairs(listOfPairs)) {
                    if (pair == 8) {
                        score += pair
                    }
                }
                "9" -> for (pair in getPairs(listOfPairs)) {
                    if (pair == 9) {
                        score += pair
                    }
                }
                "10" -> for (pair in getPairs(listOfPairs)) {
                    if (pair == 10) {
                        score += pair
                    }
                }
                "11" -> for (pair in getPairs(listOfPairs)) {
                    if (pair == 11) {
                        score += pair
                    }
                }
                "12" -> for (pair in getPairs(listOfPairs)) {
                    if (pair == 12) {
                        score += pair
                    }
                }
            }
            return score
        }
        private fun getPairs(listOfPairs: MutableList<Int>) : MutableList<Int> {
            var calculatedPairs = mutableListOf<Int>()
            var counter = 0
            val firstPair = listOfPairs[counter] + listOfPairs[counter+1]
            calculatedPairs.add(firstPair)
            counter += 2
            val secondPair = listOfPairs[counter] + listOfPairs[counter+1]
            calculatedPairs.add(secondPair)
            counter += 2
            val thirdPair = listOfPairs[counter] + listOfPairs[counter+1]
            calculatedPairs.add(thirdPair)
            return calculatedPairs
        }
    }
}