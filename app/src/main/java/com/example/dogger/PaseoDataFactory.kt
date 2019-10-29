package com.example.dogger


object PaseoDataFactory{

    fun getPaseos(fecha : String) : List<Paseo> {
        val paseos = mutableListOf<Paseo>(
                        Paseo(fecha, "10:00", "11:00", "Puchi", "Emilio Lamarca", R.drawable.dog_footprint),
                        Paseo(fecha, "10:00", "11:00", "Firulais", "Av. San Martin", R.drawable.dog_footprint),
                        Paseo(fecha, "10:00", "11:00", "Moncho", "Mercedes", R.drawable.dog_footprint),
                        Paseo(fecha, "10:00", "11:00", "Anga", "Gabriela Mistral", R.drawable.dog_footprint),
                        Paseo(fecha, "10:00", "11:00", "Canuto", "Av. Mosconi", R.drawable.dog_footprint),
                        Paseo(fecha, "12:00", "13:00", "Boby", "Ladines", R.drawable.dog_footprint),
                        Paseo(fecha, "12:00", "13:00", "Trapo", "Griveo", R.drawable.dog_footprint),
                        Paseo(fecha, "12:00", "13:00", "Mugroso", "Carlos A. Lopez", R.drawable.dog_footprint),
                        Paseo(fecha, "12:00", "13:00", "Pulgoso", "Gualeguaychu", R.drawable.dog_footprint),
                        Paseo(fecha, "12:00", "13:00", "Manaos", "Sanabria", R.drawable.dog_footprint)
                                    )
        return paseos
    }


}