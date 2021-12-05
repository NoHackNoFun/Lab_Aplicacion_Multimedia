package com.example.lab_aplicacion_multimedia.Dominio;

import android.content.ComponentName;
import android.content.Context;

import com.example.lab_aplicacion_multimedia.Persistencia.CancionesFavoritasDAO;

public class CancionFavorita {

    private String id_cancion_favorita_list;
    private String id_usuario;
    private String id_cancion;

    private CancionesFavoritasDAO gestor_canciones_favoritas = new CancionesFavoritasDAO();

    /**
     *
     * Descripcion: Constructor con los parametros que debe tener cada lista de canciones favoritas
     *
     * @param id_cancion_favorita_list
     * @param id_usuario
     * @param id_cancion
     */
    public CancionFavorita(String id_cancion_favorita_list, String id_usuario, String id_cancion){

        this.id_cancion_favorita_list = id_cancion_favorita_list;
        this.id_usuario = id_usuario;
        this.id_cancion = id_cancion;
    }

    /**
     *
     * Descripcion: Constructor para hacer consultas
     *
     */
    public CancionFavorita(){
    }

    /**
     *
     * @param context
     * @param id_cancion
     * @param nombre_usuario
     * @return
     */
    public int buscarCancionesRegistradaBBDD(Context context, String id_cancion, String nombre_usuario) {
        return gestor_canciones_favoritas.buscarCancionesRegistrada(context, id_cancion, nombre_usuario);
    }

    /**
     *
     * @param context
     * @param id_usuario
     * @param id_cancion
     */
    public void insertarDatosTablaCancionesFavoritasBBDD(Context context, String id_usuario, String id_cancion){
        gestor_canciones_favoritas.insertarDatosTablaCancionesFavoritas(context, id_usuario, id_cancion);
    }

    /**
     *
     * @param context
     * @param id_usuario
     * @return
     */
    public int getNumeroCancionesUsuarioBBDD(Context context, String id_usuario) {
        return gestor_canciones_favoritas.getNumeroCancionesUsuario(context, id_usuario);
    }

    /**
     *
     * @param context
     * @param id_usuario
     * @param index
     * @return
     */
    public String [] getListaCancionesFavoritasBBDD(Context context, String id_usuario, int index){
        return gestor_canciones_favoritas.getListaCancionesFavoritas(context, id_usuario, index);
    }

    /**
     *
     * @param context
     * @param nombre_usuario
     * @param id_cancion
     */
    public void eliminarCancionesFavoritasBBDD(Context context, String nombre_usuario, String id_cancion){
        gestor_canciones_favoritas.eliminarCancionesFavoritas(context, nombre_usuario, id_cancion);
    }

    /**
     *
     * @return identificador de la playlist en la aplicacion
     */
    public String getIdCancionesFavoritas(){
        return this.id_cancion_favorita_list;
    }

    /**
     *
     * @param ncf modificacion y asignacion id a la playlist
     */
    public void setIdFotosFavoritas(String ncf){
        this.id_cancion_favorita_list = ncf;
    }

    /**
     *
     * @return identificador del usuario al que pertenece esa cancion en la aplicacion
     */
    public String getIdUsuario(){
        return this.id_usuario;
    }

    /**
     *
     * @param niu modificacion y asignacion nuevo id del usuario a la playlist
     */
    public void setIdUsuario(String niu){
        this.id_usuario = niu;
    }

    /**
     *
     * @return identificador de la cancion de la playlist en la aplicacion
     */
    public String getIdCancion(){
        return this.id_cancion;
    }

    /**
     *
     * @param nc modificacion y asignacion nuevo id de la cancion a la playlist
     */
    public void setIdCancion(String nc){
        this.id_cancion = nc;
    }

}
