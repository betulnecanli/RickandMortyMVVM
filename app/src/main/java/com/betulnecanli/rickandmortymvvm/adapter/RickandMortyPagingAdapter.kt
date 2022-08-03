package com.betulnecanli.rickandmortymvvm.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.betulnecanli.rickandmortymvvm.data.models.Details
import com.betulnecanli.rickandmortymvvm.databinding.ListItemBinding

class RickandMortyPagingAdapter: PagingDataAdapter<Details, RickandMortyPagingAdapter.MyViewHolder>(diffCallback = diffCallBack ) {


    companion object{
        val diffCallBack = object : DiffUtil.ItemCallback<Details>(){
            override fun areItemsTheSame(oldItem: Details, newItem: Details): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Details, newItem: Details): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class MyViewHolder(val binding : ListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                characterName.text = currentItem?.name.toString()
                val imgLink  = currentItem?.image
                characterImg.load(imgLink){
                    crossfade(true)
                    crossfade(1000)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}