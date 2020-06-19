package flipcard.n11.com.flipcardproject

import android.support.v7.widget.RecyclerView

import android.view.View
import android.widget.ImageView



class ModelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val imgCard: ImageView = view.findViewById(R.id.imgCard)


    fun bindItem(item: Card) {
        imgCard.setImageResource(item.image!!)

    }



}