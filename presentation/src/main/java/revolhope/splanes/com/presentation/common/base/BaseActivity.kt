package revolhope.splanes.com.presentation.common.base

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import revolhope.splanes.com.presentation.R

abstract class BaseActivity<T : ViewDataBinding>  : AppCompatActivity() {

    /**
     * Enum class to hold transitions between activities.
     */
    protected enum class NavTransition {
        LATERAL,
        MODAL
    }

    /**
     * Extra which indicates which kind of transition should be performed when changing between activities
     */
    companion object {
        const val EXTRA_NAVIGATION_TRANSITION = "nav.transition"
    }

    abstract val layoutResource: Int
    lateinit var binding: T
    //private var appLoader: AppLoader? = null
    private var isFirstOnResume = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResource)
        //appLoader = findViewById(R.id.app_loader)
        initViews()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstOnResume) {
            loadData()
            isFirstOnResume = false
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overrideTransition()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return item?.let { super.onOptionsItemSelected(item) } ?: false
    }

    override fun startActivity(intent: Intent?) {
        val anim = getNavAnimations(intent)
        super.startActivity(intent)
        overridePendingTransition(anim.first, anim.second)
    }

    /**
     * Open method where place data loadings.
     * This method is called on first call of [AppCompatActivity.onResume].
     */
    open fun loadData() {
        /* Nothing to do here */
    }

    /**
     * Open method where initialize the activity view.
     */
    open fun initViews() {
        /* Nothing to do here */
    }

    /**
     * Open method where initialize observing reactive data.
     */
    open fun initObservers() {
        /* Nothing to do here */
    }

    /**
     * Private util method to obtain animation to perform when changing between activities.
     * @param intent [Intent] object to obtain extra param and know which [NavTransition] should be executed.
     * @param isStart [Boolean] indicates if this animation is from starting new activity or if it is from
     * going back from.
     *
     * @return [Pair] object containing animations to perform. [Pair.first] animation resource is for current activity,
     * [Pair.second] animation resource is for incoming activity.
     */
    private fun getNavAnimations(intent: Intent?, isStart: Boolean = true): Pair<Int, Int> {
        val bundle = intent?.extras
        return when (bundle?.getSerializable(EXTRA_NAVIGATION_TRANSITION) as NavTransition?) {
            NavTransition.LATERAL ->
                if (isStart) {
                    bundle?.putSerializable(EXTRA_NAVIGATION_TRANSITION, NavTransition.LATERAL)
                    Pair(R.anim.slide_in_right, R.anim.slide_out_left)
                } else {
                    Pair(R.anim.slide_in_left, R.anim.slide_out_right)
                }
            NavTransition.MODAL ->
                if (isStart) {
                    bundle?.putSerializable(EXTRA_NAVIGATION_TRANSITION, NavTransition.MODAL)
                    Pair(R.anim.slide_down, R.anim.hold)
                } else {
                    Pair(R.anim.hold, R.anim.slide_up)
                }
            else -> Pair(0, 0)
        }
    }

    /**
     * Private util method to override transitions between activities.
     */
    private fun overrideTransition() {
        val anim = getNavAnimations(intent, isStart = false)
        overridePendingTransition(anim.first, anim.second)
    }

}