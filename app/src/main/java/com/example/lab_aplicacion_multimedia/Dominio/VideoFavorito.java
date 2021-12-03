package com.example.lab_aplicacion_multimedia.Dominio;

import android.content.Context;

import com.example.lab_aplicacion_multimedia.Persistencia.VideosFavoritosDAO;

public class VideoFavorito {

    private String id_video_favorito_list;
    private String id_usuario;
    private String id_video;

    private VideosFavoritosDAO gestor_videos_favoritos = new VideosFavoritosDAO();

    /**
     *
     * Descripcion: Constructor con los parametros que debe tener cada lista de videos favoritos
     *
     * @param id_video_favorito_list
     * @param id_usuario
     * @param id_video
     */
    public VideoFavorito(String id_video_favorito_list, String id_usuario, String id_video){

        this.id_video_favorito_list = id_video_favorito_list;
        this.id_usuario = id_usuario;
        this.id_video = id_video;
    }

    /**
     *
     * Descripcion: Constructor para hacer consultas
     *
     */
    public VideoFavorito(){
    }

    /**
     *
     * @param context
     * @param nombre_usuario
     * @param id_video
     */
    public void eliminarVideoFavoritosBBDD(Context context, String nombre_usuario, String id_video){
        gestor_videos_favoritos.eliminarVideoFavoritos(context, nombre_usuario, id_video);
    }

    /**
     *
     * @param context
     * @param id_usuario
     * @return
     */
    public int getNumeroVideosUsuarioBBDD(Context context, String id_usuario) {
        return gestor_videos_favoritos.getNumeroVideosUsuario(context, id_usuario);
    }

    /**
     *
     * @param context
     * @param id_usuario
     * @param index
     * @return
     */
    public String [] getListaVideosFavoritosBBDD(Context context, String id_usuario, int index){
        return gestor_videos_favoritos.getListaVideosFavoritos(context, id_usuario, index);
    }

    /**
     *
     * @param context
     * @param id_video
     * @param nombre_usuario
     * @return
     */
    public int buscarVideoRegistradoBBDD(Context context, String id_video, String nombre_usuario) {
        return gestor_videos_favoritos.buscarVideoRegistrado(context, id_video, nombre_usuario);
    }

    /**
     *
     * @param context
     * @param id_usuario
     * @param id_video
     */
    public void insertarDatosTablaVideosFavoritosBBDD(Context context, String id_usuario, String id_video){
        gestor_videos_favoritos.insertarDatosTablaVideosFavoritos(context, id_usuario, id_video);
    }

    /**
     *
     * @return identificador de la playlist en la aplicacion
     */
    public String getIdVideosFavoritos(){
        return this.id_video_favorito_list;
    }

    /**
     *
     * @param nvf modificacion y asignacion id a la playlist
     */
    public void setIdVideosFavoritos(String nvf){
        this.id_video_favorito_list = nvf;
    }

    /**
     *
     * @return identificador del usuario al que pertenece esa video en la aplicacion
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
     * @return identificador del video de la playlist en la aplicacion
     */
    public String getIdVideo(){
        return this.id_video;
    }

    /**
     *
     * @param nv modificacion y asignacion nuevo id del video a la playlist
     */
    public void setIdVideo(String nv){
        this.id_video = nv;
    }
}
