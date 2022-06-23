package org.d3if2123.kalkulatorkalori.ui.hitungKalori.About

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import org.d3if2123.kalkulatorkalori.R
import org.d3if2123.kalkulatorkalori.databinding.FragmentAboutBinding
import org.d3if2123.kalkulatorkalori.databinding.FragmentHistoryBinding
import org.d3if2123.kalkulatorkalori.db.KaloriDb
import org.d3if2123.kalkulatorkalori.model.Tentang
import org.d3if2123.kalkulatorkalori.network.ApiStatus
import org.d3if2123.kalkulatorkalori.network.KaloriApi
import org.d3if2123.kalkulatorkalori.ui.hitungKalori.History.HistoryViewModel
import org.d3if2123.kalkulatorkalori.ui.hitungKalori.History.HistoryViewModelFactory

class AboutFragment: Fragment() {
    private val viewModel: AboutViewModel by lazy {
        val factory = AboutViewModelFactory(requireActivity().application)
        ViewModelProvider(this, factory)[AboutViewModel::class.java]
    }

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner){
            getTentang(it)
        }
        viewModel.getStatus().observe(viewLifecycleOwner){
            getStatus(it)
        }
        Glide.with(binding.imageAPI)
            .load(KaloriApi.getLogoUrl())
            .error(R.drawable.ic_baseline_broken_image_24)
            .into(binding.imageAPI)
    }

    private fun getStatus(it: ApiStatus?) {
        when (it) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS -> {
            binding.progressBar.visibility = View.GONE
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }

    private fun getTentang(it: Tentang?) {
        if (it != null){
            binding.aboutAplikasi.text = it.about_aplikasi
        }
    }


}