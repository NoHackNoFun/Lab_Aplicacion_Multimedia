package com.example.lab_aplicacion_multimedia.Persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.lab_aplicacion_multimedia.Constantes.Constantes;

public class CancionesFavoritasDAO {

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
     * Descripcion: Metodo que permite crear la tabla CancionesFavoritas
     *
     * @param context
     */
    public void crearTablaCancionesFavoritas(Context context){

        SQLiteDatabase db = this.getConnWrite(context);
        db.execSQL(Constantes.CREAR_TABLA_CANCION_FAVORITA);
    }

    /**
     *
     * Descripcion: Metodo que permite obtener el numero de determinadas canciones
     *
     * @param context
     * @param id_cancion
     * @return
     */
    public int buscarCancionesRegistrada(Context context, String id_cancion, String nombre_usuario) {

        String countQuery = "SELECT CancionFavoritaNombreUsuario FROM " + Constantes.NOMBRE_TABLA_CANCIONES_FAVORITAS_BBDD+
                " WHERE CancionFavoritoIdCancion='"+id_cancion+"' AND CancionFavoritaNombreUsuario='"+nombre_usuario+"'";

        SQLiteDatabase db = this.getConnRead(context);
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        return count;
    }

    /**
     *
     * Descripcion: Metodo que permite insertar una cancion en nuestra playlist
     *
     * @param context
     * @param id_usuario
     * @param id_cancion
     */
    public void insertarDatosTablaCancionesFavoritas(Context context, String id_usuario, String id_cancion){

        SQLiteDatabase db = this.getConnWrite(context);

        String sql = "INSERT INTO CancionesFavoritas VALUES (?,?,?)";

        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, "2222");
        statement.bindString(2, id_usuario);
        statement.bindString(3, id_cancion);

        statement.executeInsert();
        db.close();
    }

    /**
     *
     * Descripcion: Metodo que permite elimanar las canciones que un usuario tiene asociado al eliminar
     * sus datos del sistema
     *
     * @param context
     * @param nombre_usuario
     * @param id_cancion
     */
    public void eliminarCancionesFavoritas(Context context, String nombre_usuario, String id_cancion){

        int resultado_consulta = -1;
        SQLiteDatabase db = this.getConnWrite(context);

        String eliminar_cancion_sql = "DELETE FROM "+ Constantes.NOMBRE_TABLA_CANCIONES_FAVORITAS_BBDD+
                " WHERE CancionFavoritaNombreUsuario='"+nombre_usuario+"' AND CancionFavoritoIdCancion='"+id_cancion+"'";

        try {

            db.execSQL(eliminar_cancion_sql);
            resultado_consulta = 1;

        } catch (Exception e) {
            Log.d("Debug_Excepcion", "Se ha producido un error al realizar la consulta");
        }
        db.close();
    }

    /**
     *
     * Descripcion: Metodo que permite obtener los identificadores de las canciones que tiene asociado
     * un determinado usuario
     *
     * @param context
     * @param id_usuario
     * @param index
     * @return
     */
    public String [] getListaCancionesFavoritas(Context context, String id_usuario, int index){

        String [] id_canciones = new String[index];
        int i = 0;

        String countQuery = "SELECT CancionFavoritoIdCancion FROM " + Constantes.NOMBRE_TABLA_CANCIONES_FAVORITAS_BBDD+
                " WHERE CancionFavoritaNombreUsuario='"+id_usuario+"'";

        SQLiteDatabase db = this.getConnRead(context);
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            id_canciones[i] = cursor.getString(cursor.getColumnIndex("CancionFavoritoIdCancion"));
            i = i + 1;

            cursor.moveToNext();
        }

        db.close();
        return id_canciones;
    }

    /**
     *
     * Descripcion: Metodo que permite obtener el numero de canciones que un usuario tiene asociado
     *
     * @param context
     * @param id_usuario
     * @return
     */
    public int getNumeroCancionesUsuario(Context context, String id_usuario) {

        String countQuery = "SELECT CancionFavoritoIdCancion FROM " + Constantes.NOMBRE_TABLA_CANCIONES_FAVORITAS_BBDD+
                " WHERE CancionFavoritaNombreUsuario='"+id_usuario+"'";

        SQLiteDatabase db = this.getConnRead(context);
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        return count;
    }
}
