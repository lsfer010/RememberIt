package com.example.rememberit.entidades

class Recordatorios {
    private var id_recordatorio : Int
    private var id_evento : Int
    private var titulo_evento : String

    init {
        this.id_recordatorio = 0
        this.id_evento = 0
        this.titulo_evento = ""
    }

    fun getIdRecordatorio() : Int {
        return id_recordatorio
    }

    fun getIdEvento() : Int {
        return id_evento
    }

    fun getTituloEvento() : String {
        return titulo_evento
    }

    fun setIdRecordatorio(id_recordatorio : Int) {
        this.id_recordatorio = id_recordatorio
    }

    fun setIdEvento(id_evento : Int) {
        this.id_evento = id_evento
    }

    fun setTituloEvento(titulo_evento : String) {
        this.titulo_evento = titulo_evento
    }
}