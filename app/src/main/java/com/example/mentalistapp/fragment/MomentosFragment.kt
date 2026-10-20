package com.example.mentalistapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mentalistapp.R
import com.example.mentalistapp.adapter.MomentoAdapter
import com.example.mentalistapp.databinding.FragmentMomentosBinding
import com.example.mentalistapp.model.MomentoRepository

class MomentosFragment : Fragment() {

    private var _binding: FragmentMomentosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMomentosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MomentoAdapter(MomentoRepository.momentos()) { momento ->
            val bundle = Bundle().apply { putInt("momentoId", momento.id) }
            findNavController().navigate(R.id.detalleMomento, bundle)
        }

        binding.recyclerMomentos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMomentos.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
