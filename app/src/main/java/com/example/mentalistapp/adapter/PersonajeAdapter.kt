package com.example.mentalistapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mentalistapp.databinding.ItemPersonajeBinding
import com.example.mentalistapp.model.Personaje

class PersonajeAdapter(
    private val personajes: List<Personaje>,
    private val onItemClick: (Personaje) -> Unit
) : RecyclerView.Adapter<PersonajeAdapter.PersonajeViewHolder>() {

    class PersonajeViewHolder(val binding: ItemPersonajeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeViewHolder {
        val binding = ItemPersonajeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonajeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonajeViewHolder, position: Int) {
        val personaje = personajes[position]
        holder.binding.imagePersonaje.setImageResource(personaje.imagenRes)
        holder.binding.nombrePersonaje.text = personaje.nombre
        holder.binding.rolPersonaje.text = personaje.rol
        holder.binding.root.setOnClickListener { onItemClick(personaje) }
    }

    override fun getItemCount(): Int = personajes.size
}
