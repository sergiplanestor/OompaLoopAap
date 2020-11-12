package revolhope.splanes.com.presentation.feature.dashboard.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import revolhope.splanes.com.domain.feature.employees.model.Gender
import revolhope.splanes.com.presentation.common.component.selector.SelectorComponent
import revolhope.splanes.com.presentation.common.component.selector.SelectorUiModel
import revolhope.splanes.com.presentation.common.extensions.toString
import revolhope.splanes.com.presentation.databinding.BottomSheetFilterBinding

class FilterBottomSheet(
    private val currentFilter: FilterModel?,
    private val onFilterApplied: (FilterModel) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.genderSelector.bind(
            model = SelectorUiModel(
                optionRight = Gender.MALE.toString(requireContext()),
                optionLeft = Gender.FEMALE.toString(requireContext()),
                optionSelected = fromGendersToOptions(currentFilter?.genderFilter)
            )
        )
        binding.professionEditText.setText(currentFilter?.queryProfession ?: "")
        binding.doneButton.setOnClickListener {
            onFilterApplied.invoke(
                FilterModel(
                    genderFilter = fromOptionsToGender(binding.genderSelector.optionSelected),
                    queryProfession = binding.professionEditText.text.toString()
                )
            )
            this.dismiss()
        }
    }

    fun show(fm: FragmentManager) {
        this.show(fm, javaClass.simpleName)
    }

    private fun fromGendersToOptions(list: List<Gender>?): SelectorComponent.Options {
        if (list.isNullOrEmpty()) return SelectorComponent.Options.ANY
        if (list.size >= 2) return SelectorComponent.Options.BOTH
        return when (list[0]) {
            Gender.MALE -> SelectorComponent.Options.RIGHT
            Gender.FEMALE -> SelectorComponent.Options.LEFT
            Gender.UNKNOWN -> SelectorComponent.Options.ANY
        }
    }

    private fun fromOptionsToGender(options: SelectorComponent.Options): List<Gender> =
        when (options) {
            SelectorComponent.Options.RIGHT -> listOf(Gender.MALE)
            SelectorComponent.Options.LEFT -> listOf(Gender.FEMALE)
            SelectorComponent.Options.BOTH -> listOf(Gender.MALE, Gender.FEMALE)
            SelectorComponent.Options.ANY -> emptyList()
        }
}