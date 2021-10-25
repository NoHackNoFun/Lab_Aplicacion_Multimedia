package com.example.lab_aplicacion_multimedia.Persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.lab_aplicacion_multimedia.Constantes.Constantes;
import com.example.lab_aplicacion_multimedia.Dominio.Usuario;

public class UsuarioDAO {

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
     * Descripcion: Permite crear la tabla Usuario
     *
     * @param context
     */
    public void crearTablaUsuario(Context context){

        SQLiteDatabase db = this.getConnWrite(context);
        db.execSQL(Constantes.CREAR_TABLA_USUARIO);

    }

    /**
     *
     * Descripcion: Metodo que permite borrar la tabla de usuarios entera
     *
     * @param context ventana
     * @return resultado_resultado_consulta comprueba si la consulta se ha realizado correctamente
     */
    public int borrarTablaUsuario(Context context){

        int resultado_consulta = -1;
        SQLiteDatabase db = this.getConnWrite(context);

        String borrar_tabla_usuario_sql = "DROP TABLE Usuario";

        try{

            db.execSQL(borrar_tabla_usuario_sql);
            resultado_consulta = 1;

        } catch (Exception e) {
            Log.d("Debug_Excepcion","Se ha producido un error al realizar la consulta");
        }

        db.close();

        return resultado_consulta;
    }

    /**
     *
     * Descripcion: Metodo para insertar un usuario registrado en la base de datos de la aplicacion
     *
     * @param context ventana
     * @param usuario contiene los datos de los campos
     * @param imagen imagen asociada al perfil del usuario
     */
    public void insertarDatosTablaUsuario(Context context, Usuario usuario, byte [] imagen){

        SQLiteDatabase db = this.getConnWrite(context);

        byte[] data = imagen;

        String sql = "INSERT INTO Usuario VALUES (?,?,?,?,?,?)";

        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, usuario.getNombreUsario_pk());
        statement.bindString(2, usuario.getPassword());
        statement.bindString(3, usuario.getTelefono());
        statement.bindString(4, usuario.getCorreoElectronico());
        statement.bindString(5, usuario.getFechaNacimiento());
        statement.bindBlob(6, imagen);

        statement.executeInsert();

        db.close();

    }

    /**
     *
     * Descripcion: Metodo para conocer datos de los usuarios. Se utiliza por ejemplo para saber
     * si el usuario esta registrado en el sistema
     *
     * @param context ventana
     * @param nombre_usuario nombre del usuario del que se quiere comprobar algun dato si esta en el sistema (clave primaria)
     * @param parametro nombre del campo del dato que se quiere buscar
     * @return el dato del campo que queremos conocer
     */
    public String buscarDatosUsuarioRegistrado(Context context, String nombre_usuario, String parametro){

        String [] clave_primaria = new String[1];
        String [] parametro_buscado = new String [1];

        clave_primaria [0] = nombre_usuario;
        parametro_buscado [0] = parametro;

        String dato_buscado = null;
        SQLiteDatabase db = this.getConnRead(context);

        try {

            Cursor cursor = db.query(Constantes.NOMBRE_TABLA_USARIO_BBDD, parametro_buscado,
                    Constantes.CAMPO_USUARIO_NOMBRE_USUARIO+"=?",
                    clave_primaria, null,null,null);

            cursor.moveToFirst();
            dato_buscado = cursor.getString(0);
            cursor.close();

        } catch (Exception e) {
            Log.d("Debug_Excepcion", "Se ha producido un error al realizar la consulta");
        }

        db.close();

        return dato_buscado;
    }
}
