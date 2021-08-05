package com.example.rememberit.entidades

class Eventos {

    private var id : Int
    private var titulo : String
    private var fecha : String
    private var hora : String
    private var desc : String
    private var com : String

    init {
        this.id = 0
        this.titulo = ""
        this.fecha = ""
        this.hora = ""
        this.desc = ""
        this.com = ""
    }

    fun getId() : Int {
        return id
    }

    fun setId(id : Int) {
        this.id = id
    }

    fun getTitulo() : String {
        return titulo
    }

    fun setTitulo(titulo : String) {
        this.titulo = titulo
    }

    fun getFecha() : String {
        return fecha
    }

    fun setFecha(fecha : String) {
        this.fecha = fecha
    }

    fun getHora() : String {
        return hora
    }

    fun setHora(hora : String) {
        this.hora = hora
    }

    fun getDesc() : String {
        return desc
    }

    fun setDesc(desc : String) {
        this.desc = desc
    }

    fun getCom() : String {
        return com
    }

    fun setCom(com : String) {
        this.com = com
    }
}