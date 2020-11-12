package revolhope.splanes.com.presentation.common.component.emptystate

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import revolhope.splanes.com.presentation.databinding.ComponentEmptyStateBinding

class EmptyStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val binding =
        ComponentEmptyStateBinding.inflate(LayoutInflater.from(context), this, true)

    fun show() {
        isVisible = true
    }

    fun hide() {
        isGone = true
    }
}