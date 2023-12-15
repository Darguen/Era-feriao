package cl.brownarmoryelling.era_feriao.Background

import cl.brownarmoryelling.era_feriao.Classes.Feriado

interface ApiCallback
{
    fun onRequestComplete(result : String) : MutableList<Feriado>
}