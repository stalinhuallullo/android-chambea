package online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.adapters

import online.transporteari.chambea.presentation.base.mvp.LoadingView
import online.transporteari.chambea.presentation.base.mvp.View
import online.transporteari.chambea.presentation.common.error.ErrorModel
import online.transporteari.chambea.presentation.feature.requestTransport.domain.models.ScheduleDayCanonicalModel
import online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.models.DurationModel

interface ConfigurationRequestTransportView : LoadingView, View {

    //fun setPackRecurrency()
    fun showConfigurationDayService(items: List<ScheduleDayCanonicalModel>, alertShow: Boolean)
    fun showConfigurationFrequency(items: List<DurationModel>, alertShow: Boolean)
    fun showConfigurationTypeOfMerchandise(items: List<DurationModel>, alertShow: Boolean)
    fun showConfigurationQuantity()
    //fun showConfigurationDayDelivery(items: List<ScheduleDayCanonicalModel>, alertShow: Boolean)
    fun onError(errorModel: ErrorModel?)
    //fun setTimeZones(timeZones: List<ScheduleDayCanonicalModel>)
}
