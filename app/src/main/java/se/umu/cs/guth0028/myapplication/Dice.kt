package se.umu.cs.guth0028.myapplication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Dice (var value: Int, var isSaved: Boolean, var drawableId: Int ) : Parcelable {

    fun throwDice () {
        this.value = (1..6).random()
        when (this.value) {
            1 -> this.drawableId = R.drawable.grey1
            2 -> this.drawableId = R.drawable.grey2
            3 -> this.drawableId = R.drawable.grey3
            4 -> this.drawableId = R.drawable.grey4
            5 -> this.drawableId = R.drawable.grey5
            6 -> this.drawableId = R.drawable.grey6
        }
    }
}