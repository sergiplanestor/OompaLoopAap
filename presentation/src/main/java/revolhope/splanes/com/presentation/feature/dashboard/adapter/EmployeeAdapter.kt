package revolhope.splanes.com.presentation.feature.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaModel
import revolhope.splanes.com.presentation.R
import revolhope.splanes.com.presentation.common.extensions.*


class EmployeeAdapter(
    private val context: Context,
    private var items: MutableList<OompaLoompaModel>,
    private val onDetailClick: (OompaLoompaModel) -> Unit
) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

    private lateinit var recyclerView: RecyclerView
    private val originalWidth = context.screenWidth - 48.dp
    private val expandedWidth = context.screenWidth - 24.dp
    private var originalHeight = -1 // will be calculated dynamically
    private var expandedHeight = -1 // will be calculated dynamically
    private var expandedModel: OompaLoompaModel? = null
    private val listItemExpandDuration: Long get() = (300L / 1.5).toLong()

    private val originalBg by lazy { context.resources.getColor(R.color.teal_100, null) }
    private val expandedBg by lazy { context.resources.getColor(R.color.teal_200, null) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.holder_employee,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items[position]) {

            // set expand state on binded items because views are recycler
            expandItem(holder, expand = this == expandedModel, animate = false)

            holder.cardContainer.setOnClickListener {
                when (expandedModel) {
                    null -> {
                        // expand clicked view
                        expandItem(holder, expand = true, animate = true)
                        expandedModel = this
                    }
                    this -> {
                        // collapse clicked view
                        expandItem(holder, expand = false, animate = true)
                        expandedModel = null
                    }
                    else -> {
                        // collapse previously expanded view
                        val expandedModelPosition = items.indexOf(expandedModel!!)
                        val oldViewHolder =
                            recyclerView.findViewHolderForAdapterPosition(expandedModelPosition) as? ViewHolder
                        if (oldViewHolder != null) expandItem(
                            oldViewHolder,
                            expand = false,
                            animate = true
                        )

                        // expand clicked view
                        expandItem(holder, expand = true, animate = true)
                        expandedModel = this
                    }
                }
            }

            holder.image.loadCircularUrl(image)
            holder.name.text = String.format("%s, %s", lastName, firstName)
            holder.email.text = email
            holder.profession.text = profession
            holder.country.text = country
            holder.detailsButton.setOnClickListener { onDetailClick.invoke(this) }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)

        // get originalHeight & expandedHeight if not gotten before
        if (expandedHeight < 0) {
            expandedHeight = 0 // so that this block is only called once

            holder.cardContainer.doOnLayout { view ->
                originalHeight = view.height

                // show expandView and record expandedHeight in next layout pass
                // (doOnPreDraw) and hide it immediately. We use onPreDraw because
                // it's called after layout is done. doOnNextLayout is called during
                // layout phase which causes issues with hiding expandView.
                holder.expandedContainer.isVisible = true
                view.doOnPreDraw {
                    expandedHeight = view.height
                    holder.expandedContainer.isVisible = false
                }
            }
        }
    }

    fun updateItems(newItems: List<OompaLoompaModel>) {
        if (newItems.isEmpty()) {
            items.clear()
            notifyDataSetChanged()
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                val diffResult = calculateDiffResult(newItems)
                items.clear()
                items.addAll(newItems)
                diffResult.dispatchUpdatesTo(this@EmployeeAdapter)
            }
        }
    }

    private suspend fun calculateDiffResult(newData: List<OompaLoompaModel>): DiffUtil.DiffResult =
        withContext(Dispatchers.IO) {
            DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                override fun getOldListSize(): Int = items.size

                override fun getNewListSize(): Int = newData.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    items[oldItemPosition].id == newData[newItemPosition].id

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean = items[oldItemPosition] == newData[newItemPosition]
            })
        }

    private fun expandItem(holder: ViewHolder, expand: Boolean, animate: Boolean) {

        if (animate) {
            val animator = getValueAnimator(
                expand, listItemExpandDuration, AccelerateDecelerateInterpolator()
            ) { progress -> setExpandProgress(holder, progress) }

            if (expand) animator.doOnStart { holder.expandedContainer.isVisible = true }
            else animator.doOnEnd { holder.expandedContainer.isVisible = false }

            animator.start()
        } else {

            // show expandView only if we have expandedHeight (onViewAttached)
            holder.expandedContainer.isVisible = expand && expandedHeight >= 0
            setExpandProgress(holder, if (expand) 1f else 0f)
        }
    }

    private fun setExpandProgress(holder: ViewHolder, progress: Float) {
        if (expandedHeight > 0 && originalHeight > 0) {
            holder.cardContainer.layoutParams.height =
                (originalHeight + (expandedHeight - originalHeight) * progress).toInt()
        }
        holder.cardContainer.layoutParams.width =
            (originalWidth + (expandedWidth - originalWidth) * progress).toInt()

        holder.cardContainer.setCardBackgroundColor(blendColors(originalBg, expandedBg, progress))
        holder.cardContainer.requestLayout()

        holder.arrow.rotation = -180 * progress
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val arrow: View by lazy { itemView.findViewById(R.id.arrow) }
        val cardContainer: CardView by lazy { itemView.findViewById(R.id.card_container) }
        val expandedContainer: View by lazy { itemView.findViewById(R.id.expanded_layout) }
        val image: ImageView by lazy { itemView.findViewById(R.id.image) }
        val name: TextView by lazy { itemView.findViewById(R.id.name) }
        val email: TextView by lazy { itemView.findViewById(R.id.email) }
        val profession: TextView by lazy { itemView.findViewById(R.id.profession) }
        val country: TextView by lazy { itemView.findViewById(R.id.country) }
        val detailsButton: Button by lazy { itemView.findViewById(R.id.details_button) }
    }

}