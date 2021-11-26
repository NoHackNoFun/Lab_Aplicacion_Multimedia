package com.example.lab_aplicacion_multimedia.Presentacion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lab_aplicacion_multimedia.Dominio.Usuario;
import com.example.lab_aplicacion_multimedia.R;


/**
 *
 */
public class fragment_perfil extends Fragment {

    private Usuario usuario;

    private TextView lblNombre_usuario_perfil_BBDD;
    private TextView nombrePerfilTitulo;
    private TextView CorreoPerfilTitulo;
    private TextView TelefonoPerfilTitulo;
    private TextView FechaNacimientoPerfilTitulo;
    private ImageView image_foto_perfil;

    public fragment_perfil(Usuario usuario) {

        this.usuario = usuario;

    }
    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     */

    boolean mCalled;
    android.app.Fragment mHost;

    public void onAttach(Context context) {
        super.onAttach(context);
        mCalled = true;
        final Activity hostActivity = mHost == null ? null : mHost.getActivity();
        if (hostActivity != null) {
            mCalled = false;
            onAttach(hostActivity);
        }
    }

    /**
     * @deprecated Use {@link #onAttach(Context)} instead.
     */
    @Deprecated
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCalled = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        inicializarDatos(view);
        mostrarDatos();

        return view;

    }

    /**
     *
     * Descripcion: Metodo que permite inicializar los datos de la ventana reproductor
     *
     */
    private void inicializarDatos(View view){


    }

    /**
     *
     * Descripcion: Metodo que permite mostart los datos despues de hacer las consultas a la base de datos
     *
     */
    private void mostrarDatos(){


    }

}