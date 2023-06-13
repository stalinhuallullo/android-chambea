package online.transporteari.chambea.presentation.feature.requestTransport.application.interactor

import io.reactivex.observers.DisposableObserver
import online.transporteari.chambea.presentation.feature.requestTransport.domain.models.ScheduleDayCanonicalModel

class RequestTransportUseCase {

    constructor() {

    }

    fun getDayService(observer: DisposableObserver<List<ScheduleDayCanonicalModel>>) {
        //execute(recurrencyRepository.getFrecquencies().onErrorResumeNext(askForGetFrequencies()), observer)
    }
}