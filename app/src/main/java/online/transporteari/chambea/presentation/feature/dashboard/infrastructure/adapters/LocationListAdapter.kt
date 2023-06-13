package online.transporteari.chambea.presentation.feature.dashboard.infrastructure.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import online.transporteari.chambea.R
import online.transporteari.chambea.presentation.feature.dashboard.infrastructure.models.AddressAutoCompletePredictions

class LocationListAdapter(private val ctx: Context) :
    RecyclerView.Adapter<LocationListAdapter.ViewHolder>() {
    private var items: MutableList<AddressAutoCompletePredictions> = ArrayList()
    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View?, obj: String?, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = mItemClickListener
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var title: TextView
        var subtitle: TextView
        var lyt_parent: View

        init {
            title = v.findViewById(R.id.title) as TextView
            subtitle = v.findViewById(R.id.subtitle) as TextView
            lyt_parent = v.findViewById(R.id.lytParent) as View
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationListAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: LocationListAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.primaryText
        holder.subtitle.text = item.secondaryText
        holder.lyt_parent.setOnClickListener { view ->
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(view, item.fullText, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setItems(items: MutableList<AddressAutoCompletePredictions>) {
        this.items = items
        notifyDataSetChanged()
    }
}