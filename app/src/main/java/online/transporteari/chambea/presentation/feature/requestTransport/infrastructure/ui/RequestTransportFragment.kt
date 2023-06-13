package online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.globant.inkafarma.domain.entity.PaymentMethod
import online.transporteari.chambea.R
import online.transporteari.chambea.databinding.FragmentRequestTransportBinding
import online.transporteari.chambea.databinding.ViewDeliveryDayBinding
import online.transporteari.chambea.databinding.ViewMeasurementUnitsBinding
import online.transporteari.chambea.databinding.ViewTypeOfMerchandiseBinding
import online.transporteari.chambea.domain.LocalDateTimeModel
import online.transporteari.chambea.domain.RequestTransportPack
import online.transporteari.chambea.presentation.base.BaseChambeaFragment
import online.transporteari.chambea.presentation.common.error.ErrorModel
import online.transporteari.chambea.presentation.common.utils.Constant
import online.transporteari.chambea.presentation.common.utils.ConstantFunctions
import online.transporteari.chambea.presentation.common.utils.OnSingleClickListener
import online.transporteari.chambea.presentation.common.widget.CustomItemBig
import online.transporteari.chambea.presentation.feature.payment.infrastructure.ui.PaymentMethodActivity
import online.transporteari.chambea.presentation.feature.requestTransport.domain.models.ScheduleCanonicalModel
import online.transporteari.chambea.presentation.feature.requestTransport.domain.models.ScheduleDayCanonicalModel
import online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.adapters.ConfigurationRequestTransportView
import online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.adapters.DaysAdapter
import online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.adapters.MeasurementUnitsAdapter
import online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.adapters.TypeOfMerchandiseAdapter
import online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.models.DurationModel
import online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.models.TimeUnitModel
import online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.presenter.ConfigurationRequestTransportPresenter


class RequestTransportFragment : BaseChambeaFragment(), ConfigurationRequestTransportView, CustomItemBig.ItemListener {
    private lateinit var binding: FragmentRequestTransportBinding

    //@Inject
    lateinit var presenter: ConfigurationRequestTransportPresenter
    //lateinit var listener: ConfigurationRecListener

    var measurementUnitItems: List<DurationModel>? = null
    var positionMeasurementUnit: Int? = null
    var positionPreviousMeasurementUnit: Int? = null

    var typeOfMerchandiseItems: List<DurationModel>? = null
    var positionTypeOfMerchandise: Int? = null
    var positionPreviousTypeOfMerchandise: Int? = null

    lateinit var adapterDays: DaysAdapter
    var positionDay: Int? = null
    var previusPositionDay: Int? = null

    var scheduleTimeZones: List<ScheduleDayCanonicalModel> = ArrayList()
    var previousScheduleTimeZones: List<ScheduleCanonicalModel> = ArrayList()

    var buttonMeasurementUnit = false
    var buttonDay = false
    var buttonHour = false
    var buttonTypeOfMerchandise = false
    var confirmCartValue = false

    var requestTransportPackUdpate: RequestTransportPack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestTransportBinding.inflate(layoutInflater, container, false)
        binding.event = this
        initClickListener()
        adapterDays = DaysAdapter(requireContext())

        presenter = ConfigurationRequestTransportPresenter()
        presenter.attachView(this)
        // llenado temporal
        requestTransportPackUdpate = RequestTransportPack()
        requestTransportPackUdpate?.name = "Taylor Swift"

        return binding.root
    }

    fun initClickListener() {
        binding.ccDayDelivery.setOnClickListener(object: OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                selectedDayService()
            }
        })
        binding.ccMeasurementUnits.setOnClickListener(object: OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                selectedMeasurementUnits()
            }
        })
        binding.ccQuantity.setOnClickListener(object: OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                selectedQuantity()
            }
        })


        binding.ccTypeOfMerchandise.setOnClickListener(object: OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                selectedTypeOfMerchandise()
            }
        })


        binding.ciPickUpPlace.setOnclickItem(this)
        binding.ciPlaceOfUnloading.setOnclickItem(this)
        //binding.ciPaymentMethod.setOnclickItem(this)
        binding.ciReceip.setOnclickItem(this)
    }

    /*override fun setTimeZones(timeZones: List<ScheduleDayCanonicalModel>) {
        scheduleTimeZones = timeZones as MutableList
        selecteDayDelivery(true)
    }*/

    fun selectedDayService() {
        presenter?.getDayDelivery(true)

        var list = mutableListOf<ScheduleDayCanonicalModel>()

        val listMeasure = listOf("2023-06-08", "2023-06-09", "2023-06-10", "2023-06-11", "2023-06-12", "2023-06-13", "2023-06-14")
        val listMeasureString = listOf("jueves, 08 de junio", "viernes, 09 de junio", "sábado, 10 de junio", "domingo, 11 de junio", "lunes, 12 de junio", "martes, 13 de junio", "miércoles, 14 de junio")
        //val listMeasure = listOf("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo")
        listMeasure.mapIndexed {index, item ->

            var scheduleDay = ScheduleDayCanonicalModel()
            scheduleDay.code = index.toString()
            scheduleDay.id = index + 1
            scheduleDay.day = item
            scheduleDay.selectedDay = false
            scheduleDay.message = listMeasureString[index]
            scheduleDay.today = "08-06-2023"
            scheduleDay.seleceted = false
            list.add(scheduleDay)
        }

        scheduleTimeZones = list

        if (scheduleTimeZones == null) {
            //presenter?.getFrequencies(true)
        } else {
            showConfigurationDayService(scheduleTimeZones!!, true)
        }
    }

    fun selectedMeasurementUnits() {
        var list = mutableListOf<DurationModel>()

        val listMeasure = listOf("Peso", "Volumen")
        listMeasure.mapIndexed {index, item ->
            var timeUnitModel1 = TimeUnitModel()
            timeUnitModel1.timeUnitId = index + 10
            timeUnitModel1.description = item
            timeUnitModel1.descriptionSingular = item
            timeUnitModel1.descriptionPlural = item

            var durationModel1 = DurationModel()
            durationModel1.timeUnit = timeUnitModel1
            durationModel1.value = index + 1
            durationModel1.selected = false
            durationModel1.textSelected = item
            list.add(durationModel1)
        }

        measurementUnitItems = list

        if (measurementUnitItems == null) {
            //presenter?.getFrequencies(true)
        } else {
            showConfigurationFrequency(measurementUnitItems!!, true)
        }
    }
    fun selectedQuantity() {
        showConfigurationQuantity()
    }

    fun selectedTypeOfMerchandise() {
        var list = mutableListOf<DurationModel>()


        val listMerchandise = listOf(
            "Explosivos",
            "Gases",
            "Líquidos inflamables",
            "Sólidos inflamables",
            "Sustancias comburentes y peróxidos orgánicos",
            "Sustancias tóxicas y sustancias infecciosas",
            "Material radiactivo",
            "Sustancias corrosivas",
            "Sustancias y objetos peligrosos varios",
        )
        listMerchandise.mapIndexed {index, item ->
            var timeUnitModel1 = TimeUnitModel()
            timeUnitModel1.timeUnitId = index + 10
            timeUnitModel1.description = item
            timeUnitModel1.descriptionSingular = item +" Singular"
            timeUnitModel1.descriptionPlural = item +" Plural"

            var durationModel1 = DurationModel()
            durationModel1.timeUnit = timeUnitModel1
            durationModel1.value = index + 1
            durationModel1.selected = false
            durationModel1.textSelected = item
            list.add(durationModel1)
        }

        typeOfMerchandiseItems = list
        if (typeOfMerchandiseItems == null) {
            //presenter?.getFrequencies(true)
        } else {
            showConfigurationTypeOfMerchandise(typeOfMerchandiseItems!!, true)
        }
    }

    /*override fun showConfigurationDayService(items: List<ScheduleDayCanonicalModel>, alertShow: Boolean) {

        }*/
    override fun showConfigurationDayService(items: List<ScheduleDayCanonicalModel>, alertShow: Boolean) {
        scheduleTimeZones = items

        val alertDialogBuilder = AlertDialog.Builder(context)
        val viewBinding = ViewDeliveryDayBinding.inflate(LayoutInflater.from(requireContext()))

        alertDialogBuilder.setView(viewBinding.root)

        adapterDays.attachtItems(scheduleTimeZones)


        /*recurrencyPackUdpate?.let {

            if (it.deliveryDate != null && it.deliveryDay != null) {

                val deliveryDate = it.deliveryDate
                positionDay = adapterDays.daySelectedDefault(it.deliveryDay!!, deliveryDate!!.date!!)
                previusPositionDay = positionDay

                val dayName = adapterDays.getSelectedName(positionDay!!)

                binding.lnDayDelivery.setInputTitle(stringFirstUppercase(dayName))

                previousScheduleTimeZones = scheduleTimeZones[positionDay!!].schedules as MutableList

                if (recurrencyPackUdpate!!.scheduleHour != null) {
                    binding.lnHourDelivery.setInputTitle(recurrencyPackUdpate!!.scheduleHour)
                }
                if (!validateAddressModification()) {
                    binding.btnSave.isEnabled = false
                }

            }

        }*/

        positionDay = previusPositionDay
        scheduleTimeZones = adapterDays.setPreviusSelectedPosition(previusPositionDay) as MutableList<ScheduleDayCanonicalModel>

        val layoutmanager = GridLayoutManager(requireContext(), 4)
        viewBinding.rvItem.layoutManager = layoutmanager
        viewBinding.rvItem.layoutManager = layoutmanager
        viewBinding.rvItem.adapter = adapterDays


        adapterDays.attachListener(object : DaysAdapter.DayAdapterListener {
            override fun enabledButton(value: Boolean) {
                viewBinding.btnAcept.isEnabled = value
                //binding.lnHourDelivery.setInputTitle(resources.getString(R.string.message_selected_hour_recurrency))
            }

            override fun seletedDay(position: Int, date: String) {
                positionDay = position
                viewBinding.txtMessage1.text = resources.getString(R.string.default_text_day_complete, date)
            }
        })

        if (!adapterDays.daySelected().isEmpty()) {
            viewBinding.txtMessage1.text = resources.getString(R.string.default_text_day_complete, adapterDays.daySelected())
        }

        val alertDialog = alertDialogBuilder.create()

        viewBinding.imgClose.setOnClickListener {
            if (positionDay != null) {
                scheduleTimeZones = adapterDays.resetSelected(positionDay!!) as MutableList<ScheduleDayCanonicalModel>

            }
            if (previusPositionDay != null) {
                val dayName = adapterDays.getSelectedName(previusPositionDay!!)
                binding.ccDayDelivery.setInputTitle(dayName)
            }
            buttonDay = false
            alertDialog.dismiss()
            alertDialog.cancel()

        }

        viewBinding.btnAcept.setOnClickListener {
            scheduleTimeZones = adapterDays.getItemsDays()
            previusPositionDay = positionDay

            val dayName = adapterDays.getSelectedName(positionDay!!)
            binding.ccDayDelivery.setInputTitle(stringFirstUppercase(dayName))

            println("sssssssssssssssssssss ==> "  + requestTransportPackUdpate)
            requestTransportPackUdpate?.let {

                it.deliveryDay = adapterDays.getSelectedCodeDay(positionDay!!)
                println("deliveryDay =====> "  + adapterDays.getSelectedCodeDay(positionDay!!))
                val localDateTimeModel = LocalDateTimeModel()
                localDateTimeModel.date = scheduleTimeZones[positionDay!!].day
                it.deliveryDate = localDateTimeModel
                it.processEndConfirm = null
                it.processStartConfirm = null
                it.scheduleHour = null

                //positionCurrentSchedule = null
                //previousPositionSchedule = null

                /*if (!KEY_ACTIVE_PACK.equals(statusPack)) {
                    udpdatePack()
                } else {
                    calculateNextOrderTime()
                }*/

            }

            //previousScheduleTimeZones = scheduleTimeZones[positionDay!!].schedules as MutableList

            //constan call to cart
            alertDialog.dismiss()
            alertDialog.cancel()

        }
        val window = alertDialog.window

        if (window != null) {
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            window.setWindowAnimations(android.R.style.Animation_Dialog)
            viewBinding.root.startAnimation(ConstantFunctions().animationView())
        }
        alertDialog.setCancelable(false)
        if (alertShow) {
            alertDialog.show()
        }
    }



    override fun showConfigurationFrequency(items: List<DurationModel>, alertShow: Boolean) {
        measurementUnitItems = items

        val alertDialogBuilder = AlertDialog.Builder(context)
        val viewBinging = ViewMeasurementUnitsBinding.inflate(LayoutInflater.from(requireContext()))
        alertDialogBuilder.setView(viewBinging.root)

        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = MeasurementUnitsAdapter(requireContext())
        viewBinging.rvItems.layoutManager = layoutManager
        viewBinging.rvItems.adapter = adapter
        adapter.attachItems(measurementUnitItems!!)
        positionMeasurementUnit = positionPreviousMeasurementUnit
        measurementUnitItems = adapter.setPreviousSelectedPosition(positionPreviousMeasurementUnit)

        adapter.attachListener(object : MeasurementUnitsAdapter.MeasurementUnitAdapterListener {
            override fun selectedPosition(position: Int) {
                positionMeasurementUnit = position
            }

            override fun enabledButton() {
                viewBinging.btnAcept.isEnabled = true
            }
        })

        val alertDialog = alertDialogBuilder.create()
        viewBinging.imgClose.setOnClickListener {
            if (positionMeasurementUnit != null) {
                measurementUnitItems = adapter.resetSelected(positionMeasurementUnit!!) as MutableList<DurationModel>
            }
            if (positionPreviousMeasurementUnit != null) {
                val dayName = adapter.getSelectedName(positionPreviousMeasurementUnit!!)
                binding.ccMeasurementUnits.setInputTitle(dayName)
            }

            buttonMeasurementUnit = false
            alertDialog.cancel()
            alertDialog.dismiss()

        }

        viewBinging.btnAcept.setOnClickListener {
            measurementUnitItems = adapter.getItems() as MutableList
            positionPreviousMeasurementUnit = positionMeasurementUnit

            val dayName = adapter.getSelectedName(positionMeasurementUnit!!)
            binding.ccMeasurementUnits.setInputTitle(dayName)

            //add configuration reclist
            /*recurrencyPackUdpate?.let {
                it.frecuencyValue = adapter.getSelectedValue(positionFrequency!!)
                it.frecuencyUnit = adapter.getSelectedTimeUnid(positionFrequency!!)!!.timeUnitId

                udpdatePack()
            }*/

            alertDialog.dismiss()
        }

        val window = alertDialog.window
        if (window != null) {
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            window.setWindowAnimations(android.R.style.Animation_Dialog)
            viewBinging.root.startAnimation(ConstantFunctions().animationView())
        }

        alertDialog.setCancelable(false)
        if (alertShow) {
            alertDialog.show()
        }
    }

    override fun showConfigurationTypeOfMerchandise(items: List<DurationModel>, alertShow: Boolean) {
        typeOfMerchandiseItems = items

        val alertDialogBuilder = AlertDialog.Builder(context)
        val viewBinging = ViewTypeOfMerchandiseBinding.inflate(LayoutInflater.from(requireContext()))
        alertDialogBuilder.setView(viewBinging.root)

        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = TypeOfMerchandiseAdapter(requireContext())
        viewBinging.rvItems.layoutManager = layoutManager
        viewBinging.rvItems.adapter = adapter
        adapter.attachItems(typeOfMerchandiseItems!!)
        /*recurrencyPackUdpate?.durationValue?.let {
            positionDurations = adapter.getItemSelectedDefault(it)
            positionPreviousDurations = positionDurations
            binding.lnDateDelivery.setInputTitle(adapter.getItemSelected()!!.textSelected)
        }*/

        positionTypeOfMerchandise = positionPreviousTypeOfMerchandise
        typeOfMerchandiseItems = adapter.setPreviousSelectedPosition(positionPreviousTypeOfMerchandise)

        adapter.attachListener(object : TypeOfMerchandiseAdapter.TypeOfMerchandiseAdapterListener {
            override fun selectedPosition(position: Int) {
                positionTypeOfMerchandise = position
            }

            override fun enabledButton() {
                viewBinging.btnAcept.isEnabled = true
            }
        })

        val alertDialog = alertDialogBuilder.create()
        viewBinging.imgClose.setOnClickListener {
            if (positionTypeOfMerchandise != null) {
                typeOfMerchandiseItems = adapter.resetSelected(positionTypeOfMerchandise!!) as MutableList<DurationModel>
            }
            if (positionPreviousTypeOfMerchandise != null) {
                val dayName = adapter.getSelectedName(positionPreviousTypeOfMerchandise!!)
                binding.ccTypeOfMerchandise.setInputTitle(dayName)
            }

            buttonTypeOfMerchandise = false
            alertDialog.cancel()
            alertDialog.dismiss()

        }

        viewBinging.btnAcept.setOnClickListener {
            typeOfMerchandiseItems = adapter.getItems() as MutableList
            positionPreviousTypeOfMerchandise = positionTypeOfMerchandise

            val dayName = adapter.getSelectedName(positionTypeOfMerchandise!!)
            binding.ccTypeOfMerchandise.setInputTitle(dayName)

            //add configuration reclist
            /*recurrencyPackUdpate?.let {
                it.frecuencyValue = adapter.getSelectedValue(positionFrequency!!)
                it.frecuencyUnit = adapter.getSelectedTimeUnid(positionFrequency!!)!!.timeUnitId

                udpdatePack()
            }*/

            alertDialog.dismiss()
        }

        val window = alertDialog.window
        if (window != null) {
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            window.setWindowAnimations(android.R.style.Animation_Dialog)
            viewBinging.root.startAnimation(ConstantFunctions().animationView())
        }

        alertDialog.setCancelable(false)
        if (alertShow) {
            alertDialog.show()
        }
    }
    override fun showConfigurationQuantity() {

    }
    /*override fun setPackRecurrency() {

    }*/

    fun stringFirstUppercase(str: String?): String? {
        return if (str == null || str.isEmpty()) {
            ""
        } else {
            str.substring(0, 1).uppercase() + str.substring(1)
        }
    }

    override fun onError(errorModel: ErrorModel?) {

    }

    override fun context(): Context? {
        val activity = activity
        return activity?.applicationContext
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RequestTransportFragment().apply {
                /*arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }*/
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        //presenter.destroy()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ConfigurationRecListener) {
            //this.listener = context
        }
    }
    interface ConfigurationRecListener {
        fun goToPickAddres()
    }

    override fun testClick(tag: String?) {
        when (tag) {
            binding.ciPickUpPlace.tag -> {
                Toast.makeText(context(), "Cuchis Lugar de recojo", Toast.LENGTH_SHORT).show()
            }
            binding.ciPlaceOfUnloading.tag -> {
                Toast.makeText(context(), "Cuchis Lugar de descarga", Toast.LENGTH_SHORT).show()
            }
            /*binding.ciPaymentMethod.tag -> {
                Toast.makeText(context(), "Cuchis Método de pago", Toast.LENGTH_SHORT).show()
                validatePaymentsAvailable()
            }*/
            binding.ciReceip.tag -> {
                Toast.makeText(context(), "Cuchis Comprobante de pago", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun validatePaymentsAvailable() {
        val paymentsAvailable: MutableList<PaymentMethod> = ArrayList()
        /*paymentsRecurrency.forEach { t ->
            paymentsCurrent.forEach { c ->
                if (t.id == c.id) {
                    paymentsAvailable.add(c)
                }
            }
        }*/
        //listener.goToPaymentMethod(paymentsAvailable)
        goToPaymentMethod(paymentsAvailable)
    }

    fun udpdatePack() {
        //presenter.updateConfiguration()
    }

    fun goToPaymentMethod(payments: List<PaymentMethod>) {
        val intent = Intent(activity, PaymentMethodActivity::class.java)
        //intent.putParcelableArrayListExtra(Constant().EXTRA_LIST_PAYMENT_METHOD, payments as java.util.ArrayList<out Parcelable?>?)
        //intent.putExtra(Constant().EXTRA_FROM_RECURRENCY_PACK, true)
        startActivity(intent) // , Constant().INTENT_PICK_PAYMENT_METHOD
    }
}