package com.example.lab_aplicacion_multimedia.Presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.lab_aplicacion_multimedia.Dominio.Video;
import com.example.lab_aplicacion_multimedia.R;
import com.iceteck.silicompressorr.SiliCompressor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.Locale;

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
    private Toast notificacion;

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

                String output = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/VideosNoComprimidos/"+"save_"+nombre_video_BBDD;

                Uri uri = Uri.parse(output);

                String input = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/VideosComprimidos";

                File dir = new File(input);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                new VideoCompressAsyncTask(ventana_reproduccion_video.this)
                        .execute(uri.toString(), dir.getPath());
            }
        });
    }

    class VideoCompressAsyncTask extends AsyncTask<String, String, String> {

        Context mContext;

        public VideoCompressAsyncTask(Context context){
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            compress_size.setText("Comprimiendo ...");

        }

        @Override
        protected String doInBackground(String... paths) {
            String filePath = null;
            try {

                filePath = SiliCompressor.with(mContext).compressVideo(paths[0], paths[1]);

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return  filePath;

        }


        @Override
        protected void onPostExecute(String compressedFilePath) {
            super.onPostExecute(compressedFilePath);
            File imageFile = new File(compressedFilePath);
            float length = imageFile.length() / 1024f; // Size in KB
            String value;
            /**
            if(length >= 1024)
                value = length/1024f+" MB";
            else
                value = length+" KB";
            */
            compress_size.setText("Nuevo valor video comprimido: "+length+" KB");

            mostrarNotificacion("El VIDEO ha sido comprimida con SiliCompressor con EXITO");
            mostrarNotificacion("Ubicacion: "+Environment.getExternalStorageDirectory() +
                    "/ImagenesComprimidas");
            /**
            File f = new File(compressedFilePath);
            Uri uri = Uri.fromFile(f);

            video_original.setVideoURI(uri);

            MediaController media_controller = new MediaController(ventana_reproduccion_video.this);
            video_original.setMediaController(media_controller);
            media_controller.setAnchorView(video_original);
            video_original.start();
             */
        }
    }

    /**
     *
     * Descripcion: Metodo que notifica al usuario de una accion
     *
     * @param cadena con el mensaje personaliszado dependiendo de la situacion
     */
    private void mostrarNotificacion(String cadena){

        this.notificacion = Toast.makeText(ventana_reproduccion_video.this, cadena,
                Toast.LENGTH_LONG);
        this.notificacion.show();
    }
}