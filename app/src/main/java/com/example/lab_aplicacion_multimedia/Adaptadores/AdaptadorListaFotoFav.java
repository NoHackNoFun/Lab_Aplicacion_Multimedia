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

import com.example.lab_aplicacion_multimedia.Dominio.Foto;
import com.example.lab_aplicacion_multimedia.Dominio.FotoFavorita;
import com.example.lab_aplicacion_multimedia.Interfaz.OnItemSelectedListener;
import com.example.lab_aplicacion_multimedia.R;
import com.example.lab_aplicacion_multimedia.Presentacion.ventana_menu_principal;

import java.util.ArrayList;

/**
 *
 * Descripcion: Clase AdaptadorListaFotoFav para la lista personalizada de un usuario
 *
 */
public class AdaptadorListaFotoFav extends RecyclerView.Adapter<AdaptadorListaFotoFav.ViewHolder> {

    private ArrayList<Foto> fotos;
    private OnItemSelectedListener itemSelectedListener;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView lblNombreFoto;
        private TextView lblDescripcionFoto;
        private ImageView imgFoto;

        ViewHolder(View view) {

            super(view);

            this.lblNombreFoto = view.findViewById(R.id.lblNombreFoto);
            this.lblDescripcionFoto = view.findViewById(R.id.lblDescripcionFoto);
            this.imgFoto = view.findViewById(R.id.imgFoto);
        }
    }

    /**
     *
     * Descripcion: Constructor clase AdaptadorListaFotosFavoritas
     *
     * @param fotos
     */
    public AdaptadorListaFotoFav(ArrayList<Foto> fotos){
        this.fotos = fotos;
    }

    @Override
    public AdaptadorListaFotoFav.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_foto, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorListaFotoFav.ViewHolder holder, int position) {

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

                if(holder.lblNombreFoto.getText().toString() != " No disponible"){

                    new AlertDialog.Builder(holder.itemView.getContext())
                            .setTitle("¿Está seguro que desea elimar esta FOTO de favoritos?")
                            .setMessage("La FOTO se eliminara de esta playlist.")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    FotoFavorita gestor_fotolist = new FotoFavorita();

                                    gestor_fotolist.eliminarFotoFavoritosBBDD(holder.itemView.getContext(),
                                            ventana_menu_principal.usuario_sesion_iniciada, fotos.get(position).getIdImagen());

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
        return fotos.size();
    }
}
