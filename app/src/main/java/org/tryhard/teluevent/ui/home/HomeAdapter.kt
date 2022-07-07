package org.tryhard.teluevent.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_home_horizontal.view.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.api.EventClickHandler
import org.tryhard.teluevent.model.dummy.HomeModel
import org.tryhard.teluevent.model.dummy.ProfileMenuModel
import org.tryhard.teluevent.model.event.Event
import org.tryhard.teluevent.ui.home.terbaru.HomeNewAdapter
import org.tryhard.teluevent.ui.profile.ProfileMenuAdapter



class HomeAdapter(
    private val listData: List<Event>,
    private val itemAdapterCallback: ItemAdapterCallback,
) :RecyclerView.Adapter<HomeAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_home_horizontal,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }



    override fun getItemCount(): Int {
        val limit:Int = 5
        return listData.size.coerceAtMost(limit)
    }

    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        fun bind(data:Event,itemAdapterCallback: ItemAdapterCallback){
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


