package revolhope.splanes.com.presentation.common.component.snackbar

import android.annotation.SuppressLint
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.BaseTransientBottomBar
import revolhope.splanes.com.presentation.common.component.snackbar.model.SnackBarModel
import revolhope.splanes.com.presentation.common.component.snackbar.view.SnackBarView
import revolhope.splanes.com.presentation.common.component.snackbar.view.SnackBarViewError
import revolhope.splanes.com.presentation.common.extensions.dp
import revolhope.splanes.com.presentation.common.extensions.findSuitableParent

class SnackBar(
    parent: ViewGroup,
    content: SnackBarView<*>
) : BaseTransientBottomBar<SnackBar>(parent, content, content) {

    init {
        getView().apply {
            setBackgroundColor(context.getColor(android.R.color.transparent))
            setPadding(0, 0, 0, PADDING_BOTTOM_DP.dp)
        }
    }

    companion object {

        private const val PADDING_BOTTOM_DP = 20
        private const val DURATION = 3000

        @SuppressLint("WrongConstant")
        fun show(view: View?, model: SnackBarModel) {

            val viewGroup = view.findSuitableParent() ?: return

            val content: SnackBarView<*> = when (model) {
                is SnackBarModel.Success -> {
                    // Should be implemented to give successfully feedback to user on others features.
                    // leaved here to illustrate it :)
                    SnackBarViewError(viewGroup.context)
                }
                is SnackBarModel.Error -> {
                    SnackBarViewError(viewGroup.context).apply { bind(model) }
                }
            }

            SnackBar(
                viewGroup,
                content
            ).apply {
                duration = DURATION
                content.setOnClickListener {
                    model.onClick?.invoke()
                    dismiss()
                }
            }.show()
            TransitionManager.beginDelayedTransition(viewGroup)
        }
    }
}