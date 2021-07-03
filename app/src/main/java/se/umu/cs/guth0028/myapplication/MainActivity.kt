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
private const val PAIR_BUTTON = "pairbutton"
private const val IS_PAIRING = "is_pairing"
private const val SELECTED_ITEM = "selected_item"

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

    }

    private fun initThrowButtonListener(binding: ActivityMainBinding) {
        binding.button.setOnClickListener { //Logic for every throw
            if (gameViewModel.round < gameViewModel.gameRounds) {
                button.text = "THROW"
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
                    currentScore.text = "Current score for this round: "
                }
            }
        }
    }

    private fun initSubmitPairsButtonListener(binding: ActivityMainBinding) {
        binding.submitPairsButton.setOnClickListener { //Logic for the pairing of dice
            var selectedItem = spinner.selectedItem.toString()

            if (checkAnyDicePaired()) {
                for (pair in gameViewModel.whiteDices) {
                    if (pair.isPaired && !pair.isLockedIn) {
                        gameViewModel.listOfPairs.add(pair)
                        pair.isLockedIn = true
                    }
                }
                gameViewModel.score += ScoreCalculator.calculateScore(selectedItem, gameViewModel.listOfPairs) //calculate score based on what's currently paired and which game mode is selected
                Log.d("gustaf", "${gameViewModel.score}")
                currentScore.text = "Current score for this round: ${gameViewModel.score}"
                gameViewModel.listOfPairs.clear() //clear the paired list so it can be reused for next pair
            } else {
                Toast.makeText(this, "You haven't paired anything!", Toast.LENGTH_SHORT).show()
            }

            checkDicesPaired(binding) //checks whether all of the dices are paired and modifies imageview onclick listeners

            if (gameViewModel.dicesPaired) { //if ALL dices are paired
                binding.submitPairsButton.visibility = View.INVISIBLE //Hide pair button
                gameViewModel.listOfResult.add(gameViewModel.score)
                gameViewModel.listOfModes.add(selectedItem)
                gameViewModel.score = 0 //reset score for next round
                prepareNextRound(binding)
            }
        }
    }

    private fun initDiceOnClickListeners(binding: ActivityMainBinding) {//Initiates the listeners for the dice imageview that then saves dice on click
        binding.imageView2.setOnClickListener {
            if (gameViewModel.checkThrows(1)) {
                swapDiceImage(imageView2, 0, "saving")
            }
        }

        binding.imageView3.setOnClickListener {
            if (gameViewModel.checkThrows(1)) {
                swapDiceImage(imageView3, 1, "saving")
            }
        }

        binding.imageView4.setOnClickListener {
            if (gameViewModel.checkThrows(1)) {
                swapDiceImage(imageView4, 2, "saving")
            }
        }

        binding.imageView5.setOnClickListener {
            if (gameViewModel.checkThrows(1)) {
                swapDiceImage(imageView5, 3, "saving")
            }
        }

        binding.imageView6.setOnClickListener {
            if (gameViewModel.checkThrows(1)) {
                swapDiceImage(imageView6, 4, "saving")
            }
        }

        binding.imageView7.setOnClickListener {
            if (gameViewModel.checkThrows(1)) {
                swapDiceImage(imageView7, 5, "saving")
            }
        }
    }

    private fun modifyDiceOnClickListeners(binding: ActivityMainBinding) : Boolean {
        /*
        Changes the on click listeners on the last round so they check for pairings instead of what dice are being saved, also updates the image
        */

        binding.imageView2.setOnClickListener {
            if (!gameViewModel.checkIsLockedIn(0)) {
                swapDiceImage(imageView2, 0, "pairing")
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView3.setOnClickListener {
            if (!gameViewModel.checkIsLockedIn(1)) {
                swapDiceImage(imageView3, 1, "pairing")
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView4.setOnClickListener {
            if (!gameViewModel.checkIsLockedIn(2)) {
                swapDiceImage(imageView4, 2, "pairing")
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView5.setOnClickListener {
            if (!gameViewModel.checkIsLockedIn(3)) {
                swapDiceImage(imageView5, 3, "pairing")
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView6.setOnClickListener {
            if (!gameViewModel.checkIsLockedIn(4)) {
                swapDiceImage(imageView6, 4, "pairing")
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView7.setOnClickListener {
            if (!gameViewModel.checkIsLockedIn(5)) {
                swapDiceImage(imageView7, 5, "pairing")
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

    private fun refreshDiceImages(listOfDice: List<Dice>) { //updates image for saved dices
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
            if (listOfDice[counter].isPaired) {
                imageView.setImageResource(gameViewModel.redDices[listOfDice[counter].value-1].drawableId)
            }
            counter+=1
        }
    }

    private fun swapDiceImage(imageView: ImageView, index: Int, mode:String) {
        /*
         Swaps the dice image depending on the game state and updates it's state
        */
        when (mode) {
            "saving" -> {
                if (!gameViewModel.whiteDices[index].isSaved) {
                    imageView.setImageResource(gameViewModel.greyDices[gameViewModel.whiteDices[index].value-1].drawableId)
                    gameViewModel.whiteDices[index].isSaved = true
                } else {
                    imageView.setImageResource(gameViewModel.whiteDices[gameViewModel.greyDices[index].value-1].drawableId)
                    gameViewModel.whiteDices[index].isSaved = false
                }
            }
            "pairing" -> {
                if (!gameViewModel.whiteDices[index].isPaired) {
                    imageView.setImageResource(gameViewModel.redDices[gameViewModel.whiteDices[index].value-1].drawableId)
                    gameViewModel.whiteDices[index].isPaired = true
                } else {
                    imageView.setImageResource(gameViewModel.whiteDices[gameViewModel.redDices[index].value-1].drawableId)
                    gameViewModel.whiteDices[index].isPaired = false
                }
            }
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

    private fun checkDicesPaired(binding: ActivityMainBinding) { //Checks if all the dices are paired
        gameViewModel.dicesPaired = modifyDiceOnClickListeners(binding)
    }

    private fun checkAnyDicePaired() : Boolean { //Checks for any paired dice that is not already locked in
        for (dice in gameViewModel.whiteDices) {
            if (dice.isPaired && !dice.isLockedIn) {
                return true
            }
        }
        return false
    }

    private fun prepareNextRound(binding: ActivityMainBinding) {
        if (gameViewModel.round < gameViewModel.gameRounds) {
            var selectedItem = spinner.selectedItem.toString()
            resetDiceSaveState(gameViewModel.whiteDices) //reset all dice that was saved
            gameViewModel.gameModes.remove(selectedItem) //remove gamemode from list
            updateSpinner(spinner, gameViewModel.gameModes)
            binding.button.isEnabled=true
            gameViewModel.throws = 0 //reset throws
            gameViewModel.resetDicesPaired()
            gameViewModel.resetDicesLockedIn()
            initDiceOnClickListeners(binding)
            isPairing = false

        } else { //Create, start a new activity and pass over the gamemode list and result list as extras
            val intent = Intent(this,RecyclerResultActivity::class.java)
            intent.putExtra("GameName", gameViewModel.listOfModes)
            intent.putExtra("GameScore",gameViewModel.listOfResult)
            startActivity(intent)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) { //Saves the game state
        super.onSaveInstanceState(savedInstanceState)
        saveInstanceState(savedInstanceState)
    }

    private fun saveInstanceState(savedInstanceState: Bundle) {
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
        savedInstanceState.putBoolean(IS_PAIRING, isPairing)
        savedInstanceState.putInt(SELECTED_ITEM, spinner.selectedItemPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        restoreInstanceState(savedInstanceState)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        gameViewModel.whiteDices = savedInstanceState.getSerializable(DICES) as ArrayList<Dice>
        gameViewModel.listOfPairs = savedInstanceState.getSerializable(PAIRS) as ArrayList<Dice>
        gameViewModel.listOfResult = savedInstanceState.getSerializable(RESULTS) as ArrayList<Int>
        gameViewModel.listOfModes = savedInstanceState.getSerializable(PLAYED_MODES) as ArrayList<String>
        gameViewModel.throws = savedInstanceState.getInt(GAME_THROWS)
        gameViewModel.round = savedInstanceState.getInt(GAME_ROUND)
        gameViewModel.score = savedInstanceState.getInt(GAME_SCORE)
        gameViewModel.gameModes = savedInstanceState.getSerializable(GAME_MODES) as ArrayList<String>
        updateSpinner(spinner, gameViewModel.gameModes)
        spinner.setSelection(savedInstanceState.getInt(SELECTED_ITEM))
        currentScore.text = "Current score for this round: ${gameViewModel.score}"
        button.isEnabled = savedInstanceState.getBoolean(THROW_BUTTON)
        submitPairsButton.visibility = savedInstanceState.getInt(PAIR_BUTTON)
        isPairing = savedInstanceState.getBoolean(IS_PAIRING)
        if (gameViewModel.throws >= 1) { //refreshes UI if the game has started, it will set the images to dices 1-6 if not
            setDiceImages(gameViewModel.whiteDices)
            refreshDiceImages(gameViewModel.whiteDices) //Checks for saved and paired dices and sets the image resource to a grey/red dice
        }
    }
}