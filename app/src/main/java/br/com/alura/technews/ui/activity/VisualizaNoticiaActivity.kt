package br.com.alura.technews.ui.activity

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
