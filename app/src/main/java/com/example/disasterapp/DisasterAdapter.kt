package com.example.disasterapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.disasterapp.databinding.ItemDisasterBinding

typealias OnClickDisaster = (Disaster) -> Unit
class DisasterAdapter(private val listDisaster: List<Disaster>, private val onClickDisaster: OnClickDisaster):
    RecyclerView.Adapter<DisasterAdapter.ItemDisasterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemDisasterViewHolder {
//        TODO("Not yet implemented")
        val binding =
            ItemDisasterBinding.inflate(LayoutInflater.from(parent.context), parent,
                false)
        return ItemDisasterViewHolder(binding)
    }


    override fun onBindViewHolder(
        holder: ItemDisasterViewHolder,
        position: Int
    ) {
//        TODO("Not yet implemented")
        holder.bind(listDisaster[position])
    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return listDisaster.size
    }


    inner class ItemDisasterViewHolder(private val binding: ItemDisasterBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(data: Disaster) {
                with(binding) {
                    imgDisaster.setImageResource(data.imgDisaster)
                    txtDisasterName.text = data.nameDisaster
                    txtDisasterType.text = data.disasterType

                    itemView.setOnClickListener {
                        onClickDisaster(data)
                    }
                }
            }
    }



}