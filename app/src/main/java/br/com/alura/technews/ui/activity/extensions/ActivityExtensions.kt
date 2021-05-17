package br.com.alura.technews.ui.activity.extensions

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction

fun Activity.mostraErro(mensagem: String) {
    Toast.makeText(
        this,
        mensagem,
        Toast.LENGTH_LONG
    ).show()
}

/**
 * Aqui eu tenho uma função.
 * Na sua assinatura eu preciso fornecer uma função. Dado que vou implementar uma função lambda.
 * Essa função vai se chamar de executa. O segredo para fazer com que o escopo dessa chamada
 * seja da nossa transação é criar uma extensão (de FragmentTransaction) e indicamos que ela vai executar
 * essa função que estamos fornecendo, sem retornar nada.
 *
 * Como faço para executar essa função e ela ser utilizada para quem implementar a expressão lambda?
 * Para isso temos que ter acesso a uma transação. Como? Via supportFragmentManager.
 *
 * Vou ter uma variável transação que tem o valor dessa transação, chamo a função executa e depois
 * faço o commit.
 *
 * Com essa abordagem evitamos a responsabilidade de qualquer programador de ter que ficar abrindo
 * a transação e depois fazer o commit, sendo que ele vai sempre ter que fazer isso.
 *
 * Eu posso também fazer com que essa função seja uma extensão de uma Activity para que outras activities
 * que considerarem o uso de fragments também tenha acesso a esse tipo de comportamento.
 */
fun AppCompatActivity.transacaoFragment(executa: FragmentTransaction.() -> Unit){
    val transacao = supportFragmentManager.beginTransaction()
    executa(transacao)
    transacao.commit()
}