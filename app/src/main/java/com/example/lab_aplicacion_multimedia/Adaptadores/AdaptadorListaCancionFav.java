package com.example.lab_aplicacion_multimedia.Adaptadores;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_aplicacion_multimedia.Dominio.CancionFavorita;
import com.example.lab_aplicacion_multimedia.Dominio.Foto;
import com.example.lab_aplicacion_multimedia.Dominio.FotoFavorita;
import com.example.lab_aplicacion_multimedia.Interfaz.OnItemSelectedListener;
import com.example.lab_aplicacion_multimedia.Presentacion.ventana_menu_principal;
import com.example.lab_aplicacion_multimedia.R;

import java.util.ArrayList;

/**
 *
 * Descripcion: Clase AdaptadorListaFotoFav para la lista personalizada de un usuario
 *
 */
public class AdaptadorListaCancionFav extends RecyclerView.Adapter<AdaptadorListaCancionFav.ViewHolder> {

    private ArrayList<CancionFavorita> cancionFavoritas;
    private OnItemSelectedListener itemSelectedListener;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView lblNombreCancion;
        private ImageView imgCancion;

        ViewHolder(View view) {

            super(view);

            this.lblNombreCancion = view.findViewById(R.id.lblNombreCancionF);
            this.imgCancion = view.findViewById(R.id.imgCancionF);
        }
    }

    /**
     *
     * Descripcion: Constructor clase AdaptadorListaCancionesFavoritas
     *
     * @param canciones_fav
     */
    public AdaptadorListaCancionFav(ArrayList<CancionFavorita> canciones_fav){
        this.cancionFavoritas = canciones_fav;
    }

    @Override
    public AdaptadorListaCancionFav.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_cancion, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorListaCancionFav.ViewHolder holder, int position) {

        holder.lblNombreCancion.setText(cancionFavoritas.get(position).getIdCancion());
        holder.imgCancion.setImageResource(R.drawable.icono_cancion_fav);

        /**
         *
         * Descripcion: Oyente asociado al pulsar un item de la lista
         *
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.lblNombreCancion.getText().toString() != " No disponible"){

                    new AlertDialog.Builder(holder.itemView.getContext())
                            .setTitle("¿Está seguro que desea elimar esta CANCION de favoritos?")
                            .setMessage("La CANCION se eliminara de esta playlist.")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    CancionFavorita gestor_cancion_fav = new CancionFavorita();

                                    gestor_cancion_fav.eliminarCancionesFavoritasBBDD(holder.itemView.getContext(),
                                            ventana_menu_principal.usuario_sesion_iniciada,
                                            cancionFavoritas.get(position).getIdCancion());

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
        return cancionFavoritas.size();
    }
}
