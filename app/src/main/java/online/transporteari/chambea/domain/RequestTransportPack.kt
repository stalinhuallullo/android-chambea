package online.transporteari.chambea.domain

import android.os.Parcel
import android.os.Parcelable

class RequestTransportPack {
    var recListId: Long? = null
    var name: String? = null
    var userId: String? = null
    var paymentMethodId: Int? = null
    var creditCardId: Int? = null
    var nextOrderDate: String? = null
    var frecuencyValue: Int? = null
    var deliveryDay: Int? = null
    var durationValue: Int? = null
    var scheduleHour: String? = null
    var addressId: Int? = null
    var processStartConfirm: LocalDateTimeModel? = null
    var processEndConfirm: LocalDateTimeModel? = null
    var deliveryDate: LocalDateTimeModel? = null
}

class LocalDateTimeModel() : Parcelable {
    var date: String? = null
    var time: String? = null

    constructor(parcel: Parcel) : this() {
        date = parcel.readString()
        time = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocalDateTimeModel> {
        override fun createFromParcel(parcel: Parcel): LocalDateTimeModel {
            return LocalDateTimeModel(parcel)
        }

        override fun newArray(size: Int): Array<LocalDateTimeModel?> {
            return arrayOfNulls(size)
        }
    }

}