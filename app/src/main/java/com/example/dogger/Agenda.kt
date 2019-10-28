package com.example.dogger

import android.icu.util.Calendar

class Agenda(fecha: Calendar, paseos: List<Paseo>) {
    private var fecha: Calendar = fecha
    private var paseos: List<Paseo> = paseos
}