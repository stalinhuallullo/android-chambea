package online.transporteari.chambea.presentation.feature.dashboard.infrastructure.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import online.transporteari.chambea.R
import online.transporteari.chambea.presentation.feature.dashboard.infrastructure.models.RideClass


class RideClassListAdapter(private val ctx: Context, items: List<RideClass>) :
    RecyclerView.Adapter<RideClassListAdapter.ViewHolder>() {
    private var items: List<RideClass> = ArrayList()
    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View?, obj: RideClass?, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = mItemClickListener
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var image: ImageView
        var class_name: TextView
        var price: TextView
        //var pax: TextView
        //var duration: TextView
        var lyt_parent: View

        init {
            image = v.findViewById(R.id.image)
            class_name = v.findViewById(R.id.className)
            price = v.findViewById(R.id.price)
            //pax = v.findViewById(R.id.pax)
            //duration = v.findViewById(R.id.duration)
            lyt_parent = v.findViewById(R.id.lytParent)
        }
    }

    init {
        println("items =====> " + items)
        this.items = items
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ride_class, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val r: RideClass = items[position]
        Picasso.with(ctx).load(r.image).into(holder.image)
        holder.class_name.setText(r.class_name)
        holder.price.setText(r.price)
        //holder.pax.setText(r.pax)
        //holder.duration.setText(r.duration)
        holder.lyt_parent.setOnClickListener { view ->
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(view, r, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}