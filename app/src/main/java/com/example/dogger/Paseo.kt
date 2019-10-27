package com.example.dogger

import android.icu.util.Calendar

class Paseo(fecha: String, horaRetiro: String, horaDevolucion: String, nombreMascota: String, fotoMascota: Int) {
    private var fecha: String = fecha
    private var horaRetiro: String = horaRetiro
    private var horaDevolucion: String = horaDevolucion
    var nombreMascota: String = nombreMascota
    var fotoMascota: Int = fotoMascota
}