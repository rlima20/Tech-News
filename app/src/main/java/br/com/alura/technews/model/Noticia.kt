package br.com.alura.technews.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity - representa a tabela que será criada
 * Todos entity precisa definir uma proporty como primarykey.
 * autogenerate - incrementa o valor do id.
 * id = 0 pois o banco de dados deve gerar esse valor para nós. Nesse caso posso definir um valor padrão
 * para o id.
 */

@Entity
data class Noticia(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val titulo: String = "",
    val texto: String = ""
)
