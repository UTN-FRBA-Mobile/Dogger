package com.example.dogger

import android.icu.util.Calendar

object PaseoDataFactory{

    fun getPaseos(fecha : String) : List<Paseo> {
        val paseos = mutableListOf<Paseo>(
                                        Paseo(fecha, "10:00", "11:00", "Puchi", R.drawable.dog_footprint),
                                        Paseo(fecha, "10:00", "11:00", "Firulais", R.drawable.dog_footprint),
                                        Paseo(fecha, "10:00", "11:00", "Moncho", R.drawable.dog_footprint),
                                        Paseo(fecha, "10:00", "11:00", "Anga", R.drawable.dog_footprint),
                                        Paseo(fecha, "10:00", "11:00", "Canuto", R.drawable.dog_footprint),
                                        Paseo(fecha, "12:00", "13:00", "Boby", R.drawable.dog_footprint),
                                        Paseo(fecha, "12:00", "13:00", "Trapo", R.drawable.dog_footprint),
                                        Paseo(fecha, "12:00", "13:00", "Mugroso", R.drawable.dog_footprint),
                                        Paseo(fecha, "12:00", "13:00", "Pulgoso", R.drawable.dog_footprint),
                                        Paseo(fecha, "12:00", "13:00", "Manaos", R.drawable.dog_footprint)
                                    )
        return paseos
    }


}