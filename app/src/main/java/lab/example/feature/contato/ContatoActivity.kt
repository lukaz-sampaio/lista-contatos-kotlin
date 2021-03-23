package lab.example.listadecontatos.feature.contato

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_contato.*
import lab.example.application.ContatoApplication
import lab.example.listadecontatos.R
import lab.example.listadecontatos.bases.BaseActivity
import lab.example.listadecontatos.feature.listacontatos.model.ContatosVO

class ContatoActivity : BaseActivity() {

    private var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)
        setupToolBar(toolBar, "Contato", true)
        setupContato()
        btnSalvarConato.setOnClickListener { onClickSalvarContato() }
    }

    private fun setupContato() {
        index = intent.getIntExtra("index", -1)
        if (index == -1) {
            btnExcluirContato.visibility = View.GONE
            return
        }

        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(500)
            var lista = ContatoApplication.instance.helperDB?.buscarContatos("$index", true)
                ?: return@Runnable
            var contato = lista.getOrNull(0) ?: return@Runnable

            runOnUiThread {
                etNome.setText(contato.nome)
                etTelefone.setText(contato.telefone)
                progress.visibility = View.GONE
            }
        }).start()
    }

    private fun onClickSalvarContato() {
        val nome = etNome.text.toString()
        val telefone = etTelefone.text.toString()
        val contato = ContatosVO(
            index,
            nome,
            telefone,
            ""
        )

        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(500)
            if (index == -1) {
                ContatoApplication.instance.helperDB?.salvarContato(contato)
            } else {
                ContatoApplication.instance.helperDB?.atualizarContato(contato)
            }

            runOnUiThread {
                progress.visibility = View.GONE
                finish()
            }
        }).start()
    }

    fun onClickExcluirContato(view: View) {
        if (index > -1) {

            progress.visibility = View.VISIBLE
            Thread(Runnable {
                Thread.sleep(500)
                ContatoApplication.instance.helperDB?.deletarContato(index)
                runOnUiThread {
                    progress.visibility = View.GONE
                    finish()
                }
            }).start()
        }
    }
}
