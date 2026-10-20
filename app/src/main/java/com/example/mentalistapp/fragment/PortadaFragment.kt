package com.example.mentalistapp.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.mentalistapp.R
import com.example.mentalistapp.adapter.SliderAdapter
import com.example.mentalistapp.databinding.FragmentPortadaBinding

class PortadaFragment : Fragment() {

    private var _binding: FragmentPortadaBinding? = null
    private val binding get() = _binding!!

    private val handler = Handler(Looper.getMainLooper())
    private var autoScrollRunnable: Runnable? = null

    private val imagenes = listOf(
        R.drawable.slider1,
        R.drawable.slider2,
        R.drawable.slider3
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPortadaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SliderAdapter(imagenes)
        binding.viewPagerSlider.adapter = adapter
        binding.viewPagerSlider.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        crearDots(imagenes.size)

        binding.viewPagerSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                actualizarDots(position)
                reiniciarAutoScroll()
            }
        })

        binding.btnReproducir.setOnClickListener {
            findNavController().navigate(R.id.nav_momentos)
        }

        binding.btnInfo.setOnClickListener {
            findNavController().navigate(R.id.nav_acerca)
        }

        iniciarAutoScroll()
    }

    private fun crearDots(cantidad: Int) {
        binding.dotsContainer.removeAllViews()
        for (i in 0 until cantidad) {
            val dot = ImageView(requireContext())
            val params = ViewGroup.MarginLayoutParams(
                resources.getDimensionPixelSize(R.dimen.dot_size),
                resources.getDimensionPixelSize(R.dimen.dot_size)
            )
            params.marginStart = resources.getDimensionPixelSize(R.dimen.dot_margin)
            params.marginEnd = resources.getDimensionPixelSize(R.dimen.dot_margin)
            dot.layoutParams = params
            dot.setImageResource(if (i == 0) R.drawable.dot_active else R.drawable.dot_inactive)
            binding.dotsContainer.addView(dot)
        }
    }

    private fun actualizarDots(posicion: Int) {
        for (i in 0 until binding.dotsContainer.childCount) {
            val dot = binding.dotsContainer.getChildAt(i) as ImageView
            dot.setImageResource(if (i == posicion) R.drawable.dot_active else R.drawable.dot_inactive)
        }
    }

    private fun iniciarAutoScroll() {
        autoScrollRunnable = Runnable {
            val adapter = binding.viewPagerSlider.adapter ?: return@Runnable
            val siguiente = (binding.viewPagerSlider.currentItem + 1) % adapter.itemCount
            binding.viewPagerSlider.setCurrentItem(siguiente, true)
        }
        handler.postDelayed(autoScrollRunnable!!, AUTO_SCROLL_DELAY)
    }

    private fun reiniciarAutoScroll() {
        autoScrollRunnable?.let { handler.removeCallbacks(it) }
        autoScrollRunnable?.let { handler.postDelayed(it, AUTO_SCROLL_DELAY) }
    }

    override fun onPause() {
        super.onPause()
        autoScrollRunnable?.let { handler.removeCallbacks(it) }
    }

    override fun onResume() {
        super.onResume()
        reiniciarAutoScroll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        autoScrollRunnable?.let { handler.removeCallbacks(it) }
        _binding = null
    }

    companion object {
        private const val AUTO_SCROLL_DELAY = 4000L
    }
}
