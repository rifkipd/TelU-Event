package org.tryhard.teluevent.ui.home.admin

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_home_horizontal.view.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.dummy.HomeModel
import org.tryhard.teluevent.model.event.Event
import org.tryhard.teluevent.ui.home.HomeAdapter


class HomeAdminAdapter(private val listData:ArrayList<Event>,
                       private val itemAdapterCallback: ItemAdapterCallback ) : RecyclerView.Adapter<HomeAdminAdapter.ViewHolder>(){



    private var storageRef = FirebaseStorage.getInstance().reference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdminAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home_horizontal,parent,false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeAdminAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int {
        val limit:Int = 5
        return listData.size.coerceAtMost(limit)
    }



    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        fun bind(data:Event, itemAdapterCallback: ItemAdapterCallback){
            val storageRef = FirebaseStorage.getInstance().reference

            itemView.apply {
                tvBigTitle.text = data.title
                storageRef.child("uploads/${data.title}").downloadUrl.addOnSuccessListener {
                    Glide.with(itemView)
                        .load(it)
                        .into(ivBigBanner)

                }.addOnFailureListener{
                    Log.d("TAG",it.message.toString())
                }
                itemView.setOnClickListener{itemAdapterCallback.onClick(it,data)}
            }
        }
    }




    interface ItemAdapterCallback{
        fun onClick(v: View, data:Event)
    }
}