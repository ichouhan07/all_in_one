package com.applligent.hilt_di_retrofit_viewmodel.room

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.applligent.hilt_di_retrofit_viewmodel.common.Utils.log
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ItemRoomUserRowBinding
import com.applligent.hilt_di_retrofit_viewmodel.room.Room.RoomUsers

class RoomUserAdapter(context: Context, adapterListener: RoomAdapterListener) :
    RecyclerView.Adapter<RoomUserAdapter.MyViewHolder?>() {
    var context: Context
    private val usersList: MutableList<RoomUsers?>
    private val adapterListener: RoomAdapterListener

    init {
        this.context = context
        usersList = ArrayList()
        this.adapterListener = adapterListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addUser(users: RoomUsers?) {
        usersList.add(users)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeUser(position: Int) {
        usersList.removeAt(position)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        usersList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemRoomUserRowBinding =
            ItemRoomUserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: RoomUsers? = usersList[position]
        holder.binding.name.text = item?.name
        holder.binding.email.text = item?.email
        holder.binding.update.setOnClickListener { adapterListener.update(item) }
        holder.binding.delete.setOnClickListener {
            adapterListener.delete(
                item?.id,
                position
            )
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    class MyViewHolder(binding: ItemRoomUserRowBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ItemRoomUserRowBinding
        init { this.binding = binding }
    }
}