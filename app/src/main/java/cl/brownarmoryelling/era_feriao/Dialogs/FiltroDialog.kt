package cl.brownarmoryelling.era_feriao.Dialogs

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import cl.brownarmoryelling.era_feriao.Api.HolidaysApi


class FiltroDialog(private val context: Context) {

    private val feriadosApi = HolidaysApi()
    private lateinit var selectedOption: String

    private var optionsDialogCallback: DialogCallback? = null
    private lateinit var selectedFilter: String
    var selected = ""
    private var specificDate = ""

    fun showTop5HolidaysDialog()
    {
        showAnioDialog(1)
    }

    fun showOptionsDialog() {
        val options = arrayOf("Año", "Año y Mes", "Año, Mes y Día")

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Filtrar por: ")
            .setItems(options) { dialogInterface: DialogInterface, i: Int ->
                // Handle item click here
                val selectedOption = options[i]

                if(selectedOption == "Año")
                {
                    showAnioDialog(0)
                    selectedFilter = selectedOption
                }
                if(selectedOption == "Año y Mes")
                {
                    showAnioDialog(1)
                    selectedFilter = selectedOption
                }
                if(selectedOption == "Año, Mes y Día")
                {
                    showAnioDialog(2)
                    selectedFilter = selectedOption
                }
                // Do something with the selected option
            }
            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }

        builder.create().show()
    }


    interface DialogCallback {
        fun onOptionSelected(selectedOption: String)
    }

    private var dialogCallback: DialogCallback? = null

    private fun getMonth(month : String) : String
    {
        when(month)
        {
            "Enero" -> {return "1"}
            "Febrero" -> {return "2"}
            "Marzo" -> {return "3"}
            "Abril" -> {return "4"}
            "Mayo" -> {return "5"}
            "Junio" -> {return "6"}
            "Julio" -> {return "7"}
            "Agosto" -> {return "8"}
            "Septiembre" -> {return "9"}
            "Octubre" -> {return "10"}
            "Noviembre" -> {return "11"}
            "Diciembre" -> {return "12"}
        }
        return " "
    }

    fun setDialogCallback(callback: DialogCallback) {
        this.dialogCallback = callback
    }

    fun showAnioDialog(remainDialogs : Int)
    {
        val options = arrayOf("2013" ,"2014" , "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023")
        val builder = AlertDialog.Builder(context)
        var selectedOption = ""
        builder.setTitle("Seleccione un año: ")
            .setItems(options) { dialogInterface: DialogInterface, i: Int ->
                selectedOption = options[i]
                // Notify the callback when the option is selected
                if (remainDialogs == 0) {
                    dialogCallback?.onOptionSelected(selectedOption)
                    this.selectedOption = selectedOption
                }
                else showMesDialog(remainDialogs - 1, selectedOption)


            }
            .setNegativeButton("Cancel")
            { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }

        builder.create().show()

    }


    /*fun getYear():String{

        return selected
    }*/

    fun getSpecificDate(): String
    {
        return specificDate
    }



    fun setYear(year: String): String{
        Log.i("year", year)
        return year
    }

    fun showMesDialog(remainDialogs : Int, year : String)
    {
        val options = arrayOf("Enero","Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
            "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
        val builder = AlertDialog.Builder(context)
        var selectedOption = "0"

        builder.setTitle("Seleccione un mes: ")
            .setItems(options) { dialogInterface: DialogInterface, i: Int ->
                // Handle item click here
                selectedOption = options[i]
                if (remainDialogs == 0) {
                    dialogCallback?.onOptionSelected(year + "/" + getMonth(selectedOption).toInt())
                    this.selectedOption = year + "/" + getMonth(selectedOption).toInt()
                }
                else showDiaDialog(getMonth(selectedOption).toInt(), year, remainDialogs - 1)
                // Do something with the selected option
            }
            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }

        builder.create().show()
    }

    fun showDiaDialog(month : Int, year : String, remainDialogs : Int)
    {
        var dayArray : Array<String> = emptyArray()

        if(month == 2)
        {
            if(year == "2016" || year == "2020")
            {
                val febBicie = arrayOf("1","2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                    "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29")
                dayArray = febBicie;
            }
            else
            {
                val febNormie = arrayOf("1","2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                    "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28")

                dayArray = febNormie;
            }
        }
        else if((month <= 7 && month % 2 == 1) || (month > 7 && month % 2 == 0))
        {
            val mesPar = arrayOf("1","2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31")
            dayArray = mesPar;
        }
        else
        {
            val mesImpar = arrayOf("1","2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30")
            dayArray = mesImpar;
        }

        val options = dayArray
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Seleccione un dia: ")
            .setItems(options) { dialogInterface: DialogInterface, i: Int ->
                val selectedOption = options[i]

                if (remainDialogs == 0) dialogCallback?.onOptionSelected(year + "/" + month + "/" + selectedOption)
                else
                {
                    specificDate = year + "/" + month + "/" + selectedOption
                }

                this.selectedOption = year + "/" + month + "/" + selectedOption

            }
            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }

        builder.create().show()
    }
}