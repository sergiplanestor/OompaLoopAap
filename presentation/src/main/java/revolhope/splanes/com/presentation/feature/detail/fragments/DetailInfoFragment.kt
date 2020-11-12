package revolhope.splanes.com.presentation.feature.detail.fragments

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaDetailsModel
import revolhope.splanes.com.presentation.common.extensions.justify
import revolhope.splanes.com.presentation.databinding.FragmentDetailInfoBinding

class DetailInfoFragment(
    private val model: OompaLoompaDetailsModel
) : Fragment() {

    private lateinit var binding: FragmentDetailInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.physicalCharacteristicsView.bind(model)
        binding.description.text =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Html.fromHtml(model.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(model.description)
            }
        binding.description.justify()
    }
}