package com.example.lab_aplicacion_multimedia.Presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lab_aplicacion_multimedia.R;

public class ventana_inicio_sesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_inicio_sesion);
    }

    /**
     *
     * Descripcion: Oyente asociado al boton Registro que inicializa la actividad para Registrarse
     *
     * @param view
     */
    public void oyente_inciar_registro(View view){
        Intent actividad_registro = new Intent(this, ventana_registro.class );
        startActivity(actividad_registro);
    }

}