package revolhope.splanes.com.presentation.feature.detail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaDetailsModel
import revolhope.splanes.com.presentation.common.extensions.justify
import revolhope.splanes.com.presentation.databinding.FragmentDetailSongBinding

class DetailSongFragment(
    private val model: OompaLoompaDetailsModel
) : Fragment() {

    private lateinit var binding: FragmentDetailSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailSongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.song.text = model.favorite.song
        binding.song.justify()
    }
}