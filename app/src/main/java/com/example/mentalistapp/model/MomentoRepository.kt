package com.example.mentalistapp.model

import com.example.mentalistapp.R

object MomentoRepository {
        fun momentos(): List<Momento> = listOf(
            Momento(
                id = 1,
                titulo = "El método de Jane",
                subtitulo = "Resolver casos con solo observar",
                imagenRes = R.drawable.momento1,
                descripcion = "Patrick Jane no es policía, pero su capacidad de observación es infalible. Mientras el equipo del CBI sigue pistas convencionales, Jane descubre la verdad en un detalle mínimo: una mentira, un gesto o una incongruencia en la escena. Este momento muestra cómo su mente convierte lo que todos pasan por alto en la clave para resolver el caso.",
                videoRawName = "video1"
            ),
            Momento(
                id = 2,
                titulo = "El equipo del CBI",
                subtitulo = "Jane, Lisbon, Cho, Rigsby y Van Pelt",
                imagenRes = R.drawable.momento2,
                descripcion = "Más que un grupo de agentes, el equipo del CBI funciona como una familia disfuncional. Lisbon mantiene el orden, Cho aporta la frialdad analítica, Rigsby la fuerza y Van Pelt la intuición. Cuando trabajan juntos, complementan cada debilidad y se convierten en una de las unidades más efectivas... a pesar de Jane.",
                videoRawName = "video2"
            ),
            Momento(
                id = 3,
                titulo = "Teresa y Patrick",
                subtitulo = "El vínculo que creció temporada tras temporada",
                imagenRes = R.drawable.momento3,
                descripcion = "La relación entre Teresa Lisbon y Patrick Jane es el corazón de la serie. Lo que empieza como una alianza profesional marcada por las diferencias se transforma en confianza, complicidad y, finalmente, en un amor que ninguno de los dos se atreve a admitir. Este momento reúne los instantes que definen su vínculo a lo largo de las siete temporadas.",
                videoRawName = "video4"
            )
        )

        fun momentoPorId(id: Int): Momento =
            momentos().first { it.id == id }
}
