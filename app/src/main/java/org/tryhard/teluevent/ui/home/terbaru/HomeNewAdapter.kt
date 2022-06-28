package org.tryhard.teluevent.ui.home.terbaru


import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.dummy.HomeVerticalModel
import org.tryhard.teluevent.model.event.Event
import org.w3c.dom.Text
import java.security.AccessControlContext
import java.security.AccessController.getContext


class HomeNewAdapter(private val eventList:ArrayList<Event>) :RecyclerView.Adapter<HomeNewAdapter.ViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNewAdapter.ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home_vertical,parent,false)


        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: HomeNewAdapter.ViewHolder, position: Int) {
        val currentItem = eventList[position]


        holder.tvNewTitle.text = currentItem.title
        holder.tvLocationEvent.text = currentItem.place








    }





    override fun getItemCount(): Int {
        return eventList.size
    }

    class ViewHolder(itemView:View ): RecyclerView.ViewHolder(itemView) {

        val tvNewTitle: TextView = itemView.findViewById(R.id.tvNewTitle)
        val tvLocationEvent: TextView = itemView.findViewById(R.id.tvLocationEvent)
        val optionMenu : TextView = itemView.findViewById<TextView>(R.id.txt_option)







//        fun bind(data:HomeVerticalModel,itemAdapterCallback: ItemAdapterCallback){
//            itemView.apply {
//                tvNewTitle.text = data.title
//                Glide.with(context)
//                    .load(R.drawable.banner1)
//                    .into(ivNewImg)
//
//                itemView.setOnClickListener{itemAdapterCallback.onClick(it,data)}
//            }
//        }
    }





    interface ItemAdapterCallback{
        fun onClick(v: View, data:HomeVerticalModel)
    }
}