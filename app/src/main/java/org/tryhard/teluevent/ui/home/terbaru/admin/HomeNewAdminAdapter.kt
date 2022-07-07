package org.tryhard.teluevent.ui.home.terbaru.admin


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_home_vertical.view.ivSchedule
import kotlinx.android.synthetic.main.item_home_vertical.view.tvLocSchedule
import kotlinx.android.synthetic.main.item_home_vertical.view.tvTitleSchedule
import kotlinx.android.synthetic.main.item_home_vertical_admin.view.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.event.DAOEvent
import org.tryhard.teluevent.model.event.Event


class HomeNewAdminAdapter(private val eventList:ArrayList<Event>,
                         private val itemAdapterCallback:HomeNewAdminAdapter.ItemAdapterCallback) :RecyclerView.Adapter<HomeNewAdminAdapter.ViewHolder>() {


    private var storageRef = FirebaseStorage.getInstance().reference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_vertical_admin, parent, false)


        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(eventList[position], itemAdapterCallback)






    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        fun bind(data:Event,itemAdapterCallback: HomeNewAdminAdapter.ItemAdapterCallback){
            val storageRef = FirebaseStorage.getInstance().reference


            itemView.apply {



                tvTitleSchedule.text = data.title
                tvLocSchedule.text = data.place
                storageRef.child("uploads/${data.title}").downloadUrl.addOnSuccessListener {
                    Glide.with(itemView)
                        .load(it)
                        .into(ivSchedule)

                }.addOnFailureListener{
                    Log.d("TAG",it.message.toString())
                }



                optionAdminBtn.setOnClickListener {

                    val context = it.context


                    val popup = PopupMenu(it.context, optionAdminBtn)
                    popup.inflate(R.menu.option_menu)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.menu_remove -> {
                                val key = data.key.toString()
                                val dao = DAOEvent()

                                if (key != null) {
                                    dao.remove(key).addOnSuccessListener {
                                        Toast.makeText(
                                            context,
                                            "Event has been removed",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }.addOnFailureListener {
                                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                    }
                                }
                                true
                            }
                            else -> false
                        }
                    }

                    popup.show()


                }






                itemView.setOnClickListener{itemAdapterCallback.onClick(it,data)}
            }
        }
    }

    interface ItemAdapterCallback{
        fun onClick(v: View, data:Event)
    }
}















