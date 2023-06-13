package online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import online.transporteari.chambea.R
import online.transporteari.chambea.databinding.ViewHolderDaysBinding
import online.transporteari.chambea.presentation.common.utils.DateUtils
import online.transporteari.chambea.presentation.feature.requestTransport.domain.models.ScheduleDayCanonicalModel

class DaysAdapter(var requireContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var days: MutableList<ScheduleDayCanonicalModel> = ArrayList()
    var listener: DayAdapterListener? = null
    var previusPosition: Int? = null

    fun attachtItems(days: List<ScheduleDayCanonicalModel>) {
        this.days = days as MutableList<ScheduleDayCanonicalModel>
        validateEnabledButton()
        notifyDataSetChanged()
    }

    fun validateEnabledButton() {
        days.forEach {
            if (it.seleceted) {
                listener!!.enabledButton(it.seleceted)
            }
        }
    }

    fun attachListener(listener: DayAdapterListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ViewHolderDaysBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DaysViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val day = days[position]
        if (holder is DaysViewHolder) {

            val nameDay = DateUtils().getDayName(day.day!!)

            if (day.seleceted) {
                holder.binding.txtDay.setBackgroundResource(R.drawable.shape_circle_blue)
                holder.binding.txtDay.setTextColor(ContextCompat.getColor(requireContext, R.color.white))
            } else {
                holder.binding.txtDay.setBackgroundResource(R.drawable.shape_circle_gray)
                holder.binding.txtDay.setTextColor(ContextCompat.getColor(requireContext, R.color.subtitle_label))
            }

            holder.binding.txtDay.text = initialDayName(nameDay!!)
            holder.binding.lnItem.setOnClickListener {
                listener!!.seletedDay(position, day.message!!)
                validateDaySelcted(position)
                validateEnabledButton()
            }
        }
    }

    fun getSelectedName(position: Int): String? {
        return DateUtils().getDayName(days[position].day!!)
    }

    fun getSelectedCodeDay(position: Int): Int {
        val nameDay = DateUtils().getDayName(days[position].day!!)
        val day = initialDayName(nameDay!!)
        return codeDayByName(day)
    }

    fun getItemsDays(): MutableList<ScheduleDayCanonicalModel> {
        return days
    }

    fun setPreviusSelectedPosition(position: Int?): List<ScheduleDayCanonicalModel> {
        if (position != null) {
            days[position].seleceted = true
        }
        return days
    }

    fun resetSelected(positon: Int): List<ScheduleDayCanonicalModel> {
        days[positon].seleceted = false
        return days
    }

    fun daySelected(): String {
        days.forEach {
            if (it.seleceted) {
                return it.message!!
            }
        }
        return ""
    }

    fun isEnableButton(): Boolean {
        days.forEach {
            if (it.seleceted) {
                return true
            }
        }
        return false
    }

    fun validateDaySelcted(position: Int) {
        var x = 0
        days.forEach {
            if (x == position) {
                it.seleceted = true
            } else {
                it.seleceted = false
            }
            x++;
        }
        notifyDataSetChanged()
    }

    fun initialDayName(nameDay: String): String {

        when (nameDay.lowercase()) {
            "lunes" -> {
                return "L"
            }
            "martes" -> {
                return "M"
            }
            "miércoles" -> {
                return "X"
            }
            "jueves" -> {
                return "J"
            }
            "viernes" -> {
                return "V"
            }
            "sábado" -> {
                return "S"
            }
            "domingo" -> {
                return "D"
            }
        }
        return ""
    }

    fun codeDayByName(day: String): Int {

        when (day) {
            "L" -> {
                return 1
            }
            "M" -> {
                return 2
            }
            "X" -> {
                return 3
            }
            "J" -> {
                return 4
            }
            "V" -> {
                return 5
            }
            "S" -> {
                return 6
            }
            "D" -> {
                return 7
            }

        }
        return 0
    }

    override fun getItemCount(): Int {
        return days.size
    }

    fun daySelectedDefault(value: Int, deliveryDate: String): Int {
        var position = 0
        days.forEach {
            if (it.day.equals(deliveryDate)) {
                it.seleceted = true
                return position
            }

            position++
        }
        return 0
    }

    class DaysViewHolder(var binding: ViewHolderDaysBinding) : RecyclerView.ViewHolder(binding.root)

    interface DayAdapterListener {
        fun seletedDay(position: Int, date: String);
        fun enabledButton(value: Boolean)

    }
}