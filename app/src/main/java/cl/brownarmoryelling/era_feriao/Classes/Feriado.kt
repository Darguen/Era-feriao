package cl.brownarmoryelling.era_feriao.Classes

import android.os.Parcel
import android.os.Parcelable

data class Feriado(

    val name: String?,
    val comments: String?,
    val date: String?,
    val inalienable: Boolean,
    val type: String?,
    val laws: List<String>?,
    val lawsURL: List<String>?

) : Parcelable {

    constructor(parcel : Parcel) : this
    (
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(parcel : Parcel, flags : Int)
    {
        parcel.writeString(name)
        parcel.writeString(comments)
        parcel.writeString(date)
        parcel.writeByte(if (inalienable) 1 else 0)
        parcel.writeString(type)
        parcel.writeStringList(laws)
        parcel.writeStringList(lawsURL)
    }

    override fun describeContents(): Int
    {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Feriado>
    {
        override fun createFromParcel(parcel: Parcel) : Feriado
        {
            return Feriado(parcel)
        }

        override fun newArray(size: Int) : Array<Feriado?>
        {
            return arrayOfNulls(size)
        }
    }
}