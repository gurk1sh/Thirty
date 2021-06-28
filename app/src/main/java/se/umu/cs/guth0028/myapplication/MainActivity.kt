package se.umu.cs.guth0028.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import se.umu.cs.guth0028.myapplication.databinding.ActivityMainBinding

private const val GAME_THROWS = "game_throws"
private const val DICES = "dices"
private const val THROW_BUTTON = "throwbutton"
private const val SUBMIT_BUTTON = "submitbutton"
private const val PAIR_BUTTON = "pairbutton"

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val gameViewModel: GameViewModel by lazy { //Load viewmodel
        ViewModelProvider(this).get(GameViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) { //Picks up game state as well as buttons state
            gameViewModel.greyDices = savedInstanceState.getSerializable(DICES) as ArrayList<Dice>
            gameViewModel.throws = savedInstanceState.getInt(GAME_THROWS)
            button.isEnabled = savedInstanceState.getBoolean(THROW_BUTTON)
            submitButton.isEnabled = savedInstanceState.getBoolean(SUBMIT_BUTTON)
            submitPairsButton.visibility = savedInstanceState.getInt(PAIR_BUTTON)
            if (gameViewModel.throws >= 1) { //refreshes UI if the game has started, it will set the images to dices 1-6 if not
                setDiceImages(gameViewModel.greyDices)
            }
        }

        binding.submitPairsButton.text = "Submit pair"

        binding.spinner.onItemSelectedListener = this

        updateSpinner(spinner, gameViewModel.gameModes) //Update spinner with game modes

        initDiceOnClickListeners(binding)

        binding.button.setOnClickListener { //Logic for every throw
            if (gameViewModel.round < gameViewModel.gameRounds) {
                binding.submitPairsButton.visibility = View.INVISIBLE //Hide pair button
                button.text = "THROW"
                submitButton.isEnabled = false
                for (dice in gameViewModel.greyDices) { //Check if dice is saved, if not, reroll it's value and update the drawable ID
                    if (!dice.isSaved) {
                        dice.throwDice()
                    }
                }

                setDiceImages(gameViewModel.greyDices)

                gameViewModel.throws = gameViewModel.throws + 1

                if (gameViewModel.throws == gameViewModel.gameRoundThrows) { //Logic for when a round is on the last throw
                    checkDicesPaired(binding)
                    binding.submitPairsButton.visibility = View.VISIBLE
                    binding.button.isEnabled=false
                    binding.button.text = "New round"
                    gameViewModel.round = gameViewModel.round + 1
                }
            }
        }

        binding.submitPairsButton.setOnClickListener { //Logic for the pairing of dice
            val selectedItem = spinner.selectedItem.toString()
            if (gameViewModel.listOfPairs.size != 0) {
                gameViewModel.score += ScoreCalculator.calculateScore(selectedItem, gameViewModel.listOfPairs) //calculate score based on what's currently paired and which game mode is selected
                gameViewModel.listOfPairs.clear() //clear the paired list so it can be reused for next pair
            } else {
                Toast.makeText(this, "You haven't paired anything!", Toast.LENGTH_SHORT).show()
            }

            checkDicesPaired(binding) //checks whether all of the dices are paired and modifies imageview onclick listeners

            if (gameViewModel.dicesPaired) { //if ALL dices are paired
                submitButton.isEnabled = true
                gameViewModel.listOfResult.add(gameViewModel.score)
                gameViewModel.listOfModes.add(selectedItem)
                gameViewModel.score = 0 //reset score for next round
            }
        }

        submitButton.setOnClickListener { //Logic for submitting the round
            if (gameViewModel.round < gameViewModel.gameRounds) {
                val selectedItem = spinner.selectedItem.toString()
                resetDiceSaveState(gameViewModel.greyDices) //reset all dice that was saved
                gameViewModel.gameModes.remove(selectedItem) //remove gamemode from list
                updateSpinner(spinner, gameViewModel.gameModes)
                binding.button.isEnabled=true
                if(binding.button.isEnabled) { //If throw button is enabled, disable submit button
                    submitButton.isEnabled=false
                }
                gameViewModel.throws = 0 //reset throws
                resetDicesPaired()
                initDiceOnClickListeners(binding)

            } else { //Create, start a new activity and pass over the gamemode list and result list as extras
                val intent = Intent(this,RecyclerResultActivity::class.java)
                intent.putExtra("GameName", gameViewModel.listOfModes)
                intent.putExtra("GameScore",gameViewModel.listOfResult)
                startActivity(intent)
            }
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) { //Saves the game state
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putSerializable(DICES, gameViewModel.greyDices)
        savedInstanceState.putInt(GAME_THROWS, gameViewModel.throws)
        savedInstanceState.putBoolean(THROW_BUTTON, button.isEnabled)
        savedInstanceState.putBoolean(SUBMIT_BUTTON, submitButton.isEnabled)
        savedInstanceState.putInt(PAIR_BUTTON, submitPairsButton.visibility)
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

    private fun updateSpinner(spinner: Spinner, gameModeList: List<String>) { //Updates content in the spinner
       val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, gameModeList)
       arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
       spinner.adapter = arrayAdapter
    }

    private fun resetDiceSaveState(diceList: List<Dice>) { //Resets state for all saved dices
        for (dice in diceList) {
            dice.isSaved = false
        }
    }

    private fun initDiceOnClickListeners(binding: ActivityMainBinding) {//Initiates the listeners for the dice imageview that then saves dice on click
        binding.imageView2.setOnClickListener {
            if (gameViewModel.throws >= 1) { //Shouldn't be able to save a dice unless one round is played
                gameViewModel.greyDices[0].isSaved = !gameViewModel.greyDices[0].isSaved
            }
        }

        binding.imageView3.setOnClickListener {
            if (gameViewModel.throws >= 1) {
                gameViewModel.greyDices[1].isSaved = !gameViewModel.greyDices[1].isSaved
            }
        }

        binding.imageView4.setOnClickListener {
            if (gameViewModel.throws >= 1) {
                gameViewModel.greyDices[2].isSaved = !gameViewModel.greyDices[2].isSaved
            }
        }

        binding.imageView5.setOnClickListener {
            if (gameViewModel.throws >= 1) {
                gameViewModel.greyDices[3].isSaved = !gameViewModel.greyDices[3].isSaved
            }
        }

        binding.imageView6.setOnClickListener {
            if (gameViewModel.throws >= 1) {
                gameViewModel.greyDices[4].isSaved = !gameViewModel.greyDices[4].isSaved
            }
        }

        binding.imageView7.setOnClickListener {
            if (gameViewModel.throws >= 1) {
                gameViewModel.greyDices[5].isSaved = !gameViewModel.greyDices[5].isSaved
            }
        }
    }

    private fun modifyDiceOnClickListeners(binding: ActivityMainBinding) : Boolean {
        /*
        Changes the on click listeners on the last round so they check for pairings instead of what dice are being saved, also updates the image
        */

        binding.imageView2.setOnClickListener {
            if (!gameViewModel.greyDices[0].isPaired) {
                gameViewModel.greyDices[0].drawableId = gameViewModel.redDices[gameViewModel.greyDices[0].value-1].drawableId
                imageView2.setImageResource(gameViewModel.greyDices[0].drawableId)
                gameViewModel.greyDices[0].isPaired = true
                gameViewModel.listOfPairs.add(gameViewModel.greyDices[0].value)
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView3.setOnClickListener {
            if (!gameViewModel.greyDices[1].isPaired) {
                gameViewModel.greyDices[1].drawableId = gameViewModel.redDices[gameViewModel.greyDices[1].value-1].drawableId
                imageView3.setImageResource(gameViewModel.greyDices[1].drawableId)
                gameViewModel.greyDices[1].isPaired = true
                gameViewModel.listOfPairs.add(gameViewModel.greyDices[1].value)
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView4.setOnClickListener {
            if (!gameViewModel.greyDices[2].isPaired) {
                gameViewModel.greyDices[2].drawableId = gameViewModel.redDices[gameViewModel.greyDices[2].value-1].drawableId
                imageView4.setImageResource(gameViewModel.greyDices[2].drawableId)
                gameViewModel.greyDices[2].isPaired = true
                gameViewModel.listOfPairs.add(gameViewModel.greyDices[2].value)
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView5.setOnClickListener {
            if (!gameViewModel.greyDices[3].isPaired) {
                gameViewModel.greyDices[3].drawableId = gameViewModel.redDices[gameViewModel.greyDices[3].value-1].drawableId
                imageView5.setImageResource(gameViewModel.greyDices[3].drawableId)
                gameViewModel.greyDices[3].isPaired = true
                gameViewModel.listOfPairs.add(gameViewModel.greyDices[3].value)
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView6.setOnClickListener {
            if (!gameViewModel.greyDices[4].isPaired) {
                gameViewModel.greyDices[4].drawableId = gameViewModel.redDices[gameViewModel.greyDices[4].value-1].drawableId
                imageView6.setImageResource(gameViewModel.greyDices[4].drawableId)
                gameViewModel.greyDices[4].isPaired = true
                gameViewModel.listOfPairs.add(gameViewModel.greyDices[4].value)
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView7.setOnClickListener {
            if (!gameViewModel.greyDices[5].isPaired) {
                gameViewModel.greyDices[5].drawableId = gameViewModel.redDices[gameViewModel.greyDices[5].value-1].drawableId
                imageView7.setImageResource(gameViewModel.greyDices[5].drawableId)
                gameViewModel.greyDices[5].isPaired = true
                gameViewModel.listOfPairs.add(gameViewModel.greyDices[5].value)
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        for (dice in gameViewModel.greyDices) { //Check for ALL of the dice and if they are paired
            if (!dice.isPaired) {
                return false
            }
        }
        return true
    }

    private fun resetDicesPaired() { //Resets state for all paired dices
        for (dice in gameViewModel.greyDices) {
            if (dice.isPaired) {
                dice.isPaired = false
            }
            gameViewModel.dicesPaired = false
        }
    }

    private fun checkDicesPaired(binding: ActivityMainBinding) { //Checks if all the dices are paired
        gameViewModel.dicesPaired = modifyDiceOnClickListeners(binding)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}