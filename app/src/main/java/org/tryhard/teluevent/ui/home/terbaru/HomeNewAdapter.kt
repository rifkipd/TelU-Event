package org.tryhard.teluevent.ui.home.terbaru

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_home_vertical.view.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.dummy.HomeVerticalModel


class HomeNewAdapter(
    private val listData: List<HomeVerticalModel>,
    private val itemAdapterCallback: ItemAdapterCallback,
) :RecyclerView.Adapter<HomeNewAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNewAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_home_vertical,parent,false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: HomeNewAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }



    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        fun bind(data:HomeVerticalModel,itemAdapterCallback: ItemAdapterCallback){
            itemView.apply {
                tvNewTitle.text = data.title
                Glide.with(context)
                    .load(R.drawable.banner1)
                    .into(ivNewImg)

                itemView.setOnClickListener{itemAdapterCallback.onClick(it,data)}
            }
        }
    }

    interface ItemAdapterCallback{
        fun onClick(v: View, data:HomeVerticalModel)
    }
}