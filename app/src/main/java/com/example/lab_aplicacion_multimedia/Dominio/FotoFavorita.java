package com.example.lab_aplicacion_multimedia.Dominio;

import android.content.Context;

import com.example.lab_aplicacion_multimedia.Persistencia.FotosFavoritasDAO;

public class FotoFavorita {

    private String id_foto_favorita_list;
    private String id_usuario;
    private String id_foto;

    private FotosFavoritasDAO gestor_fotos_favoritas = new FotosFavoritasDAO();

    /**
     *
     * Descripcion: Constructor con los parametros que debe tener cada lista de fotos favoritas
     *
     * @param id_foto_favorita_list
     * @param id_usuario
     * @param id_foto
     */
    public FotoFavorita(String id_foto_favorita_list, String id_usuario, String id_foto){

        this.id_foto_favorita_list = id_foto_favorita_list;
        this.id_usuario = id_usuario;
        this.id_foto = id_foto;
    }

    /**
     *
     * Descripcion: Constructor para hacer consultas
     *
     */
    public FotoFavorita(){
    }

    /**
     *
     * @param context
     * @param id_foto
     * @param nombre_usuario
     * @return
     */
    public int buscarFotoRegistradaBBDD(Context context, String id_foto, String nombre_usuario) {
        return gestor_fotos_favoritas.buscarFotoRegistrada(context, id_foto, nombre_usuario);
    }

    /**
     *
     * @param context
     * @param id_usuario
     * @param id_foto
     */
    public void insertarDatosTablaFotosFavoritasBBDD(Context context, String id_usuario, String id_foto){
        gestor_fotos_favoritas.insertarDatosTablaFotosFavoritas(context, id_usuario, id_foto);
    }

    /**
     *
     * @param context
     * @param id_usuario
     * @return
     */
    public int getNumeroFotosUsuarioBBDD(Context context, String id_usuario) {
        return gestor_fotos_favoritas.getNumeroFotosUsuario(context, id_usuario);
    }

    /**
     *
     * @param context
     * @param id_usuario
     * @param index
     * @return
     */
    public String [] getListaFotosFavoritas(Context context, String id_usuario, int index){
        return gestor_fotos_favoritas.getListaFotosFavoritas(context, id_usuario, index);
    }

    /**
     *
     * @param context
     * @param nombre_usuario
     * @param id_foto
     */
    public void eliminarFotoFavoritosBBDD(Context context, String nombre_usuario, String id_foto){
        gestor_fotos_favoritas.eliminarFotoFavoritos(context, nombre_usuario, id_foto);
    }

    /**
     *
     * @return identificador de la playlist en la aplicacion
     */
    public String getIdFotosFavoritas(){
        return this.id_foto_favorita_list;
    }

    /**
     *
     * @param nff modificacion y asignacion id a la playlist
     */
    public void setIdFotosFavoritas(String nff){
        this.id_foto_favorita_list = nff;
    }

    /**
     *
     * @return identificador del usuario al que pertenece esa foto en la aplicacion
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
     * @return identificador de la foto de la playlist en la aplicacion
     */
    public String getIdFoto(){
        return this.id_foto;
    }

    /**
     *
     * @param nf modificacion y asignacion nuevo id de la foto a la playlist
     */
    public void setIdFoto(String nf){
        this.id_foto = nf;
    }
}
