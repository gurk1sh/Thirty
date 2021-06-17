package se.umu.cs.guth0028.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import se.umu.cs.guth0028.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val game = Game()

    private val setOfDices = mutableListOf<Dice>()

    private var resultList = mutableListOf<GameResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spinner.onItemSelectedListener = this

        updateSpinner(spinner, game.gameModes)

        initDiceOnClickListeners(binding)

        binding.button.setOnClickListener {
            if (game.round < game.gameRounds){
                button.text = "THROW"
                submitButton.isEnabled = false
                for (dice in game.dices) {
                    if (!dice.isSaved) {
                        dice.throwDice()
                    }
                    if (game.throws == (game.gameRoundThrows-1)) {
                        setOfDices.add(dice)
                    }
                }

                setDiceImages(game.dices)

                game.throws = game.throws + 1

                if (game.throws == game.gameRoundThrows) {
                    submitButton.isEnabled = true
                    button.isEnabled=false
                    button.text = "New round"
                    val selectedItem = spinner.selectedItem.toString()
                    resultList.add(GameResult(selectedItem, setOfDices))
                    game.round = game.round + 1
                    game.throws = 0
                    game.score = 0
                }
            }
            else {
                button.isEnabled = false
                submitButton.text="CALCULATE SCORE"
                submitButton.isEnabled = true
            }
        }

        submitButton.setOnClickListener {
            if (game.round < game.gameRounds) {
                resetDiceSaveState(game.dices)
                setOfDices.clear()
                val selectedItem: Any = spinner.selectedItem.toString()
                game.gameModes.remove(selectedItem)
                updateSpinner(spinner, game.gameModes)
                button.isEnabled=true
                if(button.isEnabled){
                    submitButton.isEnabled=false
                }
            }
            else {
                val intent = ResultActivity.newIntent(this, resultList)
                startActivity(intent)
            }
        }
    }

    private fun setDiceImages(listOfDice: List<Dice>) {

        val diceImage1:ImageView = findViewById(R.id.imageView2)
        val diceImage2:ImageView = findViewById(R.id.imageView3)
        val diceImage3:ImageView = findViewById(R.id.imageView4)
        val diceImage4:ImageView = findViewById(R.id.imageView5)
        val diceImage5:ImageView = findViewById(R.id.imageView6)
        val diceImage6:ImageView = findViewById(R.id.imageView7)

        diceImage1.setImageResource(listOfDice[0].drawableId)
        diceImage2.setImageResource(listOfDice[1].drawableId)
        diceImage3.setImageResource(listOfDice[2].drawableId)
        diceImage4.setImageResource(listOfDice[3].drawableId)
        diceImage5.setImageResource(listOfDice[4].drawableId)
        diceImage6.setImageResource(listOfDice[5].drawableId)

    }

    private fun updateSpinner(spinner: Spinner, gameModeList: List<String>) {
       val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, gameModeList)
       arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
       spinner.adapter = arrayAdapter
    }

    private fun resetDiceSaveState(diceList: List<Dice>) {
        for (dice in diceList) {
            dice.isSaved = false
        }
    }

    private fun initDiceOnClickListeners(binding: ActivityMainBinding) {
        binding.imageView2.setOnClickListener {
            game.dices[0].isSaved = !game.dices[0].isSaved
        }

        binding.imageView3.setOnClickListener {
            game.dices[1].isSaved = !game.dices[1].isSaved
        }

        binding.imageView4.setOnClickListener {
            game.dices[2].isSaved = !game.dices[2].isSaved
        }

        binding.imageView5.setOnClickListener {
            game.dices[3].isSaved = !game.dices[3].isSaved
        }

        binding.imageView6.setOnClickListener {
            game.dices[4].isSaved = !game.dices[4].isSaved
        }

        binding.imageView7.setOnClickListener {
            game.dices[5].isSaved = !game.dices[5].isSaved
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        print("hej")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}