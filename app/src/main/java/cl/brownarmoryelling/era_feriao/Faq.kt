package cl.brownarmoryelling.era_feriao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class Faq : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
    }
    fun mostrarRespuesta1(view: View) {
        val respuestaTextView = findViewById<TextView>(R.id.TextViewFAQ_resp1)

        // Cambiar la visibilidad de la respuesta
        if (respuestaTextView.visibility == View.GONE) {
            respuestaTextView.visibility = View.VISIBLE
        } else {
            respuestaTextView.visibility = View.GONE
        }
    }
    fun mostrarRespuesta2(view: View) {
        val respuestaTextView = findViewById<TextView>(R.id.TextViewFAQ_resp2)

        // Cambiar la visibilidad de la respuesta
        if (respuestaTextView.visibility == View.GONE) {
            respuestaTextView.visibility = View.VISIBLE
        } else {
            respuestaTextView.visibility = View.GONE
        }
    }
    fun mostrarRespuesta3(view: View) {
        val respuestaTextView = findViewById<TextView>(R.id.TextViewFAQ_resp3)

        // Cambiar la visibilidad de la respuesta
        if (respuestaTextView.visibility == View.GONE) {
            respuestaTextView.visibility = View.VISIBLE
        } else {
            respuestaTextView.visibility = View.GONE
        }
    }
}