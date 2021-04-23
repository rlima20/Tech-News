package br.com.alura.technews.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.alura.technews.model.Noticia

/**
 * A implementação do DAO é feita a partir de uma inteface que mantém as assinaturas
 * com as ações que serão realizadas com o banco de dados.
 * Mantém todas as funções que interagem com o banco de dados.
 */

@Dao
interface NoticiaDAO {

    @Query("SELECT * FROM Noticia ORDER BY id DESC")
    fun buscaTodos(): LiveData<List<Noticia>>

    @Insert(onConflict = REPLACE)
    fun salva(noticia: Noticia)

    @Delete
    fun remove(noticia: Noticia)

    @Query("SELECT * FROM Noticia WHERE id = :id")
    fun buscaPorId(id: Long): LiveData<Noticia?>

    @Insert(onConflict = REPLACE)
    fun salva(noticias: List<Noticia>)

}
