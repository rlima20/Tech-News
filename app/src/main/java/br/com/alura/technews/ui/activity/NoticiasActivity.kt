package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.fragment.ListaNoticiasFragment
import br.com.alura.technews.ui.fragment.VisualizaNoticiaFragment

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

class NoticiasActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)
        title = TITULO_APPBAR

        var transacao = supportFragmentManager.beginTransaction()
        transacao.add(R.id.activity_noticias_container_primario, ListaNoticiasFragment())
        transacao.commit()

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

        if(fragment is VisualizaNoticiaFragment){
            fragment.quandoFinalizaTela = { finish()}
            fragment.quandoSelecionaMenuEdicao = { noticiaSelecionada ->  abreFormularioEdicao(noticiaSelecionada)}
        }
    }

    private fun abreFormularioModoCriacao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        startActivity(intent)
    }

    private fun abreVisualizadorNoticia(noticia: Noticia) {
        criaFragmentProgramaticamente(noticia)
    }

    private fun criaFragmentProgramaticamente(noticia: Noticia) {
        val transacao = supportFragmentManager.beginTransaction()
        val fragment = VisualizaNoticiaFragment()
        val dados = Bundle()
        dados.putLong(NOTICIA_ID_CHAVE, noticia.id)
        fragment.arguments = dados
        transacao.replace(R.id.activity_noticias_container_secundario, fragment)
        transacao.commit()
    }

    private fun abreFormularioEdicao(noticia: Noticia) {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticia.id)
        startActivity(intent)
    }
}
