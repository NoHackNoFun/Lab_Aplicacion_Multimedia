package com.example.lab_aplicacion_multimedia.Persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.lab_aplicacion_multimedia.Constantes.Constantes;

public class FotoDAO {

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
     * Descripcion: Metodo que permite crear la tabla Artista
     *
     * @param context
     */
    public void crearTablaFoto(Context context){

        SQLiteDatabase db = this.getConnWrite(context);
        db.execSQL(Constantes.CREAR_TABLA_FOTO);
    }

    /**
     *
     * Descripcion: Metodo que permite obtner el numero total de fotos
     *
     * @param context
     * @return
     */
    public int getNumeroTotalFotos(Context context) {

        String countQuery = "SELECT  * FROM " + Constantes.NOMBRE_TABLA_FOTO_BBDD;
        SQLiteDatabase db = this.getConnRead(context);
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        return count;
    }

    /**
     *
     * Descripcion: Metodo que permite obtner los identificadores de las fotos en el sitema
     *
     * @param context
     * @param index
     * @return
     */
    public String [] getListaFotos(Context context, int index){

        String [] id_fotos = new String[index];
        int i = 0;

        String countQuery = "SELECT IdFoto FROM " + Constantes.NOMBRE_TABLA_FOTO_BBDD;

        SQLiteDatabase db = this.getConnRead(context);
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            id_fotos[i] = cursor.getString(cursor.getColumnIndex("IdFoto"));
            i = i + 1;

            cursor.moveToNext();
        }
        db.close();
        return id_fotos;
    }

    /**
     *
     * Descripcion: Metodo que permite realizar una consulta para obtener un determinado dato de una
     * foto
     *
     * @param context
     * @param id_foto
     * @param parametro
     * @return
     */
    public String buscarDatosFotos(Context context, String id_foto, String parametro){

        String [] clave_primaria = new String[1];
        String [] parametro_buscado = new String [1];

        clave_primaria [0] = id_foto;
        parametro_buscado [0] = parametro;

        String dato_buscado = null;
        SQLiteDatabase db = this.getConnRead(context);

        try {
            Cursor cursor = db.query(Constantes.NOMBRE_TABLA_FOTO_BBDD, parametro_buscado, Constantes.CAMPO_FOTO_ID+"=?",clave_primaria, null,null,null);

            cursor.moveToFirst();
            dato_buscado = cursor.getString(0);
            cursor.close();

        } catch (Exception e) {
            Log.d("Debug_Excepcion", "Se ha producido un error al realizar la consulta");
        }

        db.close();
        return dato_buscado;
    }

    /**
     *
     * Descripcion: Metodo que permite realizar una consulta para obtener una foto
     * dado su clave primaria y el nombre de la columna imagen en la base de datos
     *
     * @param context
     * @param id_foto
     * @param parametro
     * @return
     */
    public Bitmap buscarImagenFoto(Context context, String id_foto, String parametro){

        String [] clave_primaria = new String[1];
        String [] parametro_buscado = new String [1];
        Bitmap bitmap = null;

        clave_primaria [0] = id_foto;
        parametro_buscado [0] = parametro;

        byte [] image = null;
        SQLiteDatabase db = this.getConnRead(context);

        try {

            Cursor cursor = db.query(Constantes.NOMBRE_TABLA_FOTO_BBDD, parametro_buscado,
                    Constantes.CAMPO_FOTO_ID+"=?",clave_primaria, null,null,null);

            cursor.moveToFirst();
            image = cursor.getBlob(0);
            cursor.close();

            bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        } catch (Exception e) {
            Log.d("Debug_Excepcion", "Se ha producido un error al realizar la consulta");
        }

        db.close();
        return bitmap;
    }
}
