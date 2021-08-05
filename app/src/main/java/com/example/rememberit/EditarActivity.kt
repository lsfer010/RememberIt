package com.example.rememberit

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rememberit.db.DbEventos
import com.example.rememberit.db.DbRecordatorios
import com.example.rememberit.entidades.Eventos

class EditarActivity : AppCompatActivity() {

    private lateinit var editTitulo : EditText
    private lateinit var editFecha : EditText
    private lateinit var editHora : EditText
    private lateinit var editDesc : EditText
    private lateinit var editCom : EditText
    private lateinit var btnGuardar : Button
    private lateinit var btnEditar : Button
    private lateinit var btnEliminar : Button
    private var correcto_evento : Boolean = false
    private var correcto_recordatorio : Boolean = false

    private var evento = Eventos()
    private var id : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver)
        editTitulo = findViewById(R.id.editTituloEd)
        editFecha = findViewById(R.id.editFechaEd)
        editHora = findViewById(R.id.editHoraEd)
        editDesc = findViewById(R.id.editDescEd)
        editCom = findViewById(R.id.editMultiComEd)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnEditar = findViewById(R.id.btnEditar)
        btnEliminar = findViewById(R.id.btnEliminar)

        //Se validan diferentes formas para recibir el parámetro que se envía desde listaEventosAdapter.
        if(savedInstanceState == null){
            val extras = getIntent().getExtras()

            if(extras == null) {
                id = Integer.parseInt(null)
            } else {
                id = extras.getInt("ID")
            }
        } else {
            id = savedInstanceState.getSerializable("ID") as Int //as Int quiere decir que va a hacer casteo a INT
        }

        val dbEventos = DbEventos(this)
        val dbRecordatorios = DbRecordatorios(this)
        evento = dbEventos.leerEvento(id)

        if (evento != null) {
            editTitulo.setText(evento.getTitulo())
            editFecha.setText(evento.getFecha())
            editHora.setText(evento.getHora())
            editDesc.setText(evento.getDesc())
            editCom.setText(evento.getCom())
            //En VerActivity aqui se deshabilitaban los editext y el boton de guardar registro para edición, aquí los dejamos visibles y editables.
            btnEditar.setVisibility(View.INVISIBLE)
            btnEliminar.setVisibility(View.INVISIBLE)
        }

        editFecha.setOnClickListener {
            showDatePickerDialog()
        }

        editHora.setOnClickListener {
            showTimePickerDialog()
        }

        btnGuardar.setOnClickListener() {
            //ANTES VALIDAR QUE NO SEA VACÍO (REVISAR OTRAS VALIDACIONES)
            editTitulo.setError(null)
            editFecha.setError(null)
            editHora.setError(null)
            editDesc.setError(null)

            val titulo = editTitulo.getText().toString()
            val fecha = editFecha.getText().toString()
            val hora = editHora.getText().toString()
            val desc = editDesc.getText().toString()
            val com = editCom.getText().toString()

            if(!titulo.equals("") && !fecha.equals("") && !hora.equals("") && !desc.equals("")) {

                correcto_evento = dbEventos.editarEvento(id, titulo, fecha, hora, desc, com)

                correcto_recordatorio = dbRecordatorios.editarRecordatorios(id, titulo, fecha, hora)

                if(correcto_evento && correcto_recordatorio){
                    Toast.makeText(this,"EVENTO ACTUALIZADO CORRECTAMENTE", Toast.LENGTH_LONG).show()
                    verRegistro()
                } else {
                    if(!correcto_evento && correcto_recordatorio) {
                        Toast.makeText(this,"OCURRIO UN ERROR AL ACTUALIZAR EL EVENTO", Toast.LENGTH_LONG).show()
                    } else if (correcto_evento && !correcto_recordatorio){
                        Toast.makeText(this,"OCURRIO UN ERROR AL ACTUALIZAR LOS RECORDATORIOS", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this,"OCURRIO UN ERROR AL ACTUALIZAR EL EVENTO Y LOS RECORDATORIOS", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                if(titulo.equals("")) {
                    editTitulo.setError("Introduce un título")
                    editTitulo.requestFocus()
                }
                if(fecha.equals("")) {
                    editFecha.setError("Selecciona una fecha")
                    editFecha.requestFocus()
                }
                if(hora.equals("")) {
                    editHora.setError("Selecciona una hora")
                    editHora.requestFocus()
                }
                if(desc.equals("")) {
                    editDesc.setError("Introduce una descripción")
                    editDesc.requestFocus()
                }
                //Toast.makeText(this,"SE DEBE LLENAR EL TITULO, LA FECHA Y HORA, Y LA DESCRIPCIÓN DEL EVENTO", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { dia, mes, anio -> onDateSelected(dia, mes, anio) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun onDateSelected(dia:Int, mes:Int, anio:Int){
        val fecha_corregida = corregirFecha(dia,mes,anio)
        editFecha.setText(fecha_corregida)
    }

    fun corregirFecha(dia:Int, mes:Int, anio:Int) : String {
        var fecha_corregida : String = ""
        //CORREGIR DIA DE LA FECHA
        if(dia >= 1 && dia < 10) {
            fecha_corregida += "0" + dia.toString() + "-"
        } else {
            fecha_corregida += dia.toString() + "-"
        }
        //CORREGIR MES DE LA FECHA
        var mes_ok = mes
        mes_ok += 1
        if(mes_ok >= 1 && mes_ok < 10) {
            fecha_corregida += "0" + mes_ok.toString() + "-"
        } else {
            fecha_corregida += mes_ok.toString() + "-"
        }
        //AÑADIR AÑO
        fecha_corregida += anio.toString()
        return fecha_corregida
    }

    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment { onTimeSelected(it) }
        timePicker.show(supportFragmentManager, "timePicker")
    }

    private fun onTimeSelected(time: String) {
        val hora_corregida = corregirHora(time)
        editHora.setText("$hora_corregida")
    }

    private fun corregirHora(time: String) : String {
        val hora_partida = time.split(":")
        val hora_int = hora_partida[0].toInt()
        val minutos_int = hora_partida[1].toInt()
        var hora_corregida : String = ""
        if (hora_int >= 0 && hora_int < 10) {
            hora_corregida += "0" + hora_partida[0] + ":"
        } else {
            hora_corregida += hora_partida[0] + ":"
        }
        if (minutos_int >= 0 && minutos_int < 10) {
            hora_corregida += "0" + hora_partida[1]
        } else {
            hora_corregida += hora_partida[1]
        }
        return hora_corregida
    }

    private fun verRegistro(){
        val intent = Intent(this, VerActivity::class.java)
        intent.putExtra("ID", id)
        startActivity(intent)
    }
}