package com.example.mentalistapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mentalistapp.databinding.ItemMomentoBinding
import com.example.mentalistapp.model.Momento

class MomentoAdapter(
    private val momentos: List<Momento>,
    private val onItemClick: (Momento) -> Unit
) : RecyclerView.Adapter<MomentoAdapter.MomentoViewHolder>() {

    class MomentoViewHolder(val binding: ItemMomentoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentoViewHolder {
        val binding = ItemMomentoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MomentoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MomentoViewHolder, position: Int) {
        val momento = momentos[position]
        holder.binding.imageMomento.setImageResource(momento.imagenRes)
        holder.binding.tituloMomento.text = momento.titulo
        holder.binding.subtituloMomento.text = momento.subtitulo
        holder.binding.root.setOnClickListener { onItemClick(momento) }
    }

    override fun getItemCount(): Int = momentos.size
}
