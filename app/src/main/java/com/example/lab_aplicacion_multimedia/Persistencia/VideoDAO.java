package com.example.lab_aplicacion_multimedia.Persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab_aplicacion_multimedia.Constantes.Constantes;

public class VideoDAO {

    /**
     *
     * Descipcion: Sirve para obtener la conexion (escritura)
     *
     * @param context
     * @return db
     */
    public SQLiteDatabase getConnWrite(Context context) {

        //Cada vez que se borre una tabla se cambia la version

        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(context,
                "dbProyectoMultimedia", null, 4);
        SQLiteDatabase db = conexion.getWritableDatabase();

        return db;
    }

    /**
     *
     * Descipcion: Sirve para obtener la conexion (lectura)
     *
     * @param context
     * @return db
     */
    public SQLiteDatabase getConnRead(Context context) {

        //Cada vez que se borre una tabla se cambia la version

        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(context,
                "dbProyectoMultimedia", null, 4);
        SQLiteDatabase db = conexion.getReadableDatabase();

        return db;
    }

    /**
     *
     * Descripcion: Metodo que permite crear la tabla Video
     *
     * @param context
     */
    public void crearTablaVideo(Context context){

        SQLiteDatabase db = this.getConnWrite(context);
        db.execSQL(Constantes.CREAR_TABLA_VIDEO);
    }
}
