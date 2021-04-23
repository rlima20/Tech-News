package br.com.alura.technews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.alura.technews.database.dao.NoticiaDAO
import br.com.alura.technews.model.Noticia

/**
 * A configuração do Database é feita a partir de uma classe abstrata que
 * herda da classe RoomDatabase, portanto, vamos criar a AppDatabase que vai representar
 * o baco de dados do nosso projeto.
 *
 * Toda classe que herda diretamente do RoomDatabase precisa ser anotada com @Database
 * entities: Array de entidades que serão incluídas pelo Room a partir do Database.
 *
 * version: versão do banco de dados para permitir o uso de migration
 *
 * Por fim precisamos apenas adicionar uma função abstrata que devolve o DAO
 *
 *
 */

private const val NOME_BANCO_DE_DADOS = "news.db"

@Database(entities = [Noticia::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val noticiaDAO: NoticiaDAO

    companion object {

        private lateinit var db: AppDatabase

        fun getInstance(context: Context): AppDatabase {

            if(::db.isInitialized) return db

            db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                NOME_BANCO_DE_DADOS
            ).build()

            return db
        }

    }

}