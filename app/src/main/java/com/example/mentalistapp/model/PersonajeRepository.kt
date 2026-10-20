package com.example.mentalistapp.model

import com.example.mentalistapp.R

object PersonajeRepository {
        fun personajes(): List<Personaje> = listOf(
            Personaje(
                id = 1,
                nombre = "Patrick Jane",
                actor = "Simon Baker",
                rol = "Consultor del CBI / FBI",
                imagenRes = R.drawable.personaje_jane,
                descripcion = "Ex médium y famoso personaje de televisión. Tras el asesinato de su esposa e hija a manos de Red John, abandonó la farsa de la pseudociencia y se convirtió en consultor independiente. Su memoria eidética, observación hiperdetallada y manipulación psicológica lo convierten en un genio para resolver crímenes, aunque su arrogancia y su obsesión por vengarse de Red John lo colocan siempre al límite."
            ),
            Personaje(
                id = 2,
                nombre = "Teresa Lisbon",
                actor = "Robin Tunney",
                rol = "Agente / Supervisora del CBI",
                imagenRes = R.drawable.personaje_lisbon,
                descripcion = "Directora del equipo del CBI y más tarde agente especial del FBI. Mujer firme, pragmática y protectora, es la única capaz de mantener a raya a Jane. A pesar de su profesionalismo y de las tensiones constantes, desarrolla una profunda confianza y afecto hacia él, hasta que su relación da un giro decisivo en las últimas temporadas."
            ),
            Personaje(
                id = 3,
                nombre = "Kimball Cho",
                actor = "Tim Kang",
                rol = "Agente del CBI / FBI",
                imagenRes = R.drawable.personaje_cho,
                descripcion = "Agente metódico, callado y letalmente eficiente. Antes de unirse al CBI tuvo un pasado turbulento que prefiere no comentar. Es el miembro más equilibrado del equipo y el que más confía en las capacidades de Jane, actuando a menudo como mediador entre el consultor y el resto del grupo."
            ),
            Personaje(
                id = 4,
                nombre = "Wayne Rigsby",
                actor = "Owain Yeoman",
                rol = "Agente del CBI",
                imagenRes = R.drawable.personaje_rigsby,
                descripcion = "Agente entusiasta y algo impulsivo, experto en fuego y escenas del crimen. Su romance con Grace Van Pelt es uno de los hilos románticos de la serie. Aunque a veces se deja llevar por sus emociones, es un miembro leal y valiente del equipo."
            ),
            Personaje(
                id = 5,
                nombre = "Grace Van Pelt",
                actor = "Amanda Righetti",
                rol = "Analista / Agente del CBI",
                imagenRes = R.drawable.personaje_vanpelt,
                descripcion = "Analista de inteligencia del equipo, observadora y con gran intuición. Al principio escéptica de los métodos de Jane, poco a poco se convierte en una de sus aliadas más cercanas. Su relación con Rigsby atraviesa altibajos a lo largo de las siete temporadas."
            )
        )

        fun personajePorId(id: Int): Personaje =
            personajes().first { it.id == id }
}
