package online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.models

class DurationModel {
    var timeUnit: TimeUnitModel? = null
    var value: Int? = null
    var selected: Boolean = false
    var textSelected: String? = null
}

class TimeUnitModel {
    var timeUnitId: Int? = null
    var description: String? = null
    var descriptionSingular: String? = null
    var descriptionPlural: String? = null
}