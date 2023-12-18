package cl.brownarmoryelling.era_feriao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import cl.brownarmoryelling.era_feriao.Api.HolidaysApi
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.os.Handler
import android.os.Looper
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var filterButton: AppCompatImageButton
    private lateinit var faqButton: AppCompatImageButton
    private lateinit var aboutButton: AppCompatImageButton
    private lateinit var holidayButton: AppCompatImageButton
    private lateinit var toolBar: Toolbar
    private lateinit var textViewTiempo: TextView
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        filterButton = findViewById(R.id.ImgBut_filter)
        faqButton = findViewById(R.id.ImgBut_faq)
        aboutButton = findViewById(R.id.ImgBut_about)
        holidayButton = findViewById(R.id.ImgBut_holyday)

        toolBar = findViewById(R.id.toolbar)
        textViewTiempo = findViewById(R.id.textCronometro)

        toolBar.title = ""

        setSupportActionBar(toolBar)

        filterButton.setOnClickListener{
            val filterB = Intent(this, ResultadoFeriadoPorFiltro::class.java)
            startActivity(filterB)
        }
        faqButton.setOnClickListener{
            val faqB = Intent(this, Faq::class.java)
            startActivity(faqB)
        }
        aboutButton.setOnClickListener{
            val aboutB = Intent(this, About::class.java)
            startActivity(aboutB)
        }
        holidayButton.setOnClickListener{
            val holidayB = Intent(this, FeriadoMasCercano::class.java)
            startActivity(holidayB)
        }
        // Actualizar el tiempo cada segundo
        actualizarTiempoCadaSegundo()

        val feriadosApi = HolidaysApi()
        feriadosApi.getData( "2023", "01", "01", "5", "5")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.buscarFlitro -> {
                val filtro = Intent(this, ResultadoFeriadoPorFiltro::class.java)
                startActivity(filtro)
                return true
            }
            R.id.feriadoMasCercano -> {
                val feriadoMasCercano = Intent(this, FeriadoMasCercano::class.java)
                startActivity(feriadoMasCercano)
                return true
            }
            R.id.faq->{
                val faq = Intent(this, Faq::class.java)
                startActivity(faq)
                return true
            }
            R.id.about->{
                val about = Intent(this, About::class.java)
                startActivity(about)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun actualizarTiempoCadaSegundo() {
        // Crea un Runnable para actualizar el tiempo
        val runnable = object : Runnable {
            override fun run() {
                // Obtén el tiempo restante hasta el 25 de diciembre
                val tiempoRestante = obtenerTiempoRestanteHastaNavidad()
                val horasRestantes = tiempoRestante.horas + tiempoRestante.dias * 24

                // Formatea y actualiza el TextView
                val tiempoFormateado = "$horasRestantes:${tiempoRestante.minutos}:${tiempoRestante.segundos}"

                textViewTiempo.text = tiempoFormateado

                // Programa la siguiente actualización después de 1 segundo
                handler.postDelayed(this, 1000)
            }
        }

        // Ejecuta el Runnable inmediatamente
        handler.post(runnable)
    }

    private fun obtenerTiempoRestanteHastaNavidad(): TiempoRestante {
        val navidad = obtenerFechaNavidad()
        val ahora = Calendar.getInstance().time

        // Calcula la diferencia en milisegundos
        val diferenciaMillis = navidad.time - ahora.time

        // Calcula días, horas, minutos y segundos
        val diasRestantes = diferenciaMillis / (1000 * 60 * 60 * 24)
        val horasRestantes = (diferenciaMillis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
        val minutosRestantes = (diferenciaMillis % (1000 * 60 * 60)) / (1000 * 60)
        val segundosRestantes = (diferenciaMillis % (1000 * 60)) / 1000

        return TiempoRestante(diasRestantes, horasRestantes, minutosRestantes, segundosRestantes)
    }

    private fun obtenerFechaNavidad(): Date {
        val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fechaNavidad = "2023-12-25"
        return formato.parse(fechaNavidad)!!
    }
    data class TiempoRestante(
        val dias: Long,
        val horas: Long,
        val minutos: Long,
        val segundos: Long
    )

}


