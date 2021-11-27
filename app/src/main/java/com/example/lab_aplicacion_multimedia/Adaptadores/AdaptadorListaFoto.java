package com.example.lab_aplicacion_multimedia.Adaptadores;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int posicion = getAdapterPosition();
                    if(itemSelectedListener != null){
                        itemSelectedListener.onFotoSeleccionado(posicion);
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
                        itemSelectedListener.onMenuContextualFoto(getAdapterPosition(), item);
                    }
                    return true;
                }
            });
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
