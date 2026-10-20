package com.example.mentalistapp.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.mentalistapp.R
import com.example.mentalistapp.databinding.FragmentContratameBinding
import java.io.File

class ContratameFragment : Fragment() {

    private var _binding: FragmentContratameBinding? = null
    private val binding get() = _binding!!

    private val nombrePdf = "cv_leydi.pdf"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContratameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnVerCv.setOnClickListener { abrirPdf() }

        binding.btnAcerca.setOnClickListener {
            androidx.navigation.fragment.NavHostFragment.findNavController(this)
                .navigate(R.id.nav_acerca)
        }

        binding.contrataCorreo.setOnClickListener { enviarCorreo() }
        binding.contrataTelefono.setOnClickListener { llamar() }
    }

    private fun copiarPdfDesdeAssets(): File? {
        return try {
            val destino = File(requireContext().cacheDir, nombrePdf)
            if (!destino.exists() || destino.length() == 0L) {
                requireContext().assets.open(nombrePdf).use { entrada ->
                    destino.outputStream().use { salida ->
                        entrada.copyTo(salida)
                    }
                }
            }
            destino
        } catch (e: Exception) {
            null
        }
    }

    private fun abrirPdf() {
        val archivo = copiarPdfDesdeAssets()
        if (archivo == null) {
            Toast.makeText(requireContext(), R.string.contrata_pdf_error, Toast.LENGTH_SHORT).show()
            return
        }

        val uri: Uri = FileProvider.getUriForFile(
            requireContext(),
            "com.example.mentalistapp.fileprovider",
            archivo
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), R.string.contrata_pdf_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun enviarCorreo() {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${getString(R.string.contrata_correo)}"))
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), R.string.contrata_pdf_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun llamar() {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${getString(R.string.contrata_telefono)}"))
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), R.string.contrata_pdf_error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
