package com.example.lab_aplicacion_multimedia.Presentacion;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_aplicacion_multimedia.Dominio.Foto;
import com.example.lab_aplicacion_multimedia.R;

public class ventana_detalles_foto extends AppCompatActivity {

    private ImageView image_foto;
    private TextView nombre_foto;
    private TextView descripcion_foto;
    private Toast notificacion;

    private String identificador_foto;
    private Foto gestor_foto = new Foto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_foto);

        inicializarDatos();
        inicializarDatosBBDD();
        mostarNotificacion();
    }

    /**
     *
     * Descripcion: Metodo que permite inicializar los datos de la ventana detalles foto
     *
     */
    private void inicializarDatos(){

        this.nombre_foto = findViewById(R.id.lblNombreFotoDetalle);
        this.descripcion_foto = findViewById(R.id.lblDescripcionFoto);
        this.image_foto = findViewById(R.id.ivImagen);

        Bundle bundle = getIntent().getExtras();
        this.identificador_foto = bundle.getString("identificador_foto");
    }

    /**
     *
     * Descripcion: Metodo que inicializa los datos realizando consultas en la base de datos
     *
     */
    private void inicializarDatosBBDD(){

        this.nombre_foto.setText(gestor_foto.buscarDatosFotosBBDD(ventana_detalles_foto.this,
                this.identificador_foto, "NombreFoto"));

        this.descripcion_foto.setText(gestor_foto.buscarDatosFotosBBDD(ventana_detalles_foto.this,
                this.identificador_foto, "DescripcionFoto"));

        this.image_foto.setImageBitmap(gestor_foto.buscarImagenFotoBBDD(ventana_detalles_foto.this,
                this.identificador_foto, "ImagenFoto"));
    }

    /**
     *
     * Descripcion: Metodo que muestra una notificacion
     *
     */
    private void mostarNotificacion(){

        this.notificacion = Toast.makeText(this, "Imagen seleccionada: "
                + gestor_foto.buscarDatosFotosBBDD(ventana_detalles_foto.this,
                this.identificador_foto, "NombreFoto"), Toast.LENGTH_LONG);

        notificacion.show();
    }
}