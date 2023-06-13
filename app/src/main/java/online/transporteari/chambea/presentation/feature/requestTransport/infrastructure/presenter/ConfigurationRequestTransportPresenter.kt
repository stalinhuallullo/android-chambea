package online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.presenter

import online.transporteari.chambea.domain.interactor.BaseObserver
import online.transporteari.chambea.presentation.base.mvp.Presenter
import online.transporteari.chambea.presentation.exception.ErrorFactory
import online.transporteari.chambea.presentation.feature.requestTransport.application.interactor.RequestTransportUseCase
import online.transporteari.chambea.presentation.feature.requestTransport.domain.models.ScheduleDayCanonicalModel
import online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.adapters.ConfigurationRequestTransportView

class ConfigurationRequestTransportPresenter : Presenter<ConfigurationRequestTransportView> {
    var recurrencytUseCase: RequestTransportUseCase
    var configurationView: ConfigurationRequestTransportView? = null

    constructor() {
        recurrencytUseCase = RequestTransportUseCase()
    }
    override fun attachView(view: ConfigurationRequestTransportView) {
        configurationView = view
    }

    override fun resume() {

    }

    override fun pause() {

    }

    override fun destroy() {

    }

    fun getDayDelivery(isShowModal: Boolean) {
        println("getFrequencies ==> 1111111")
        configurationView!!.showLoading()
        println("getFrequencies ==> 2222222")
        recurrencytUseCase.getDayService(DayServiceObservable(isShowModal))
        println("getFrequencies ==> 333333333")
    }

    private inner class DayServiceObservable(value: Boolean) : BaseObserver<List<ScheduleDayCanonicalModel>>() {
        var isModalShow = value
        override fun onNext(items: List<ScheduleDayCanonicalModel>) {
            configurationView!!.hideLoading()
            configurationView!!.showConfigurationDayService(items, isModalShow)
        }

        override fun onError(exception: Throwable) {
            super.onError(exception)
            configurationView!!.onError(ErrorFactory.create(exception))
            configurationView!!.hideLoading()
        }
    }

}