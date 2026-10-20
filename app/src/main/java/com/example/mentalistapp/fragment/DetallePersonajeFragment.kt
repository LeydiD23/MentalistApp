package com.example.mentalistapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mentalistapp.databinding.FragmentDetallePersonajeBinding
import com.example.mentalistapp.model.PersonajeRepository

class DetallePersonajeFragment : Fragment() {

    private var _binding: FragmentDetallePersonajeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetallePersonajeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val personajeId = arguments?.getInt("personajeId") ?: 1
        val personaje = PersonajeRepository.personajePorId(personajeId)

        binding.imageDetalle.setImageResource(personaje.imagenRes)
        binding.nombreDetalle.text = personaje.nombre
        binding.actorDetalle.text = "Interpretado por ${personaje.actor}"
        binding.rolDetalle.text = personaje.rol
        binding.descripcionDetalle.text = personaje.descripcion

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
