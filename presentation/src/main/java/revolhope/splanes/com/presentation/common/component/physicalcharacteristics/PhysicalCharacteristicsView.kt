package revolhope.splanes.com.presentation.common.component.physicalcharacteristics

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaDetailsModel
import revolhope.splanes.com.presentation.common.extensions.toString
import revolhope.splanes.com.presentation.databinding.ComponentPhysicalCharacteristicsBinding

class PhysicalCharacteristicsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val binding =
        ComponentPhysicalCharacteristicsBinding.inflate(LayoutInflater.from(context), this, true)

    fun bind(model: OompaLoompaDetailsModel) {
        binding.gender.text = model.gender.toString(context)
        binding.age.text = model.age.toString()
        binding.height.text = model.height.toString()
    }

}