package com.example.rememberit

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.rememberit.db.DbEventos
import com.example.rememberit.db.DbRecordatorios
import com.example.rememberit.entidades.Eventos
import com.example.rememberit.entidades.Recordatorios

class VerActivity : AppCompatActivity() {

    private lateinit var editTitulo : EditText
    private lateinit var editFecha : EditText
    private lateinit var editHora : EditText
    private lateinit var editDesc : EditText
    private lateinit var editCom : EditText
    private lateinit var btnGuardar : Button
    private lateinit var btnEditar : Button
    private lateinit var btnEliminar : Button

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
        var recordatoriosDel : ArrayList<Recordatorios>

        if (evento != null) {
            editTitulo.setText(evento.getTitulo())
            editFecha.setText(evento.getFecha())
            editHora.setText(evento.getHora())
            editDesc.setText(evento.getDesc())
            editCom.setText(evento.getCom())
            //Lo siguiente se activa cuando se desea ver el evento pero no modificarlo, ocultamos boton de guardar y los edittext los ponemos como no editables.
            btnGuardar.setVisibility(View.INVISIBLE)
            editTitulo.setInputType(InputType.TYPE_NULL)
            editFecha.setInputType(InputType.TYPE_NULL)
            editHora.setInputType(InputType.TYPE_NULL)
            editDesc.setInputType(InputType.TYPE_NULL)
            editCom.setInputType(InputType.TYPE_NULL)
        }

        btnEditar.setOnClickListener {
            val intent = Intent(this, EditarActivity::class.java)
            intent.putExtra("ID", id)
            startActivity(intent)
        }

        btnEliminar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("¿Está seguro/a de eliminar este evento?")
                    .setPositiveButton("SI") { dialogInterface, i ->
                        recordatoriosDel = dbRecordatorios.consultarRecordatorios(id)
                        for (recordatorio in recordatoriosDel) { //CANCELAMOS ALARMAS EN CASO DE QUE SIGAN ACTIVAS.
                            this@VerActivity.eliminarAlarma(recordatorio.getIdRecordatorio(),this@VerActivity)
                        }
                        if (dbEventos.eliminarEvento(id) && dbRecordatorios.eliminarRecordatorios(id)) {
                            lista()
                        }}
                    .setNegativeButton("NO") { dialogInterface, i -> }.show()
        }
    }

    private fun lista() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun eliminarAlarma(i: Int, ctx: Context) {
        val alarmManager = ctx.getSystemService(ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(ctx, AlarmReceiver::class.java)
        val pendingIntent: PendingIntent
        pendingIntent = PendingIntent.getBroadcast(ctx, i, alarmIntent, PendingIntent.FLAG_ONE_SHOT)
        alarmManager.cancel(pendingIntent)
    }
}