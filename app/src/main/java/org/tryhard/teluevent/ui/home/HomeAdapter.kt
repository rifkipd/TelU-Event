package org.tryhard.teluevent.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.item_home_horizontal.view.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.dummy.HomeModel
import org.tryhard.teluevent.model.event.Event


class HomeAdapter(private val eventList:ArrayList<Event>) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>(){


    private var storageRef = FirebaseStorage.getInstance().reference


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home_horizontal,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = eventList[position]

        storageRef.child("uploads/${currentItem.title}").downloadUrl.addOnSuccessListener {
            Glide.with(holder.itemView)
                .load(it)
                .into(holder.foto)

        }.addOnFailureListener{
            Log.d("TAG",it.message.toString())
        }


        holder.tvTitle.text = currentItem.title
    }

    override fun getItemCount(): Int {
        val limit:Int = 5
        return eventList.size.coerceAtMost(limit)
    }



    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView = itemView.findViewById(R.id.tvBigTitle)
        val foto: ImageView = itemView.findViewById(R.id.ivBigBanner)

    }

    interface ItemAdapterCallback{
        fun onClick(v: View, data:HomeModel)
    }
}