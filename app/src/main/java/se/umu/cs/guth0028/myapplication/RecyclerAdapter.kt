package se.umu.cs.guth0028.myapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_item.view.*

class RecyclerAdapter (var listOfGameModes: MutableList<String>, var listOfGameScores: MutableList<Int>) :
    RecyclerView.Adapter<ResultViewHolder>() {

    /*
     Adapter for cards used in recyclerResultActivity
     Sets the data used in cards
    */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
       var gameItem=listOfGameModes[position]
        var gameScore=listOfGameScores[position]

        holder.bind(gameItem,gameScore)
        if (position%2==0) {
            holder.itemView.constraint.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.green))
        } else {
            holder.itemView.constraint.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.red))
        }
    }

    override fun getItemCount(): Int {
       return listOfGameScores.size
    }

}

class ResultViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    fun bind(gameModeElement:String,gameScoreElement:Int){
        itemView.tv_GameMode.text=gameModeElement
        itemView.tv_GameScore.text=gameScoreElement.toString()
    }

}


