package org.tryhard.teluevent.ui.home.terbaru


import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.dummy.HomeVerticalModel
import org.tryhard.teluevent.model.event.DAOEvent
import org.tryhard.teluevent.model.event.Event
import org.tryhard.teluevent.ui.addevent.AddEventFragment


class HomeNewAdminAdapter(private val eventList:ArrayList<Event>) :RecyclerView.Adapter<HomeNewAdminAdapter.ViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNewAdminAdapter.ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home_vertical_admin,parent,false)


        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: HomeNewAdminAdapter.ViewHolder, position: Int) {
        val currentItem = eventList[position]
        val event = eventList[position]



        holder.tvNewTitle.text = currentItem.title
        holder.tvLocationEvent.text = currentItem.place

        holder.optionMenu.setOnClickListener {

            val context = it.context


            val popup = PopupMenu(it.context, holder.optionMenu)
            popup.inflate(R.menu.option_menu)
            popup.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.menu_edit ->{

                        val intent = Intent(it.context,AddEventFragment::class.java)
                        intent.putExtra("EDIT",event)
                        (context as Activity).startActivity(intent)

                        true
                    }
                    R.id.menu_remove ->{
                        val key = currentItem.key.toString()
                        val dao = DAOEvent()

                        if (key != null) {
                            dao.remove(key).addOnSuccessListener {
                                Toast.makeText(context,"Event has been removed",Toast.LENGTH_SHORT).show()
                                notifyItemRemoved(position)

                            }.addOnFailureListener{
                                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                            }
                        }
                        true
                    }
                    else -> false
                }
            }

            popup.show()


        }



    }















    override fun getItemCount(): Int {
        return eventList.size
    }

    class ViewHolder(itemView:View ): RecyclerView.ViewHolder(itemView) {

        val tvNewTitle: TextView = itemView.findViewById(R.id.tvNewTitle)
        val tvLocationEvent: TextView = itemView.findViewById(R.id.tvLocationEvent)
        val optionMenu : ImageView = itemView.findViewById(R.id.optionAdminBtn)









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


