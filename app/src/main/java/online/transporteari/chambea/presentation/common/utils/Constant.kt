package online.transporteari.chambea.presentation.common.utils

import android.content.Context
import online.transporteari.chambea.R
import online.transporteari.chambea.presentation.feature.dashboard.infrastructure.models.RideClass
import java.util.*

class Constant  {
   /* companion object {
        //val APP_COMPANY = Content().APP_COMPANY + ":" + BuildConfig.COMPANY
        //val APP_CHANNEL = Content.APP_CHANNEL + ":" + BuildConfig.CHANNEL
        //val APP_CLIENT_ID = Content.APP_CLIENT_ID + ":" + BuildConfig.CLIENT_ID
        val REC_USER_ID = "rec-userId"
    }*/
   val LOCALE_MX: Locale? = Locale("es", "PE")
    val EXTRA_LIST_PAYMENT_METHOD = "EXTRA_LIST_PAYMENT_METHOD"
    val EXTRA_FROM_RECURRENCY_PACK = "FROM_RECURRENCY_PACK"
    val INTENT_PICK_PAYMENT_METHOD = 701

    fun getLocations(context: Context): List<String> {
        val items: MutableList<String> = ArrayList()
        val locations: Array<String> = context.getResources().getStringArray(R.array.locations)
        locations.indices.map {
            items.add(locations[it])
        }

        return items
    }

    fun getRideClassData(ctx: Context): List<RideClass> {
        val items: MutableList<RideClass> = java.util.ArrayList<RideClass>()
        val images = ctx.resources.obtainTypedArray(R.array.ride_image)
        val names = ctx.resources.getStringArray(R.array.ride_name)
        val prices = ctx.resources.getStringArray(R.array.ride_price)
        val paxs = ctx.resources.getStringArray(R.array.ride_pax)
        val durations = ctx.resources.getStringArray(R.array.ride_duration)
        for (i in names.indices) {
            val item = RideClass()
            item.class_name = names[i]
            item.image = images.getResourceId(i, -1)
            item.price = prices[i]
            item.pax = paxs[i] + " pax"
            item.duration = durations[i] + " min"
            items.add(item)
        }
        return items
    }

    fun baseUrl(): String {
        return ""//BuildConfig.BASE_URL_CHAMBEA
    }

    fun getDays(): String {
        return this.baseUrl() + ""//BuildConfig.BASE_URL_CHAMBEA
    }
}