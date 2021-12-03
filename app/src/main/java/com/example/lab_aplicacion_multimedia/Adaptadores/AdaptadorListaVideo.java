package com.example.lab_aplicacion_multimedia.Adaptadores;

import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_aplicacion_multimedia.Dominio.Video;
import com.example.lab_aplicacion_multimedia.Interfaz.OnItemSelectedListener;
import com.example.lab_aplicacion_multimedia.Presentacion.ventana_reproduccion_video;
import com.example.lab_aplicacion_multimedia.R;
import java.util.ArrayList;

/**
 *
 * Descripcion: Clase AdapatadorListaVideo
 *
 */
public class AdaptadorListaVideo extends RecyclerView.Adapter<AdaptadorListaVideo.ViewHolder> {

    private ArrayList<Video> videos;
    private OnItemSelectedListener itemSelectedListener;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView lblNombreVideo;
        private TextView lblDuracionVideo;
        private ImageView imagVideo;

        ViewHolder(View view) {

            super(view);

            lblNombreVideo = view.findViewById(R.id.lblNombreVideo);
            lblDuracionVideo = view.findViewById(R.id.lblDescripcionVideo);
            imagVideo = view.findViewById(R.id.imgVideo);

            /**
             *
             * Descripcion: Permite saber que item se esta seleccionando
             *
             */
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int posicion = getAdapterPosition();
                    if(itemSelectedListener != null){
                        itemSelectedListener.onMultimediaSeleccionado(posicion);
                    }
                }
            });

            //Creación del menú popup

            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.getMenuInflater().inflate(R.menu.menu_contextual, popup.getMenu());
            view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override public void onCreateContextMenu(ContextMenu menu, View v,
                                                          ContextMenu.ContextMenuInfo menuInfo) {
                    popup.show();
                }
            });

            //Oyente de selección de opciones del menú popup

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                @Override public boolean onMenuItemClick(MenuItem item) {
                    if (itemSelectedListener != null) {
                        itemSelectedListener.onMenuContextual(getAdapterPosition(), item);
                    }
                    return true;
                }
            });
        }
    }

    /**
     *
     * Descripcion: Constructor clase AdaptadorListaVideo
     *
     * @param videos
     */
    public AdaptadorListaVideo(ArrayList<Video> videos){
        this.videos = videos;
    }

    @Override
    public AdaptadorListaVideo.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_video, parent,
                false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorListaVideo.ViewHolder holder, int position) {

        holder.lblNombreVideo.setText(videos.get(position).getNombreVideo());
        holder.lblDuracionVideo.setText(videos.get(position).getDescripcionVideo());
        holder.imagVideo.setImageBitmap(videos.get(position).getMiniaturaVideo());

        /**
         *
         * Descripcion: Oyente asociado al pulsar un item de la lista
         *
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.lblNombreVideo.getText().toString() != " No disponible" ||
                        videos.get(position).getMiniaturaVideo() != null){

                    Intent reproducir_video = new Intent(holder.itemView.getContext(),
                            ventana_reproduccion_video.class);
                    reproducir_video.putExtra("identificador_video", videos.get(position).getIdVideo());
                    holder.itemView.getContext().startActivity(reproducir_video);
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
