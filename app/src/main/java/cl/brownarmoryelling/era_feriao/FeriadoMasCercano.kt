package cl.brownarmoryelling.era_feriao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import cl.brownarmoryelling.era_feriao.Adapters.FeriadoAdapter
import cl.brownarmoryelling.era_feriao.Api.FeriadosApi
import cl.brownarmoryelling.era_feriao.Background.ApiCallback
import cl.brownarmoryelling.era_feriao.Background.ApiTask
import cl.brownarmoryelling.era_feriao.Classes.Feriado
import cl.brownarmoryelling.era_feriao.Dialogs.FiltroDialog
import org.json.JSONArray
import org.json.JSONException

class FeriadoMasCercano : AppCompatActivity(), ApiCallback {

    private lateinit var adapter : ArrayAdapter<Feriado>
    private lateinit var adapterElements : FeriadoAdapter
    private lateinit var feriados : MutableList<Feriado>
    private lateinit var feriadosLV : ListView

    private val filtroDialog = FiltroDialog(this)
    private val feriadosApi = FeriadosApi()
    private var URL: String = "https://apis.digital.gob.cl/fl/feriados/2013"
    private  var newUrl = URL
    private lateinit var option: String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feriado_mas_cercano)

        option = ""
        filtroDialog.showOptionsDialog()

        configureFeriadosLV()

        filtroDialog.setDialogCallback(object : FiltroDialog.DialogCallback {
            override fun onOptionSelected(selectedOption: String) {
                // Handle the selected option here
                println("Selected Option: $selectedOption")
                 option = selectedOption
                Log.i("Option", option)
                newUrl = feriadosApi.getData(option, "5")
                Log.i("NewURL", newUrl)

                val apiRequestTask = ApiTask(this@FeriadoMasCercano)
                apiRequestTask.execute(newUrl)
                Log.i("PostNewURL", newUrl)
            }
        })
        
    }

    private fun configureFeriadosLV()
    {
        feriadosLV = findViewById(R.id.nearHolidaysLV)
        adapter = ArrayAdapter<Feriado>(this, android.R.layout.simple_list_item_1)
        feriadosLV.adapter = adapter
    }

    fun processingData(result: String): MutableList<Feriado> {

        val list: MutableList<Feriado> = mutableListOf()
        Log.i("FeriadosApi", "JSON Response: $result")

        try {

            val jsonArray = JSONArray(result)
            for (i in 0 until jsonArray.length()) {
                val feriadoObject = jsonArray.getJSONObject(i)

                val nombre = feriadoObject.getString("nombre")
                val comentarios = feriadoObject.getString("comentarios")
                val fecha = feriadoObject.getString("fecha")
                val irrenunciable = feriadoObject.getString("irrenunciable")
                val tipo = feriadoObject.getString("tipo")

                val leyesArray = feriadoObject.getJSONArray("leyes")
                var leyesNombre: ArrayList<String>? = arrayListOf()
                var leyesURL: ArrayList<String>? = arrayListOf()

                for (j in 0 until leyesArray.length()) {
                    val leyObject = leyesArray.getJSONObject(j)
                    val leyNombre = leyObject.getString("nombre")
                    val leyUrl = leyObject.getString("url")

                    leyesNombre?.add(leyNombre)
                    leyesURL?.add(leyUrl)
                }

                val feriado = Feriado(nombre, comentarios, fecha, irrenunciable.toBoolean(), tipo, leyesNombre, leyesURL)
                list.add(feriado)
                Log.i("FeriadosApi","amount of list:" + list.size.toString())
            }


        } catch (e: JSONException) {
            e.printStackTrace()
            list.add(Feriado("", "", "", false, "", arrayListOf(), arrayListOf()))
        }

        return list.toMutableList()
    }

    override fun onRequestComplete(result: String): MutableList<Feriado>
    {
        feriados = processingData(result)

        setAdapter()

        return feriados
    }

    private fun setAdapter()
    {
        feriadosLV.invalidate()
        adapterElements = FeriadoAdapter(this, R.layout.feriado_view_search, feriados)
        feriadosLV.adapter = adapterElements
    }

}