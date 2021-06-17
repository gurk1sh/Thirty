package se.umu.cs.guth0028.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
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

        binding.toastButton.setOnClickListener {
            val gameResultList = intent.getParcelableArrayListExtra<GameResult>(GAME_RESULT_LIST)
            if (gameResultList != null) {
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

                setTexts(gameModeTextViews, scoreTextViews, gameResultList)

            }
        }

    }

    private fun setTexts(gameModeTextViews: List<TextView>, scoreTextViews: List<TextView>, scoreList: ArrayList<GameResult>) {
        /*
        Method for setting all textViews to the different gameModes and their respective score
        */

        var newList = scoreList
        var gameModeCounter = 0
        var scoreSum = 0
        for (textView in gameModeTextViews) { //sets the left textviews with the gameModes
            textView.text=scoreList[gameModeCounter].gameName
            gameModeCounter = gameModeCounter + 1
        }

        for (GameResult in newList) { //crappy idiot kotlin wont work
            for (Dice in GameResult.diceSet) {
                Toast.makeText(this, "värde: ${Dice.value}", Toast.LENGTH_SHORT).show()
                scoreSum = scoreSum + Dice.value
                Toast.makeText(this, "scoreSum är nu: ${scoreSum}", Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(this, "scoreSum: ${scoreSum}", Toast.LENGTH_SHORT).show()
            scoreSum = 0
            Toast.makeText(this, "resettad scoreSum: ${scoreSum}", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newIntent(packageContext: Context, gameResultList: List<GameResult>): Intent {
            return Intent(packageContext, ResultActivity::class.java).apply {
                putParcelableArrayListExtra(GAME_RESULT_LIST, gameResultList as ArrayList<GameResult>)
            }
        }
    }
}