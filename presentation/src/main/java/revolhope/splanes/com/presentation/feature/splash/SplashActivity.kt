package revolhope.splanes.com.presentation.feature.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener
import revolhope.splanes.com.presentation.R
import revolhope.splanes.com.presentation.common.base.BaseActivity
import revolhope.splanes.com.presentation.databinding.ActivitySplashBinding
import revolhope.splanes.com.presentation.feature.dashboard.DashboardActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val layoutResource: Int
        get() = R.layout.activity_splash

    companion object {
        const val ALPHA_ANIMATION_DURATION = 500L
        const val SPLASH_DURATION = 1500L
    }

    override fun loadData() {
        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(binding.appTitle, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(binding.lottieView, "alpha", 0f, 1f)
            )
            duration = ALPHA_ANIMATION_DURATION
            interpolator = AccelerateDecelerateInterpolator()
            addListener(
                onEnd = {
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            DashboardActivity.start(baseActivity = this@SplashActivity)
                            finish()
                        },
                        SPLASH_DURATION
                    )
                }
            )
            start()
        }
    }
}