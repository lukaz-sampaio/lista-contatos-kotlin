package lab.example.listadecontatos.singleton

import lab.example.listadecontatos.feature.listacontatos.model.ContatosVO

object ContatoSingleton {
    var lista: MutableList<ContatosVO> = mutableListOf()
}