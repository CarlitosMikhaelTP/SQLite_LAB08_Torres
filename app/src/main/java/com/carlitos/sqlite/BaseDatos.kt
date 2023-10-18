package com.carlitos.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

var BD = "baseDatos"

class BaseDatos(contexto: Context):SQLiteOpenHelper(contexto,BD,null,1) {

    override fun onCreate(db: SQLiteDatabase?) {
        var sql = "CREATE TABLE IF NOT EXISTS Usuario(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre VARCHAR(250), edad INTEGER)"

        db?.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVertsion: Int) {
        TODO("Not yet implemented")
    }

    fun insertarDatos(usuario:Usuario):String {
        val db = this.writableDatabase
        var contenedorValores = ContentValues()

        contenedorValores.put("nombre", usuario.nombre)
        contenedorValores.put("edad", usuario.edad)

        var resultado = db.insert("Usuario", null, contenedorValores)

        if(resultado == -1.toLong()){
            return "Insercion Fallida"
        }else{
            return "Datos Insertados (ok)"
        }
    }


    fun listarDatos():MutableList<Usuario>{
        var lista:MutableList<Usuario> = ArrayList()
        val db = this.readableDatabase

        val sql = "SELECT * FROM Usuario"
        var resultado = db?.rawQuery (sql, null)

        if (resultado != null && resultado.moveToFirst()) {
            do {
                var usu = Usuario()
                usu.id = resultado.getString(resultado.getColumnIndexOrThrow("id")).toInt()
                usu.nombre = resultado.getString(resultado.getColumnIndexOrThrow("nombre"))
                usu.edad = resultado.getString(resultado.getColumnIndexOrThrow("edad")).toInt()

                lista.add(usu)

            } while (resultado.moveToNext())

            resultado.close()
            db.close()
        }

        return lista

    }

    fun actualizarDatos(id:String, nombre:String, edad:Int):String{
        val db = this.writableDatabase
        var contenedorValores = ContentValues()
        contenedorValores.put("nombre", nombre)
        contenedorValores.put("edad", edad)

        var resultado = db.update("Usuario", contenedorValores, "id=?", arrayOf(id))

        db.close()

        if(resultado > 0){
            return "Actualizacion Realizada"
        }else{
            return "Error en Actualizacion"
        }

    }

    fun eliminarDatos(id:String):Int{
        val db = this.writableDatabase
        var resultado = 0
        if(id.length > 0) {
            resultado = db.delete("Usuario", "id=?", arrayOf(id))
            db.close()
        }
        return resultado
    }




}
