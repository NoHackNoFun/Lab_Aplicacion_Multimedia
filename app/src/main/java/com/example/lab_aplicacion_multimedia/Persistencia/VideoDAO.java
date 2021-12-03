package com.example.lab_aplicacion_multimedia.Persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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

    /**
     *
     * Descripcion: Metodo que permite realizar una consulta para obtener un determinado dato de un
     * video
     *
     * @param context
     * @param id_video
     * @param parametro
     * @return
     */
    public String buscarDatosVideo(Context context, String id_video, String parametro){

        String [] clave_primaria = new String[1];
        String [] parametro_buscado = new String [1];

        clave_primaria [0] = id_video;
        parametro_buscado [0] = parametro;

        String dato_buscado = null;
        SQLiteDatabase db = this.getConnRead(context);

        try {
            Cursor cursor = db.query(Constantes.NOMBRE_TABLA_VIDEO_BBDD, parametro_buscado,
                    Constantes.CAMPO_VIDEO_ID+"=?",clave_primaria, null,null,null);

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
     * Descripcion: Metodo que permite obtner el numero total de videos
     *
     * @param context
     * @return
     */
    public int getNumeroTotalVideos(Context context) {

        String countQuery = "SELECT  * FROM " + Constantes.NOMBRE_TABLA_VIDEO_BBDD;
        SQLiteDatabase db = this.getConnRead(context);
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        return count;
    }

    /**
     *
     * Descripcion: Metodo que permite obtner los identificadores de los videos en el sitema
     *
     * @param context
     * @param index
     * @return
     */
    public String [] getListaVideos(Context context, int index){

        String [] id_videos = new String[index];
        int i = 0;

        String countQuery = "SELECT IdVideo FROM " + Constantes.NOMBRE_TABLA_VIDEO_BBDD;

        SQLiteDatabase db = this.getConnRead(context);
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            id_videos[i] = cursor.getString(cursor.getColumnIndex("IdVideo"));
            i = i + 1;

            cursor.moveToNext();
        }
        db.close();
        return id_videos;
    }

    /**
     *
     * Descripcion: Metodo que permite realizar una consulta para obtener una miniatura
     * dado su clave primaria y el nombre de la columna imagen en la base de datos
     *
     * @param context
     * @param id_video
     * @param parametro
     * @return
     */
    public Bitmap buscarMiniaturaVideo(Context context, String id_video, String parametro){

        String [] clave_primaria = new String[1];
        String [] parametro_buscado = new String [1];
        Bitmap bitmap = null;

        clave_primaria [0] = id_video;
        parametro_buscado [0] = parametro;

        byte [] image = null;
        SQLiteDatabase db = this.getConnRead(context);

        try {

            Cursor cursor = db.query(Constantes.NOMBRE_TABLA_VIDEO_BBDD, parametro_buscado,
                    Constantes.CAMPO_VIDEO_ID+"=?",clave_primaria, null,null,null);

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
