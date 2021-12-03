package com.example.lab_aplicacion_multimedia.Persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.lab_aplicacion_multimedia.Constantes.Constantes;

public class FotosFavoritasDAO {

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
    public void crearTablaFotosFavoritas(Context context){

        SQLiteDatabase db = this.getConnWrite(context);
        db.execSQL(Constantes.CREAR_TABLA_FOTO_FAVORITA);
    }

    /**
     *
     * Descripcion: Metodo que permite obtener el numero de determinadas fotos
     *
     * @param context
     * @param id_foto
     * @return
     */
    public int buscarFotoRegistrada(Context context, String id_foto, String nombre_usuario) {

        String countQuery = "SELECT FotoFavoritaNombreUsuario FROM " + Constantes.NOMBRE_TABLA_FOTOS_FAVORITAS_BBDD+
                " WHERE FotoFavoritaIdFoto='"+id_foto+"' AND FotoFavoritaNombreUsuario='"+nombre_usuario+"'";

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
     * @param id_foto
     */
    public void insertarDatosTablaFotosFavoritas(Context context, String id_usuario, String id_foto){

        SQLiteDatabase db = this.getConnWrite(context);

        String sql = "INSERT INTO FotosFavoritas VALUES (?,?,?)";

        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, "0000");
        statement.bindString(2, id_usuario);
        statement.bindString(3, id_foto);

        statement.executeInsert();
        db.close();
    }

    /**
     *
     * Descripcion: Metodo que permite obtener el numero de fotos que un usuario tiene asociado
     *
     * @param context
     * @param id_usuario
     * @return
     */
    public int getNumeroFotosUsuario(Context context, String id_usuario) {

        String countQuery = "SELECT FotoFavoritaIdFoto FROM " + Constantes.NOMBRE_TABLA_FOTOS_FAVORITAS_BBDD+
                " WHERE FotoFavoritaNombreUsuario='"+id_usuario+"'";

        SQLiteDatabase db = this.getConnRead(context);
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        return count;
    }

    /**
     *
     * Descripcion: Metodo que permite obtener los identificadores de las fotos que tiene asociado
     * un determinado usuario
     *
     * @param context
     * @param id_usuario
     * @param index
     * @return
     */
    public String [] getListaFotosFavoritas(Context context, String id_usuario, int index){

        String [] id_fotos = new String[index];
        int i = 0;

        String countQuery = "SELECT FotoFavoritaIdFoto FROM " + Constantes.NOMBRE_TABLA_FOTOS_FAVORITAS_BBDD+
                " WHERE FotoFavoritaNombreUsuario='"+id_usuario+"'";

        SQLiteDatabase db = this.getConnRead(context);
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            id_fotos[i] = cursor.getString(cursor.getColumnIndex("FotoFavoritaIdFoto"));
            i = i + 1;

            cursor.moveToNext();
        }

        db.close();
        return id_fotos;
    }

    /**
     *
     * Descripcion: Metodo que permite elimanar las fotos que un usuario tiene asociado al eliminar
     * sus datos del sistema
     *
     * @param context
     * @param nombre_usuario
     * @param id_foto
     */
    public void eliminarFotoFavoritos(Context context, String nombre_usuario, String id_foto){

        int resultado_consulta = -1;
        SQLiteDatabase db = this.getConnWrite(context);

        String eliminar_usuario_sql = "DELETE FROM "+ Constantes.NOMBRE_TABLA_FOTOS_FAVORITAS_BBDD+
                " WHERE FotoFavoritaNombreUsuario='"+nombre_usuario+"' AND FotoFavoritaIdFoto='"+id_foto+"'";

        try {

            db.execSQL(eliminar_usuario_sql);
            resultado_consulta = 1;

        } catch (Exception e) {
            Log.d("Debug_Excepcion", "Se ha producido un error al realizar la consulta");
        }
        db.close();
    }
}
