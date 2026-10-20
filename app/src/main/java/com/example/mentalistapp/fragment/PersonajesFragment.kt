package com.example.mentalistapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mentalistapp.R
import com.example.mentalistapp.adapter.PersonajeAdapter
import com.example.mentalistapp.databinding.FragmentPersonajesBinding
import com.example.mentalistapp.model.PersonajeRepository

class PersonajesFragment : Fragment() {

    private var _binding: FragmentPersonajesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonajesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PersonajeAdapter(PersonajeRepository.personajes()) { personaje ->
            val bundle = Bundle().apply { putInt("personajeId", personaje.id) }
            findNavController().navigate(R.id.detallePersonaje, bundle)
        }

        binding.recyclerPersonajes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerPersonajes.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
