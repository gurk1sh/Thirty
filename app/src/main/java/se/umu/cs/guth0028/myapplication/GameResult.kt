package se.umu.cs.guth0028.myapplication

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable

@Parcelize
data class GameResult(var gameName: String?, var diceSet: @RawValue MutableList<Dice>) : Parcelable {

}