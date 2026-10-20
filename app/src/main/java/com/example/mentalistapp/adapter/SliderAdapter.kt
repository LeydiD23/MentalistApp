package com.example.mentalistapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mentalistapp.databinding.ItemSliderBinding

class SliderAdapter(private val imagenes: List<Int>) :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    class SliderViewHolder(val binding: ItemSliderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val binding = ItemSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.binding.imageSlider.setImageResource(imagenes[position])
    }

    override fun getItemCount(): Int = imagenes.size
}
