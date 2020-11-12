package revolhope.splanes.com.presentation.common.component.selector

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import revolhope.splanes.com.presentation.R
import revolhope.splanes.com.presentation.databinding.ComponentSelectorBinding

class SelectorComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    enum class Options {
        RIGHT,
        LEFT,
        BOTH,
        ANY
    }

    private val binding =
        ComponentSelectorBinding.inflate(LayoutInflater.from(context), this, true)

    private val selectedBackground by lazy {
        ContextCompat.getDrawable(
            context,
            R.drawable.shape_pill_selected
        )
    }
    private val deselectedBackground by lazy {
        ContextCompat.getDrawable(
            context,
            R.drawable.shape_pill_deselected
        )
    }
    private var _optionSelected: Options = Options.ANY
    val optionSelected: Options get() = _optionSelected


    fun bind(model: SelectorUiModel) {
        binding.optionLeft.text = model.optionLeft
        binding.optionLeft.setOnClickListener {
            _optionSelected = when (_optionSelected) {
                Options.RIGHT -> Options.BOTH
                Options.LEFT -> Options.ANY
                Options.BOTH -> Options.RIGHT
                Options.ANY -> Options.LEFT
            }
            changeSelection()
        }
        binding.optionRight.text = model.optionRight
        binding.optionRight.setOnClickListener {
            _optionSelected = when (_optionSelected) {
                Options.RIGHT -> Options.ANY
                Options.LEFT -> Options.BOTH
                Options.BOTH -> Options.LEFT
                Options.ANY -> Options.RIGHT
            }
            changeSelection()
        }
        _optionSelected = model.optionSelected
        changeSelection()
    }

    private fun changeSelection() {
        when (_optionSelected) {
            Options.RIGHT -> {
                binding.optionLeft.background = deselectedBackground
                binding.optionRight.background = selectedBackground
            }
            Options.LEFT -> {
                binding.optionLeft.background = selectedBackground
                binding.optionRight.background = deselectedBackground
            }
            Options.BOTH -> {
                binding.optionLeft.background = selectedBackground
                binding.optionRight.background = selectedBackground
            }
            Options.ANY -> {
                binding.optionLeft.background = deselectedBackground
                binding.optionRight.background = deselectedBackground
            }
        }
    }
}