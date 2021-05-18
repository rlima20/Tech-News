package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.activity.extensions.transacaoFragment
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

class NoticiasActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)

        if(savedInstanceState == null){
            abreListaNoticias()
        }
    }

    private fun abreListaNoticias() {
        transacaoFragment {
            add(R.id.activity_noticias_container, ListaNoticiasFragment())
        }
    }

    /**
     * Recurso when expression - É como se fosse um switch case
     * Coloca-se um membro e dentro do seu escopo temos a capacidade de verificar possibilidades que
     * esse fragment pode atender.
     * Quando o fragment é uma lista de notícias, fazemos algo (tomamos uma ação)
     */
    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        when(fragment){
            is ListaNoticiasFragment -> {
                configuraListaNoticias(fragment)
            }
            is VisualizaNoticiaFragment -> {
                configuraVisualizaNoticia(fragment)
            }
        }
    }

    private fun configuraVisualizaNoticia(fragment: VisualizaNoticiaFragment) {
        fragment.quandoFinalizaTela = { finish() }
        fragment.quandoSelecionaMenuEdicao =
            { noticiaSelecionada -> abreFormularioEdicao(noticiaSelecionada) }
    }

    private fun configuraListaNoticias(fragment: ListaNoticiasFragment) {
        fragment.quandoNoticiaSelecionada = {
            abreVisualizadorNoticia(it)
        }
        fragment.quandoFabNoticiaSalvaNoticiaClicado = {
            abreFormularioModoCriacao()
        }
    }

    private fun abreFormularioModoCriacao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        startActivity(intent)
    }

    /**
     * Reutilizando a função transacaoFragment
     */
    private fun abreVisualizadorNoticia(noticia: Noticia) {

        val fragment = VisualizaNoticiaFragment()
        val dados = Bundle()
        dados.putLong(NOTICIA_ID_CHAVE, noticia.id)
        fragment.arguments = dados

        transacaoFragment {
            addToBackStack(null)
            replace(R.id.activity_noticias_container, fragment)
        }
    }

    private fun abreFormularioEdicao(noticia: Noticia) {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticia.id)
        startActivity(intent)
    }
}

/**
 * package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.alura.technews.R
import br.com.alura.technews.ui.fragment.VisualizaNoticiaFragment

private const val TITULO_APPBAR = "Notícia"

class VisualizaNoticiaActivity : AppCompatActivity() {

private val noticiaId: Long by lazy {
intent.getLongExtra(NOTICIA_ID_CHAVE, 0)
}

override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_visualiza_noticia)
title = TITULO_APPBAR
criaFragmentProgramaticamente()
}

private fun criaFragmentProgramaticamente() {
/**
 * Para mandar algum tipo de argumento, eu vou precisar criar um fragment programáticamente. Com iso
 * não vou utuilizar a tag<fragment>.
 *
*/
val transacao = supportFragmentManager.beginTransaction()
val fragment = VisualizaNoticiaFragment()

/**
 * Criada a referência de Budle para inicializar o fragment.arguments.
 * Essa é uma instância de um Bundle.
 * Vamos computar os dados da mesma maneira que fazemos com Intents (putLong).
 * Essa função recebe uma key e o valor que eu quero mandar.
*/
val dados = Bundle()
dados.putLong(NOTICIA_ID_CHAVE, noticiaId)

/**
 * É a partir do acesso ao fragment que eu vou conseguir mandar argumentos.
 * Aqui dentro do fragment eu tenho acesso ao arguments que é uma property e eu posso inicializalo.
 * Para fazer isso eu preciso criar uma referência de Bundle.
*/
fragment.arguments = dados

/**
 * Com a função add eu tenho acesso a uma transação. Vou utilizar a sobrecarga de
 * um containerview como argumento.
 * Nos argumentos eu mando id do container, no caso é o meu Constraint layout.
 * Como segundo argumento eu preciso mandar um fragment (VisualizaNoticiaFragment())
*/
transacao.add(R.id.activity_visualiza_noticia_container, fragment)

/**
 * O comando para salvar essa transação é o commit.
 * Todas as vezes que eu fizer um commit ele vai pegar essa transação que foi criada através
 * do beginTransaction, vai pegar todas as ações que foram realizadas durante a criação e vai executar
 * a partir do momento que eu fizer o commit. É muito similar ao Builder.
*/
transacao.commit()
}

override fun onAttachFragment(fragment: Fragment?) {
super.onAttachFragment(fragment)
if(fragment is VisualizaNoticiaFragment){
}
}
}

 */

/**
 * Uma técnica bacana para evitar essa verbosidade:
 * DSL - Domain Specific Language. Vamos criar uma função que vai ter a responsabilidade
 * de criar uma linguagem que cria transação e no final das contas ela faz o commit.

private fun criaFragmentProgramaticamente(noticia: Noticia) {
val transacao = supportFragmentManager.beginTransaction()
val fragment = VisualizaNoticiaFragment()
val dados = Bundle()
dados.putLong(NOTICIA_ID_CHAVE, noticia.id)
fragment.arguments = dados
transacao.replace(R.id.activity_noticias_container, fragment)
transacao.commit()
}*/
