package br.com.alura.technews.di.modules

import androidx.room.Room
import br.com.alura.technews.database.AppDatabase
import br.com.alura.technews.database.dao.NoticiaDAO
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.ui.viewmodel.FormularioNoticiaViewModel
import br.com.alura.technews.ui.viewmodel.ListaNoticiasViewModel
import br.com.alura.technews.ui.viewmodel.VisualizaNoticiaViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * O que é dependência?
 * Qualquer objeto que uma classe precisa
 *
 * A partir da função module eu consigo indicar todas as dependências que eu quero que o koin
 * saiba criar e consiga injetar.
 */

private const val NOME_BANCO_DE_DADOS = "news.db"

val appModules = module{

    /**
     * Essa é a injeção de dependência de uma instância do AppDatabase.
     */
    single<AppDatabase>{
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            NOME_BANCO_DE_DADOS
        ).build()
    }

    single<NoticiaDAO>{
        get<AppDatabase>().noticiaDAO
    }

    single<NoticiaRepository>{
        NoticiaRepository(get())
    }

    viewModel<ListaNoticiasViewModel> {
        ListaNoticiasViewModel(get())
    }

    viewModel<VisualizaNoticiaViewModel> { (id: Long) ->
        VisualizaNoticiaViewModel(id, get())
    }

    viewModel<FormularioNoticiaViewModel> {
        FormularioNoticiaViewModel(get())
    }
}