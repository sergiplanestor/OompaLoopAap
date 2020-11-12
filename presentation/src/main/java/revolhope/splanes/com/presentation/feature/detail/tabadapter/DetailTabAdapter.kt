package revolhope.splanes.com.presentation.feature.detail.tabadapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaDetailsModel
import revolhope.splanes.com.presentation.R
import revolhope.splanes.com.presentation.feature.detail.fragments.DetailQuotaFragment
import revolhope.splanes.com.presentation.feature.detail.fragments.DetailInfoFragment
import revolhope.splanes.com.presentation.feature.detail.fragments.DetailSongFragment

class DetailTabAdapter(
    private val context: Context,
    private val model: OompaLoompaDetailsModel,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private companion object {
        const val TAB_COUNT = 3
    }

    override fun getCount(): Int = TAB_COUNT

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                DetailInfoFragment(model)
            }
            1 -> {
                DetailQuotaFragment(model)
            }
            else /* 2 */ -> {
                DetailSongFragment(model)
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position) {
            0 -> context.getString(R.string.tab_info)
            1 -> context.getString(R.string.tab_quota)
            else /* 2 */ -> context.getString(R.string.tab_song)
        }
    }
}