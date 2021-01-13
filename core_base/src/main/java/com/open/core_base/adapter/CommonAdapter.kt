package com.open.core_base.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class CommonAdapter : RecyclerView.Adapter<CommonViewHolder<*,*>>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder<*, *> {

    }

    override fun onBindViewHolder(holder: CommonViewHolder<*, *>, position: Int) {

    }

    override fun getItemCount(): Int {

    }

}

abstract class CommonViewHolder<B : ViewDataBinding, T : Any>(val binding: B) :
    RecyclerView.ViewHolder(binding.root), ViewHolderBinder<T>

interface ViewHolderBinder<T> {
    fun bind(item: T?)
}