package cl.brownarmoryelling.era_feriao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cl.brownarmoryelling.era_feriao.Api.FeriadosApi
import cl.brownarmoryelling.era_feriao.Dialogs.FiltroDialog

class FeriadoMasCercano : AppCompatActivity() {
    private val filtroDialog = FiltroDialog(this)
    private val feriadosApi = FeriadosApi()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feriado_mas_cercano)
        filtroDialog.showAnioDialog()

        /*Log.i("getAnio", filtroDialog.getAnio())

        when(filtroDialog.getAnio()){
            "2013" -> {
                feriadosApi.getData(filtroDialog.getAnio(), "5", "")
                Log.i("api", feriadosApi.getData(filtroDialog.getAnio(), "5", "1").toString())}
            }*/
        }
    }