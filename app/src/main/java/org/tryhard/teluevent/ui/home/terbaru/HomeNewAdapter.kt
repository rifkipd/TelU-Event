package org.tryhard.teluevent.ui.home.terbaru


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_home_vertical.view.*
import kotlinx.android.synthetic.main.item_home_vertical.view.ivSchedule
import kotlinx.android.synthetic.main.item_home_vertical.view.tvLocSchedule
import kotlinx.android.synthetic.main.item_home_vertical.view.tvTitleSchedule
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.event.Event


class HomeNewAdapter(private val listData:ArrayList<Event>,private val itemAdapterCallback: ItemAdapterCallback) :RecyclerView.Adapter<HomeNewAdapter.ViewHolder>() {

    val TAG = "STATUS"
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var storageRef = FirebaseStorage.getInstance().reference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNewAdapter.ViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_vertical, parent, false)


        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int {
        return listData.size
    }


    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        fun bind(data:Event,itemAdapterCallback: HomeNewAdapter.ItemAdapterCallback){
            val storageRef = FirebaseStorage.getInstance().reference
            var database: DatabaseReference
            var fAuth = FirebaseAuth.getInstance()

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



                optionSaveBtn.setOnClickListener {
                    val key = data.key
                    val title = data.title
                    val place = data.place
                    val date = data.date
                    val desc = data.desc

                    val popup = PopupMenu(context, optionSaveBtn)
                    popup.inflate(R.menu.option_menu_save)
                    popup.setOnMenuItemClickListener{item ->
                        when(item.itemId){
                            R.id.menu_save->{
                                database = FirebaseDatabase.getInstance().getReference("SavedEvents")
                                val event = Event(title,place,date,desc,key)
                                database.child(fAuth.currentUser?.uid.toString()).setValue(event).addOnSuccessListener {
                                    Toast.makeText(context,"Success adding event",Toast.LENGTH_SHORT).show()
                                }.addOnFailureListener{
                                    Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
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





//        holder.optionMenu.setOnClickListener {
//
//            val context = it.context
//
//
//            val popup = PopupMenu(it.context, holder.optionMenu)
//            popup.inflate(R.menu.option_menu)
//            popup.setOnMenuItemClickListener { item ->
//                when(item.itemId){
//                    R.id.menu_edit ->{
//
//                        val intent = Intent(it.context,AddEventFragment::class.java)
//                        intent.putExtra("EDIT",event)
//                        (context as Activity).startActivity(intent)
//
//                        true
//                    }
//                    R.id.menu_remove ->{
//                        val key = currentItem.key.toString()
//                        val dao = DAOEvent()
//
//                        if (key != null) {
//                            dao.remove(key).addOnSuccessListener {
//                                Toast.makeText(context,"Event has been removed",Toast.LENGTH_SHORT).show()
//                                notifyItemRemoved(position)
//
//                            }.addOnFailureListener{
//                                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                        true
//                    }
//                    else -> false
//                }
//            }
//
//            popup.show()
//
//
//        }






