package cl.brownarmoryelling.era_feriao.Api

import cl.brownarmoryelling.era_feriao.Background.ApiCallback
import cl.brownarmoryelling.era_feriao.Background.ApiTask
import cl.brownarmoryelling.era_feriao.Classes.Feriado
import org.json.JSONArray
import org.json.JSONException

class FeriadosApi : ApiCallback
{
    private var URL : String = "https://apis.digital.gob.cl/fl/feriados"

    private fun apiRequest(URL : String, callback : ApiCallback) : MutableList<Feriado>
    {
        val apiRequestTask = ApiTask(callback)
        return apiRequestTask.execute(URL)
    }

    fun getData(year : String, month : String, day : String, limit : String, offset : String) : MutableList<Feriado>
    {
        val url = "$URL/$year/$month/$day?limit=$limit&offset=$offset"
        return apiRequest(url, this)
    }

    fun getData(year : String, month : String, limit : String, offset : String) : MutableList<Feriado>
    {
        val url = "$URL/$year/$month?limit=$limit&offset=$offset"
        return apiRequest(url, this)
    }

    fun getData(year : String, limit : String, offset : String) : MutableList<Feriado>
    {
        val url = "$URL/$year?limit=$limit&offset=$offset"
        return apiRequest(url, this)
    }

    private fun processingData(result : String) : MutableList<Feriado>
    {
        val list : MutableList<Feriado> = mutableListOf()

        try
        {
            val jsonArray = JSONArray(result)

            for (i in 0 until jsonArray.length())
            {
                val jsonObject = jsonArray.getJSONObject(i)

                val nombre = jsonObject.getString("nombre")
                val comentarios = jsonObject.getString("comentarios")
                val fecha = jsonObject.getString("fecha")
                val irrenunciable = jsonObject.getString("irrenunciable")
                val tipo = jsonObject.getString("tipo")

                val leyesArray = jsonObject.getJSONArray("leyes")
                var leyesNombre : ArrayList<String>? = arrayListOf()
                var leyesURL : ArrayList<String>? = arrayListOf()

                for (j in 0 until leyesArray.length())
                {
                    val leyObject = leyesArray.getJSONObject(j)
                    val leyNombre = leyObject.getString("nombre")
                    val leyUrl = leyObject.getString("url")

                    leyesNombre?.add(leyNombre)
                    leyesURL?.add(leyUrl)
                }

                val feriado = Feriado(nombre, comentarios, fecha, irrenunciable.toBoolean(), tipo, leyesNombre, leyesURL)
                list.add(feriado)
            }
        }
        catch(e : JSONException)
        {
            e.printStackTrace()
            list.add(Feriado("", "", "", false, "", arrayListOf(), arrayListOf()))
        }

        return list.toMutableList()
    }

    override fun onRequestComplete(result: String) : MutableList<Feriado>
    {
        return processingData(result)
    }
}