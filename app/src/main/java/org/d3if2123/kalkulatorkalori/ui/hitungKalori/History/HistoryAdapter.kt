package org.d3if2123.kalkulatorkalori.ui.hitungKalori.History

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if2123.kalkulatorkalori.R
import org.d3if2123.kalkulatorkalori.databinding.ItemHistoryBinding
import org.d3if2123.kalkulatorkalori.db.KaloriEntity
import org.d3if2123.kalkulatorkalori.model.KategoriKalori
import org.d3if2123.kalkulatorkalori.model.hitungKalori
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter: ListAdapter<KaloriEntity, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<KaloriEntity>() {
            override fun areItemsTheSame(oldItem: KaloriEntity, newItem: KaloriEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: KaloriEntity, newItem: KaloriEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root) {

        private val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        fun bind(item: KaloriEntity) = with(binding) {
            val hasilKalori = item.hitungKalori()
            kategoriTextView.text = hasilKalori.kategori.toString().substring(0,1)
            val colorRes = when(hasilKalori.kategori) {
                KategoriKalori.RENDAH -> R.color.rendah
                else -> R.color.tinggi
            }
            val circleBg = kategoriTextView.background as GradientDrawable
            circleBg.setColor(ContextCompat.getColor(root.context, colorRes))

            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
            kaloriTextView.text = root.context.getString(R.string.hasil_x, hasilKalori.bmr, hasilKalori.kategori.toString())

            val gender = root.context.getString(
                if (item.isMale) R.string.pria else R.string.wanita)
            dataTextView.text = root.context.getString(R.string.data_x, item.berat, item.tinggi, item.usia, gender)
        }
    }
}