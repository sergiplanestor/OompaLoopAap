package revolhope.splanes.com.presentation.feature.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.os.bundleOf
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaDetailsModel
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaModel
import revolhope.splanes.com.presentation.R
import revolhope.splanes.com.presentation.common.base.BaseActivity
import revolhope.splanes.com.presentation.common.component.snackbar.SnackBar
import revolhope.splanes.com.presentation.common.component.snackbar.model.SnackBarModel
import revolhope.splanes.com.presentation.common.extensions.loadCircularUrl
import revolhope.splanes.com.presentation.common.extensions.observe
import revolhope.splanes.com.presentation.databinding.ActivityDetailsBinding
import revolhope.splanes.com.presentation.feature.detail.tabadapter.DetailTabAdapter

class DetailActivity : BaseActivity<ActivityDetailsBinding>() {

    private val viewModel: DetailViewModel by viewModel()

    override val layoutResource: Int
        get() = R.layout.activity_details


    companion object {

        private const val STATE_DETAILS = "detailactivity.state.details"
        private const val EXTRA_MODEL = "detailactivity.extra.model"

        fun start(baseActivity: BaseActivity<*>, model: OompaLoompaModel) {
            baseActivity.startActivity(
                Intent(baseActivity, DetailActivity::class.java).apply {
                    putExtras(
                        bundleOf(
                            EXTRA_NAVIGATION_TRANSITION to NavTransition.MODAL,
                            EXTRA_MODEL to model
                        )
                    )
                }
            )
        }
    }

    override fun loadData() {
        super.loadData()
        viewModel.fetchDetails(getModel())
    }

    /**
     * Open method where initialize the activity view.
     */
    override fun initViews() {
        super.initViews()
        binding.tabLayout.setupWithViewPager(binding.pager)
        getModel()?.let {
            setupActionBar(it)
            binding.image.loadCircularUrl(it.image)
            initCardView(it)
        }
    }

    /**
     * Open method where initialize observing reactive data.
     */
    override fun initObservers() {
        super.initObservers()
        observe(viewModel.loaderState) { binding.appLoader.isLoaderVisible = it }
        observe(viewModel.onErrorState) {
            SnackBar.show(
                view = binding.root,
                model = SnackBarModel.Error(getString(R.string.error_default))
            )
        }
        observe(viewModel.details) {
            binding.pager.adapter = DetailTabAdapter(
                context = this,
                model = it,
                fm = supportFragmentManager
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.details.value?.let {
            outState.putParcelable(STATE_DETAILS, it)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.takeIf { it.containsKey(STATE_DETAILS) }?.let {
            it.getParcelable<OompaLoompaDetailsModel>(STATE_DETAILS)?.let { model ->
                binding.pager.adapter = DetailTabAdapter(
                    context = this,
                    model = model,
                    fm = supportFragmentManager
                )
            }
        }
    }

    private fun setupActionBar(model: OompaLoompaModel) {
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_back)
            setDisplayHomeAsUpEnabled(true)
            title = String.format("%s, %s", model.lastName, model.firstName)
        }
    }

    private fun initCardView(model: OompaLoompaModel) {
        binding.name.text = String.format("%s, %s", model.lastName, model.firstName)
        binding.email.text = model.email
        binding.profession.text = model.profession
    }

    private fun getModel(): OompaLoompaModel? =
        intent.extras?.takeIf { it.containsKey(EXTRA_MODEL) }?.getParcelable(EXTRA_MODEL)
}