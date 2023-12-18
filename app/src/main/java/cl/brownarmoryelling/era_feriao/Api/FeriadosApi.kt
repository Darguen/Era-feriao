package cl.brownarmoryelling.era_feriao.Api

import android.util.Log
import cl.brownarmoryelling.era_feriao.Background.ApiCallback
import cl.brownarmoryelling.era_feriao.Background.ApiTask
import cl.brownarmoryelling.era_feriao.Classes.Feriado
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class FeriadosApi  {
    private var URL: String = "https://apis.digital.gob.cl/fl/feriados"
    private lateinit var resultList: MutableList<Feriado>


    fun getData(year: String, month: String, day: String, limit: String, offset: String): String {
        val url = "$URL/$year/$month/$day?limit=$limit&offset=$offset"
        return url
    }

    fun getData(year: String, month: String, limit: String, offset: String): String {
        val url = "$URL/$year/$month?limit=$limit&offset=$offset"
        return url
    }

    fun getData(year: String, limit: String, offset: String): String {
        val url = "$URL/$year?limit=$limit&offset=$offset"
        return url
    }

    fun getData(year: String): String {
        val url = "$URL/$year"
        return url
    }

    fun getData(year: String, limit: String): String {
        val url = "$URL/$year?limit=$limit"
        return url
    }





}
