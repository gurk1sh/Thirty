package se.umu.cs.guth0028.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_result.*
import se.umu.cs.guth0028.myapplication.databinding.ActivityMainBinding
import se.umu.cs.guth0028.myapplication.databinding.ActivityRecyclerResultBinding

class RecyclerResultActivity : AppCompatActivity() { //Fetches extra from the intent and then passes the scores and gamemodes to the data adapter
    //Also calculates and sets final score at bottom of page
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var gameNameList= intent.getSerializableExtra("GameName") as MutableList<String>
        var gameScoreList=intent.getSerializableExtra("GameScore") as MutableList<Int>
        resultsRecycler.layoutManager=LinearLayoutManager(this)
        resultsRecycler.adapter=RecyclerAdapter(gameNameList,gameScoreList)
        totalScoreTextView.text= "Final score: ${calculateTotalScore(gameScoreList)}"

        binding.playAgainButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    private fun calculateTotalScore(gameScoreList: MutableList<*>) : Int {
        var totalScore = 0
        for (score in gameScoreList) {
            totalScore += score as Int
        }
        return totalScore
    }
}