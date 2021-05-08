package br.com.alura.technews.di.modules

import androidx.room.Room
import br.com.alura.technews.database.AppDatabase
import br.com.alura.technews.database.dao.NoticiaDAO
import br.com.alura.technews.repository.NoticiaRepository
import org.koin.dsl.module

/**
 * O que é dependência?
 * Qualquer objeto que uma classe precisa
 */

private const val NOME_BANCO_DE_DADOS = "news.db"

val appModules = module{
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
}