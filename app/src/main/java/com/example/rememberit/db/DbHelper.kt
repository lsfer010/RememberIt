package com.example.rememberit.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DbHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NOMBRE, null, DATABASE_VERSION) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        //sqLiteDatabase es una especie de cursor que nos permite ejecutar sentencias SQL.
        sqLiteDatabase.execSQL(
            "CREATE TABLE " + TABLE_EVENTOS + "(" +
                    "id_evento INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "titulo_evento TEXT NOT NULL," +
                    "fecha_evento TEXT NOT NULL," +
                    "hora_evento TEXT NOT NULL," +
                    "desc_evento TEXT NOT NULL," +
                    "com_evento TEXT)"
        )
        sqLiteDatabase.execSQL(
            "CREATE TABLE " + TABLE_RECORDATORIOS + "(" +
                    "id_recordatorio INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id_evento INTEGER NOT NULL," +
                    "titulo_evento TEXT NOT NULL," +
                    "fecha_evento TEXT NOT NULL," +
                    "hora_evento TEXT NOT NULL)"
        )
    }

    //Este método se ejecuta cuando se cambie la versión de la base de datos, es decir DATABASE_VERSION.
    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_EVENTOS)
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_RECORDATORIOS)
        onCreate(sqLiteDatabase) //Volvemos a llamar al método onCreate para que vuelva a crear las tablas actualizadas.
    }

    //Aqui se escriben los parámetros que recibirá el constructor SQLiteOpenHelper
    companion object {
        private const val DATABASE_VERSION = 2 //Controlamos la versión de la base de datos, depende la versión de los cambios
        private const val DATABASE_NOMBRE = "rememberit.db" //Nombre de la base de datos
        const val TABLE_EVENTOS = "t_eventos" //Tabla de eventos
        const val TABLE_RECORDATORIOS = "t_recordatorios" //Tabla de recordatorios
    }
}