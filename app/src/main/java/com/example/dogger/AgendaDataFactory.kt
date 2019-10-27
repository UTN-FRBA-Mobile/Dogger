package com.example.dogger

import android.icu.text.DateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi

object AgendaDataFactory{

    @RequiresApi(Build.VERSION_CODES.N)
    fun getAgenda() : List<Dia> {
        val fecha = Calendar.getInstance()
        val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
        val agenda  = mutableListOf<Dia>()

        //Paseos del dia de hoy
        agenda.add(Dia(dateFormatter.format(fecha.time), PaseoDataFactory.getPaseos(dateFormatter.format(fecha.time))))

        //Paseos del dia de maniana
        fecha.add(Calendar.DAY_OF_WEEK, 1)
        agenda.add(Dia(dateFormatter.format(fecha.time), PaseoDataFactory.getPaseos(dateFormatter.format(fecha.time))))

        return agenda
    }
}