package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.alura.technews.R
import br.com.alura.technews.database.AppDatabase
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.ui.activity.extensions.mostraErro
import br.com.alura.technews.ui.viewmodel.VisualizaNoticiaViewModel
import br.com.alura.technews.ui.viewmodel.factory.VisualizaNoticiaViewModelFactory
import kotlinx.android.synthetic.main.activity_visualiza_noticia.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val NOTICIA_NAO_ENCONTRADA = "Notícia não encontrada"
private const val TITULO_APPBAR = "Notícia"
private const val MENSAGEM_FALHA_REMOCAO = "Não foi possível remover notícia"

class VisualizaNoticiaActivity : AppCompatActivity() {

    /**
     * Aqui vou fazer a injeção de dependência através da property val viewModel que é do tipo
     * VisualizaNoticiaViewModel. Faço a injeção através do by viewModel. Como essa injeção precisa de um arumento
     * eu preciso abrir as chaves e dentro das chaves colocar o parametersOf e o argumento dentro.
     *
     * Com essa abordagem eu também não preciso mais usar a injeção de dependência do repository pois internamente
     * o Injetor do VisualizaViewModel já sabe como fazer isso.
     * private val repository: NoticiaRepository by inject()
     */

    private val noticiaId: Long by lazy {
        intent.getLongExtra(NOTICIA_ID_CHAVE, 0)
    }

    private val viewModel:VisualizaNoticiaViewModel by viewModel(){
        parametersOf(noticiaId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualiza_noticia)
        title = TITULO_APPBAR
        verificaIdDaNoticia()
        buscaNoticiaSelecionada()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.visualiza_noticia_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.visualiza_noticia_menu_edita -> abreFormularioEdicao()
            R.id.visualiza_noticia_menu_remove -> remove()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun buscaNoticiaSelecionada() {
        viewModel.noticiaEncontrada.observe(this, Observer { noticiaEncontrada ->
            noticiaEncontrada?.let {
                preencheCampos(it)
            }
        })
    }

    private fun verificaIdDaNoticia() {
        if (noticiaId == 0L) {
            mostraErro(NOTICIA_NAO_ENCONTRADA)
            finish()
        }
    }

    private fun preencheCampos(noticia: Noticia) {
        activity_visualiza_noticia_titulo.text = noticia.titulo
        activity_visualiza_noticia_texto.text = noticia.texto
    }

    private fun remove() {
        viewModel.remove().observe(this, Observer {
            if (it.erro == null) {
                finish()
            } else {
                mostraErro(MENSAGEM_FALHA_REMOCAO)
            }
        })
    }

    private fun abreFormularioEdicao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticiaId)
        startActivity(intent)
    }

}
