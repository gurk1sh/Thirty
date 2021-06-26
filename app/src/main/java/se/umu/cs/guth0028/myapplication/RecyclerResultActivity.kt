package se.umu.cs.guth0028.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_result.*

class RecyclerResultActivity : AppCompatActivity() { //Fetches extra from the intent and then passes the scores and gamemodes to the data adapter
    //Also calculates and sets final score at bottom of page
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_result)

        var gameNameList= intent.getSerializableExtra("GameName") as MutableList<String>
        var gameScoreList=intent.getSerializableExtra("GameScore") as MutableList<Int>
        resultsRecycler.layoutManager=LinearLayoutManager(this)
        resultsRecycler.adapter=RecyclerAdapter(gameNameList,gameScoreList)
        totalScoreTextView.text= "Final score: ${calculateTotalScore(gameScoreList)}"
    }

    private fun calculateTotalScore(gameScoreList: MutableList<*>) : Int {
        var totalScore = 0
        for (score in gameScoreList) {
            totalScore += score as Int
        }
        return totalScore
    }
}