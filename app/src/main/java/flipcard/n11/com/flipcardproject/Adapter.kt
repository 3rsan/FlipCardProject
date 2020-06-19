package flipcard.n11.com.flipcardproject


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup


class CardAdapter (val cardList: MutableList<Card>):RecyclerView.Adapter<ModelViewHolder>(){

    public lateinit var modelViewHolder: ModelViewHolder

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int):ModelViewHolder {
        val v =LayoutInflater.from(parent.context).inflate(R.layout.grid_item,parent,false)
        modelViewHolder= ModelViewHolder(v)
        return modelViewHolder
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: ModelViewHolder,position: Int) {

        // holder.bindItems(cardList.get(position))
    }

}
