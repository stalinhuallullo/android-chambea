package com.globant.inkafarma.domain.entity

import android.os.Parcel
import android.os.Parcelable
import online.transporteari.chambea.presentation.feature.requestTransport.domain.models.CreditCard

open class PaymentMethod : Parcelable {

    var id: Long? = 0
    var name: String? = null
    var imageUrl: String? = null
    var creditCardList: List<CreditCard>? = null
    var creditCardModel: CreditCard? = null
    var isFavorite: Boolean? = false
    var provider: String? = null
    var cardName: String? = null
    var type: String? = null
    var paidAmount: Float? = 0f
    var changeAmount: Float? = 0f
    var isHeader: Boolean? = false
    var isSelect: Boolean? = false

    constructor()
    constructor(id: Long, name: String?, provider: String?, cardName: String?, paidAmount: Float, changeAmount: Float) {
        this.id = id
        this.name = name
        this.provider = provider
        this.cardName = cardName
        this.paidAmount = paidAmount
        this.changeAmount = changeAmount
    }

    protected constructor(parcel: Parcel) {
        id = if (parcel.readByte().toInt() == 0) {
            null
        } else {
            parcel.readLong()
        }

        name = parcel.readString()
        imageUrl = parcel.readString()
        creditCardList = parcel.createTypedArrayList(CreditCard.CREATOR)

        creditCardModel = if (parcel.readByte().toInt() == 0) {
            null
        } else {
            parcel.readParcelable(CreditCard::class.java.classLoader)
        }

        isFavorite = if (parcel.readByte().toInt() == 0) {
            null
        } else {
            parcel.readByte().toInt() != 0
        }

        provider = parcel.readString()
        cardName = parcel.readString()
        type = parcel.readString()

        paidAmount = if (parcel.readByte().toInt() == 0) {
            null
        } else {
            parcel.readFloat()
        }

        changeAmount = if (parcel.readByte().toInt() == 0) {
            null
        } else {
            parcel.readFloat()
        }

        isHeader = if (parcel.readByte().toInt() == 0) {
            null
        } else {
            parcel.readByte().toInt() != 0
        }

        isSelect = if (parcel.readByte().toInt() == 0) {
            null
        } else {
            parcel.readByte().toInt() != 0
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        id?.let {
            parcel.writeByte(1.toByte())
            parcel.writeLong(it)
        } ?: parcel.writeByte(0.toByte())

        parcel.writeString(name)
        parcel.writeString(imageUrl)
        parcel.writeTypedList(creditCardList)

        creditCardModel?.let {
            parcel.writeByte(1.toByte())
            parcel.writeParcelable(it, flags)
        } ?: parcel.writeByte(0.toByte())

        isFavorite?.let {
            parcel.writeByte(1.toByte())
            parcel.writeByte((if (it) 1 else 0).toByte())
        } ?: parcel.writeByte(0.toByte())

        parcel.writeString(provider)
        parcel.writeString(cardName)
        parcel.writeString(type)

        paidAmount?.let {
            parcel.writeByte(1.toByte())
            parcel.writeFloat(it)
        } ?: parcel.writeByte(0.toByte())

        changeAmount?.let {
            parcel.writeByte(1.toByte())
            parcel.writeFloat(it)
        } ?: parcel.writeByte(0.toByte())

        isHeader?.let {
            parcel.writeByte(1.toByte())
            parcel.writeByte((if (it) 1 else 0).toByte())
        } ?: parcel.writeByte(0.toByte())

        isSelect?.let {
            parcel.writeByte(1.toByte())
            parcel.writeByte((if (it) 1 else 0).toByte())
        } ?: parcel.writeByte(0.toByte())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PaymentMethod> {
        override fun createFromParcel(parcel: Parcel): PaymentMethod {
            return PaymentMethod(parcel)
        }

        override fun newArray(size: Int): Array<PaymentMethod?> {
            return arrayOfNulls(size)
        }
    }
}