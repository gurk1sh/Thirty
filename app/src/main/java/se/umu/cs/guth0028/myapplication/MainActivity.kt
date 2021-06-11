package se.umu.cs.guth0028.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var sum = 0
        var counter = 0
        var isImageViewOneClicked = true
        var isImageViewTwoClicked = true
        var isImageViewThreeClicked = true
        var isImageViewFourClicked = true
        var isImageViewFiveClicked = true
        var isImageViewSixClicked = true

        var listOfClickedItems = mutableListOf<Int>()

        var listOfGreyDice = listOf<Int>(
            R.drawable.grey1,
            R.drawable.grey2,
            R.drawable.grey3,
            R.drawable.grey4,
            R.drawable.grey5,
            R.drawable.grey6
        )

        var listOfPlaceHolders = listOf<Int>(
            R.id.imageView2,
            R.id.imageView3,
            R.id.imageView4,
            R.id.imageView5,
            R.id.imageView6,
            R.id.imageView7
        )

        imageView2.setOnClickListener {
            isImageViewOneClicked = clickToggle(isImageViewOneClicked, listOfClickedItems, R.id.imageView2)
        }

        imageView3.setOnClickListener {
            isImageViewTwoClicked = clickToggle(isImageViewTwoClicked, listOfClickedItems, R.id.imageView3)
        }

        imageView4.setOnClickListener {
            isImageViewThreeClicked = clickToggle(isImageViewThreeClicked, listOfClickedItems, R.id.imageView4)
        }

        imageView5.setOnClickListener {
            isImageViewFourClicked = clickToggle(isImageViewFourClicked, listOfClickedItems, R.id.imageView5)
        }

        imageView6.setOnClickListener {
            isImageViewFiveClicked = clickToggle(isImageViewFiveClicked, listOfClickedItems, R.id.imageView6)
        }

        imageView7.setOnClickListener {
            isImageViewSixClicked = clickToggle(isImageViewSixClicked, listOfClickedItems, R.id.imageView7)
        }

        button.setOnClickListener {
            counter+=1
            if (counter <= 3) {
                for (i in 0..5) {
                    var imageView:ImageView = findViewById(listOfPlaceHolders.get(i))
                    if (!(listOfClickedItems.contains(listOfPlaceHolders.get(i)))) {
                        var random = (0..5).random()
                        sum = random+sum+1
                        imageView.setImageResource(listOfGreyDice.get(random))
                    }
                }
                if (counter==3) {
                    button.isEnabled = false
                    Toast.makeText(this, "The sum is: $sum", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun clickToggle(clicked: Boolean, list: MutableList<Int>, imageViewId: Int) : Boolean {
        if (clicked) {
            list.add(imageViewId)
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
            return false
        } else {
            list.remove(imageViewId)
            Toast.makeText(this, "not saved", Toast.LENGTH_SHORT).show()
            return true
        }
    }
}