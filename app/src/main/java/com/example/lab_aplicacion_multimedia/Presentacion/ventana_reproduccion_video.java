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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;

public class ventana_reproduccion_video extends AppCompatActivity {

    private Button btn_comprimir_video;
    private VideoView video_original;
    private TextView nombre_video;
    private TextView descripcion_video;

    private TextView original_size;
    private TextView compress_size;

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
        tamanoVideoOriginal();
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
        this.original_size = findViewById(R.id.txtOriginal);
        this.compress_size = findViewById(R.id.txtComprido);

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
        this.video_original.start();
    }

    /**
     *
     * Descripcion: Metodo que almacena un video y obtiene su size
     *
     */
    private void tamanoVideoOriginal(){

        try{

            Uri uri = Uri.parse(this.video_path);

            AssetFileDescriptor videoAsset = getContentResolver().openAssetFileDescriptor(uri, "r");
            FileInputStream in = videoAsset.createInputStream();

            File dir = new File(Environment.getExternalStorageDirectory() +
                    "/VideosNoComprimidos");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filename = "save_"+this.nombre_video_BBDD;
            File newfile = new File(dir, filename);
            if (newfile.exists()) newfile.delete();

            OutputStream out = new FileOutputStream(newfile);

            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();

            File original_video_file = new File(Environment.getExternalStorageDirectory() +
                    "/VideosNoComprimidos/"+filename);
            long length = original_video_file.length();
            length = length/1024;

            this.original_size.setText("Tamano video original: "+length+" KB");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Descripcion: Metodo que contiene el oyente asociado al boton comprimir
     *
     */
    private void oyentesBotones(){

        /**
         *
         * Descripcion: Oyente asociado al boton comprimir
         *
         */
        this.btn_comprimir_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}