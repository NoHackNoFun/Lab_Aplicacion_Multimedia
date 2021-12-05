package com.example.lab_aplicacion_multimedia.Presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
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
import java.net.URISyntaxException;

public class ventana_reproduccion_video extends AppCompatActivity {

    //Datos relacionados con la actividad de reproduccion de video

    private VideoView video_original;
    private TextView nombre_video;
    private TextView descripcion_video;
    private TextView original_size;
    private TextView compress_size;
    private Button btn_comprimir_video;
    private Toast notificacion;

    //Datos relacionados con la obtencion de valores de BBDD

    private String identificador_video;
    private String identificador_video_BBDD;
    private String nombre_video_BBDD;
    private String descripcion_video_BBDD;
    private String video_path;

    //Datos para gestionar operaciones BBDD

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
     * Descripcion: Metodo que mediante el ID del video en la BBDD localica el video correspondiente
     * en la carpeta R.raw y lo almcena en un directorio externo llamado VideosNoComprimos, para
     * depues obtener el tamano del video sin comprimir y mostrarlo por pantalla
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

            long length_original = original_video_file.length();
            long length_original_kb = length_original/1024; //Tamano KB
            long length_original_mb = length_original_kb/1024; //Tamano MB

            this.original_size.setText("Tamano video original: "+length_original_kb+" KB --> "
                    +length_original_mb+" MB");

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

    /**
     *
     * Descripcion: Clase que permite comprimir un video haciendo uso de SiliCompressor
     *
     */
    class VideoCompressAsyncTask extends AsyncTask<String, String, String> {

        Context mContext;

        /**
         *
         * Descripcion: Obtenemos el contexto de la aplicacion para saber la ventana donde se
         * tienen que mostrar los datos
         *
         * @param context
         */
        public VideoCompressAsyncTask(Context context){
            mContext = context;
        }

        /**
         *
         * Descripcion: Mostrar texto para indicar al usuario que se esta llevanddo a cabo una
         * operacion
         *
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            compress_size.setText("Comprimiendo ...");
        }

        /**
         *
         * Descripcion: Operacion que compprime el video. Es necesario el contexto y la ruta
         * donde se encuentra el archivo a comprimir y el distino como parametros
         *
         * @param paths
         * @return
         */
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

        /**
         *
         * Descripcion: Una vez comprimido el video se muestra el resultado de la compresion
         *
         * @param compressedFilePath
         */
        @Override
        protected void onPostExecute(String compressedFilePath) {
            super.onPostExecute(compressedFilePath);

            File videoFile = new File(compressedFilePath);

            long length_compress = videoFile.length();
            long length_compress_kb = length_compress/1024; //Tamano KB
            long length_compress_mb = length_compress_kb/1024; //Tamano MB

            compress_size.setText("Tamano video comprimido: "+length_compress_kb+" KB --> "
                    +length_compress_mb+" MB");
            mostrarNotificacion("El VIDEO ha sido comprimida con SiliCompressor con EXITO");
            mostrarNotificacion("Ubicacion: "+Environment.getExternalStorageDirectory() +
                    "/ImagenesComprimidas");
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