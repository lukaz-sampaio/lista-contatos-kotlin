package lab.example.listadecontatos.feature.listacontatos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import lab.example.application.ContatoApplication
import lab.example.listadecontatos.R
import lab.example.listadecontatos.bases.BaseActivity
import lab.example.listadecontatos.feature.contato.ContatoActivity
import lab.example.listadecontatos.feature.listacontatos.adapter.ContatoAdapter
import lab.example.listadecontatos.feature.listacontatos.model.ContatosVO
import lab.example.listadecontatos.singleton.ContatoSingleton


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

    private fun onClickBuscar(){
        val busca = etBuscar.text.toString()
        var listaFiltrada: List<ContatosVO> = mutableListOf()

        try {
            listaFiltrada =
                ContatoApplication.instance.helperDB?.buscarContatos(busca)
                    ?: mutableListOf()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        adapter = ContatoAdapter(this, listaFiltrada) { onClickItemRecyclerView(it) }
        recyclerView.adapter = adapter
        Toast.makeText(this, "Buscando por $busca", Toast.LENGTH_SHORT).show()
    }

}
