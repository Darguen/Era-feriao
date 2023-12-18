package cl.brownarmoryelling.era_feriao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import cl.brownarmoryelling.era_feriao.Api.FeriadosApi
import cl.brownarmoryelling.era_feriao.Background.ApiCallback
import cl.brownarmoryelling.era_feriao.Background.ApiTask
import cl.brownarmoryelling.era_feriao.Classes.Feriado
import cl.brownarmoryelling.era_feriao.Dialogs.FiltroDialog
import org.json.JSONArray
import org.json.JSONException

class FeriadoMasCercano : AppCompatActivity(), ApiCallback {
    private val filtroDialog = FiltroDialog(this)
    private val feriadosApi = FeriadosApi()
    private var URL: String = "https://apis.digital.gob.cl/fl/feriados/2013"
    private lateinit var nombreTextView: TextView
    private lateinit var comentariosTextView: TextView
    private lateinit var fechaTextView: TextView
    private lateinit var irrenunciableTextView: TextView
    private lateinit var tipoTextView: TextView
    private lateinit var leyesTextView: TextView
    private lateinit var urlLeyesTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feriado_mas_cercano)

        nombreTextView = findViewById(R.id.feriadoNameTV)
        comentariosTextView = findViewById(R.id.feriadoCommentsTV)
        fechaTextView = findViewById(R.id.feriadoDateTV)
        irrenunciableTextView = findViewById(R.id.feriadoInalienableTV)
        tipoTextView = findViewById(R.id.feriadoTypeTV)
        leyesTextView = findViewById(R.id.feriadoLawsTV)
        urlLeyesTextView = findViewById(R.id.feriadoLawsUrlTV)
        //filtroDialog.showAnioDialog()

        //Log.i("getAnio", filtroDialog.getAnio())

        /*when (filtroDialog.getAnio()) {
            "2013" -> {
                val apiResponseActivity = Intent(this, Faq::class.java)
                startActivity(apiResponseActivity)
            }


        }*/

        val apiRequestTask = ApiTask(this)
        apiRequestTask.execute(URL)
    }

    private fun apiRequest(URL: String, callback: ApiCallback): MutableList<Feriado> {
        val apiRequestTask = ApiTask(callback)
        return apiRequestTask.execute(URL)
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

                nombreTextView.text = "Nombre: $nombre"
                comentariosTextView.text = "Comentarios: $comentarios"
                fechaTextView.text = "Fecha: $fecha"
                irrenunciableTextView.text = "Irrenuncialbe: $irrenunciable"
                tipoTextView.text = "Tipo: $tipo"

                val leyesArray = feriadoObject.getJSONArray("leyes")
                var leyesNombre: ArrayList<String>? = arrayListOf()
                var leyesURL: ArrayList<String>? = arrayListOf()

                for (j in 0 until leyesArray.length()) {
                    val leyObject = leyesArray.getJSONObject(j)
                    val leyNombre = leyObject.getString("nombre")
                    val leyUrl = leyObject.getString("url")

                    leyesTextView.text = "Ley: $leyNombre"
                    urlLeyesTextView.text = "Url de ley: $leyUrl"

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

    override fun onRequestComplete(result: String): MutableList<Feriado> {
        Log.i("RequestComplete", result)

        return processingData(result)
    }



}