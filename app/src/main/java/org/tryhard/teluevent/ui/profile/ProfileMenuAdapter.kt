package org.tryhard.teluevent.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_menu_profile.view.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.dummy.ProfileMenuModel


class ProfileMenuAdapter(
    private val listData: List<ProfileMenuModel>,
    private val itemAdapterCallback: ItemAdapterCallback,
) :RecyclerView.Adapter<ProfileMenuAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileMenuAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_menu_profile,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileMenuAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }



    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        fun bind(data:ProfileMenuModel,itemAdapterCallback: ItemAdapterCallback){
            itemView.apply {
                tvTitleMenuProfile.text = data.title
                itemView.setOnClickListener{itemAdapterCallback.onClick(it,data)}
            }
        }
    }

    interface ItemAdapterCallback{
        fun onClick(v: View, data:ProfileMenuModel)
    }
}