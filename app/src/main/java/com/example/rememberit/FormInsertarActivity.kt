package com.example.rememberit

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.rememberit.db.DbEventos
import com.example.rememberit.db.DbRecordatorios
import java.util.*

class FormInsertarActivity : AppCompatActivity() {

    lateinit var txtTitulo : EditText
    lateinit var txtFecha : EditText
    lateinit var txtHora : EditText
    lateinit var txtDesc : EditText
    lateinit var txtCom : EditText
    lateinit var checkRec1 : CheckBox
    lateinit var txtFechaRec1 : EditText
    lateinit var txtHoraRec1 : EditText
    lateinit var checkRec2 : CheckBox
    lateinit var txtFechaRec2 : EditText
    lateinit var txtHoraRec2 : EditText
    lateinit var checkRec3 : CheckBox
    lateinit var txtFechaRec3 : EditText
    lateinit var txtHoraRec3 : EditText
    lateinit var btnAgregar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_insertar)

        txtTitulo = findViewById(R.id.editTitulo)
        txtFecha = findViewById(R.id.editFecha)
        txtHora = findViewById(R.id.editHora)
        txtDesc = findViewById(R.id.editDesc)
        txtCom = findViewById(R.id.editMultiCom)
        checkRec1 = findViewById(R.id.checkRec1)
        txtFechaRec1 =  findViewById(R.id.editFechaRec1)
        txtHoraRec1 =  findViewById(R.id.editHoraRec1)
        checkRec2 = findViewById(R.id.checkRec2)
        txtFechaRec2 =  findViewById(R.id.editFechaRec2)
        txtHoraRec2 =  findViewById(R.id.editHoraRec2)
        checkRec3 = findViewById(R.id.checkRec3)
        txtFechaRec3 =  findViewById(R.id.editFechaRec3)
        txtHoraRec3 =  findViewById(R.id.editHoraRec3)
        btnAgregar = findViewById(R.id.btnAgregar)

        //Poner DatePicker a campos donde se pide fecha, y TimePicker a los campos donde se pide hora.
        txtFecha.setOnClickListener {
            showDatePickerDialogEvento()
        }

        txtFechaRec1.setOnClickListener {
            showDatePickerDialogRec1()
        }

        txtFechaRec2.setOnClickListener {
            showDatePickerDialogRec2()
        }

        txtFechaRec3.setOnClickListener {
            showDatePickerDialogRec3()
        }

        txtHora.setOnClickListener {
            showTimePickerDialogEvento()
        }

        txtHoraRec1.setOnClickListener {
            showTimePickerDialogRec1()
        }

        txtHoraRec2.setOnClickListener {
            showTimePickerDialogRec2()
        }

        txtHoraRec3.setOnClickListener {
            showTimePickerDialogRec3()
        }

        btnAgregar.setOnClickListener {
            txtTitulo.setError(null)
            txtFecha.setError(null)
            txtHora.setError(null)
            txtDesc.setError(null)
            txtFechaRec1.setError(null)
            txtFechaRec2.setError(null)
            txtFechaRec3.setError(null)
            txtHoraRec1.setError(null)
            txtHoraRec2.setError(null)
            txtHoraRec3.setError(null)

            val titulo = txtTitulo.text.toString()
            val fecha = txtFecha.text.toString()
            val hora = txtHora.text.toString()
            val desc = txtDesc.text.toString()
            val com = txtCom.text.toString()

            var id : Long = 0
            var fecharec1 : String = ""
            var fecharec2 : String = ""
            var fecharec3 : String = ""
            var horarec1 : String = ""
            var horarec2 : String = ""
            var horarec3 : String = ""
            var milisegundosrec1 : Long = 0
            var milisegundosrec2 : Long = 0
            var milisegundosrec3 : Long = 0

            if(!titulo.equals("") && !fecha.equals("") && !hora.equals("") && !desc.equals("")) {
                if (checkRec1.isChecked() or checkRec2.isChecked() or checkRec3.isChecked()) {

                    val dbRecordatorios = DbRecordatorios(this)
                    var recordatorio1 : Boolean = true
                    var recordatorio2 : Boolean = true
                    var recordatorio3 : Boolean = true

                    if (checkRec1.isChecked()){
                        txtFechaRec1.setError(null)
                        txtHoraRec1.setError(null)
                        fecharec1 = txtFechaRec1.text.toString()
                        horarec1 = txtHoraRec1.text.toString()

                        if (!fecharec1.equals("") && !horarec1.equals("")) {
                            recordatorio1 = true
                            val fecha_recordatorio1 = Calendar.getInstance()
                            var partes_fecha1 = fecharec1.split("-")
                            var partes_hora1 = horarec1.split(":")
                            fecha_recordatorio1[Calendar.DAY_OF_MONTH] = partes_fecha1[0].toInt()
                            fecha_recordatorio1[Calendar.MONTH] = partes_fecha1[1].toInt() - 1
                            fecha_recordatorio1[Calendar.YEAR] = partes_fecha1[2].toInt()
                            fecha_recordatorio1[Calendar.HOUR_OF_DAY] = partes_hora1[0].toInt()
                            fecha_recordatorio1[Calendar.MINUTE] = partes_hora1[1].toInt()
                            fecha_recordatorio1[Calendar.SECOND] = 0
                            milisegundosrec1 = fecha_recordatorio1.timeInMillis
                        } else {
                            recordatorio1 = false
                            if (fecharec1.equals("")) {
                                txtFechaRec1.setError("Seleccione una fecha")
                                txtFechaRec1.requestFocus()
                            }
                            if (horarec1.equals("")) {
                                txtHoraRec1.setError("Seleccione una hora")
                                txtHoraRec1.requestFocus()
                            }
                        }
                    }

                    if (checkRec2.isChecked()){
                        txtFechaRec2.setError(null)
                        txtHoraRec2.setError(null)
                        fecharec2 = txtFechaRec2.text.toString()
                        horarec2 = txtHoraRec2.text.toString()

                        if (!fecharec2.equals("") && !horarec2.equals("")) {
                            recordatorio2 = true
                            val fecha_recordatorio2 = Calendar.getInstance()
                            var partes_fecha2 = txtFechaRec2.text.toString().split("-")
                            var partes_hora2 = txtHoraRec2.text.toString().split(":")
                            fecha_recordatorio2[Calendar.DAY_OF_MONTH] = partes_fecha2[0].toInt()
                            fecha_recordatorio2[Calendar.MONTH] = partes_fecha2[1].toInt() - 1
                            fecha_recordatorio2[Calendar.YEAR] = partes_fecha2[2].toInt()
                            fecha_recordatorio2[Calendar.HOUR_OF_DAY] = partes_hora2[0].toInt()
                            fecha_recordatorio2[Calendar.MINUTE] = partes_hora2[1].toInt()
                            fecha_recordatorio2[Calendar.SECOND] = 0
                            milisegundosrec2 = fecha_recordatorio2.timeInMillis
                        } else {
                            recordatorio2 = false
                            if (fecharec2.equals("")) {
                                txtFechaRec2.setError("Seleccione una fecha")
                                txtFechaRec2.requestFocus()
                            }
                            if (horarec2.equals("")) {
                                txtHoraRec2.setError("Seleccione una hora")
                                txtHoraRec2.requestFocus()
                            }
                        }
                    }

                    if (checkRec3.isChecked()){
                        fecharec3 = txtFechaRec3.text.toString()
                        horarec3 = txtHoraRec3.text.toString()

                        if (!fecharec3.equals("") && !horarec3.equals("")) {
                            recordatorio3 = true
                            val fecha_recordatorio3 = Calendar.getInstance()
                            var partes_fecha3 = txtFechaRec3.text.toString().split("-")
                            var partes_hora3 = txtHoraRec3.text.toString().split(":")
                            fecha_recordatorio3[Calendar.DAY_OF_MONTH] = partes_fecha3[0].toInt()
                            fecha_recordatorio3[Calendar.MONTH] = partes_fecha3[1].toInt() - 1
                            fecha_recordatorio3[Calendar.YEAR] = partes_fecha3[2].toInt()
                            fecha_recordatorio3[Calendar.HOUR_OF_DAY] = partes_hora3[0].toInt()
                            fecha_recordatorio3[Calendar.MINUTE] = partes_hora3[1].toInt()
                            fecha_recordatorio3[Calendar.SECOND] = 0
                            milisegundosrec3 = fecha_recordatorio3.timeInMillis
                        } else {
                            recordatorio3 = false
                            if (fecharec3.equals("")) {
                                txtFechaRec3.setError("Seleccione una fecha")
                                txtFechaRec3.requestFocus()
                            }
                            if (horarec3.equals("")) {
                                txtHoraRec3.setError("Seleccione una hora")
                                txtHoraRec3.requestFocus()
                            }
                        }
                    }

                    if (recordatorio1 && recordatorio2 && recordatorio3) {
                        val dbEventos = DbEventos(this)
                        id = dbEventos.insertarEvento(titulo,fecha,hora,desc,com)
                        if(checkRec1.isChecked()) {
                            val id_rec1 : Long = dbRecordatorios.insertarRecordatorio(id, titulo, fecharec1, horarec1)
                            this@FormInsertarActivity.establecerAlarma(id_rec1.toInt(),milisegundosrec1,this@FormInsertarActivity, titulo, fecha, hora)
                        }
                        if(checkRec2.isChecked()) {
                            val id_rec2 : Long = dbRecordatorios.insertarRecordatorio(id, titulo , fecharec2, horarec2)
                            this@FormInsertarActivity.establecerAlarma(id_rec2.toInt(),milisegundosrec2,this@FormInsertarActivity, titulo, fecha, hora)
                        }
                        if(checkRec3.isChecked()) {
                            val id_rec3 : Long = dbRecordatorios.insertarRecordatorio(id, titulo, fecharec3, horarec3)
                            this@FormInsertarActivity.establecerAlarma(id_rec3.toInt(),milisegundosrec3,this@FormInsertarActivity, titulo, fecha, hora)
                        }
                    }

                    if (id > 0) {
                        Toast.makeText(this, "EVENTO Y RECORDATORIOS REGISTRADOS", Toast.LENGTH_LONG).show()
                        limpiar()
                    } else {
                        Toast.makeText(this, "ERROR AL REGISTRAR EL EVENTO Y RECORDATORIOS", Toast.LENGTH_LONG).show()
                    }
                } else {
                    val dbEventos = DbEventos(this)
                    id = dbEventos.insertarEvento(titulo,fecha,hora,desc,com)

                    if (id > 0) {
                        Toast.makeText(this, "EVENTO REGISTRADO", Toast.LENGTH_LONG).show()
                        limpiar()
                    } else {
                        Toast.makeText(this, "ERROR AL REGISTRAR EL EVENTO", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                if (titulo.equals("")) {
                    txtTitulo.setError("Introduce un título")
                    txtTitulo.requestFocus()
                }
                if (fecha.equals("")) {
                    txtFecha.setError("Selecciona una fecha")
                    txtFecha.requestFocus()
                }
                if (hora.equals("")) {
                    txtHora.setError("Selecciona una hora")
                    txtHora.requestFocus()
                }
                if (desc.equals("")) {
                    txtDesc.setError("Introduce una descripción")
                    txtDesc.requestFocus()
                }
            }
        }
    }

    fun onCheckRec1Clicked(view : View) {
        if(view is CheckBox) {
            if(checkRec1.isChecked) {
                val fechatxt1 = txtFecha.getText().toString()
                if(!fechatxt1.equals("") && txtFechaRec1.getText().toString().equals("")) {
                    txtFechaRec1.setText(fechatxt1)
                }
            } else {
                txtFechaRec1.setText("")
                txtFechaRec1.setHint(R.string.txt_fecha_ed)
            }
        }
    }

    fun onCheckRec2Clicked(view : View) {
        if(view is CheckBox) {
            if(checkRec2.isChecked) {
                val fechatxt2 = txtFecha.getText().toString()
                if(!fechatxt2.equals("") && txtFechaRec2.getText().toString().equals("")) {
                    txtFechaRec2.setText(fechatxt2)
                }
            } else {
                txtFechaRec2.setText("")
                txtFechaRec2.setHint(R.string.txt_fecha_ed)
            }
        }
    }

    fun onCheckRec3Clicked(view : View) {
        if(view is CheckBox) {
            if(checkRec3.isChecked) {
                val fechatxt3 = txtFecha.getText().toString()
                if(!fechatxt3.equals("") && txtFechaRec3.getText().toString().equals("")) {
                    txtFechaRec3.setText(fechatxt3)
                }
            } else {
                txtFechaRec3.setText("")
                txtFechaRec3.setHint(R.string.txt_fecha_ed)
            }
        }
    }

    private fun showDatePickerDialogEvento() {
        val datePicker = DatePickerFragment { dia, mes, anio -> onDateSelected(dia, mes, anio) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun onDateSelected(dia:Int, mes:Int, anio:Int){
        val fecha_corregida = corregirFecha(dia,mes,anio)
        txtFecha.setText(fecha_corregida)
        if(checkRec1.isChecked) {
            txtFechaRec1.setText(fecha_corregida)
        }
        if(checkRec2.isChecked) {
            txtFechaRec2.setText(fecha_corregida)
        }
        if(checkRec3.isChecked) {
            txtFechaRec3.setText(fecha_corregida)
        }
    }

    private fun showDatePickerDialogRec1() {
        val datePicker = DatePickerFragment { dia, mes, anio -> onDateSelectedRec1(dia, mes, anio) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun onDateSelectedRec1(dia:Int, mes:Int, anio:Int){
        val fecha_corregida = corregirFecha(dia,mes,anio)
        txtFechaRec1.setText(fecha_corregida)
    }

    private fun showDatePickerDialogRec2() {
        val datePicker = DatePickerFragment { dia, mes, anio -> onDateSelectedRec2(dia, mes, anio) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun onDateSelectedRec2(dia:Int, mes:Int, anio:Int){
        val fecha_corregida = corregirFecha(dia,mes,anio)
        txtFechaRec2.setText(fecha_corregida)
    }

    private fun showDatePickerDialogRec3() {
        val datePicker = DatePickerFragment { dia, mes, anio -> onDateSelectedRec3(dia, mes, anio) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun onDateSelectedRec3(dia:Int, mes:Int, anio:Int){
        val fecha_corregida = corregirFecha(dia,mes,anio)
        txtFechaRec3.setText(fecha_corregida)
    }

    private fun showTimePickerDialogEvento() {
        val timePicker = TimePickerFragment { onTimeSelected(it) }
        timePicker.show(supportFragmentManager, "timePicker")
    }

    private fun onTimeSelected(time: String) {
        val hora_corregida = corregirHora(time)
        txtHora.setText("$hora_corregida")
    }

    private fun showTimePickerDialogRec1() {
        val timePicker = TimePickerFragment { onTimeSelectedRec1(it) }
        timePicker.show(supportFragmentManager, "timePicker")
    }

    private fun onTimeSelectedRec1(time: String) {
        val hora_corregida = corregirHora(time)
        txtHoraRec1.setText("$hora_corregida")
    }

    private fun showTimePickerDialogRec2() {
        val timePicker = TimePickerFragment { onTimeSelectedRec2(it) }
        timePicker.show(supportFragmentManager, "timePicker")
    }

    private fun onTimeSelectedRec2(time: String) {
        val hora_corregida = corregirHora(time)
        txtHoraRec2.setText("$hora_corregida")
    }

    private fun showTimePickerDialogRec3() {
        val timePicker = TimePickerFragment { onTimeSelectedRec3(it) }
        timePicker.show(supportFragmentManager, "timePicker")
    }

    private fun onTimeSelectedRec3(time: String) {
        val hora_corregida = corregirHora(time)
        txtHoraRec3.setText("$hora_corregida")
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

    private fun limpiar(){
        txtTitulo.setText("")
        txtFecha.setText("")
        txtHora.setText("")
        txtDesc.setText("")
        txtCom.setText("")
        txtFechaRec1.setText("")
        txtFechaRec2.setText("")
        txtFechaRec3.setText("")
        txtHoraRec1.setText("")
        txtHoraRec2.setText("")
        txtHoraRec3.setText("")
        checkRec1.setChecked(false)
        checkRec2.setChecked(false)
        checkRec3.setChecked(false)
    }

    fun establecerAlarma(id_alarma : Int, fecha_milisegundos : Long, ctx : Context, titulo_ev : String, fecha_ev : String, hora_ev : String) {
        val fechahora_ev = fecha_ev + " - " + hora_ev
        val alarmManager = ctx.getSystemService(ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(ctx, AlarmReceiver::class.java)
        alarmIntent.putExtra("id_notif",id_alarma)
        alarmIntent.putExtra("titulo_ev",titulo_ev)
        alarmIntent.putExtra("fechahora_ev",fechahora_ev)
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(ctx, id_alarma, alarmIntent, PendingIntent.FLAG_ONE_SHOT)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, fecha_milisegundos, pendingIntent)
    }
}