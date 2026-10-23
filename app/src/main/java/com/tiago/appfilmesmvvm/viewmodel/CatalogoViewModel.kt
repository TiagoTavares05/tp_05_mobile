package com.tiago.appfilmesmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tiago.appfilmesmvvm.model.FilmeModel

class CatalogoViewModel : ViewModel() {

    private val dadosFilmes = mutableListOf(
        FilmeModel(
            1,
            "Batman: O Cavaleiro das Trevas",
            9.6,
            "https://upload.wikimedia.org/wikipedia/pt/b/b7/The_Dark_Knight.jpg"
        ),
        FilmeModel(
            2,
            "A Origem",
            9.3,
            "https://upload.wikimedia.org/wikipedia/pt/8/84/A_Origem_poster.jpg"
        ),
        FilmeModel(
            3,
            "Clube da Luta",
            9.1,
            "https://upload.wikimedia.org/wikipedia/pt/2/2b/Fight_Club_P%C3%B4ster.jpg"
        )
    )

    private val _listaFilmes = MutableLiveData<List<FilmeModel>>(dadosFilmes.toList())
    val listaFilmes: LiveData<List<FilmeModel>> = _listaFilmes

    private var geradorCodigo = 4

    fun cadastrarFilme(titulo: String, avaliacao: Double, urlCapa: String) {
        val item = FilmeModel(geradorCodigo++, titulo, avaliacao, urlCapa)
        dadosFilmes.add(item)
        _listaFilmes.value = dadosFilmes.toList()
    }

    fun atualizarFilme(codigo: Int, novoTitulo: String, novaAvaliacao: Double, novaUrl: String) {
        val posicao = dadosFilmes.indexOfFirst { it.codigo == codigo }
        if (posicao != -1) {
            dadosFilmes[posicao] = FilmeModel(codigo, novoTitulo, novaAvaliacao, novaUrl)
            _listaFilmes.value = dadosFilmes.toList()
        }
    }
}
