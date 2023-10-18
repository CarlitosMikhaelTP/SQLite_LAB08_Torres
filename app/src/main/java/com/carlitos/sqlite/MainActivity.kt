package com.carlitos.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var nombre:EditText
    lateinit var edad:EditText
    lateinit var codigo:EditText
    lateinit var mensaje: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nombre = findViewById(R.id.txtNombre)
        edad = findViewById(R.id.txtEdad)
        codigo = findViewById(R.id.txtCodigo)
        mensaje = findViewById(R.id.txtLista)

        verDatos()
    }

    fun verDatos(){
        var db = BaseDatos(this)
        var datos = db.listarDatos()
        mensaje.setText("")
        for(i in 0..datos.size-1){
            mensaje.append(" Codigo: " + datos.get(i).id.toString() + " Nombre: " +
                    datos.get(i).nombre + " Edad: " + datos.get(i).edad.toString() + "\n")
        }
    }

    fun GuardarDatos(view:View){
        var db = BaseDatos(this)
        var usu = Usuario()
        var resultado:String = ""
        if(nombre.text.toString().length>0 && edad.text.toString().length>0){
            usu.nombre = nombre.text.toString()
            usu.edad = edad.text.toString().toInt()
            resultado = db.insertarDatos(usu)

            if(resultado>""){
                Toast.makeText(this, "Se Guardo el Nombre: " + nombre.text.toString(), Toast.LENGTH_LONG).show()
                nombre.setText("")
                edad.setText("")
            }else{
                Toast.makeText(this, "Error al Guardar el Nombre: " + nombre.text.toString(), Toast.LENGTH_LONG).show()
            }
            verDatos()
        }
    }


    fun BorrarDatos(view: View){
        var db = BaseDatos(this)
        var resultado:Int = 0
        if(codigo.text.toString().length>0){
            resultado = db.eliminarDatos(codigo.text.toString())
            if(resultado>0){
                Toast.makeText(this, "Se Eliminó el Codigo: " + codigo.text.toString(), Toast.LENGTH_LONG).show()
                codigo.setText("")
            }else{
                Toast.makeText(this, "Error al eliminar el Codigo: " + codigo.text.toString(), Toast.LENGTH_LONG).show()
            }
            verDatos()
        }
    }

    fun actualizarDatos(view: View){
        var db = BaseDatos(this)
        var resultado:String = ""
        if(codigo.text.toString().length>0){
            resultado = db.actualizarDatos(codigo.text.toString(), nombre.text.toString(), edad.text.toString().toInt())
            if(resultado != ""){
                Toast.makeText(this, "Se actualizo el Codigo: " + codigo.text.toString(), Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "Error al actualizar el Codigo: " + codigo.text.toString(), Toast.LENGTH_LONG).show()
            }
            verDatos()
        }
    }

}
