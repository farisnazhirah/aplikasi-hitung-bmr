package org.d3if2123.kalkulatorkalori.ui.hitungKalori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.d3if2123.kalkulatorkalori.R
import org.d3if2123.kalkulatorkalori.databinding.FragmentSaranBinding
import org.d3if2123.kalkulatorkalori.model.KategoriKalori

class SaranFragment: Fragment() {

    private lateinit var binding: FragmentSaranBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSaranBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI(KategoriKalori.RENDAH)
    }

    private fun updateUI(kategori: KategoriKalori) {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        when (kategori) {
            KategoriKalori.RENDAH -> {
                actionBar?.title = getString(R.string.judul_rendah)
                binding.imageView.setImageResource(R.drawable.rendah)
                binding.textView.text = getString(R.string.saran_rendah)
            }
            KategoriKalori.TINGGI -> {
                actionBar?.title = getString(R.string.judul_tinggi)
                binding.imageView.setImageResource(R.drawable.tinggi)
                binding.textView.text = getString(R.string.saran_tinggi)
            }
        }
    }
}