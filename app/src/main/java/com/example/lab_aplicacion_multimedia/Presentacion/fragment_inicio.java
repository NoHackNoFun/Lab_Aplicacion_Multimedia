package com.example.lab_aplicacion_multimedia.Presentacion;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lab_aplicacion_multimedia.R;

/**
 *
 * Descripcion: Clase inicio que extiende Fragmento
 *
 */
public class fragment_inicio extends Fragment {

    private Button btn_mis_fotos;
    private Button btn_mis_audios;
    private Button btn_mis_videos;

    public fragment_inicio() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        this.btn_mis_fotos = view.findViewById(R.id.btnFotosFavList);
        this.btn_mis_audios = view.findViewById(R.id.btnAudioFavList);
        this.btn_mis_videos = view.findViewById(R.id.btnVideoFavList);

        oyentesBotonesFragmentInicio();

        return view;
    }

    /**
     *
     * Descripcion: Metodo que contiene los oyentes de los botones del fragment Incio
     *
     */
    private void oyentesBotonesFragmentInicio(){

        /**
         *
         * Descripcion: Oyente boton Mis Fotos
         *
         */
        this.btn_mis_fotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventana_fotolist = new Intent(getActivity(), ventana_foto_favoritas.class);
                startActivity(ventana_fotolist);
            }
        });

        /**
         *
         * Descripcion: Oyente boton Mis Videos
         *
         */
        this.btn_mis_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventana_videolist = new Intent(getActivity(), ventana_video_favoritos.class);
                startActivity(ventana_videolist);
            }
        });

        /**
         *
         * Descripcion: Oyente boton Mis Canciones
         *
         */
        this.btn_mis_audios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventana_cancionlist = new Intent(getActivity(), ventana_canciones_favoritas.class);
                startActivity(ventana_cancionlist);
            }
        });
    }
}