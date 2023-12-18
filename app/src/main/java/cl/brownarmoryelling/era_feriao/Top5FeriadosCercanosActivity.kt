package cl.brownarmoryelling.era_feriao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import cl.brownarmoryelling.era_feriao.Adapters.FeriadoAdapter
import cl.brownarmoryelling.era_feriao.Api.FeriadosApi
import cl.brownarmoryelling.era_feriao.Background.ApiCallback
import cl.brownarmoryelling.era_feriao.Background.ApiTask
import cl.brownarmoryelling.era_feriao.Classes.Feriado
import cl.brownarmoryelling.era_feriao.Dialogs.FiltroDialog
import org.json.JSONArray
import org.json.JSONException

class Top5FeriadosCercanosActivity : AppCompatActivity(), ApiCallback
{
    private lateinit var adapter : ArrayAdapter<Feriado>
    private lateinit var adapterElements : FeriadoAdapter
    private val filtroDialog = FiltroDialog(this)
    private lateinit var feriados : MutableList<Feriado>
    private val feriadosApi = FeriadosApi()
    private lateinit var feriadosLV : ListView
    private lateinit var option : String

    private var URL: String = "https://apis.digital.gob.cl/fl/feriados/2013"
    private var newUrl = URL

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top5_feriados_cercanos)

        configureFeriadosLV()
        filtroDialog.showAnioDialog()

        filtroDialog.setDialogCallback(object : FiltroDialog.DialogCallback {

            override fun onOptionSelected(selectedOption: String)
            {
                println("Selected Option: $selectedOption")
                option = selectedOption
                Log.i("Option", option)
                newUrl = feriadosApi.getData(option, "5")
                Log.i("NewURL", newUrl)

                val apiRequestTask = ApiTask(this@Top5FeriadosCercanosActivity)
                apiRequestTask.execute(newUrl)
                Log.i("PostNewURL", newUrl)
            }

        })
    }

    private fun configureFeriadosLV()
    {
        feriadosLV = findViewById(R.id.feriadosLV)

        adapter = ArrayAdapter<Feriado>(this, android.R.layout.simple_list_item_1)
        feriadosLV.adapter = adapter
    }

    fun processingData(result : String) : MutableList<Feriado>
    {
        val list : MutableList<Feriado> = mutableListOf()

        try
        {
            val jsonArray = JSONArray(result)

            for(i in 0 until jsonArray.length())
            {
                val feriadoObject = jsonArray.getJSONObject(i)

                val nombre = feriadoObject.getString("nombre")
                val comentarios = feriadoObject.getString("comentarios")
                val fecha = feriadoObject.getString("fecha")
                val irrenunciable = feriadoObject.getString("irrenunciable")
                val tipo = feriadoObject.getString("tipo")

                val leyesArray = feriadoObject.getJSONArray("leyes")
                var leyesNombre: ArrayList<String>? = arrayListOf()
                var leyesURL: ArrayList<String>? = arrayListOf()

                for(j in 0 until leyesArray.length())
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
        catch(e: JSONException)
        {
            e.printStackTrace()
            list.add(Feriado("", "", "", false, "", arrayListOf(), arrayListOf()))
        }

        return list.toMutableList()
    }

    private fun setAdapter()
    {
        feriadosLV.invalidate()
        adapterElements = FeriadoAdapter(this, R.layout.feriado_view_search, feriados)
        feriadosLV.adapter = adapterElements
    }

    override fun onRequestComplete(result: String): MutableList<Feriado>
    {
        feriados = processingData(result)

        setAdapter()

        return feriados
    }
}