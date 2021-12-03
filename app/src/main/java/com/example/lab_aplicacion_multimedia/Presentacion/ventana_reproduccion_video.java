package com.example.lab_aplicacion_multimedia.Presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.lab_aplicacion_multimedia.Dominio.Video;
import com.example.lab_aplicacion_multimedia.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

public class ventana_reproduccion_video extends AppCompatActivity {

    private Button btn_comprimir_video;
    private VideoView video_original;
    private TextView nombre_video;
    private TextView descripcion_video;

    private String identificador_video;
    private String identificador_video_BBDD;
    private String nombre_video_BBDD;
    private String descripcion_video_BBDD;
    private String video_path;

    private Video gestor_video = new Video();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_reproduccion_video);

        inicializarDatos();
        inicializarDatosBBDD();
        mostrarDatos();
        reproducirVideo();
        oyentesBotones();
    }

    /**
     *
     * Descripcion: Metodo que permite inicializar los datos de esta ventana
     *
     */
    private void inicializarDatos(){

        this.video_original = findViewById(R.id.video_view_original);
        this.btn_comprimir_video = findViewById(R.id.btn_comprimir_video);
        this.nombre_video = findViewById(R.id.lbl_video_nombre);
        this.descripcion_video = findViewById(R.id.lbl_video_descripcion);

        Bundle bundle = getIntent().getExtras();
        this.identificador_video = bundle.getString("identificador_video");
    }

    /**
     *
     * Descripcion: Metodo que inicializa los datos de video realizando consultas a BBDD
     *
     */
    private void inicializarDatosBBDD(){

        this.identificador_video_BBDD = gestor_video.buscarDatosVideoBBDD(
                ventana_reproduccion_video.this,
                this.identificador_video, "IdVideo");

        this.nombre_video_BBDD = gestor_video.buscarDatosVideoBBDD(ventana_reproduccion_video.this,
                this.identificador_video, "NombreVideo");

        this.descripcion_video_BBDD = gestor_video.buscarDatosVideoBBDD(ventana_reproduccion_video.this,
                this.identificador_video, "DescripcionVideo");

    }

    /**
     *
     * Descripcion: Metodo que permite mostrar los datos despues de hacer las consultas a BBDD
     *
     */
    private void mostrarDatos(){

        this.nombre_video.setText(nombre_video_BBDD);
        this.descripcion_video.setText(descripcion_video_BBDD);
    }

    /**
     *
     * Descripcion: Metodo que permite reproducir videos
     *
     */
    private void reproducirVideo(){

        String filename = this.identificador_video_BBDD;

        this.video_path = "android.resource://"+getPackageName()+"/"+
                this.getResources().getIdentifier(filename, "raw", this.getPackageName());

        Uri uri = Uri.parse(video_path);
        this.video_original.setVideoURI(uri);

        MediaController media_controller = new MediaController(ventana_reproduccion_video.this);
        this.video_original.setMediaController(media_controller);
        media_controller.setAnchorView(this.video_original);
    }

    /**
     *
     * Descripcion: Metodo que contiene el oyente asociado al boton comprimir
     *
     */
    private void oyentesBotones(){

        this.btn_comprimir_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    File newfile;
                    String data = "android.resource://"+getPackageName()+"/"+
                            getResources().getIdentifier(identificador_video_BBDD, "raw",
                                    getPackageName());

                    Uri uri = Uri.parse(data);

                    AssetFileDescriptor videoAsset = getContentResolver().openAssetFileDescriptor(uri, "r");
                    FileInputStream in = videoAsset.createInputStream();

                    File filepath = Environment.getExternalStorageDirectory();
                    File dir = new File(Environment.getExternalStorageDirectory() + "/VideosComprimidos");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    newfile = new File(dir, "save_"+System.currentTimeMillis()+".mp4");

                    if (newfile.exists()) newfile.delete();

                    OutputStream out = new FileOutputStream(newfile);

                    // Copy the bits from instream to outstream
                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    in.close();
                    out.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}