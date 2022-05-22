package org.d3if2123.kalkulatorkalori

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import org.d3if2123.kalkulatorkalori.databinding.ActivityMainBinding
import org.d3if2123.kalkulatorkalori.model.HasilKalori
import org.d3if2123.kalkulatorkalori.model.KategoriKalori

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener { (hitungBMR()) }
        binding.resetButton.setOnClickListener { (resetBMR()) }
        viewModel.getHasilKalori().observe(this, { showResult(it) })
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
    }

    private fun hitungBMR() {
        val berat = binding.beratBadanInp.text.toString().toFloat()
        if (TextUtils.isEmpty(berat.toString())) {
            Toast.makeText(this, R.string.berat_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tinggi = binding.tinggiBadanInp.text.toString().toFloat()
        if (TextUtils.isEmpty(tinggi.toString())) {
            Toast.makeText(this, R.string.tinggi_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val usia = binding.usiaInp.text.toString().toFloat()
        if (TextUtils.isEmpty(usia.toString())) {
            Toast.makeText(this, R.string.usia_invalid, Toast.LENGTH_LONG).show()
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