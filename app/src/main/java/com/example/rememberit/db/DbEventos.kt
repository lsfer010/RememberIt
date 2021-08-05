package com.example.rememberit.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.rememberit.entidades.Eventos

class DbEventos(context: Context?) : DbHelper(context) {

    lateinit var context : Context

    init {
        if (context != null) {
            this.context = context
        }
    }

    fun insertarEvento(titulo: String, fecha: String, hora: String, desc: String, com: String) : Long {
        try {
            val dbHelper = DbHelper(context)
            val db = dbHelper.getWritableDatabase()
            val values = ContentValues()
            values.put("titulo_evento", titulo)
            values.put("fecha_evento", fecha)
            values.put("hora_evento", hora)
            values.put("desc_evento", desc)
            values.put("com_evento", com)
            val id = db.insert(TABLE_EVENTOS, null, values)
            return id
        } catch (ex: Exception) {
            Log.d("Error SQL", ex.toString())
            return 0
        }
    }

    fun leerEventos() : ArrayList<Eventos> {
        val dbHelper = DbHelper(context)
        val db = dbHelper.getWritableDatabase()
        var listaEventos = ArrayList<Eventos>()
        var evento : Eventos
        var cursorEvento : Cursor

        cursorEvento = db.rawQuery("SELECT id_evento, titulo_evento, desc_evento, fecha_evento, hora_evento FROM " + TABLE_EVENTOS, null)

        if(cursorEvento.moveToFirst()) { //Movemos el cursor hacia la primera posición de lo que lea el cursor, sin antes comprobar si hay resultados.
            do {
                evento = Eventos()
                evento.setId(cursorEvento.getInt(0))
                evento.setTitulo(cursorEvento.getString(1))
                evento.setDesc(cursorEvento.getString(2))
                evento.setFecha(cursorEvento.getString(3))
                evento.setHora(cursorEvento.getString(4))
                listaEventos.add(evento)
            } while (cursorEvento.moveToNext()) //mover el cursor (apuntador) al siguiente registro, si no encuentra un siguiente, se sale.
        }

        cursorEvento.close()
        return listaEventos
    }

    //Seleccionar un evento para mostrar su información en la vista activity_ver
    fun leerEvento(id : Int) : Eventos {
        val dbHelper = DbHelper(context)
        val db = dbHelper.getWritableDatabase()

        var evento = Eventos()
        var cursorEvento : Cursor

        cursorEvento = db.rawQuery("SELECT * FROM " + TABLE_EVENTOS + " WHERE id_evento = " + id + " LIMIT 1", null)

        if(cursorEvento.moveToFirst()) { //Movemos el cursor hacia la primera posición de lo que lea el cursor, sin antes comprobar si hay resultados.
            evento.setId(cursorEvento.getInt(0))
            evento.setTitulo(cursorEvento.getString(1))
            evento.setFecha(cursorEvento.getString(2))
            evento.setHora(cursorEvento.getString(3))
            evento.setDesc(cursorEvento.getString(4))
            evento.setCom(cursorEvento.getString(5))
        }
        cursorEvento.close()
        return evento
    }

    fun editarEvento(id : Int, titulo: String, fecha: String, hora: String, desc: String, com: String) : Boolean {

        var correcto = false
        val dbHelper = DbHelper(context)
        val db = dbHelper.getWritableDatabase()

        try {
            db.execSQL("UPDATE " + TABLE_EVENTOS + " SET titulo_evento = '" + titulo + "', fecha_evento = '" + fecha + "', hora_evento = '" + hora + "', desc_evento = '" + desc + "', com_evento = '" + com + "' WHERE id_evento = " + id.toString())
            correcto = true
            return correcto
        } catch (ex: Exception) {
            Log.d("Error SQL", ex.toString())
            correcto = false
            return correcto
        } finally {
            db.close()
        }
    }

    fun eliminarEvento(id : Int) : Boolean {
        var correcto = false
        val dbHelper = DbHelper(context)
        val db = dbHelper.getWritableDatabase()

        try {
            db.execSQL("DELETE FROM " + TABLE_EVENTOS + " WHERE id_evento = " + id.toString())
            correcto = true
            return correcto
        } catch (ex: Exception) {
            Log.d("Error SQL", ex.toString())
            correcto = false
            return correcto
        } finally {
            db.close()
        }
    }
}