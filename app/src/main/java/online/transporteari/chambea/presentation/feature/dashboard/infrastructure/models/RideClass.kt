package online.transporteari.chambea.presentation.feature.dashboard.infrastructure.models

import java.io.Serializable

class RideClass : Serializable {
    var image = 0
    var class_name: String? = null
    var price: String? = null
    var pax: String? = null
    var duration: String? = null
}