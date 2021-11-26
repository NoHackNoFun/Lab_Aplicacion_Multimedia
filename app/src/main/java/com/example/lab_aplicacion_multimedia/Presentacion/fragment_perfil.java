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
 * Descripcion: Clase fragmento que extiende a Fragmento
 *
 */
public class fragment_perfil extends Fragment {

    //Usuario que se encuentra altualmente en el sistema
    private Usuario usuario;

    private TextView nombre_usuario;
    private TextView correo_usuario;
    private TextView telefono_usuario;
    private TextView fecha_nacimiento_usuario;
    private ImageView foto_perfil_usuario;

    private Button btn_Configuracion_Avanzada;

    /**
     *
     * @param usuario
     */
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
        oyentesBotones();

        return view;
    }

    /**
     *
     * Descripcion: Metodo que permite inicializar los datos del fragmento perfil
     *
     */
    private void inicializarDatos(View view){

        this.btn_Configuracion_Avanzada = view.findViewById(R.id.btnConfiguracionAvanzada);

        this.nombre_usuario = (TextView) view.findViewById(R.id.lblNombreUsuario);
        this.correo_usuario = (TextView) view.findViewById(R.id.CorreoPerfilTitulo);
        this.telefono_usuario = (TextView) view.findViewById(R.id.TelefonoPerfilTitulo);
        this.fecha_nacimiento_usuario = (TextView) view.findViewById(R.id.FechaNacimientoPerfilTitulo);
        this.foto_perfil_usuario = (ImageView) view.findViewById(R.id.imageView_foto_perfil);
    }

    /**
     *
     * Descripcion: Metodo que permite mostart los datos despues de hacer las consultas
     *
     */
    private void mostrarDatos(){

        this.nombre_usuario.setText(this.usuario.getNombreUsario_pk());
        this.correo_usuario.setText("Correo: "+this.usuario.getCorreoElectronico());
        this.telefono_usuario.setText("Telefono: "+this.usuario.getTelefono());
        this.fecha_nacimiento_usuario.setText("Fecha Nacimiento: "+this.usuario.getFechaNacimiento());
        this.foto_perfil_usuario.setImageBitmap(this.usuario.getFotoPerfil());
    }

    /**
     *
     * Descripcion: Metodo que contiene el oyente asociado al boton configuracion avanzada
     *
     */
    private void oyentesBotones(){
        btn_Configuracion_Avanzada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent configuracion_avanzada = new Intent(getActivity(), ventana_configuracion.class);
                startActivity(configuracion_avanzada);
            }
        });
    }
}