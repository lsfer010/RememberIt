package com.example.rememberit.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.rememberit.entidades.Eventos
import com.example.rememberit.entidades.Recordatorios

class DbRecordatorios(context: Context?) : DbHelper(context) {

    lateinit var context : Context

    init {
        if (context != null) {
            this.context = context
        }
    }

    fun insertarRecordatorio(id_evento: Long, titulo: String, fecha: String, hora: String,) : Long {
        try {
            val dbHelper = DbHelper(context)
            val db = dbHelper.getWritableDatabase()
            val values = ContentValues()
            values.put("id_evento", id_evento)
            values.put("titulo_evento", titulo)
            values.put("fecha_evento", fecha)
            values.put("hora_evento", hora)
            val id = db.insert(TABLE_RECORDATORIOS, null, values)
            return id
        } catch (ex: Exception) {
            Log.d("Error SQL", ex.toString())
            return 0
        }
    }

    //OJO ESTA FUNCIÓN SOLO EDITA LA INFORMACIÓN REDUNDANTE DEL EVENTO ASOCIADO A CADA RECORDATORIO, NO EDITA LOS RECORDATORIOS EN SI NI LAS ALARMAS.
    fun editarRecordatorios(id_evento: Int, titulo: String, fecha: String, hora: String) : Boolean {
        var correcto = false
        val dbHelper = DbHelper(context)
        val db = dbHelper.getWritableDatabase()
        try {
            db.execSQL("UPDATE " + TABLE_RECORDATORIOS + " SET titulo_evento = '" + titulo + "', fecha_evento = '" + fecha + "', hora_evento = '" + hora + "' WHERE id_evento = " + id_evento.toString())
            correcto = true
            return correcto
        } catch (ex: Exception) {
            Log.d("Error SQL", ex.toString())
            return correcto
        } finally {
            db.close()
        }
    }

    fun eliminarRecordatorios(id_evento : Int) : Boolean {
        var correcto = false
        val dbHelper = DbHelper(context)
        val db = dbHelper.getWritableDatabase()
        try {
            db.execSQL("DELETE FROM " + TABLE_RECORDATORIOS + " WHERE id_evento = " + id_evento.toString())
            correcto = true
            return correcto
        } catch (ex: Exception) {
            Log.d("Error SQL", ex.toString())
            return correcto
        } finally {
            db.close()
        }
    }

    fun consultarRecordatorios(id_evento : Int) : ArrayList<Recordatorios> {
        val dbHelper = DbHelper(context)
        val db = dbHelper.getWritableDatabase()
        var listaRecordatorios = ArrayList<Recordatorios>()
        var recordatorio : Recordatorios
        var cursorRecordatorio : Cursor

        cursorRecordatorio = db.rawQuery("SELECT id_recordatorio, id_evento, titulo_evento FROM " + TABLE_RECORDATORIOS + " WHERE id_evento = " + id_evento.toString(), null)

        if(cursorRecordatorio.moveToFirst()) { //Movemos el cursor hacia la primera posición de lo que lea el cursor, sin antes comprobar si hay resultados.
            do {
                recordatorio = Recordatorios()
                recordatorio.setIdRecordatorio(cursorRecordatorio.getInt(0))
                recordatorio.setIdEvento(cursorRecordatorio.getInt(1))
                recordatorio.setTituloEvento(cursorRecordatorio.getString(2))
                listaRecordatorios.add(recordatorio)
            } while (cursorRecordatorio.moveToNext()) //mover el cursor (apuntador) al siguiente registro, si no encuentra un siguiente, se sale.
        }

        cursorRecordatorio.close()
        return listaRecordatorios
    }
}