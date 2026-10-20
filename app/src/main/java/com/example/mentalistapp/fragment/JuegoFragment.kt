package com.example.mentalistapp.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mentalistapp.R
import com.example.mentalistapp.databinding.FragmentJuegoBinding
import com.example.mentalistapp.databinding.ItemCartaBinding
import kotlin.random.Random

class JuegoFragment : Fragment() {

    private var _binding: FragmentJuegoBinding? = null
    private val binding get() = _binding!!

    private val rondas = listOf(
        RondaConfig(numCartas = 3, numMezclas = 5, duracionMezcla = 700L),
        RondaConfig(numCartas = 3, numMezclas = 8, duracionMezcla = 420L),
        RondaConfig(numCartas = 4, numMezclas = 10, duracionMezcla = 380L),
        RondaConfig(numCartas = 5, numMezclas = 12, duracionMezcla = 320L)
    )

    private data class RondaConfig(
        val numCartas: Int,
        val numMezclas: Int,
        val duracionMezcla: Long
    )

    private data class Carta(
        val view: View,
        val icono: android.widget.ImageView,
        var x: Float,
        val ancho: Int
    )

    private var rondaActual = 0
    private var vidas = 3
    private var cartaObjetivo: Carta? = null
    private var mezclando = false
    private var esperandoToque = false

    private val cartas = mutableListOf<Carta>()
    private var anchoCarta = 0
    private var separacion = 0f
    private var centroY = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJuegoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actualizarHud()
        binding.btnAccion.setOnClickListener { iniciarJuego() }
        binding.btnAccion.text = getString(R.string.juego_jugar)
        binding.txtEstado.text = getString(R.string.juego_instruccion)
    }

    private fun iniciarJuego() {
        rondaActual = 0
        vidas = 3
        binding.btnAccion.isEnabled = false
        binding.btnAccion.text = getString(R.string.juego_mezclando)
        actualizarHud()
        prepararRonda()
    }

    private fun prepararRonda() {
        val config = rondas[rondaActual]
        binding.contenedorCartas.post {
            construirCartas(config.numCartas)
            mostrarObjetivo()
        }
    }

    private fun construirCartas(numCartas: Int) {
        binding.contenedorCartas.removeAllViews()
        cartas.clear()

        anchoCarta = resources.getDimensionPixelSize(R.dimen.carta_ancho)
        separacion = resources.getDimensionPixelSize(R.dimen.carta_separacion).toFloat()
        centroY = 0f

        val totalAncho = numCartas * anchoCarta + (numCartas - 1) * separacion
        val startX = (binding.contenedorCartas.width - totalAncho) / 2f

        for (i in 0 until numCartas) {
            val itemBinding = ItemCartaBinding.inflate(layoutInflater, binding.contenedorCartas, false)
            val root = itemBinding.root
            root.layoutParams = FrameLayout.LayoutParams(
                anchoCarta, resources.getDimensionPixelSize(R.dimen.carta_alto)
            )
            root.translationX = startX + i * (anchoCarta + separacion)
            root.translationY = 0f
            binding.contenedorCartas.addView(root)

            val carta = Carta(
                view = root,
                icono = itemBinding.cartaIcono,
                x = root.translationX,
                ancho = anchoCarta
            )
            cartas.add(carta)
        }
    }

    private fun mostrarObjetivo() {
        binding.txtEstado.text = getString(R.string.juego_mira)
        for ((index, carta) in cartas.withIndex()) {
            carta.view.setBackgroundResource(R.drawable.card_back)
            carta.icono.setImageResource(R.drawable.ic_card_logo)
            carta.view.alpha = 1f
            carta.view.isClickable = false
        }
        val objetivo = cartas[Random.nextInt(cartas.size)]
        cartaObjetivo = objetivo
        objetivo.view.setBackgroundResource(R.drawable.card_jane)
        objetivo.icono.setImageResource(R.drawable.ic_card_jane)

        objetivo.view.animate()
            .scaleX(1.12f).scaleY(1.12f).setDuration(350)
            .withEndAction {
                objetivo.view.animate().scaleX(1f).scaleY(1f).setDuration(250).start()
            }
            .start()

        objetivo.view.postDelayed({
            ocultarObjetivo()
            mezclar()
        }, 2200)
    }

    private fun ocultarObjetivo() {
        for (carta in cartas) {
            carta.view.setBackgroundResource(R.drawable.card_back)
            carta.icono.setImageResource(R.drawable.ic_card_logo)
        }
    }

    private fun mezclar() {
        val config = rondas[rondaActual]
        mezclando = true
        binding.txtEstado.text = getString(R.string.juego_mezclando)
        animarIntercambios(config.numMezclas, config.duracionMezcla, 0)
    }

    private fun animarIntercambios(total: Int, duracion: Long, hechas: Int) {
        if (hechas >= total) {
            mezclando = false
            esperandoToque = true
            binding.txtEstado.text = getString(R.string.juego_elige)
            habilitarToques()
            return
        }

        val a = Random.nextInt(cartas.size)
        var b = Random.nextInt(cartas.size)
        while (b == a) b = Random.nextInt(cartas.size)

        val cartaA = cartas[a]
        val cartaB = cartas[b]
        val xa = cartaA.view.translationX
        val xb = cartaB.view.translationX

        val animA = ObjectAnimator.ofFloat(cartaA.view, View.TRANSLATION_X, xa, xb)
        val animB = ObjectAnimator.ofFloat(cartaB.view, View.TRANSLATION_X, xb, xa)
        animA.duration = duracion
        animB.duration = duracion
        animA.interpolator = AccelerateDecelerateInterpolator()
        animB.interpolator = AccelerateDecelerateInterpolator()

        val temp = cartas[a].x
        cartas[a].x = cartas[b].x
        cartas[b].x = temp

        val set = android.animation.AnimatorSet()
        set.playTogether(animA, animB)
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                animarIntercambios(total, duracion, hechas + 1)
            }
        })
        set.start()
    }

    private fun habilitarToques() {
        for (carta in cartas) {
            carta.view.isClickable = true
            carta.view.setOnClickListener {
                if (!esperandoToque) return@setOnClickListener
                esperandoToque = false
                deshabilitarToques()
                verificarEleccion(carta)
            }
        }
    }

    private fun deshabilitarToques() {
        for (carta in cartas) {
            carta.view.isClickable = false
        }
    }

    private fun verificarEleccion(tocada: Carta) {
        val ganaste = tocada === cartaObjetivo

        if (ganaste) {
            tocada.view.setBackgroundResource(R.drawable.card_jane)
            tocada.icono.setImageResource(R.drawable.ic_card_jane)
            binding.txtEstado.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.juego_exito)
            )
            binding.txtEstado.text = getString(R.string.juego_acierto)
            vibrar(40)
        } else {
            tocada.view.setBackgroundResource(R.drawable.card_error)
            binding.txtEstado.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.juego_error)
            )
            binding.txtEstado.text = getString(R.string.juego_fallo)
            cartaObjetivo?.let { correcta ->
                correcta.view.setBackgroundResource(R.drawable.card_jane)
                correcta.icono.setImageResource(R.drawable.ic_card_jane)
            }
            vibrar(120)
        }

        binding.contenedorCartas.postDelayed({
            if (ganaste) {
                rondaActual++
                binding.txtEstado.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.text_primary)
                )
                if (rondaActual >= rondas.size) {
                    mostrarVictoria()
                } else {
                    actualizarHud()
                    binding.contenedorCartas.postDelayed({ prepararRonda() }, 600)
                }
            } else {
                vidas--
                binding.txtEstado.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.text_primary)
                )
                actualizarHud()
                if (vidas <= 0) {
                    mostrarDerrota()
                } else {
                    binding.contenedorCartas.postDelayed({ prepararRonda() }, 900)
                }
            }
        }, 1200)
    }

    private fun actualizarHud() {
        binding.txtRonda.text = getString(
            R.string.juego_ronda, (rondaActual + 1).coerceAtMost(rondas.size), rondas.size
        )
        binding.txtVidas.text = getString(R.string.juego_vidas) + ": " + "❤".repeat(vidas.coerceAtLeast(0))
        binding.progreso.max = rondas.size
        binding.progreso.progress = rondaActual
    }

    private fun mostrarVictoria() {
        binding.progreso.progress = rondas.size
        binding.txtEstado.setTextColor(
            ContextCompat.getColor(requireContext(), R.color.juego_exito)
        )
        binding.txtEstado.text = getString(R.string.juego_victoria)
        binding.btnAccion.isEnabled = true
        binding.btnAccion.text = getString(R.string.juego_reintentar)
        vibrar(200)
    }

    private fun mostrarDerrota() {
        binding.txtEstado.setTextColor(
            ContextCompat.getColor(requireContext(), R.color.juego_error)
        )
        binding.txtEstado.text = getString(R.string.juego_derrota)
        binding.btnAccion.isEnabled = true
        binding.btnAccion.text = getString(R.string.juego_reintentar)
        vibrar(300)
    }

    private fun vibrar(ms: Long) {
        val vibrador = ContextCompat.getSystemService(requireContext(), Vibrator::class.java)
            ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrador.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrador.vibrate(ms)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
