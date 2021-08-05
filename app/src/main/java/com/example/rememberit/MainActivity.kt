package com.example.rememberit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rememberit.adaptadores.ListaEventosAdapter
import com.example.rememberit.db.DbEventos
import com.example.rememberit.db.DbHelper
import com.example.rememberit.entidades.Eventos

class
MainActivity : AppCompatActivity() {

    private lateinit var listaEventos : RecyclerView
    private lateinit var listaArrayEventos : ArrayList<Eventos>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.listarEventos()
        this.inicializarBotonAgregar()
        //NOTA: LLAMAR CUANDO SE REQUIERA ACTUALIZAR O MODIFICAR LA BASE DE DATOS.
        /*try {
            val dbHelper = DbHelper(this)
            val db = dbHelper.getWritableDatabase()
            if(db != null) {
                Toast.makeText(this,"BASE DE DATOS ACTUALIZADA CORRECTAMENTE",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"LA BASE DE DATOS NO SE ACTUALIZÓ",Toast.LENGTH_LONG).show()
            }
        } catch (ex : Exception) {
            Log.e("Error",ex.toString())
        }*/
    }

    //La siguiente función cargará el menú que se ha creado en la carpeta menu en la actividad principal.
    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        val inflater = getMenuInflater()
        inflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    //Con la siguiente función se detecte que item del menú se seleccionó, y después del return se puso un switch case.
    // Si se seleccionó la opción de menuNuevo, se llama a nuevoRegistro que ejecuta la activity donde está el formulario de registro.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuNuevo -> {
                this.nuevoRegistro()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        this.listarEventos()
        this.inicializarBotonAgregar()
    }

    private fun nuevoRegistro() {
        val intent = Intent(this, FormInsertarActivity::class.java) //El Intent le dice que pase de este contexto, a la nueva activity
        startActivity(intent) //Inicia la nueva activity
    }

    private fun inicializarBotonAgregar() {
        val fab: View = findViewById(R.id.fab_agr_eve)
        fab.setOnClickListener {
            this.nuevoRegistro()
        }
    }

    private fun listarEventos() {
        try {
            listaEventos = findViewById(R.id.listaEventos)
            listaEventos.setLayoutManager(LinearLayoutManager(this))

            val dbEventos = DbEventos(this)
            listaArrayEventos = ArrayList()
            var adapterEventos = ListaEventosAdapter(dbEventos.leerEventos())
            listaEventos.setAdapter(adapterEventos)
        } catch (ex : Exception) {
            Toast.makeText(this,ex.toString(),Toast.LENGTH_LONG).show()
        }
    }
}