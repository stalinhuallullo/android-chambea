package online.transporteari.chambea.presentation.common.error

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonElement

class ErrorModel : Parcelable {
    var code: Int
        private set
    var message: String?
    var params: JsonElement? = null
        private set

    constructor(code: Int, message: String?, params: JsonElement?) {
        this.code = code
        this.params = params
        this.message = message
    }

    protected constructor(parcel: Parcel) {
        code = parcel.readInt()
        message = parcel.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(code)
        parcel.writeString(message)
    }

   /* companion object {
        val CREATOR: Parcelable.Creator<ErrorModel> = object : Parcelable.Creator<ErrorModel?> {
            override fun createFromParcel(`in`: Parcel): ErrorModel? {
                return ErrorModel(`in`)
            }

            override fun newArray(size: Int): Array<ErrorModel?> {
                return arrayOfNulls(size)
            }
        }
    }*/
    companion object CREATOR : Parcelable.Creator<ErrorModel> {
        override fun createFromParcel(parcel: Parcel): ErrorModel {
            return ErrorModel(parcel)
        }

        override fun newArray(size: Int): Array<ErrorModel?> {
            return arrayOfNulls(size)
        }
    }
}
