package online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import online.transporteari.chambea.R
import online.transporteari.chambea.databinding.ViewholderItemMeasurementUnitBinding
import online.transporteari.chambea.presentation.common.utils.MyTypeface
import online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.models.DurationModel
import online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.models.TimeUnitModel

class MeasurementUnitsAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var measurementUnitItems: MutableList<DurationModel> = ArrayList()
    var myTypeface = MyTypeface(context)
    var listener: MeasurementUnitAdapterListener? = null
    var measurementUnitSelected: DurationModel? = null


    fun attachItems(items: List<DurationModel>) {
        measurementUnitItems = items as MutableList<DurationModel>
        measurementUnitItems.forEach {
            if (it.selected) {
                measurementUnitSelected = it
            }
        }
        notifyDataSetChanged()
    }

    fun attachListener(view: MeasurementUnitAdapterListener) {
        listener = view
    }

    fun setPreviousSelectedPosition(position: Int?): List<DurationModel> {
        if (position != null) {
            measurementUnitItems[position].selected = true
        }
        return measurementUnitItems
    }

    fun resetSelected(positon: Int): List<DurationModel> {
        measurementUnitItems[positon].selected = false
        return measurementUnitItems
    }

    fun getItems(): List<DurationModel> {
        return measurementUnitItems
    }

    fun getSelectedName(position: Int): String {
        return measurementUnitItems[position].textSelected!!
    }

    fun getSelectedValue(position: Int): Int {
        return measurementUnitItems[position].value!!
    }

    fun getSelectedTimeUnid(position: Int): TimeUnitModel? {
        return measurementUnitItems[position].timeUnit!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ViewholderItemMeasurementUnitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MeasurementUnitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MeasurementUnitViewHolder) {
            val item = measurementUnitItems[position]
            if (item.value == 1) {
                //val text = context.resources.getString(R.string.every, item.value.toString(), item.timeUnit!!.descriptionSingular)
                holder.viewBinding.txtTitle.text = item.timeUnit!!.descriptionSingular
                item.textSelected = item.timeUnit!!.descriptionSingular
            } else {
                //val text = context.resources.getString(R.string.every, item.value.toString(), item.timeUnit!!.descriptionPlural)
                holder.viewBinding.txtTitle.text = item.timeUnit!!.descriptionPlural
                item.textSelected = item.timeUnit!!.descriptionPlural
            }
            if (item.selected) {
                holder.viewBinding.txtTitle.setTextColor(ContextCompat.getColor(context, R.color.edt_default_text))
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    holder.viewBinding.txtTitle.typeface = context.resources.getFont(R.font.multi_bold)
                } else {
                    holder.viewBinding.txtTitle.typeface = myTypeface.openMultiBold()
                }
                listener!!.enabledButton()
            } else {
                holder.viewBinding.txtTitle.setTextColor(ContextCompat.getColor(context, R.color.edt_default_hint))
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    holder.viewBinding.txtTitle.typeface = context.resources.getFont(R.font.multi_regular)
                } else {
                    holder.viewBinding.txtTitle.typeface = myTypeface.openMultiRegular()
                }
            }

            holder.viewBinding.cardItem.setOnClickListener {
                listener!!.selectedPosition(position)
                validateSelected(item.value!!, item.textSelected)
            }

        }
    }

    fun validateSelected(value: Int, text: String?) {
        measurementUnitItems.forEach { item ->
            if (item.value == value) {
                item.selected = true
                item.textSelected = text
                measurementUnitSelected = item
            } else {
                item.textSelected = null
                item.selected = false
            }
        }
        //   listener!!.currenList(frequency!!)
        notifyDataSetChanged()
    }

    fun getItemSelected(): DurationModel {
        return measurementUnitSelected!!
    }

    override fun getItemCount(): Int {
        return measurementUnitItems.size
    }

    fun defaultSelected(value: Int): Int {
        var position = 0
        measurementUnitItems.forEach {
            if (it.value == value && it.value == 1) {
                val text = context.resources.getString(R.string.every, it.value.toString(), it.timeUnit!!.descriptionSingular)
                it.selected = true
                it.textSelected = text
                notifyDataSetChanged()
                return position
            } else if (it.value == value) {
                val text = context.resources.getString(R.string.every, it.value.toString(), it.timeUnit!!.descriptionPlural)
                it.selected = true
                it.textSelected = text
                notifyDataSetChanged()
                return position
            }
            position++
        }
        return position
    }


    class MeasurementUnitViewHolder(var viewBinding: ViewholderItemMeasurementUnitBinding) : RecyclerView.ViewHolder(viewBinding.root)

    interface MeasurementUnitAdapterListener {
        fun selectedPosition(position: Int)

        //fun currenList(items: List<DurationModel>)
        // fun frequencySelected(value: Int, timeUnidId: Int, text: String)
        fun enabledButton()
    }
}