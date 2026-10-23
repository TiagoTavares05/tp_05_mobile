package com.tiago.appfilmesmvvm.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tiago.appfilmesmvvm.R
import com.tiago.appfilmesmvvm.databinding.ItemFilmeBinding
import com.tiago.appfilmesmvvm.model.FilmeModel

class CatalogoAdapter(
    private var itens: List<FilmeModel>,
    private val aoClicarAlterar: (FilmeModel) -> Unit
) : RecyclerView.Adapter<CatalogoAdapter.CatalogoViewHolder>() {

    class CatalogoViewHolder(val binding: ItemFilmeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmeBinding.inflate(inflater, parent, false)
        return CatalogoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogoViewHolder, position: Int) {
        val item = itens[position]
        holder.binding.txtTituloItem.text = item.titulo
        holder.binding.txtAvaliacaoItem.text = "Avaliação: ${item.avaliacao} / 10"

        holder.binding.imgCapa.load(item.urlCapa) {
            crossfade(true)
            placeholder(R.mipmap.ic_launcher)
            error(R.mipmap.ic_launcher)
        }

        holder.binding.btnAlterar.setOnClickListener {
            aoClicarAlterar(item)
        }
    }

    override fun getItemCount(): Int = itens.size

    fun definirDados(novosItens: List<FilmeModel>) {
        itens = novosItens
        notifyDataSetChanged()
    }
}
