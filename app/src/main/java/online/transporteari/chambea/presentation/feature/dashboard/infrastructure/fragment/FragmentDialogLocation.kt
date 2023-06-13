package online.transporteari.chambea.presentation.feature.dashboard.infrastructure.fragment

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import online.transporteari.chambea.R
import online.transporteari.chambea.presentation.common.utils.Constant
import online.transporteari.chambea.presentation.feature.dashboard.infrastructure.adapters.LocationListAdapter
import online.transporteari.chambea.presentation.feature.dashboard.infrastructure.models.AddressAutoCompletePredictions
import java.util.*


class FragmentDialogLocation(val placesClient: PlacesClient, val originLatLng: LatLng) : DialogFragment() {
    var callbackResult: CallbackResult? = null
    private var hint = ""
    private var mAdapter: LocationListAdapter? = null
    private var progress_bar: ProgressBar? = null
    private var recyclerView: RecyclerView? = null
    private var items: List<String?> = ArrayList()
    fun setOnCallbackResult(callbackResult: CallbackResult?) {
        this.callbackResult = callbackResult
    }

    private var request_code = 0
    private var on_search = false
    private var root_view: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root_view = inflater.inflate(R.layout.fragment_dialog_location, container, false)
        initComponent()
        //Tools.setSystemBarColorFragment(activity, this, R.color.app_taxi_grey_soft)
        //Tools.setSystemBarLightFragment(this)
        //Tools.checkInternetConnection(activity)
        return root_view
    }

    private fun initComponent() {
        val img_clear: ImageView = root_view!!.findViewById(R.id.img_clear)
        val et_search: EditText = root_view!!.findViewById(R.id.et_search)
        recyclerView = root_view!!.findViewById(R.id.recyclerView)
        progress_bar = root_view!!.findViewById(R.id.progress_bar)
        items = Constant().getLocations(requireActivity())

        //set data and list adapter
        mAdapter = LocationListAdapter(requireActivity())
        /* mAdapter!!.setItems(items as List<String>)
         recyclerView!!.adapter = mAdapter*/
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.setHasFixedSize(true)
        et_search.hint = hint
        progress_bar!!.visibility = View.GONE
        recyclerView!!.visibility = View.GONE


        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                println("sssss ==> " + et_search.text)
                var address: MutableList<AddressAutoCompletePredictions> = ArrayList()

                val token = AutocompleteSessionToken.newInstance()
                // Create a RectangularBounds object.
                val southwestLatitude = -18.349726
                val southwestLongitude = -81.410942
                val northeastLatitude = -0.012559
                val northeastLongitude = -68.652089


                val bounds = RectangularBounds.newInstance(
                    LatLng(southwestLatitude, southwestLongitude),
                    LatLng(northeastLatitude, northeastLongitude)
                )

                val request =
                    FindAutocompletePredictionsRequest.builder() // Call either setLocationBias() OR setLocationRestriction().
                        //.setLocationBias(bounds) //
                        //.setLocationRestriction(bounds)
                        .setLocationBias(bounds)
                        .setCountry("PE")
                        .setOrigin(originLatLng)
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .setSessionToken(token)
                        .setQuery(et_search.text.toString())
                        .build()


                placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->

                    for (prediction in response.getAutocompletePredictions()) {
                        //println("=== prediction ===")
                        //println(prediction)

                        var predictions = AddressAutoCompletePredictions()
                        predictions.placeId = prediction.placeId
                        predictions.distanceMeters = prediction.distanceMeters.toString()
                        predictions.placeTypes = prediction.placeTypes.toString()
                        predictions.fullText = prediction.getFullText(null).toString()
                        predictions.primaryText = prediction.getPrimaryText(null).toString()
                        predictions.secondaryText = prediction.getSecondaryText(null).toString()
                        //predictions.fullTextMatchedSubstrings = prediction.full
                        //predictions.primaryTextMatchedSubstrings =
                        //predictions.secondaryTextMatchedSubstrings =

                        address.add(predictions)
                        mAdapter!!.setItems(address)
                        recyclerView!!.adapter = mAdapter

                        val placeFields = listOf(
                            Place.Field.ID,
                            Place.Field.NAME,
                            Place.Field.LAT_LNG,
                            Place.Field.ADDRESS
                        )
                        val fetchPlaceRequest = FetchPlaceRequest.builder(prediction.placeId, placeFields).build()
                        placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener { fetchResponse ->
                            println("fetchResponse ==> " + fetchResponse)
                            val place = fetchResponse.place

                            // Obtén los detalles del lugar, como el ID, el nombre, la latitud, la longitud y la dirección
                            val placeId = place.id
                            val name = place.name
                            val latLng = place.latLng
                            val address = place.address
                            println("placeId ==> " + placeId)
                            println("name ==> " + name)
                            println("latLng ==> " + latLng)
                            println("address ==> " + address)
                            println("==================")
                        }.addOnFailureListener { exception ->
                            // Maneja errores al obtener detalles del lugar
                        }

                    }
                }.addOnFailureListener { exception ->
                    if (exception is ApiException) {
                        val apiException = exception
                        println("Place not found: " + apiException.statusCode)
                    }
                }
                val str = et_search.text.toString().trim { it <= ' ' }
                if (str.trim { it <= ' ' } != "" && !on_search) {
                    img_clear.visibility = View.VISIBLE
                    progress_bar!!.visibility = View.VISIBLE
                    on_search = true
                    Handler().postDelayed({
                        on_search = false
                        Collections.shuffle(address)
                        mAdapter!!.setItems(address)
                        progress_bar!!.visibility = View.GONE
                        recyclerView!!.visibility = View.VISIBLE
                    }, 500)
                } else {
                    img_clear.visibility = View.INVISIBLE
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        mAdapter!!.setOnItemClickListener(object : LocationListAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, obj: String?, position: Int) {
                sendDataResult(obj!!)
                dismissDialog()
            }

        })
        img_clear.setOnClickListener { et_search.setText("") }
        (root_view!!.findViewById<View>(R.id.imgBack) as ImageView).setOnClickListener { dismissDialog() }
        (root_view!!.findViewById<View>(R.id.lyt_select_from_map) as View).setOnClickListener {
            Toast.makeText(
                activity,
                "Select location from map",
                Toast.LENGTH_SHORT
            ).show()
        }

        //Tools.hideKeyboardFragment(this);
    }

    private fun sendDataResult(loc: String) {
        if (callbackResult != null) {
            callbackResult!!.sendResult(request_code, loc)
        }
    }

    private fun dismissDialog() {
        //Tools.hideKeyboardFragment(this)
        dismiss()
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    fun setHint(hint: String) {
        this.hint = hint
    }

    fun setRequestCode(request_code: Int) {
        this.request_code = request_code
    }

    interface CallbackResult {
        fun sendResult(requestCode: Int, loc: String?)
    }
}