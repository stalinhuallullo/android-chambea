package online.transporteari.chambea.presentation.feature.requestTransport.domain.models

import android.os.Parcel
import android.os.Parcelable

open class CreditCard : Parcelable {
    var id: Long? = 0
    var name: String? = null
    var imageUrl: String? = null
    var isFavorite: Boolean? = false
    var descriptionShort: String? = null
    var descriptionLarge: String? = null
    var colorDescription: String? = null
    var cardGroup: String? = null

    constructor()
    protected constructor(parcel: Parcel) {
        id = if (parcel.readByte().toInt() == 0) {
            null
        } else {
            parcel.readLong()
        }

        name = parcel.readString()
        imageUrl = parcel.readString()

        isFavorite = if (parcel.readByte().toInt() == 0) {
            null
        } else {
            parcel.readByte().toInt() != 0
        }

        descriptionShort = parcel.readString()
        descriptionLarge = parcel.readString()
        colorDescription = parcel.readString()
        cardGroup = parcel.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        id?.let {
            parcel.writeByte(1.toByte())
            parcel.writeLong(it)
        } ?: parcel.writeByte(0.toByte())

        parcel.writeString(name)
        parcel.writeString(imageUrl)

        isFavorite?.let {
            parcel.writeByte(1.toByte())
            parcel.writeByte((if (it) 1 else 0).toByte())
        } ?: parcel.writeByte(0.toByte())

        parcel.writeString(descriptionShort)
        parcel.writeString(descriptionLarge)
        parcel.writeString(colorDescription)
        parcel.writeString(cardGroup)
    }

    companion object CREATOR : Parcelable.Creator<CreditCard> {
        override fun createFromParcel(parcel: Parcel): CreditCard {
            return CreditCard(parcel)
        }

        override fun newArray(size: Int): Array<CreditCard?> {
            return arrayOfNulls(size)
        }
    }
}