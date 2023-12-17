package cl.brownarmoryelling.era_feriao.Dialogs

import android.app.AlertDialog;
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import cl.brownarmoryelling.era_feriao.Api.FeriadosApi


class FiltroDialog(private val context: Context) {

    val feriadosApi = FeriadosApi()
    fun showOptionsDialog() {
        val options = arrayOf("Dia", "Mes", "Año")



        val builder = AlertDialog.Builder(context)
        builder.setTitle("Filtrar por: ")
            .setItems(options) { dialogInterface: DialogInterface, i: Int ->
                // Handle item click here
                val selectedOption = options[i]

                if(selectedOption == "Año"){
                    showAnioDialog()
                }
                if(selectedOption == "Mes"){
                    showMesDialog()
                }
                if(selectedOption == "Dia"){
                    showDiaDialog()
                }
                // Do something with the selected option
            }
            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }

        builder.create().show()
    }

    fun showAnioDialog():String{
        val options = arrayOf("2013","2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023")
        val builder = AlertDialog.Builder(context)
        var option = " "
        builder.setTitle("Seleccione un año: ")
            .setItems(options) { dialogInterface: DialogInterface, i: Int ->
                // Handle item click here
                val selectedOption = options[i]
                option = selectedOption
                // Do something with the selected option

                when (selectedOption){
                    "2013" ->{feriadosApi.getData(selectedOption, "5", "1")
                    Log.i("api", feriadosApi.getData(selectedOption, "5", "1").toString())}
                }
            }
            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }

        builder.create().show()
       return option
    }

    fun getAnio(): String{
        var anioSeleccionado = showAnioDialog()

            return anioSeleccionado
    }

    fun showMesDialog(){
        val options = arrayOf("Enero","Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
            "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Seleccione un mes: ")
            .setItems(options) { dialogInterface: DialogInterface, i: Int ->
                // Handle item click here
                val selectedOption = options[i]
                // Do something with the selected option
            }
            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }

        builder.create().show()
    }

    fun showDiaDialog(){
        val options = arrayOf("1","2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Seleccione un dia: ")
            .setItems(options) { dialogInterface: DialogInterface, i: Int ->
                // Handle item click here
                val selectedOption = options[i]
                // Do something with the selected option
            }
            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }

        builder.create().show()
    }
}