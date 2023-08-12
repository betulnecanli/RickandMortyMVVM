package com.betulnecanli.rickandmortymvvm.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.betulnecanli.rickandmortymvvm.data.models.Details
import com.betulnecanli.rickandmortymvvm.data.models.ListResponse
import com.betulnecanli.rickandmortymvvm.databinding.ListItemBinding

class RickandMortyPagingAdapter(
    private val listener: OnItemClickListener
) : PagingDataAdapter<Details,
        RickandMortyPagingAdapter.MyViewHolder>(diffCallback = diffCallBack) {


    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<Details>() {
            override fun areItemsTheSame(oldItem: Details, newItem: Details): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Details, newItem: Details): Boolean {
                return oldItem == newItem
            }

        }

    }

    inner class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {

            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val detail = getItem(position)
                        if (detail != null) {
                            listener.onItemClickListener(detail)
                        }
                    }
                }
            }
        }


        fun bind(details: Details) {
            val circularProgressDrawable = CircularProgressDrawable(itemView.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            binding.apply {
                characterName.text = details.name
                val imgLink = details.image
                characterImg.load(imgLink) {
                    crossfade(true)
                    crossfade(1000)
                    placeholder(circularProgressDrawable)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
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


    interface OnItemClickListener {
        fun onItemClickListener(details: Details)
    }
}