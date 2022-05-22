package org.d3if2123.kalkulatorkalori.ui.hitungKalori

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if2123.kalkulatorkalori.R
import org.d3if2123.kalkulatorkalori.databinding.FragmentHitungBinding
import org.d3if2123.kalkulatorkalori.model.HasilKalori
import org.d3if2123.kalkulatorkalori.model.KategoriKalori

class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener { (hitungBMR()) }
        binding.resetButton.setOnClickListener { (resetBMR()) }
        binding.saranButton.setOnClickListener { viewModel.mulaiNavigasi() }
        viewModel.getHasilKalori().observe(requireActivity(), { showResult(it) })
        viewModel.getNavigasi().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            findNavController().navigate(HitungFragmentDirections.actionHitungFragmentToSaranFragment(it))
            viewModel.selesaiNavigasi()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            findNavController().navigate(
                R.id.action_hitungFragment_to_aboutFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun resetBMR() {
        binding.beratBadanInp.text?.clear()
        binding.tinggiBadanInp.text?.clear()
        binding.usiaInp.text?.clear()
        binding.radioGroup.clearCheck()
        binding.bmrTextView.visibility = GONE
        binding.kategori.visibility = GONE
    }

    private fun showResult(result: HasilKalori?) {
        if (result == null) return
        binding.bmrTextView.text = getString(R.string.bmr_x, result.bmr)
        binding.kategori.text = getString(R.string.kategori_kalori, getKategoriLabel(result.kategori))
        binding.bmrTextView.visibility = VISIBLE
        binding.kategori.visibility = VISIBLE
        binding.saranButton.visibility = VISIBLE
    }

    private fun hitungBMR() {
        val berat = binding.beratBadanInp.text.toString().toFloat()
        if (TextUtils.isEmpty(berat.toString())) {
            Toast.makeText(context, R.string.berat_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tinggi = binding.tinggiBadanInp.text.toString().toFloat()
        if (TextUtils.isEmpty(tinggi.toString())) {
            Toast.makeText(context, R.string.tinggi_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val usia = binding.usiaInp.text.toString().toFloat()
        if (TextUtils.isEmpty(usia.toString())) {
            Toast.makeText(context, R.string.usia_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val selectedId = binding.radioGroup.checkedRadioButtonId
        viewModel.hitungBMR(
            berat,
            tinggi,
            usia,
            selectedId == R.id.priaRadioButton
        )
    }

    private fun getKategoriLabel(kategori: KategoriKalori): String {
        val stringRes = when (kategori) {
            KategoriKalori.RENDAH -> R.string.rendah
            KategoriKalori.TINGGI -> R.string.tinggi
        }
        return getString(stringRes)
    }
}