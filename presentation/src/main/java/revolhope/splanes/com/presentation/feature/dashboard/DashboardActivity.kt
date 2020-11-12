package revolhope.splanes.com.presentation.feature.dashboard

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaModel
import revolhope.splanes.com.presentation.R
import revolhope.splanes.com.presentation.common.base.BaseActivity
import revolhope.splanes.com.presentation.common.component.snackbar.SnackBar
import revolhope.splanes.com.presentation.common.component.snackbar.model.SnackBarModel
import revolhope.splanes.com.presentation.common.extensions.observe
import revolhope.splanes.com.presentation.databinding.ActivityDashboardBinding
import revolhope.splanes.com.presentation.feature.dashboard.adapter.EmployeeAdapter
import revolhope.splanes.com.presentation.feature.dashboard.adapter.EmployeeLayoutManager
import revolhope.splanes.com.presentation.feature.dashboard.filter.FilterBottomSheet
import revolhope.splanes.com.presentation.feature.dashboard.filter.FilterModel
import revolhope.splanes.com.presentation.feature.detail.DetailActivity

class DashboardActivity : BaseActivity<ActivityDashboardBinding>() {

    private val viewModel: DashboardViewModel by viewModel()

    override val layoutResource: Int
        get() = R.layout.activity_dashboard

    private var menu: Menu? = null

    companion object {
        private const val STATE_EMPLOYEES = "DashboardActivity.State.Employees"
        private const val STATE_FILTER = "DashboardActivity.State.Filter"

        fun start(baseActivity: BaseActivity<*>) {
            baseActivity.startActivity(
                Intent(
                    baseActivity,
                    DashboardActivity::class.java
                ).apply {
                    putExtras(
                        bundleOf(
                            EXTRA_NAVIGATION_TRANSITION to NavTransition.LATERAL
                        )
                    )
                }
            )
        }
    }

    override fun initViews() {
        super.initViews()
        initAdapter()
    }

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.loaderState) { binding.appLoader.isLoaderVisible = it }
        observe(viewModel.onErrorState) {
            SnackBar.show(
                view = binding.root,
                model = SnackBarModel.Error(getString(R.string.error_default))
            )
            binding.emptyState.isVisible = binding.employeeRecyclerView.adapter?.itemCount == 0
        }
        observe(viewModel.employees) {
            (binding.employeeRecyclerView.adapter as? EmployeeAdapter)?.updateItems(it.toMutableList())
        }
        observe(viewModel.isListFiltered) {
            this.menu?.findItem(R.id.filter)?.icon?.setTintList(ColorStateList.valueOf(
                ContextCompat.getColor(
                    this,
                    if (it) R.color.gray else R.color.white
                )
            ))
        }
        observe(viewModel.emptyState) {
            binding.employeeRecyclerView.isVisible = it.not()
            binding.emptyState.isVisible = it
        }
    }

    override fun loadData() {
        super.loadData()
        viewModel.fetchEmployees()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.employees.value?.let {
            outState.putParcelableArrayList(STATE_EMPLOYEES, ArrayList(it))
        }
        viewModel.getCurrentFilter()?.let {
            outState.putParcelable(STATE_FILTER, it)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.takeIf { it.containsKey(STATE_EMPLOYEES) }?.let {
            it.getParcelableArrayList<OompaLoompaModel>(STATE_EMPLOYEES)?.let { list ->
                (binding.employeeRecyclerView.adapter as? EmployeeAdapter)?.updateItems(list)
            }
        }
        savedInstanceState.takeIf { it.containsKey(STATE_FILTER) }?.let {
            it.getParcelable<FilterModel>(STATE_FILTER)?.let { list ->
                viewModel.filterList(list)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.filter) {
            FilterBottomSheet(
                currentFilter = viewModel.getCurrentFilter(),
                onFilterApplied = ::onFilterApplied
            ).show(supportFragmentManager)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initAdapter() {
        binding.employeeRecyclerView.layoutManager = EmployeeLayoutManager(
            context = this,
            onLastElementVisible = ::loadData
        )
        binding.employeeRecyclerView.adapter = EmployeeAdapter(
            context = this,
            items = mutableListOf(),
            onDetailClick = ::onDetailClick
        )
    }

    private fun onDetailClick(item: OompaLoompaModel) {
        DetailActivity.start(this, item)
    }

    private fun onFilterApplied(filter: FilterModel) {
        viewModel.filterList(filter)
    }
}