package com.example.dogger

import java.io.Serializable

data class Mascota(
    val id_mascota: Int = 0,
    val nombre: String = "",
    val foto: Int = 0,
    val lat: Double = 0.0,
    val lon: Double = 0.0,

    val nombre_duenio: String = "",
    val foto_duenio: Int = 0,
    val celular: String = "",

    val id_paseador: Int = 0
): Serializable