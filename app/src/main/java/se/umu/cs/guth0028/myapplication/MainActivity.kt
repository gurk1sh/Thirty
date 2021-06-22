package se.umu.cs.guth0028.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import se.umu.cs.guth0028.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val game = Game()

    private val listOfDices = mutableListOf<Dice>()

    private var listOfModes = arrayListOf<String>()

    private var listOfResult = arrayListOf<Int>()

    private var listOfPairs = mutableListOf<Int>()

    private var dicesPaired: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spinner.onItemSelectedListener = this

        updateSpinner(spinner, game.gameModes)

        initDiceOnClickListeners(binding)

        binding.button.setOnClickListener {
            if (game.round <= game.gameRounds){
                button.text = "THROW"
                submitButton.isEnabled = false
                for (dice in game.greyDices) {
                    if (!dice.isSaved) {
                        dice.throwDice()
                    }
                    if (game.throws == (game.gameRoundThrows-1)) {
                        listOfDices.add(dice)
                    }
                }

                setDiceImages(game.greyDices)

                game.throws = game.throws + 1

                if (game.throws == game.gameRoundThrows) {
                    checkDicesPaired(binding)
                    button.isEnabled=false
                    button.text = "New round"
                    submitButton.isEnabled = true
                    game.round = game.round + 1
                    game.throws = 0
                }
            }
            else {
                button.isEnabled = false
                submitButton.text="CALCULATE SCORE"
                submitButton.isEnabled = true
            }
        }

        submitButton.setOnClickListener {
            if (game.round <= game.gameRounds) {
                checkDicesPaired(binding)
                if (dicesPaired) {
                    val selectedItem = spinner.selectedItem.toString()
                    listOfModes.add(selectedItem)
                    game.score = ScoreCalculator.calculateScore(selectedItem, listOfPairs)
                    listOfResult.add(game.score)
                    listOfPairs.clear()
                    game.score = 0
                    resetDiceSaveState(game.greyDices)
                    listOfDices.clear()
                    game.gameModes.remove(selectedItem)
                    updateSpinner(spinner, game.gameModes)
                    button.isEnabled=true
                    if(button.isEnabled){
                        submitButton.isEnabled=false
                    }
                    resetDicesPaired()
                    initDiceOnClickListeners(binding)
                } else {
                    Toast.makeText(this, "Du måste para ihop alla tärningar", Toast.LENGTH_SHORT).show()
                }
            } else {
                val intent = Intent(this,ResultActivity::class.java)
                intent.putExtra("GameName",listOfModes)
                intent.putExtra("GameScore",listOfResult)
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
            game.greyDices[0].isSaved = !game.greyDices[0].isSaved
        }

        binding.imageView3.setOnClickListener {
            game.greyDices[1].isSaved = !game.greyDices[1].isSaved
        }

        binding.imageView4.setOnClickListener {
            game.greyDices[2].isSaved = !game.greyDices[2].isSaved
        }

        binding.imageView5.setOnClickListener {
            game.greyDices[3].isSaved = !game.greyDices[3].isSaved
        }

        binding.imageView6.setOnClickListener {
            game.greyDices[4].isSaved = !game.greyDices[4].isSaved
        }

        binding.imageView7.setOnClickListener {
            game.greyDices[5].isSaved = !game.greyDices[5].isSaved
        }
    }

    private fun modifyDiceOnClickListeners(binding: ActivityMainBinding) : Boolean {
        binding.imageView2.setOnClickListener {
            game.greyDices[0].drawableId = game.redDices[game.greyDices[0].value-1].drawableId
            imageView2.setImageResource(game.greyDices[0].drawableId)
            game.greyDices[0].isPaired = true
            listOfPairs.add(game.greyDices[0].value)
        }

        binding.imageView3.setOnClickListener {
            game.greyDices[1].drawableId = game.redDices[game.greyDices[1].value-1].drawableId
            imageView3.setImageResource(game.greyDices[1].drawableId)
            game.greyDices[1].isPaired = true
            listOfPairs.add(game.greyDices[1].value)
        }

        binding.imageView4.setOnClickListener {
            game.greyDices[2].drawableId = game.redDices[game.greyDices[2].value-1].drawableId
            imageView4.setImageResource(game.greyDices[2].drawableId)
            game.greyDices[2].isPaired = true
            listOfPairs.add(game.greyDices[2].value)
        }

        binding.imageView5.setOnClickListener {
            game.greyDices[3].drawableId = game.redDices[game.greyDices[3].value-1].drawableId
            imageView5.setImageResource(game.greyDices[3].drawableId)
            game.greyDices[3].isPaired = true
            listOfPairs.add(game.greyDices[3].value)
        }

        binding.imageView6.setOnClickListener {
            game.greyDices[4].drawableId = game.redDices[game.greyDices[4].value-1].drawableId
            imageView6.setImageResource(game.greyDices[4].drawableId)
            game.greyDices[4].isPaired = true
            listOfPairs.add(game.greyDices[4].value)
        }

        binding.imageView7.setOnClickListener {
            game.greyDices[5].drawableId = game.redDices[game.greyDices[5].value-1].drawableId
            imageView7.setImageResource(game.greyDices[5].drawableId)
            game.greyDices[5].isPaired = true
            listOfPairs.add(game.greyDices[5].value)
        }

        for (dice in game.greyDices) {
            if (!dice.isPaired) {
                return false
            }
        }
        return true
    }

    private fun resetDicesPaired() {
        for (dice in game.greyDices) {
            if (dice.isPaired) {
                dice.isPaired = false
            }
            dicesPaired = false
        }
    }

    private fun checkDicesPaired(binding: ActivityMainBinding) {
        dicesPaired = modifyDiceOnClickListeners(binding)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        print("hej")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}