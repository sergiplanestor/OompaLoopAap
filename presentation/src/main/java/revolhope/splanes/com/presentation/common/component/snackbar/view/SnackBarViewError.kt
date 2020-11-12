package revolhope.splanes.com.presentation.common.component.snackbar.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import revolhope.splanes.com.presentation.R
import revolhope.splanes.com.presentation.common.component.snackbar.model.SnackBarModel

class SnackBarViewError(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SnackBarView<SnackBarModel.Error>(context, attrs, defStyleAttr) {

    private val messageView: TextView by lazy { findViewById(R.id.message) }

    init {
        View.inflate(context, R.layout.component_snackbar, this)
    }

    override fun bind(model: SnackBarModel.Error) {
        messageView.text = model.message
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        messageView.alpha = 1f
        messageView.animate().alpha(0f).setDuration(duration.toLong())
            .setStartDelay(delay.toLong())
            .start()
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        messageView.alpha = 0f
        messageView.animate().alpha(1f).setDuration(duration.toLong())
            .setStartDelay(delay.toLong())
            .start()
    }
}