package se.umu.cs.guth0028.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import se.umu.cs.guth0028.myapplication.databinding.ActivityMainBinding

private const val GAME_THROWS = "game_throws"
private const val GAME_ROUND = "game_rounds"
private const val GAME_MODES = "game_modes"
private const val GAME_SCORE = "game_score"
private const val PLAYED_MODES = "played_modes"
private const val DICES = "dices"
private const val PAIRS = "pairs"
private const val RESULTS = "results"
private const val THROW_BUTTON = "throwbutton"
private const val SUBMIT_BUTTON = "submitbutton"
private const val PAIR_BUTTON = "pairbutton"
private const val IS_PAIRING = "is_pairing"

var isPairing = false

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val gameViewModel: GameViewModel by lazy { //Load viewmodel
        ViewModelProvider(this).get(GameViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitPairsButton.text = "Submit pair"

        binding.spinner.onItemSelectedListener = this

        updateSpinner(spinner, gameViewModel.gameModes) //Update spinner with game modes

        if (isPairing) {
            checkDicesPaired(binding)
        } else {
            initDiceOnClickListeners(binding)
        }

        initThrowButtonListener(binding)

        initSubmitPairsButtonListener(binding)

        initSubmitButtonListener(binding)

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) { //Saves the game state
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putSerializable(DICES, gameViewModel.whiteDices)
        savedInstanceState.putSerializable(PAIRS, gameViewModel.listOfPairs)
        savedInstanceState.putSerializable(RESULTS, gameViewModel.listOfResult)
        savedInstanceState.putSerializable(GAME_MODES, gameViewModel.gameModes)
        savedInstanceState.putSerializable(PLAYED_MODES, gameViewModel.listOfModes)
        savedInstanceState.putInt(GAME_THROWS, gameViewModel.throws)
        savedInstanceState.putInt(GAME_ROUND, gameViewModel.round)
        savedInstanceState.putInt(GAME_SCORE, gameViewModel.score)
        savedInstanceState.putInt(PAIR_BUTTON, submitPairsButton.visibility)
        savedInstanceState.putBoolean(THROW_BUTTON, button.isEnabled)
        savedInstanceState.putBoolean(SUBMIT_BUTTON, submitButton.isEnabled)
        savedInstanceState.putBoolean(IS_PAIRING, isPairing)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        gameViewModel.whiteDices = savedInstanceState.getSerializable(DICES) as ArrayList<Dice>
        gameViewModel.listOfPairs = savedInstanceState.getSerializable(PAIRS) as ArrayList<Dice>
        gameViewModel.listOfResult = savedInstanceState.getSerializable(RESULTS) as ArrayList<Int>
        gameViewModel.listOfModes = savedInstanceState.getSerializable(PLAYED_MODES) as ArrayList<String>
        gameViewModel.throws = savedInstanceState.getInt(GAME_THROWS)
        gameViewModel.round = savedInstanceState.getInt(GAME_ROUND)
        gameViewModel.score = savedInstanceState.getInt(GAME_SCORE)
        gameViewModel.gameModes = savedInstanceState.getSerializable(GAME_MODES) as ArrayList<String>
        updateSpinner(spinner, gameViewModel.gameModes)
        currentScore.text = "Current score for this round: ${gameViewModel.score}"
        button.isEnabled = savedInstanceState.getBoolean(THROW_BUTTON)
        submitButton.isEnabled = savedInstanceState.getBoolean(SUBMIT_BUTTON)
        submitPairsButton.visibility = savedInstanceState.getInt(PAIR_BUTTON)
        isPairing = savedInstanceState.getBoolean(IS_PAIRING)
        if (gameViewModel.throws >= 1) { //refreshes UI if the game has started, it will set the images to dices 1-6 if not
            setDiceImages(gameViewModel.whiteDices)
            refreshSavedDiceImages(gameViewModel.whiteDices)
            refreshPairedDiceImages(gameViewModel.whiteDices)
        }
    }

    private fun setDiceImages(listOfDice: List<Dice>) {

        val diceImage1:ImageView = findViewById(R.id.imageView2)
        val diceImage2:ImageView = findViewById(R.id.imageView3)
        val diceImage3:ImageView = findViewById(R.id.imageView4)
        val diceImage4:ImageView = findViewById(R.id.imageView5)
        val diceImage5:ImageView = findViewById(R.id.imageView6)
        val diceImage6:ImageView = findViewById(R.id.imageView7)

        val listOfImageView = listOf(
            diceImage1,
            diceImage2,
            diceImage3,
            diceImage4,
            diceImage5,
            diceImage6,
        )

        var counter = 0

        for (imageView in listOfImageView) {
            if (!listOfDice[counter].isSaved) {
                imageView.setImageResource(listOfDice[counter].drawableId)
            }
            counter+=1
        }
    }

    private fun refreshSavedDiceImages(listOfDice: List<Dice>) {
        val diceImage1:ImageView = findViewById(R.id.imageView2)
        val diceImage2:ImageView = findViewById(R.id.imageView3)
        val diceImage3:ImageView = findViewById(R.id.imageView4)
        val diceImage4:ImageView = findViewById(R.id.imageView5)
        val diceImage5:ImageView = findViewById(R.id.imageView6)
        val diceImage6:ImageView = findViewById(R.id.imageView7)

        val listOfImageView = listOf(
            diceImage1,
            diceImage2,
            diceImage3,
            diceImage4,
            diceImage5,
            diceImage6,
        )

        var counter = 0

        for (imageView in listOfImageView) {
            if (listOfDice[counter].isSaved) {
                imageView.setImageResource(gameViewModel.greyDices[listOfDice[counter].value-1].drawableId)
            }
            counter+=1
        }
    }

    private fun refreshPairedDiceImages(listOfDice: List<Dice>) {
        val diceImage1:ImageView = findViewById(R.id.imageView2)
        val diceImage2:ImageView = findViewById(R.id.imageView3)
        val diceImage3:ImageView = findViewById(R.id.imageView4)
        val diceImage4:ImageView = findViewById(R.id.imageView5)
        val diceImage5:ImageView = findViewById(R.id.imageView6)
        val diceImage6:ImageView = findViewById(R.id.imageView7)

        val listOfImageView = listOf(
            diceImage1,
            diceImage2,
            diceImage3,
            diceImage4,
            diceImage5,
            diceImage6,
        )

        var counter = 0

        for (imageView in listOfImageView) {
            if (listOfDice[counter].isPaired) {
                imageView.setImageResource(gameViewModel.redDices[listOfDice[counter].value-1].drawableId)
            }
            counter+=1
        }
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
                if (!gameViewModel.whiteDices[0].isSaved) {
                    imageView2.setImageResource(gameViewModel.greyDices[gameViewModel.whiteDices[0].value-1].drawableId)
                    gameViewModel.whiteDices[0].isSaved = true
                } else {
                    imageView2.setImageResource(gameViewModel.whiteDices[gameViewModel.greyDices[0].value-1].drawableId)
                    gameViewModel.whiteDices[0].isSaved = false
                }
            }
        }

        binding.imageView3.setOnClickListener {
            if (gameViewModel.throws >= 1) {
                if (!gameViewModel.whiteDices[1].isSaved) {
                    imageView3.setImageResource(gameViewModel.greyDices[gameViewModel.whiteDices[1].value-1].drawableId)
                    gameViewModel.whiteDices[1].isSaved = true
                } else {
                    imageView3.setImageResource(gameViewModel.whiteDices[gameViewModel.greyDices[1].value-1].drawableId)
                    gameViewModel.whiteDices[1].isSaved = false
                }
            }
        }

        binding.imageView4.setOnClickListener {
            if (gameViewModel.throws >= 1) {
                if (!gameViewModel.whiteDices[2].isSaved) {
                    imageView4.setImageResource(gameViewModel.greyDices[gameViewModel.whiteDices[2].value-1].drawableId)
                    gameViewModel.whiteDices[2].isSaved = true
                } else {
                    imageView4.setImageResource(gameViewModel.whiteDices[gameViewModel.greyDices[2].value-1].drawableId)
                    gameViewModel.whiteDices[2].isSaved = false
                }
            }
        }

        binding.imageView5.setOnClickListener {
            if (gameViewModel.throws >= 1) {
                if (!gameViewModel.whiteDices[3].isSaved) {
                    imageView5.setImageResource(gameViewModel.greyDices[gameViewModel.whiteDices[3].value-1].drawableId)
                    gameViewModel.whiteDices[3].isSaved = true
                } else {
                    imageView5.setImageResource(gameViewModel.whiteDices[gameViewModel.greyDices[3].value-1].drawableId)
                    gameViewModel.whiteDices[3].isSaved = false
                }
            }
        }

        binding.imageView6.setOnClickListener {
            if (gameViewModel.throws >= 1) {
                if (!gameViewModel.whiteDices[4].isSaved) {
                    imageView6.setImageResource(gameViewModel.greyDices[gameViewModel.whiteDices[4].value-1].drawableId)
                    gameViewModel.whiteDices[4].isSaved = true
                } else {
                    imageView6.setImageResource(gameViewModel.whiteDices[gameViewModel.greyDices[4].value-1].drawableId)
                    gameViewModel.whiteDices[4].isSaved = false
                }
            }
        }

        binding.imageView7.setOnClickListener {
            if (gameViewModel.throws >= 1) {
                if (!gameViewModel.whiteDices[5].isSaved) {
                    imageView7.setImageResource(gameViewModel.greyDices[gameViewModel.whiteDices[5].value-1].drawableId)
                    gameViewModel.whiteDices[5].isSaved = true
                } else {
                    imageView7.setImageResource(gameViewModel.whiteDices[gameViewModel.greyDices[5].value-1].drawableId)
                    gameViewModel.whiteDices[5].isSaved = false
                }
            }
        }
    }

    private fun modifyDiceOnClickListeners(binding: ActivityMainBinding) : Boolean {
        /*
        Changes the on click listeners on the last round so they check for pairings instead of what dice are being saved, also updates the image
        */

        binding.imageView2.setOnClickListener {
            if (!gameViewModel.whiteDices[0].isLockedIn) {
                if (!gameViewModel.whiteDices[0].isPaired) {
                    imageView2.setImageResource(gameViewModel.redDices[gameViewModel.whiteDices[0].value-1].drawableId)
                    gameViewModel.whiteDices[0].isPaired = true
                    gameViewModel.listOfPairs.add(gameViewModel.whiteDices[0])
                } else {
                    imageView2.setImageResource(gameViewModel.whiteDices[gameViewModel.redDices[0].value-1].drawableId)
                    gameViewModel.whiteDices[0].isPaired = false
                    gameViewModel.listOfPairs.remove(gameViewModel.whiteDices[0])
                }
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView3.setOnClickListener {
            if (!gameViewModel.whiteDices[1].isLockedIn) {
                if (!gameViewModel.whiteDices[1].isPaired) {
                    imageView3.setImageResource(gameViewModel.redDices[gameViewModel.whiteDices[1].value-1].drawableId)
                    gameViewModel.whiteDices[1].isPaired = true
                    gameViewModel.listOfPairs.add(gameViewModel.whiteDices[1])
                } else {
                    imageView3.setImageResource(gameViewModel.whiteDices[gameViewModel.redDices[1].value-1].drawableId)
                    gameViewModel.whiteDices[1].isPaired = false
                    gameViewModel.listOfPairs.remove(gameViewModel.whiteDices[1])
                }
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView4.setOnClickListener {
            if (!gameViewModel.whiteDices[2].isLockedIn) {
                if (!gameViewModel.whiteDices[2].isPaired) {
                    imageView4.setImageResource(gameViewModel.redDices[gameViewModel.whiteDices[2].value-1].drawableId)
                    gameViewModel.whiteDices[2].isPaired = true
                    gameViewModel.listOfPairs.add(gameViewModel.whiteDices[2])
                } else {
                    imageView4.setImageResource(gameViewModel.whiteDices[gameViewModel.redDices[2].value-1].drawableId)
                    gameViewModel.whiteDices[2].isPaired = false
                    gameViewModel.listOfPairs.remove(gameViewModel.whiteDices[2])
                }
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView5.setOnClickListener {
            if (!gameViewModel.whiteDices[3].isLockedIn) {
                if (!gameViewModel.whiteDices[3].isPaired) {
                    imageView5.setImageResource(gameViewModel.redDices[gameViewModel.whiteDices[3].value-1].drawableId)
                    gameViewModel.whiteDices[3].isPaired = true
                    gameViewModel.listOfPairs.add(gameViewModel.whiteDices[3])
                } else {
                    imageView5.setImageResource(gameViewModel.whiteDices[gameViewModel.redDices[3].value-1].drawableId)
                    gameViewModel.whiteDices[3].isPaired = false
                    gameViewModel.listOfPairs.remove(gameViewModel.whiteDices[3])
                }
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView6.setOnClickListener {
            if (!gameViewModel.whiteDices[4].isLockedIn) {
                if (!gameViewModel.whiteDices[4].isPaired) {
                    imageView6.setImageResource(gameViewModel.redDices[gameViewModel.whiteDices[4].value-1].drawableId)
                    gameViewModel.whiteDices[4].isPaired = true
                    gameViewModel.listOfPairs.add(gameViewModel.whiteDices[4])
                } else {
                    imageView6.setImageResource(gameViewModel.whiteDices[gameViewModel.redDices[4].value-1].drawableId)
                    gameViewModel.whiteDices[4].isPaired = false
                    gameViewModel.listOfPairs.remove(gameViewModel.whiteDices[4])
                }
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView7.setOnClickListener {
            if (!gameViewModel.whiteDices[5].isLockedIn) {
                if (!gameViewModel.whiteDices[5].isPaired) {
                    imageView7.setImageResource(gameViewModel.redDices[gameViewModel.whiteDices[5].value-1].drawableId)
                    gameViewModel.whiteDices[5].isPaired = true
                    gameViewModel.listOfPairs.add(gameViewModel.whiteDices[5])
                } else {
                    imageView7.setImageResource(gameViewModel.whiteDices[gameViewModel.redDices[5].value-1].drawableId)
                    gameViewModel.whiteDices[5].isPaired = false
                    gameViewModel.listOfPairs.remove(gameViewModel.whiteDices[5])
                }
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        for (dice in gameViewModel.whiteDices) { //Check for ALL of the dice and if they are paired
            if (!dice.isPaired) {
                return false
            }
        }
        return true
    }

    private fun initThrowButtonListener(binding: ActivityMainBinding) {
        binding.button.setOnClickListener { //Logic for every throw
            if (gameViewModel.round < gameViewModel.gameRounds) {
                button.text = "THROW"
                submitButton.isEnabled = false
                for (dice in gameViewModel.whiteDices) { //Check if dice is saved, if not, reroll it's value and update the drawable ID
                    if (!dice.isSaved) {
                        dice.throwDice()
                    }
                }

                setDiceImages(gameViewModel.whiteDices)

                gameViewModel.throws = gameViewModel.throws + 1

                if (gameViewModel.throws == gameViewModel.gameRoundThrows) { //Logic for when a round is on the last throw
                    isPairing = true
                    checkDicesPaired(binding)
                    binding.submitPairsButton.visibility = View.VISIBLE
                    binding.button.isEnabled=false
                    gameViewModel.round = gameViewModel.round + 1
                } else {
                    isPairing = false
                }
            }
        }
    }

    private fun initSubmitPairsButtonListener(binding: ActivityMainBinding) {
        binding.submitPairsButton.setOnClickListener { //Logic for the pairing of dice
            val selectedItem = spinner.selectedItem.toString()

            if (gameViewModel.listOfPairs.size != 0) {
                gameViewModel.score += ScoreCalculator.calculateScore(selectedItem, gameViewModel.listOfPairs) //calculate score based on what's currently paired and which game mode is selected
                currentScore.text = "Current score for this round: ${gameViewModel.score}"
                for (pair in gameViewModel.listOfPairs) {
                    pair.isLockedIn = true
                }
                gameViewModel.listOfPairs.clear() //clear the paired list so it can be reused for next pair
            } else {
                Toast.makeText(this, "You haven't paired anything!", Toast.LENGTH_SHORT).show()
            }

            checkDicesPaired(binding) //checks whether all of the dices are paired and modifies imageview onclick listeners

            if (gameViewModel.dicesPaired) { //if ALL dices are paired
                submitButton.isEnabled = true
                binding.submitPairsButton.visibility = View.INVISIBLE //Hide pair button
                gameViewModel.listOfResult.add(gameViewModel.score)
                gameViewModel.listOfModes.add(selectedItem)
                gameViewModel.score = 0 //reset score for next round

            }
        }
    }

    private fun initSubmitButtonListener(binding: ActivityMainBinding) {
        submitButton.setOnClickListener { //Logic for submitting the round

        }
    }

    private fun resetDicesPaired() { //Resets state for all paired dices
        for (dice in gameViewModel.whiteDices) {
            if (dice.isPaired) {
                dice.isPaired = false
            }
            gameViewModel.dicesPaired = false
        }
    }

    private fun resetDicesLockedIn() { //Resets state for all paired dices
        for (dice in gameViewModel.whiteDices) {
            if (dice.isLockedIn) {
                dice.isLockedIn = false
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