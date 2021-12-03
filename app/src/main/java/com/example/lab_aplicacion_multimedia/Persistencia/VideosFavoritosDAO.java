package com.example.lab_aplicacion_multimedia.Persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.lab_aplicacion_multimedia.Constantes.Constantes;

public class VideosFavoritosDAO {

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
     * Descripcion: Metodo que permite crear la tabla FotosFavoritas
     *
     * @param context
     */
    public void crearTablaVideosFavoritas(Context context){

        SQLiteDatabase db = this.getConnWrite(context);
        db.execSQL(Constantes.CREAR_TABLA_VIDEO_FAVORITO);
    }

    /**
     *
     * Descripcion: Metodo que permite elimanar los videos que un usuario tiene asociado al eliminar
     * sus datos del sistema
     *
     * @param context
     * @param nombre_usuario
     * @param id_video
     */
    public void eliminarVideoFavoritos(Context context, String nombre_usuario, String id_video){

        int resultado_consulta = -1;
        SQLiteDatabase db = this.getConnWrite(context);

        String eliminar_video_sql = "DELETE FROM "+ Constantes.NOMBRE_TABLA_VIDEOS_FAVORITOS_BBDD+
                " WHERE VideoFavoritaNombreUsuario='"+nombre_usuario+"' AND VideoFavoritoIdVideo='"+id_video+"'";

        try {

            db.execSQL(eliminar_video_sql);
            resultado_consulta = 1;

        } catch (Exception e) {
            Log.d("Debug_Excepcion", "Se ha producido un error al realizar la consulta");
        }
        db.close();
    }

    /**
     *
     * Descripcion: Metodo que permite obtener el numero de videos que un usuario tiene asociado
     *
     * @param context
     * @param id_usuario
     * @return
     */
    public int getNumeroVideosUsuario(Context context, String id_usuario) {

        String countQuery = "SELECT VideoFavoritoIdVideo FROM " + Constantes.NOMBRE_TABLA_VIDEOS_FAVORITOS_BBDD+
                " WHERE VideoFavoritaNombreUsuario='"+id_usuario+"'";

        SQLiteDatabase db = this.getConnRead(context);
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        return count;
    }

    /**
     *
     * Descripcion: Metodo que permite obtener los identificadores de los videos que tiene asociado
     * un determinado usuario
     *
     * @param context
     * @param id_usuario
     * @param index
     * @return
     */
    public String [] getListaVideosFavoritos(Context context, String id_usuario, int index){

        String [] id_videos = new String[index];
        int i = 0;

        String countQuery = "SELECT VideoFavoritoIdVideo FROM " + Constantes.NOMBRE_TABLA_VIDEOS_FAVORITOS_BBDD+
                " WHERE VideoFavoritaNombreUsuario='"+id_usuario+"'";

        SQLiteDatabase db = this.getConnRead(context);
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            id_videos[i] = cursor.getString(cursor.getColumnIndex("VideoFavoritoIdVideo"));
            i = i + 1;

            cursor.moveToNext();
        }

        db.close();
        return id_videos;
    }

    /**
     *
     * Descripcion: Metodo que permite obtener el numero de determinados videos
     *
     * @param context
     * @param id_video
     * @return
     */
    public int buscarVideoRegistrado(Context context, String id_video, String nombre_usuario) {

        String countQuery = "SELECT VideoFavoritaNombreUsuario FROM " + Constantes.NOMBRE_TABLA_VIDEOS_FAVORITOS_BBDD+
                " WHERE VideoFavoritoIdVideo='"+id_video+"' AND VideoFavoritaNombreUsuario='"+nombre_usuario+"'";

        SQLiteDatabase db = this.getConnRead(context);
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        return count;
    }

    /**
     *
     * Descripcion: Metodo que permite insertar un video en nuestra playlist
     *
     * @param context
     * @param id_usuario
     * @param id_video
     */
    public void insertarDatosTablaVideosFavoritos(Context context, String id_usuario, String id_video){

        SQLiteDatabase db = this.getConnWrite(context);

        String sql = "INSERT INTO VideosFavoritos VALUES (?,?,?)";

        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, "1111");
        statement.bindString(2, id_usuario);
        statement.bindString(3, id_video);

        statement.executeInsert();
        db.close();
    }
}
