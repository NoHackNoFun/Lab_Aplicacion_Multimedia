package com.example.lab_aplicacion_multimedia.Dominio;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.lab_aplicacion_multimedia.Persistencia.VideoDAO;

public class Video {

    private String id_video;
    private String nombre_video;
    private String descripcion_video;
    private Bitmap miniatura_video;

    private VideoDAO gestor_videos = new VideoDAO();

    /**
     *
     * Descripcion: Constructor con los parametros que debe tener cada video en la aplicacion
     *
     * @param id_video
     * @param nombre_video
     * @param descripcion_video
     * @param miniatura_video
     */
    public Video(String id_video, String nombre_video, String descripcion_video, Bitmap miniatura_video){

        this.id_video = id_video;
        this.nombre_video = nombre_video;
        this.descripcion_video = descripcion_video;
        this.miniatura_video = miniatura_video;
    }

    /**
     *
     * Descripcion: Constructor para hacer consultas
     *
     */
    public Video(){
    }

    /**
     *
     * @param context
     * @param id_video
     * @param parametro
     * @return
     */
    public String buscarDatosVideoBBDD(Context context, String id_video, String parametro){
        return gestor_videos.buscarDatosVideo(context, id_video, parametro);
    }

    /**
     *
     * @param context
     * @return
     */
    public int getNumeroTotalVideosBBDD(Context context) {
        return gestor_videos.getNumeroTotalVideos(context);
    }

    /**
     *
     * @param context
     * @param index
     * @return
     */
    public String [] getListaVideosBBDD(Context context, int index){
        return gestor_videos.getListaVideos(context, index);
    }

    /**
     *
     * @param context
     * @param id_video
     * @param parametro
     * @return
     */
    public Bitmap buscarMiniaturaVideoBBDD(Context context, String id_video, String parametro){
        return gestor_videos.buscarMiniaturaVideo(context, id_video, parametro);
    }

    /**
     *
     * @return identificador del video en la aplicacion
     */
    public String getIdVideo(){
        return this.id_video;
    }

    /**
     *
     * @param nidv modificacion y asignadion nuevo id al video
     */
    public void setIdVideo(String nidv){
        this.id_video = nidv;
    }

    /**
     *
     * @return nombre del video en la aplicacion
     */
    public String getNombreVideo(){
        return this.nombre_video;
    }

    /**
     *
     * @param nnv modificacion y asignacion nuevo nombre al video
     */
    public void setNombreVideo(String nnv){
        this.nombre_video = nnv;
    }

    /**
     *
     * @return descripcion del video en la aplicacion
     */
    public String getDescripcionVideo(){
        return this.descripcion_video;
    }

    /**
     *
     * @param ndv modificacion y asignacion nueva descripcion al video
     */
    public void setDescripcionVideo(String ndv){
        this.descripcion_video = ndv;
    }

    /**
     *
     * @return imagen del video en la aplicacion
     */
    public Bitmap getMiniaturaVideo(){
        return this.miniatura_video;
    }

    /**
     *
     * @param nmv modificacion y asignacion de la nueva miniatura del video
     */
    public void setMiniaturaVideo(Bitmap nmv){
        this.miniatura_video = nmv;
    }
}
