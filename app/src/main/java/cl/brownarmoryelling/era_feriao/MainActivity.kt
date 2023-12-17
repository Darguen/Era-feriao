package cl.brownarmoryelling.era_feriao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.widget.AppCompatImageButton
import cl.brownarmoryelling.era_feriao.Api.FeriadosApi
import cl.brownarmoryelling.era_feriao.Background.ApiCallback
import cl.brownarmoryelling.era_feriao.Classes.Feriado


class MainActivity : AppCompatActivity() {

    private lateinit var filterButton: AppCompatImageButton
    private lateinit var faqButton: AppCompatImageButton
    private lateinit var aboutButton: AppCompatImageButton
    private lateinit var holidayButton: AppCompatImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        filterButton = findViewById(R.id.ImgBut_filter)
        faqButton = findViewById(R.id.ImgBut_faq)
        aboutButton = findViewById(R.id.ImgBut_about)
        holidayButton = findViewById(R.id.ImgBut_holyday)

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

        val feriadosApi = FeriadosApi()
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
}