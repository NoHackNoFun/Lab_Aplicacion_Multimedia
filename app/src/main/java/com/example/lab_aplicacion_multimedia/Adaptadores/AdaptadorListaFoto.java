package com.example.lab_aplicacion_multimedia.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.lab_aplicacion_multimedia.Dominio.Foto;
import com.example.lab_aplicacion_multimedia.Interfaz.OnItemSelectedListener;
import com.example.lab_aplicacion_multimedia.R;
import java.util.ArrayList;

public class AdaptadorListaFoto extends RecyclerView.Adapter<AdaptadorListaFoto.ViewHolder> {

    private ArrayList<Foto> fotos;
    private OnItemSelectedListener itemSelectedListener;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView lblNombreFoto;
        private TextView lblDescripcionFoto;
        private ImageView imgFoto;

        ViewHolder(View view) {

            super(view);

            lblNombreFoto = view.findViewById(R.id.lblNombreFoto);
            lblDescripcionFoto = view.findViewById(R.id.lblDescripcionFoto);
            imgFoto = view.findViewById(R.id.imgFoto);
        }
    }

    /**
     *
     * Descripcion: Constructor clase AdaptadorListaFoto
     *
     * @param fotos
     */
    public AdaptadorListaFoto(ArrayList<Foto> fotos){
        this.fotos = fotos;
    }

    @Override
    public AdaptadorListaFoto.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_foto, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorListaFoto.ViewHolder holder, int position) {

        holder.lblNombreFoto.setText(fotos.get(position).getNombreImagen());
        holder.lblDescripcionFoto.setText(fotos.get(position).getDescripcionImagen());
        holder.imgFoto.setImageBitmap(fotos.get(position).getFoto());

        /**
         *
         * Descripcion: Oyente asociado al pulsar un item de la lista
         *
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.lblNombreFoto.getText().toString() != "No disponible"){
                    /**
                    Intent informacion_artista = new Intent(holder.itemView.getContext(), ventana_detalles_artistas.class);
                    informacion_artista.putExtra("identificador_artista", artistas.get(position).getIdArtista());
                    holder.itemView.getContext().startActivity(informacion_artista);
                     */
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
        return fotos.size();
    }
}
