package com.example.lab_aplicacion_multimedia.Adaptadores;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_aplicacion_multimedia.Dominio.Video;
import com.example.lab_aplicacion_multimedia.Dominio.VideoFavorito;
import com.example.lab_aplicacion_multimedia.Interfaz.OnItemSelectedListener;
import com.example.lab_aplicacion_multimedia.Presentacion.ventana_menu_principal;
import com.example.lab_aplicacion_multimedia.R;

import java.util.ArrayList;

/**
 *
 * Descripcion: Clase AdaptadorListaVideoFav para la lista personalizada de un usuario
 *
 */
public class AdaptadorListaVideoFav extends RecyclerView.Adapter<AdaptadorListaVideoFav.ViewHolder> {

    private ArrayList<Video> videos;
    private OnItemSelectedListener itemSelectedListener;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView lblNombreVideo;
        private TextView lblDescripcionVideo;
        private ImageView imgMiniatura;

        ViewHolder(View view) {

            super(view);

            this.lblNombreVideo = view.findViewById(R.id.lblNombreVideo);
            this.lblDescripcionVideo = view.findViewById(R.id.lblDescripcionVideo);
            this.imgMiniatura = view.findViewById(R.id.imgVideo);
        }
    }

    /**
     *
     * Descripcion: Constructor clase AdaptadorListaVideosFavoritos
     *
     * @param videos
     */
    public AdaptadorListaVideoFav(ArrayList<Video> videos){
        this.videos = videos;
    }

    @Override
    public AdaptadorListaVideoFav.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_video, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorListaVideoFav.ViewHolder holder, int position) {

        holder.lblNombreVideo.setText(videos.get(position).getNombreVideo());
        holder.lblDescripcionVideo.setText(videos.get(position).getDescripcionVideo());
        holder.imgMiniatura.setImageBitmap(videos.get(position).getMiniaturaVideo());

        /**
         *
         * Descripcion: Oyente asociado al pulsar un item de la lista
         *
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.lblNombreVideo.getText().toString() != " No disponible"){

                    new AlertDialog.Builder(holder.itemView.getContext())
                            .setTitle("¿Está seguro que desea elimar esta foto de favoritos?")
                            .setMessage("La foto se eliminara de esta playlist.")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    VideoFavorito gestor_videolist = new VideoFavorito();

                                    gestor_videolist.eliminarVideoFavoritosBBDD(holder.itemView.getContext(),
                                            ventana_menu_principal.usuario_sesion_iniciada, videos.get(position).getIdVideo());

                                    Intent menu_inicio = new Intent(holder.itemView.getContext(), ventana_menu_principal.class);
                                    menu_inicio.putExtra("nombre_usuario_registrado",
                                            ventana_menu_principal.usuario_sesion_iniciada);

                                    holder.itemView.getContext().startActivity(menu_inicio);
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
            }
        });
    }

    /**
     *
     * @param itemSelectedListener
     */
    public void setItemSelectedListener(OnItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }
}
