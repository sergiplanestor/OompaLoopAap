package revolhope.splanes.com.presentation.common.extensions

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.text.Layout
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import revolhope.splanes.com.presentation.R
import revolhope.splanes.com.domain.feature.employees.model.Gender

inline val Context.screenWidth: Int
    get() = Point().also {
        (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getSize(
            it
        )
    }.x

inline val Int.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
    ).toInt()

inline fun getValueAnimator(
    forward: Boolean = true,
    duration: Long,
    interpolator: TimeInterpolator,
    crossinline updateListener: (progress: Float) -> Unit
): ValueAnimator {
    val a =
        if (forward) ValueAnimator.ofFloat(0f, 1f)
        else ValueAnimator.ofFloat(1f, 0f)
    a.addUpdateListener { updateListener(it.animatedValue as Float) }
    a.duration = duration
    a.interpolator = interpolator
    return a
}

fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        when (view) {
            is CoordinatorLayout -> return view
            is FrameLayout -> {
                if (view.id == android.R.id.content) return view else fallback = view
            }
        }

        view = if (view?.parent is View) view.parent as View else null

    } while (view != null)

    return fallback
}

fun blendColors(color1: Int, color2: Int, ratio: Float): Int {
    val inverseRatio = 1f - ratio

    val a = (Color.alpha(color1) * inverseRatio) + (Color.alpha(color2) * ratio)
    val r = (Color.red(color1) * inverseRatio) + (Color.red(color2) * ratio)
    val g = (Color.green(color1) * inverseRatio) + (Color.green(color2) * ratio)
    val b = (Color.blue(color1) * inverseRatio) + (Color.blue(color2) * ratio)
    return Color.argb(a.toInt(), r.toInt(), g.toInt(), b.toInt())
}

fun ImageView.loadCircularUrl(url: String) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .circleCrop()
        .into(this)
}

fun Gender.toString(context: Context) =
    context.resources.getString(
        when (this) {
            Gender.MALE -> R.string.gender_male
            Gender.FEMALE -> R.string.gender_female
            Gender.UNKNOWN -> R.string.gender_unknown
        }
    )

fun TextView.justify(isJustified: Boolean = true) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        justificationMode = if (isJustified) {
            Layout.JUSTIFICATION_MODE_INTER_WORD
        } else {
            Layout.JUSTIFICATION_MODE_NONE
        }
    }
}