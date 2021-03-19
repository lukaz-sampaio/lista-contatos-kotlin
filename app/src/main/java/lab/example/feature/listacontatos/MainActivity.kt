package lab.example.listadecontatos.feature.listacontatos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import lab.example.application.ContatoApplication
import lab.example.listadecontatos.R
import lab.example.listadecontatos.bases.BaseActivity
import lab.example.listadecontatos.feature.contato.ContatoActivity
import lab.example.listadecontatos.feature.listacontatos.adapter.ContatoAdapter
import lab.example.listadecontatos.feature.listacontatos.model.ContatosVO


class MainActivity : BaseActivity() {

    private var adapter:ContatoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolBar(toolBar, "Lista de contatos",false)
        setupListView()
        setupOnClicks()
    }

    private fun setupOnClicks(){
        fab.setOnClickListener { onClickAdd() }
        ivBuscar.setOnClickListener { onClickBuscar() }
    }

    private fun setupListView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        onClickBuscar()
    }

    private fun onClickAdd(){
        val intent = Intent(this,ContatoActivity::class.java)
        startActivity(intent)
    }

    private fun onClickItemRecyclerView(index: Int){
        val intent = Intent(this,ContatoActivity::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    private fun onClickBuscar() {
        val busca = etBuscar.text.toString()

        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(500)
            var listaFiltrada: List<ContatosVO> = mutableListOf()

            try {
                listaFiltrada =
                    ContatoApplication.instance.helperDB?.buscarContatos(busca)
                        ?: mutableListOf()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            /*
             1. O applicativo roda na main thread;
             2. A main thread não pode ser bloqueada porque vai quebrar o
                aplicativo;
             3. A main thread se parada por 10 segundos é mostrado um aviso que
                é melhor fechar (parar a execução) o aplicativo.
             4. Views só podem ser processadas dentro da main thread ou de uma
                UiThread. No caso aqui, só é mostrado as views (lembrando que
                todo componente é uma view). Então, ao criar uma thread pra load
                de qualquer coisa, as views devem ser colocadas dentro de
                "runOnUiThread{ // bloco de código das views aqui}" (como no
                exemplo abaixo).
            */
            runOnUiThread {
                adapter = ContatoAdapter(this, listaFiltrada) { onClickItemRecyclerView(it) }
                recyclerView.adapter = adapter
                progress.visibility = View.GONE
                Toast.makeText(this, "Buscando por $busca", Toast.LENGTH_SHORT).show()
            }
        }).start()
    }
}
