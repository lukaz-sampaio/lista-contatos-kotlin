package lab.example.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import lab.example.listadecontatos.feature.listacontatos.model.ContatosVO

/**
 * A minha classe `Helper` extende `SQLiteHelper` pra ajudar na manipulação do
 * banco de dados.
 */
class HelperDB(
    context: Context
) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_BANCO) {

    /**
     * esse companion object foi criado para delegar informações a partir do
     * helper como nome e versão do banco de dados. Ou seja, o nome do banco e a
     * versão são definidas aqui e não vinda de outros lugares.
     */
    companion object {
        private val NOME_BANCO: String = "contatos.db"
        private val VERSAO_BANCO: Int = 1
    }

    val TABLE_NAME = "contato"

    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL(
            """
            CREATE TABLE $TABLE_NAME (
                id INTEGER NOT NULL,
                nome TEXT NOT NULL,
                telefone TEXT NOT NULL,
                
                PRIMARY KEY (id AUTOINCREMENT) 
            );
        """.trimIndent()
        )
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            database?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        }
        onCreate(database)
    }

    fun buscarContatos(busca: String): List<ContatosVO> {
        val db = readableDatabase ?: return mutableListOf()
        var lista = mutableListOf<ContatosVO>()
        val sql = "SELECT id, nome, telefone FROM $TABLE_NAME"
        var cursor = db.rawQuery(sql, null) ?: return mutableListOf()
        while (cursor.moveToNext()) {
            val contato = ContatosVO(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("nome")),
                cursor.getString(cursor.getColumnIndex("telefone"))
            )
            lista.add(contato)
        }
        return lista
    }
}
