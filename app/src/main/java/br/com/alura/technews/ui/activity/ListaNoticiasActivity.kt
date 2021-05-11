package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.fragment.ListaNoticiasFragment

/**
 * Identifica se a activity tem algum fragment que foi anexado.
 * Dessa maneira teremos acesso a uma referencia de fragment via parâmetro e nela vamos ter que
 * verificar qual é o seu tipo a partir de um cast.
 * Se esse fragment for um ListaNoticiasFragment, fazemos a implementação dos seus comportamentos
 * esperados. Com isso conseguimos fazer uso de funções diretamente sem ter que usar uma interface por causa
 * do cast que fazíamos da activity.
 *
 * Dessa maneira evitamos a abordagem de ter que ter a responsabilidade de quando desatachar o fragment da acitivity.
 * Também não temos que estar lidando com interfaces.
 *
 */

private const val TITULO_APPBAR = "Notícias"

class ListaNoticiasActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_noticias)
        title = TITULO_APPBAR
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        if(fragment is ListaNoticiasFragment){
            fragment.quandoNoticiaSelecionada = {
                abreVisualizadorNoticia(it)
            }
            fragment.quandoFabNoticiaSalvaNoticiaClicado = {
                abreFormularioModoCriacao()
            }
        }
    }

    private fun abreFormularioModoCriacao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        startActivity(intent)
    }

    private fun abreVisualizadorNoticia(it: Noticia) {
        val intent = Intent(this, VisualizaNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, it.id)
        startActivity(intent)
    }
}
