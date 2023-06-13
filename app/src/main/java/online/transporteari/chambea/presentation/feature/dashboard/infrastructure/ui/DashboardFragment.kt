package online.transporteari.chambea.presentation.feature.dashboard.infrastructure.ui

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.firestore.GeoPoint
import online.transporteari.chambea.presentation.common.utils.effect.CarMoveAnim
import online.transporteari.chambea.presentation.feature.dashboard.domain.models.DriverLocation
import online.transporteari.chambea.R
import online.transporteari.chambea.databinding.FragmentDashboardBinding
import online.transporteari.chambea.presentation.base.BaseChambeaFragment
import online.transporteari.chambea.presentation.common.utils.Constant
import online.transporteari.chambea.presentation.feature.dashboard.domain.providers.GeoProvider
import online.transporteari.chambea.presentation.feature.dashboard.infrastructure.adapters.RideClassListAdapter
import online.transporteari.chambea.presentation.feature.dashboard.infrastructure.models.RideClass
import online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.ui.RequestTransportActivity
import org.imperiumlabs.geofirestore.callbacks.GeoQueryEventListener
import java.util.*


class DashboardFragment : BaseChambeaFragment(), OnMapReadyCallback, Listener {
    private lateinit var binding: FragmentDashboardBinding
    private var googleMap: GoogleMap? = null
    private var easyWayLocation: EasyWayLocation? = null
    private var myLocationLatLng: LatLng? = null
    private val geoProvider = GeoProvider()

    // GOOGLE PLACES
    private var places: PlacesClient? = null
    private var autocompleteOrigin: AutocompleteSupportFragment? = null
    private var autocompleteDestination: AutocompleteSupportFragment? = null
    private var originName = ""
    private var destinationName = ""
    private var originLatLng: LatLng? = null
    private var destinationLatLng: LatLng? = null

    private var isLocationEnabled = false

    private val driverMarkers = ArrayList<Marker>()
    private val driversLocation = ArrayList<DriverLocation>()
    //private val modalMenu = ModalBottomSheetMenu()

    private var transaction: FragmentTransaction? = null
    private val etPickup: EditText? = null
    private  var etDestination: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_dashboard, container, false);
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)

        runMap()
        startGooglePlaces()
        myLocation()
        handlerComponentClick()
        return binding.root
    }

    private fun startGooglePlaces() {
        if (!Places.isInitialized()) {
            Places.initialize(context()!!, resources.getString(R.string.google_maps_key))
        }

        places = Places.createClient(context()!!)
        instanceAutocompleteOrigin()
        instanceAutocompleteDestination()
    }

    private fun instanceAutocompleteOrigin() {
        autocompleteOrigin = childFragmentManager.findFragmentById(R.id.placesAutocompleteOrigin) as AutocompleteSupportFragment
        autocompleteOrigin?.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS,
            )
        )
        autocompleteOrigin?.setHint("Lugar de recogida")
        autocompleteOrigin?.setCountry("PE")
        autocompleteOrigin?.setOnPlaceSelectedListener(object: PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                originName = place.name!!
                originLatLng = place.latLng
                Log.d("PLACES", "Address: $originName")
                Log.d("PLACES", "LAT: ${originLatLng?.latitude}")
                Log.d("PLACES", "LNG: ${originLatLng?.longitude}")
            }

            override fun onError(p0: Status) {

            }
        })
    }

    private fun instanceAutocompleteDestination() {
        autocompleteDestination = childFragmentManager.findFragmentById(R.id.placesAutocompleteDestination) as AutocompleteSupportFragment
        autocompleteDestination?.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS,
            )
        )
        autocompleteDestination?.setHint("Destino")
        autocompleteDestination?.setCountry("PE")
        autocompleteDestination?.setOnPlaceSelectedListener(object: PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                destinationName = place.name!!
                destinationLatLng = place.latLng
                Log.d("PLACES", "Address: $destinationName")
                Log.d("PLACES", "LAT: ${destinationLatLng?.latitude}")
                Log.d("PLACES", "LNG: ${destinationLatLng?.longitude}")
            }

            override fun onError(p0: Status) {

            }
        })
    }

    fun handlerComponentClick() {
        binding.lytRide.setOnClickListener {
            showDialogRideClass()
        }
        binding.lytRequestTransport.setOnClickListener {
            goToRequestTransport()
        }
        /*binding.lytOrigin.setOnClickListener {
            Toast.makeText(context(), "Click en origen de recojo", Toast.LENGTH_SHORT).show()
            //showDialogLocation(true, places!!)

            autocompleteOrigin = childFragmentManager.findFragmentById(R.id.placesAutocompleteDestination) as AutocompleteSupportFragment
            autocompleteOrigin?.setPlaceFields(
                listOf(
                    Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.LAT_LNG,
                    Place.Field.ADDRESS,
                )
            )
            autocompleteOrigin
            autocompleteOrigin?.setHint("Lugar de recogida")
            autocompleteOrigin?.setCountry("PE")
            autocompleteOrigin?.setOnPlaceSelectedListener(object: PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {
                    originName = place.name!!
                    originLatLng = place.latLng
                    Log.d("PLACES", "Address: $originName")
                    Log.d("PLACES", "LAT: ${originLatLng?.latitude}")
                    Log.d("PLACES", "LNG: ${originLatLng?.longitude}")
                }

                override fun onError(p0: Status) {

                }
            })
        }
        binding.lytDestination.setOnClickListener {
            Toast.makeText(context(), "Click en destino de recojo", Toast.LENGTH_SHORT).show()
            showDialogLocation(false, places!!)
        }*/
    }

    fun goToRequestTransport() {
        val intent = Intent(activity, RequestTransportActivity::class.java)
        startActivity(intent)
    }

    private fun showDialogRideClass() {
        val context = context()
        if (context != null && isAdded) {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog.setContentView(R.layout.dialog_ride_class)
            dialog.setCancelable(true)

            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window!!.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT

            val recyclerView = dialog.findViewById<View>(R.id.recyclerView) as RecyclerView

            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.setHasFixedSize(true)

            val mAdapter = RideClassListAdapter(context, Constant().getRideClassData(context))
            recyclerView.adapter = mAdapter
            mAdapter.setOnItemClickListener(object : RideClassListAdapter.OnItemClickListener {
                override fun onItemClick(view: View?, obj: RideClass?, position: Int) {
                    //changeRideClass(obj)
                    dialog.dismiss()
                }
            })

            dialog.show()
            dialog.window!!.attributes = lp
        }
    }

    /*private fun showDialogLocation(isOrigen: Boolean, place: PlacesClient) {
        val previous = childFragmentManager.findFragmentByTag(FragmentDialogLocation::class.java.name)
        if (previous != null) {
            childFragmentManager.beginTransaction().remove(previous).commit()
        }

        val fragment = FragmentDialogLocation(place, originLatLng!!)
        val hint = if (isOrigen) "Introduce el lugar de recogo" else "Ingrese la ubicación de destino"
        val requestCode = if (isOrigen) 5000 else 6000

        fragment.setHint(hint)
        fragment.setRequestCode(requestCode)
        fragment.setOnCallbackResult(object : FragmentDialogLocation.CallbackResult {
            override fun sendResult(requestCode: Int, loc: String?) {
                if (requestCode == 5000) {
                    binding.etPickup.setText(loc)
                    //etPickup?.setText(loc)
                } else if (requestCode == 6000) {
                    binding.etDestination.setText(loc)
                    //etDestination?.setText(loc)
                }
            }
        })

        fragment.show(childFragmentManager, FragmentDialogLocation::class.java.name)
    }*/


    fun runMap() {
        val mapFragment = getChildFragmentManager().findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    val locationPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            when {
                permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    Log.d("LOCALIZACION", "Permiso concedido")
                    easyWayLocation?.startLocation()

                }
                permission.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Log.d("LOCALIZACION", "Permiso concedido con limitacion")
                    easyWayLocation?.startLocation()

                }
                permission.getOrDefault(Manifest.permission.POST_NOTIFICATIONS, false) -> {
                    Log.e("NOTIFICATIONNNN", "FCM SDK (y su aplicación) pueden publicar notificaciones. 333")
                }
                else -> {
                    Log.d("LOCALIZACION", "Permiso no concedido")
                }
            }
        }

    }

    fun myLocation() {
        val locationRequest = LocationRequest.create().apply {
            interval = 0
            fastestInterval = 0
            priority = Priority.PRIORITY_HIGH_ACCURACY
            smallestDisplacement = 1f
        }

        easyWayLocation = EasyWayLocation(context(), locationRequest, false, false, this)

        locationPermissions.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS
        ))
    }

    override fun context(): Context? {
        val activity = activity
        return activity?.applicationContext
    }


    private fun onCameraMove() {
        googleMap?.setOnCameraIdleListener {
            try {
                val geocoder = Geocoder(context()!!, Locale.getDefault())
                originLatLng = googleMap?.cameraPosition?.target

                if (originLatLng != null) {
                    val addressList = geocoder.getFromLocation(originLatLng?.latitude!!, originLatLng?.longitude!!, 1)
                    if (addressList != null) {
                        if (addressList.size > 0) {
                            val city = addressList[0].locality
                            val country = addressList[0].countryName
                            val address = addressList[0].getAddressLine(0)
                            originName = "$address $city"
                            autocompleteOrigin?.setText("$address $city")
                        }
                    }
                }

            } catch (e: Exception) {
                Log.d("ERROR", "Mensaje error: ${e.message}")
            }
        }
    }
    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.uiSettings?.isZoomControlsEnabled = true
        onCameraMove()
//        easyWayLocation?.startLocation();

        if (ActivityCompat.checkSelfPermission(
                context()!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context()!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        googleMap?.isMyLocationEnabled = true

        try {
            val success = googleMap?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(context()!!, R.raw.style_map_silver)
            )
            if (!success!!) {
                Log.d("MAPAS", "No se pudo encontrar el estilo")
            }

        } catch (e: Resources.NotFoundException) {
            Log.d("MAPAS", "Error: ${e.toString()}")
        }

    }

    private fun getPositionDriver(id: String): Int {
        var position = 0
        for (i in driversLocation.indices) {
            if (id == driversLocation[i].id) {
                position = i
                break
            }
        }
        return position
    }

    private fun getNearbyDrivers() {
        println("myLocationLatLng =====> " + myLocationLatLng)
        if (myLocationLatLng == null) return

        geoProvider.getNearbyDrivers(myLocationLatLng!!, 30.0).addGeoQueryEventListener(object:
            GeoQueryEventListener {

            override fun onKeyEntered(documentID: String, location: GeoPoint) {
                println("documentID =====> " + documentID)
                println("location =====> " + location)

                for (marker in driverMarkers) {
                    if (marker.tag != null) {
                        if (marker.tag == documentID) {
                            return
                        }
                    }
                }
                // CREAMOS UN NUEVO MARCADOR PARA EL CONDUCTOR CONECTADO
                val driverLatLng = LatLng(location.latitude, location.longitude)
                val marker = googleMap?.addMarker(
                    MarkerOptions().position(driverLatLng).title("Conductor disponible").icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.ic_truck)
                    )
                )

                marker?.tag = documentID
                driverMarkers.add(marker!!)

                val dl = DriverLocation()
                dl.id = documentID
                driversLocation.add(dl)
            }

            override fun onKeyExited(documentID: String) {
                for (marker in driverMarkers) {
                    if (marker.tag != null) {
                        if (marker.tag == documentID) {
                            marker.remove()
                            driverMarkers.remove(marker)
                            driversLocation.removeAt(getPositionDriver(documentID))
                            return
                        }
                    }
                }
            }

            override fun onKeyMoved(documentID: String, location: GeoPoint) {

                for (marker in driverMarkers) {

                    val start = LatLng(location.latitude, location.longitude)
                    var end: LatLng? = null
                    val position = getPositionDriver(marker.tag.toString())

                    if (marker.tag != null) {
                        if (marker.tag == documentID) {
//                            marker.position = LatLng(location.latitude, location.longitude)

                            if (driversLocation[position].latlng != null) {
                                end = driversLocation[position].latlng
                            }
                            driversLocation[position].latlng = LatLng(location.latitude, location.longitude)
                            if (end  != null) {
                                CarMoveAnim.carAnim(marker, end, start)
                            }

                        }
                    }
                }

            }

            override fun onGeoQueryError(exception: Exception) {

            }

            override fun onGeoQueryReady() {

            }

        })
    }

    fun onClickMoveCurrentLocation() {
        googleMap?.moveCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.builder().target(myLocationLatLng!!).zoom(15f).build()
            )
        )
        //markerLocationCurrent()
    }

    fun markerLocationCurrent() {
        googleMap?.addMarker(
            MarkerOptions().position(myLocationLatLng!!).title("Yoooooooooooooo").icon(
                BitmapDescriptorFactory.fromResource(R.drawable.my_location_green)
            )
        )
    }

    override fun locationOn() {

    }

    override fun currentLocation(location: Location) {
        myLocationLatLng = LatLng(location.latitude, location.longitude) // LAT Y LONG DE LA POSICION ACTUAL

        if (!isLocationEnabled) { // UNA SOLA VEZ
            isLocationEnabled = true
            onClickMoveCurrentLocation()
            getNearbyDrivers()
            //limitSearch()
        }
    }

    override fun locationCancelled() {

    }

}