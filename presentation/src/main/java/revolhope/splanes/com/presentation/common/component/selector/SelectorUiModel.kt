package revolhope.splanes.com.presentation.common.component.selector

data class SelectorUiModel(
    val optionRight: String,
    val optionLeft: String,
    val optionSelected: SelectorComponent.Options = SelectorComponent.Options.ANY
)