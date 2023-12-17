package cl.brownarmoryelling.era_feriao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cl.brownarmoryelling.era_feriao.Dialogs.FiltroDialog

class ResultadoFeriadoPorFiltro : AppCompatActivity() {

    private val filtroDialog = FiltroDialog(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_feriado_por_filtro)

        filtroDialog.showOptionsDialog()
    }
}