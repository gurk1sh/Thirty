package se.umu.cs.guth0028.myapplication

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import se.umu.cs.guth0028.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val game = Game()

    private var listOfModes = arrayListOf<String>()

    private var listOfResult = arrayListOf<Int>()

    private var listOfPairs = mutableListOf<Int>()

    private var dicesPaired: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spinner.onItemSelectedListener = this

        updateSpinner(spinner, game.gameModes) //Update spinner with game modes

        initDiceOnClickListeners(binding)

        binding.button.setOnClickListener { //Logic for every throw
            if (game.round < game.gameRounds) {
                binding.submitPairsButton.visibility = View.INVISIBLE //Hide pair button
                button.text = "THROW"
                submitButton.isEnabled = false
                for (dice in game.greyDices) { //Check for saved dices, if not, reroll it's value and update the drawable ID
                    if (!dice.isSaved) {
                        dice.throwDice()
                    }
                }

                setDiceImages(game.greyDices)

                game.throws = game.throws + 1

                if (game.throws == game.gameRoundThrows) { //Logic for when a round is on the last throw
                    checkDicesPaired(binding)
                    binding.submitPairsButton.visibility = View.VISIBLE
                    binding.submitPairsButton.text = "Submit pair"
                    binding.button.isEnabled=false
                    binding.button.text = "New round"
                    game.round = game.round + 1
                    game.throws = 0
                }
            }
        }

        binding.submitPairsButton.setOnClickListener { //Logic for the pairing of dice
            val selectedItem = spinner.selectedItem.toString()
            if (listOfPairs.size != 0) {
                game.score += ScoreCalculator.calculateScore(selectedItem, listOfPairs) //calculate score based on what's currently paired and which game mode is selected
                listOfPairs.clear() //clear the paired list so it can be reused for next pair
            } else {
                Toast.makeText(this, "You haven't paired anything!", Toast.LENGTH_SHORT).show()
            }


            checkDicesPaired(binding) //checks whether all of the dices are paired

            if (dicesPaired) { //if ALL dices are paired
                submitButton.isEnabled = true
                listOfResult.add(game.score)
                listOfModes.add(selectedItem)
                game.score = 0 //reset score for next round
            } else {

            }
        }

        submitButton.setOnClickListener { //Logic for submitting the round
            if (game.round < game.gameRounds) {
                val selectedItem = spinner.selectedItem.toString()
                resetDiceSaveState(game.greyDices) //reset all dice that was saved
                game.gameModes.remove(selectedItem) //remove gamemode and update spinner
                updateSpinner(spinner, game.gameModes)
                binding.button.isEnabled=true
                if(binding.button.isEnabled) {
                    submitButton.isEnabled=false
                }
                resetDicesPaired() //resets all pairing of dices
                initDiceOnClickListeners(binding) //changes the dice on click listeners back to only checking for saving dice between rounds

            } else { //Create and start a new activity and pass over the gamemodes and results as extras
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
        /*
        Changes the on click listeners on the last round so they check for pairings instead of what dice are being saved, also updates the image
        */

        binding.imageView2.setOnClickListener {
            if (!game.greyDices[0].isPaired) {
                game.greyDices[0].drawableId = game.redDices[game.greyDices[0].value-1].drawableId
                imageView2.setImageResource(game.greyDices[0].drawableId)
                game.greyDices[0].isPaired = true
                listOfPairs.add(game.greyDices[0].value)
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView3.setOnClickListener {
            if (!game.greyDices[1].isPaired) {
                game.greyDices[1].drawableId = game.redDices[game.greyDices[1].value-1].drawableId
                imageView3.setImageResource(game.greyDices[1].drawableId)
                game.greyDices[1].isPaired = true
                listOfPairs.add(game.greyDices[1].value)
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView4.setOnClickListener {
            if (!game.greyDices[2].isPaired) {
                game.greyDices[2].drawableId = game.redDices[game.greyDices[2].value - 1].drawableId
                imageView4.setImageResource(game.greyDices[2].drawableId)
                game.greyDices[2].isPaired = true
                listOfPairs.add(game.greyDices[2].value)
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView5.setOnClickListener {
            if (!game.greyDices[3].isPaired) {
                game.greyDices[3].drawableId = game.redDices[game.greyDices[3].value - 1].drawableId
                imageView5.setImageResource(game.greyDices[3].drawableId)
                game.greyDices[3].isPaired = true
                listOfPairs.add(game.greyDices[3].value)
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView6.setOnClickListener {
            if (!game.greyDices[4].isPaired) {
                game.greyDices[4].drawableId = game.redDices[game.greyDices[4].value - 1].drawableId
                imageView6.setImageResource(game.greyDices[4].drawableId)
                game.greyDices[4].isPaired = true
                listOfPairs.add(game.greyDices[4].value)
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView7.setOnClickListener {
            if (!game.greyDices[5].isPaired) {
                game.greyDices[5].drawableId = game.redDices[game.greyDices[5].value - 1].drawableId
                imageView7.setImageResource(game.greyDices[5].drawableId)
                game.greyDices[5].isPaired = true
                listOfPairs.add(game.greyDices[5].value)
            } else {
                Toast.makeText(this, "You can't pair this dice again!", Toast.LENGTH_SHORT).show()
            }
        }

        for (dice in game.greyDices) { //Check for ALL of the dice and if they are paired
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { //Show the user a short message what gamemode is selected
        if (parent != null) {
            Toast.makeText(parent.getContext(),
                "Gamemode : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}