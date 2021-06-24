package se.umu.cs.guth0028.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import se.umu.cs.guth0028.myapplication.databinding.ActivityMainBinding
import se.umu.cs.guth0028.myapplication.databinding.ActivityResultBinding

private const val GAME_RESULT_LIST = "se.umu.cs.guth0028.geoquiz.game_result_list"

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var gameNameList= intent.getSerializableExtra("GameName") as ArrayList<*>
        var gameScoreList=intent.getSerializableExtra("GameScore") as ArrayList<*>

        binding.toastButton.setOnClickListener {
               val gameModeTextViews = listOf (
                   binding.textView1,
                   binding.textView3,
                   binding.textView5,
                   binding.textView7,
                   binding.textView9,
                   binding.textView11,
                   binding.textView13,
                   binding.textView15,
                   binding.textView17,
                   binding.textView19
               )

                val scoreTextViews = listOf (
                    binding.textView2,
                    binding.textView4,
                    binding.textView6,
                    binding.textView8,
                    binding.textView10,
                    binding.textView12,
                    binding.textView14,
                    binding.textView16,
                    binding.textView18,
                    binding.textView20
                )

                setTexts(gameModeTextViews, scoreTextViews, gameNameList, gameScoreList)

                binding.totalScore.text = calculateTotalScore(gameScoreList).toString()

        }

    }

    private fun setTexts(gameModeTextViews: List<TextView>, scoreTextViews: List<TextView>, gameNameList: ArrayList<*>, gameScoreList: ArrayList<*>) {
        /*
        Method that sets all textViews to the different gameModes and their respective score
        */
        var gameModeCounter = 0
        var scoreCounter = 0

        for (textView in gameModeTextViews) {
            textView.text=gameNameList[gameModeCounter].toString()
            gameModeCounter = gameModeCounter + 1
        }

        for (textView in scoreTextViews) {
            textView.text=gameScoreList[scoreCounter].toString()
            scoreCounter = scoreCounter + 1
        }
    }

    private fun calculateTotalScore(gameScoreList: ArrayList<*>) : Int {
        var totalScore = 0
        for (score in gameScoreList) {
           totalScore += score as Int
        }
        return totalScore
    }
}