package org.d3if2123.kalkulatorkalori

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import org.d3if2123.kalkulatorkalori.databinding.ActivityMainBinding
import org.d3if2123.kalkulatorkalori.model.HasilKalori
import org.d3if2123.kalkulatorkalori.model.KategoriKalori

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener { (hitungBMR()) }
        binding.resetButton.setOnClickListener { (resetBMR()) }
    }

    private fun resetBMR() {
        binding.beratBadanInp.text?.clear()
        binding.tinggiBadanInp.text?.clear()
        binding.usiaInp.text?.clear()
        binding.radioGroup.clearCheck()
        binding.bmrTextView.visibility = GONE
        binding.kategori.visibility = GONE
    }

    private fun hitungBMR(berat: Float, tinggi: Float, usia: Float, isMale: Boolean): HasilKalori {
        val bmr = if (isMale) {
            66 + (13.7 * berat) + (5 * tinggi) - (6.8 * usia)
        } else {
            655 + (9.6 * berat) + (1.8 * tinggi) - (4.7 * usia)
        }
        val kategori = getKategori(bmr, isMale)
        return HasilKalori(bmr, kategori)
    }

    private fun showResult(result: HasilKalori) {
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
        val result = hitungBMR(
            berat,
            tinggi,
            usia,
            selectedId == R.id.priaRadioButton
        )
        showResult(result)
    }

    private fun getKategori(kalori: Double, isMale: Boolean): KategoriKalori {
        val kategori = if (isMale) {
            when {
                kalori < 2500 -> KategoriKalori.RENDAH
                else -> KategoriKalori.TINGGI
            }
        } else {
            when {
                kalori < 2000 -> KategoriKalori.RENDAH
                else -> KategoriKalori.TINGGI
            }
        }
        return kategori
    }

    private fun getKategoriLabel(kategori: KategoriKalori): String {
        val stringRes = when (kategori) {
            KategoriKalori.RENDAH -> R.string.rendah
            KategoriKalori.TINGGI -> R.string.tinggi
        }
        return getString(stringRes)
    }
}

