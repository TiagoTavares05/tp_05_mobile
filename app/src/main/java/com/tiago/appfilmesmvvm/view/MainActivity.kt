package com.tiago.appfilmesmvvm.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiago.appfilmesmvvm.databinding.ActivityMainBinding
import com.tiago.appfilmesmvvm.viewmodel.CatalogoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CatalogoViewModel by viewModels()
    private lateinit var adapter: CatalogoAdapter
    private var codigoSelecionado: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CatalogoAdapter(emptyList()) { filme ->
            codigoSelecionado = filme.codigo
            binding.inputTitulo.setText(filme.titulo)
            binding.inputAvaliacao.setText(filme.avaliacao.toString())
            binding.inputUrlCapa.setText(filme.urlCapa)
            binding.btnCadastrar.text = "Confirmar Alteração"
        }

        binding.rvCatalogo.layoutManager = LinearLayoutManager(this)
        binding.rvCatalogo.adapter = adapter

        viewModel.listaFilmes.observe(this) { filmes ->
            adapter.definirDados(filmes)
        }

        binding.btnCadastrar.setOnClickListener {
            val titulo = binding.inputTitulo.text.toString().trim()
            val avaliacaoStr = binding.inputAvaliacao.text.toString().trim().replace(",", ".")
            val urlCapa = binding.inputUrlCapa.text.toString().trim()

            if (titulo.isEmpty() || avaliacaoStr.isEmpty()) {
                Toast.makeText(this, "Informe o título e a avaliação", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val avaliacao = avaliacaoStr.toDoubleOrNull()
            if (avaliacao == null) {
                Toast.makeText(this, "Avaliação inválida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val codigoAtual = codigoSelecionado
            if (codigoAtual == null) {
                viewModel.cadastrarFilme(titulo, avaliacao, urlCapa)
            } else {
                viewModel.atualizarFilme(codigoAtual, titulo, avaliacao, urlCapa)
                codigoSelecionado = null
                binding.btnCadastrar.text = "Cadastrar Filme"
            }

            binding.inputTitulo.text.clear()
            binding.inputAvaliacao.text.clear()
            binding.inputUrlCapa.text.clear()
        }
    }
}
