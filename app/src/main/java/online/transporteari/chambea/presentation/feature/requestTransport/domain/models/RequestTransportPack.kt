package online.transporteari.chambea.presentation.feature.requestTransport.domain.models

class ScheduleDayCanonicalModel {
    var code: String? = null
    var id: Int? = null
    var day: String? = null
    var selectedDay: Boolean? = null
    var hasCapacity: Boolean? = null
    var message: String? = null
    var startHour: String? = null
    var endHour: String? = null
    var daysToPick: Int? = null
    var deliveryTime: Int? = null
    var today: String? = null
    var typeDay: Long? = null
    var schedules: List<ScheduleCanonicalModel>? = null
    var schedulesFilter: List<ScheduleCanonicalModel>? = null
    var seleceted = false
}

class ScheduleCanonicalModel {
    var id: Long? = null
    var time: String? = null
    var capacity: Int? = null
    var capacityCurrent: Int? = null
    var currentCapacity: Int? = null
    var hasCapacity = false
    var value: String? = null
    var valueEnd: String? = null
    var typeService = false
    var segment: String? = null
    var segmentEnd: String? = null
    var segmentCount: String? = null
    var message: String? = null
    var selected = false
}