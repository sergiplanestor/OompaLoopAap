package revolhope.splanes.com.presentation.common.component.loader

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import revolhope.splanes.com.presentation.databinding.ComponentLoaderBinding

class LoaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {

    val binding =
        ComponentLoaderBinding.inflate(LayoutInflater.from(context), this, true)

    inline var isLoaderVisible: Boolean
        get() = this.isVisible
        set(value) { if (value) start() else stop() }

    init { background = ContextCompat.getDrawable(context, android.R.color.transparent) }

    fun stop() {
        binding.lottieView.cancelAnimation()
        isGone = true
    }

    fun start() {
        binding.lottieView.playAnimation()
        isVisible = true
    }
}