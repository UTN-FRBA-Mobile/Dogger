package com.example.dogger

class Mascota(nombre: String, foto: Int, nombre_duenio: String, foto_duenio: Int) {
    var nombre: String = ""
    var foto: Int = 0
    var nombre_duenio: String = ""
    var foto_duenio: Int = 0

    init {
        this.nombre = nombre
        this.foto = foto
        this.nombre_duenio = nombre_duenio
        this.foto_duenio = foto_duenio
    }
}