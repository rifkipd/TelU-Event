package org.tryhard.teluevent.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_home_horizontal.view.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.dummy.HomeModel
import org.tryhard.teluevent.model.event.Event


class HomeAdapter(private val eventList:ArrayList<Event>) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home_horizontal,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = eventList[position]


        holder.tvTitle.text = currentItem.title
    }

    override fun getItemCount(): Int {
        val limit:Int = 5
        return eventList.size.coerceAtMost(limit)
    }



    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView = itemView.findViewById(R.id.tvBigTitle)

    }

    interface ItemAdapterCallback{
        fun onClick(v: View, data:HomeModel)
    }
}