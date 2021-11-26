package com.example.lab_aplicacion_multimedia.Dominio;

import android.content.Context;
import android.graphics.Bitmap;
import com.example.lab_aplicacion_multimedia.Persistencia.FotoDao;

public class Foto {

    private String id_imagen;
    private String nombre_imagen;
    private String descripcion_imagen;
    private Bitmap bitmap_imagen;

    private FotoDao gestor_fotos = new FotoDao();

    /**
     *
     * Descripcion: Constructor con los parametros que debe tener cada usuario de la aplicacion
     *
     * @param id_imagen
     * @param nombre_imagen
     * @param descripcion_imagen
     * @param bitmap_imagen
     */
    public Foto(String id_imagen, String nombre_imagen, String descripcion_imagen, Bitmap bitmap_imagen){

        this.id_imagen = id_imagen;
        this.nombre_imagen = nombre_imagen;
        this.descripcion_imagen = descripcion_imagen;
        this.bitmap_imagen = bitmap_imagen;
    }

    /**
     *
     * Descripcion: Constructor para hacer consultas
     *
     */
    public Foto(){
    }

    /**
     *
     * @param context
     * @return
     */
    public int getNumeroTotalFotosBBDD(Context context) {
        return gestor_fotos.getNumeroTotalFotos(context);
    }

    /**
     *
     * @param context
     * @param index
     * @return
     */
    public String [] getListaFotosBBDD(Context context, int index){
        return gestor_fotos.getListaFotos(context, index);
    }

    /**
     *
     * @param context
     * @param id_foto
     * @param parametro
     * @return
     */
    public String buscarDatosFotosBBDD(Context context, String id_foto, String parametro){
        return gestor_fotos.buscarDatosFotos(context, id_foto, parametro);
    }

    /**
     *
     * @param context
     * @param id_foto
     * @param parametro
     * @return
     */
    public Bitmap buscarImagenFotoBBDD(Context context, String id_foto, String parametro){
        return gestor_fotos.buscarImagenFoto(context, id_foto, parametro);
    }

    /**
     *
     * @return ID imagen
     */
    public String getIdImagen(){
        return this.id_imagen;
    }

    /**
     *
     * @param n_id modificacion y asigancion del parametro ID foto
     */
    public void setIdImagen(String n_id){
        this.id_imagen = n_id;
    }

    /**
     *
     * @return nombre imagen
     */
    public String getNombreImagen(){
        return this.nombre_imagen;
    }

    /**
     *
     * @param n_ni modificacion y asigancion del parametro nombre foto
     */
    public void setNombreImagen(String n_ni){
        this.nombre_imagen = n_ni;
    }

    /**
     *
     * @return descripcion imagen
     */
    public String getDescripcionImagen(){
        return this.descripcion_imagen;
    }

    /**
     *
     * @param n_di modificacion y asigancion del parametro descripcion foto
     */
    public void setDescripcionImagen(String n_di){
        this.descripcion_imagen = n_di;
    }

    /**
     *
     * @return foto
     */
    public Bitmap getFoto(){
        return this.bitmap_imagen;
    }

    /**
     *
     * @param n_bf modificacion y asignacion del parametro foto
     */
    public void setFoto(Bitmap n_bf){
        this.bitmap_imagen = n_bf;
    }
}
