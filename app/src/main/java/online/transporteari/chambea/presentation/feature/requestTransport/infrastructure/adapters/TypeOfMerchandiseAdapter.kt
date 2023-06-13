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

class TypeOfMerchandiseAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var typeOfMerchandiseItems: MutableList<DurationModel> = ArrayList()
    var myTypeface = MyTypeface(context)
    var listener: TypeOfMerchandiseAdapterListener? = null
    var typeOfMerchandiseSelected: DurationModel? = null

    fun attachItems(items: List<DurationModel>) {
        typeOfMerchandiseItems = items as MutableList<DurationModel>
        typeOfMerchandiseItems.forEach {
            if (it.selected) {
                typeOfMerchandiseSelected = it
            }
        }
        notifyDataSetChanged()
    }

    fun attachListener(view: TypeOfMerchandiseAdapterListener) {
        listener = view
    }

    fun setPreviousSelectedPosition(position: Int?): List<DurationModel> {
        if (position != null) {
            typeOfMerchandiseItems[position].selected = true
        }
        return typeOfMerchandiseItems
    }

    fun resetSelected(positon: Int): List<DurationModel> {
        typeOfMerchandiseItems[positon].selected = false
        return typeOfMerchandiseItems
    }

    fun getItems(): List<DurationModel> {
        return typeOfMerchandiseItems
    }

    fun getSelectedName(position: Int): String {
        return typeOfMerchandiseItems[position].textSelected!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ViewholderItemMeasurementUnitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TypeOfMerchandiseViewHolder(binding)
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TypeOfMerchandiseViewHolder) {
            val item = typeOfMerchandiseItems[position]
            if (item.value == 1) {
                val text = context.resources.getString(R.string.every_duration, item.value.toString(), item.timeUnit!!.descriptionSingular)
                holder.viewBinding.txtTitle.text = text
                item.textSelected = text
            } else {
                val text = context.resources.getString(R.string.every_duration, item.value.toString(), item.timeUnit!!.descriptionPlural)
                holder.viewBinding.txtTitle.text = text
                item.textSelected = text
            }
            if (item.selected) {
                holder.viewBinding.txtTitle.setTextColor(ContextCompat.getColor(context, R.color.edt_default_text))
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    holder.viewBinding.txtTitle.typeface = context.resources.getFont(R.font.multi_bold)
                } else {
                    holder.viewBinding.txtTitle.typeface = myTypeface.openMultiBold()
                }

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
                listener!!.enabledButton()
            }
        }
    }

    fun validateSelected(value: Int, text: String?) {
        typeOfMerchandiseItems.forEach { item ->
            if (item.value == value) {
                item.selected = true
                item.textSelected = text
                typeOfMerchandiseSelected = item
            } else {
                item.textSelected = null
                item.selected = false
            }
        }
        //   listener!!.currenList(frequency!!)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return typeOfMerchandiseItems.size
    }

    class TypeOfMerchandiseViewHolder(var viewBinding: ViewholderItemMeasurementUnitBinding) : RecyclerView.ViewHolder(viewBinding.root)

    interface TypeOfMerchandiseAdapterListener {
        fun selectedPosition(position: Int)

        //fun currenList(items: List<DurationModel>)
        // fun frequencySelected(value: Int, timeUnidId: Int, text: String)
        fun enabledButton()
    }
}