package com.example.mentalistapp.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import com.example.mentalistapp.R
import com.example.mentalistapp.databinding.FragmentDetalleMomentoBinding
import com.example.mentalistapp.model.MomentoRepository

class DetalleMomentoFragment : Fragment() {

    private var _binding: FragmentDetalleMomentoBinding? = null
    private val binding get() = _binding!!
    private var player: ExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleMomentoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val momentoId = arguments?.getInt("momentoId") ?: 1
        val momento = MomentoRepository.momentoPorId(momentoId)

        binding.tituloDetalle.text = momento.titulo
        binding.subtituloDetalle.text = momento.subtitulo
        binding.descripcionDetalle.text = momento.descripcion

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val videoResId = resources.getIdentifier(
            momento.videoRawName, "raw", requireContext().packageName
        )

        if (videoResId != 0) {
            binding.videoPlaceholder.visibility = View.GONE
            binding.playerView.visibility = View.VISIBLE
            inicializarReproductor(videoResId)
        } else {
            binding.playerView.visibility = View.GONE
            binding.videoPlaceholder.visibility = View.VISIBLE
            binding.videoPlaceholder.text =
                getString(R.string.video_no_disponible) + "\n\nArchivo: res/raw/${momento.videoRawName}.mp4"
        }
    }

    private fun inicializarReproductor(videoResId: Int) {
        player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
            binding.playerView.player = exoPlayer
            val videoUri = Uri.parse(
                "android.resource://${requireContext().packageName}/$videoResId"
            )
            val mediaItem = MediaItem.fromUri(videoUri)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player?.release()
        player = null
        _binding = null
    }
}
