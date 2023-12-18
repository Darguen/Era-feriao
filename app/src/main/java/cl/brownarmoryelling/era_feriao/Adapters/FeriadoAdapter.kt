package cl.brownarmoryelling.era_feriao.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cl.brownarmoryelling.era_feriao.Classes.Feriado
import cl.brownarmoryelling.era_feriao.R

class FeriadoAdapter
(
    context : Context,
    resource : Int,
    feriados : List<Feriado>
) : ArrayAdapter<Feriado>(context, resource, feriados)
{

    override fun getView(pos : Int, convertView: View?, parent : ViewGroup) : View
    {
        var listItemView = convertView
        val holder: ViewHolder

        if (listItemView == null)
        {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            listItemView = inflater.inflate(R.layout.feriado_view_search, parent, false)

            holder = ViewHolder()
            holder.nameTextView = listItemView.findViewById(R.id.feriadoNameTV)
            holder.commentsTextView = listItemView.findViewById(R.id.feriadoCommentsTV)
            holder.dateTextView = listItemView.findViewById(R.id.feriadoDateTV)
            holder.inalienableTextView = listItemView.findViewById(R.id.feriadoInalienableTV)
            holder.typeTextView = listItemView.findViewById(R.id.feriadoTypeTV)
            holder.lawsTextView = listItemView.findViewById(R.id.feriadoLawsTV)
            holder.lawsURLTextView = listItemView.findViewById(R.id.feriadoLawsUrlTV)

            listItemView.tag = holder
        }
        else
        {
            holder = listItemView?.tag as ViewHolder
        }

        val feriado = getItem(pos)

        holder.nameTextView.text = context.getString(R.string.wordName) + ": " + feriado?.name
        holder.commentsTextView.text = context.getString(R.string.wordComments) + ": " + feriado?.comments
        holder.dateTextView.text = context.getString(R.string.wordDate) + ": " + feriado?.date

        val inalienable = if(feriado?.inalienable!!) context.getString(R.string.wordYes) else context.getString(R.string.wordNo)
        holder.inalienableTextView.text = context.getString(R.string.wordInalienable) + ": " + inalienable

        holder.typeTextView.text = context.getString(R.string.wordType) + ": " + feriado?.type

        var lawsString: String = feriado?.laws?.joinToString(" ") ?: ""
        holder.lawsTextView.text = context.getString(R.string.wordLaws) + ": " + lawsString

        var lawsURLString: String = feriado?.lawsURL?.joinToString(" ") ?: ""
        holder.lawsURLTextView.text = context.getString(R.string.wordLawsURL) + ": " + lawsURLString

        return listItemView!!
    }

    private class ViewHolder
    {
        lateinit var nameTextView: TextView
        lateinit var commentsTextView: TextView
        lateinit var dateTextView: TextView
        lateinit var inalienableTextView: TextView
        lateinit var typeTextView: TextView
        lateinit var lawsTextView: TextView
        lateinit var lawsURLTextView: TextView
    }
}