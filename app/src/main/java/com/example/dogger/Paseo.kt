package com.example.dogger


import java.io.Serializable

data class Paseo(
    val id_paseador: String  = "",
    val fecha_paseo: String  = "",
    val hora_retiro: String = "",
    val hora_devolucion: String  = "",
    val nombre_mascota: String  = "",
    val direccion_mascota: String = "",
    val foto_mascota: Int = 0
): Serializable
