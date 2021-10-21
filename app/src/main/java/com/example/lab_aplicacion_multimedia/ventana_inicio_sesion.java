package com.example.lab_aplicacion_multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
     * Esto es una Prueba
     */
    public void oyente_inciar_registro(View view){
        Intent actividad_registro = new Intent(this, ventana_registro.class );
        startActivity(actividad_registro);
    }

}