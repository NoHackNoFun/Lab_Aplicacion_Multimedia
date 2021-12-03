package com.example.lab_aplicacion_multimedia.Presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.lab_aplicacion_multimedia.R;

public class ventana_reproduccion_video extends AppCompatActivity {

    private Button btn_comprimir_video;
    private VideoView video_original;
    private TextView nombre_video;
    private TextView descripcion_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_reproduccion_video);

        inicializarDatos();

        this.video_original = findViewById(R.id.video_view_original);
        String videoPath = "android.resource://" + getPackageName() + "/"+R.raw.video_ejemplo1;
        Uri uri = Uri.parse(videoPath);
        this.video_original.setVideoURI(uri);

        MediaController media_controller = new MediaController(ventana_reproduccion_video.this);
        this.video_original.setMediaController(media_controller);
        media_controller.setAnchorView(this.video_original);
    }

    /**
     *
     * Descripcion: Metodo que permite inicializar los datos de esta ventana
     *
     */
    private void inicializarDatos(){

        this.btn_comprimir_video = findViewById(R.id.btn_comprimir_video);
        this.nombre_video = findViewById(R.id.lbl_video_nombre);
        this.descripcion_video = findViewById(R.id.lbl_video_descripcion);
    }
}