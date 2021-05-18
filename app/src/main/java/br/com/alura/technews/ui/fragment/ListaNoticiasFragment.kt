package br.com.alura.technews.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.fragment.extensions.mostraErro
import br.com.alura.technews.ui.recyclerview.adapter.ListaNoticiasAdapter
import br.com.alura.technews.ui.viewmodel.ListaNoticiasViewModel
import kotlinx.android.synthetic.main.lista_noticia.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val MENSAGEM_FALHA_CARREGAR_NOTICIAS = "Não foi possível carregar as novas notícias"
private const val TITULO_APPBAR = "Notícias"


class ListaNoticiasFragment : Fragment() {
    /**
     * Com essa abordagem de injetar o repository eu não preciso mais fazer a instância do
     * banco de dados, que já está sendo injetada dentro do NoticiaRepository que é injetado pelo koin.
     *
     * Ele vai injetar o NoticiaRepository e dentro dele, ele já sabe que precisa do dao. E internamente
     * o koin já sabe como criar o dao.
     *
     * O mesmo acontece com o viewmodel. O koin já sabe como fazer a injeção de dependência. Logo eu não prciso mais
     * dessa linha de codigo:     private val repository: NoticiaRepository by inject()
     *
     */

    private val adapter by lazy {
        context?.let {
            ListaNoticiasAdapter(context = it)
        } ?: throw IllegalArgumentException("Contexto Inválido")
    }

    private val viewModel: ListaNoticiasViewModel by viewModel()

    /**
     * Precismos fazer a inicialização - Faço isso com uma implementação vazia que não dvolve nada e na execução
     * não acontece nada. -> Unit = {}
     */
    var quandoFabNoticiaSalvaNoticiaClicado: () -> Unit = {}//Uma função que não recebe nada e tambem não devolve nada
    var quandoNoticiaSelecionada: (noticia: Noticia) -> Unit = {} //É uma função que pode receber uma notícia e não devolve nada

    /**
     * É um mecanismo de inicialização em Fragments
     * Aqui eu faço as inicializações que fazem parte da view direta.
     * Então, no onCreate não criamos a view, fazemos a inicialização.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buscaNoticias()
    }

    /**
     * Cria uma view. Temos o mesmo comportamento de inflar uma view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lista_noticia, container, false)
    }

    /**
     * É no onViewCreated que fazemos o bind de view. Comportamento de vínculo de view.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraRecyclerView()
        configuraFabAdicionaNoticia()
        activity?.title = TITULO_APPBAR
    }

    /**
     * Uso a referência da listaNoticiasActivity chamando o abreFormulárioModoCriacao()
     * quando clico em uma notícia.
     */
    private fun configuraFabAdicionaNoticia() {
        lista_noticias_fab_salva_noticia.setOnClickListener {
            quandoFabNoticiaSalvaNoticiaClicado()
        }
    }

    private fun configuraRecyclerView() {
        val divisor = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        lista_noticias_recyclerview.addItemDecoration(divisor)
        lista_noticias_recyclerview.adapter = adapter
        configuraAdapter()
    }

    /**
     * Já no adapter qundo eu quiser colocar um comportamento no listener dele que é quando um item for clicado,
     * utilizamos a referência da listaNoticiasActivity que nada mais é também ue uma função que vai receber
     * uma notícia. E é compatível com a implementação que foi via method reference.
     */
    private fun configuraAdapter() {
        adapter.quandoItemClicado = quandoNoticiaSelecionada
    }

    private fun buscaNoticias() {
        viewModel.buscaTodos().observe(this, Observer { resource ->
            resource.dado?.let {
                adapter.atualiza(it)
            }

            resource.erro?.let {
                mostraErro(MENSAGEM_FALHA_CARREGAR_NOTICIAS)
            }
        })
    }

    /**
     * Implementação de uma inner interface.
     * Aqui dentro eu coloco os comportamentos que espero
     *         fun quandoNoticiaSelecionada(noticia: Noticia) - Envio uma notícia para poder também
     *         dar acesso para quem chamou esse listener.
     *
     *         fun qundoFabNoticiaSalvaNoticiaClicado()
     * Eu crio essa interface e ao invés de estar lidando diretamente com uma activity eu posso estar
     * lidando diretamente com a interface.
     *
     *     interface ListaNoticiasListener{
            fun quandoNoticiaSelecionada(noticia: Noticia)
            fun quandoFabNoticiaSalvaNoticiaClicado()
            }
     *
     */

}