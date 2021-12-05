package com.example.lab_aplicacion_multimedia.Presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.lab_aplicacion_multimedia.R;
import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

import java.io.File;
import java.util.ArrayList;

public class ventana_reproduccion_audio extends AppCompatActivity {

    public static final String EXTRA_NAME = "nombre_cancion";
    public static MediaPlayer media_player;

    //Datos relacionados con los botones de la ventana reproductor audio

    private Button btn_reproducir_musica;
    private Button btn_siguiente_cancion;
    private Button btn_anterior_cancion;
    private Button btn_aniadir_favoritos;
    private Button btn_comprimir_cancion;

    //Datos relacionados con las canciones

    private TextView nombre_cancion;
    private TextView inicio_cancion;
    private TextView final_cancion;
    private TextView tamano_original_audio;
    private TextView tamano_comprimido_auido;
    private SeekBar barra_musica;
    private BarVisualizer visualizador;
    private ImageView miniatura_cancion;

    //Datos generales

    private String nombre_cancion_id;
    private String nombre_cancion_actual;
    private int posicion;
    private ArrayList<File> lst_Miscanciones;
    private Thread update_seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_reproduccion_audio);

        inicializarActionBar();
        inicializarDatos();
        inicializarReproductorAudio();

        actualizarBarraCanciones();
        oyentesBotones();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (this.visualizador != null) {
            this.visualizador.release();
        }
        super.onDestroy();
    }

    /**
     *
     * Descripcion: Metodo para establecer valores iniciales de ActionBar
     *
     */
    private void inicializarActionBar(){

        //getSupportActionBar().setTitle("Cancion en marcha");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     *
     * Descripcion: Metodo que permite inicializar los datos de esta ventana
     *
     */
    public void inicializarDatos(){

        this.btn_reproducir_musica = findViewById(R.id.btnPlay);
        this.btn_siguiente_cancion = findViewById(R.id.btnSiguienteCancion);
        this.btn_anterior_cancion = findViewById(R.id.btnAnteriorCancion);
        this.btn_aniadir_favoritos = findViewById(R.id.btnFavoritosAudios);
        this.btn_comprimir_cancion = findViewById(R.id.btnComprimirAudio);

        this.nombre_cancion = findViewById(R.id.txtsn);
        this.inicio_cancion = findViewById(R.id.txtsstart);
        this.final_cancion = findViewById(R.id.txtsstop);
        this.barra_musica = findViewById(R.id.seekbar);
        this.tamano_original_audio = findViewById(R.id.txtOriginalAudioSize);
        this.tamano_comprimido_auido = findViewById(R.id.txtCompressAudioSize);
        this.visualizador = findViewById(R.id.blast);
        this.miniatura_cancion = findViewById(R.id.imgMiniaturaAudio);
    }

    /**
     *
     * Descripcion: Metodo para poner en funcionamiento el reproductor de musica
     *
     */
    private void inicializarReproductorAudio(){

        if (this.media_player != null) {
            this.media_player.stop();
            this.media_player.release();
        }

        Intent obtener_datos_canciones = getIntent();
        Bundle bundle = obtener_datos_canciones.getExtras();

        this.lst_Miscanciones = (ArrayList) bundle.getParcelableArrayList("lista_canciones");
        this.nombre_cancion_actual = obtener_datos_canciones.getStringExtra("nombre_cancion_s");
        this.posicion = bundle.getInt("index",0);

        this.nombre_cancion.setSelected(true);
        Uri uri = Uri.parse(lst_Miscanciones.get(posicion).toString());
        this.nombre_cancion_id = lst_Miscanciones.get(posicion).getName();
        this.nombre_cancion.setText(this.nombre_cancion_id);

        this.media_player = MediaPlayer.create(getApplicationContext(), uri);
        this.media_player.start();
    }

    /**
     *
     * Descripcion: Metodo relacionado con la barra de progreso de las canciones
     *
     */
    private void actualizarBarraCanciones(){

        update_seekbar = new Thread() {

            @Override
            public void run() {
                int totalDuration = media_player.getDuration();
                int currentposition = 0;

                while (currentposition<totalDuration) {
                    try {
                        sleep(500);
                        currentposition = media_player.getCurrentPosition();
                        barra_musica.setProgress(currentposition);
                    }
                    catch (InterruptedException | IllegalStateException e)  {
                        e.printStackTrace();
                    }
                }
            }
        };

        barra_musica.setMax(media_player.getDuration());
        update_seekbar.start();
        barra_musica.getProgressDrawable().setColorFilter(getResources()
                .getColor(R.color.design_default_color_secondary_variant), PorterDuff.Mode.MULTIPLY);
        barra_musica.getThumb().setColorFilter(getResources()
                .getColor(R.color.design_default_color_secondary_variant), PorterDuff.Mode.SRC_IN);

        barra_musica.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                media_player.seekTo(seekBar.getProgress());
            }
        });

        String endTime = createTime(media_player.getDuration());
        final_cancion.setText(endTime);

        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = createTime(media_player.getCurrentPosition());
                inicio_cancion.setText(currentTime);
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    /**
     *
     * Descripcion: Metodo que contiene el oyente asociado al boton comprimir
     *
     */
    private void oyentesBotones(){

        /**
         *
         * Descripcion: Oyente asociado al boton escuchar musica
         *
         */
        this.btn_reproducir_musica.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (media_player.isPlaying()) {
                    btn_reproducir_musica.setBackgroundResource(R.drawable.icono_play);
                    media_player.pause();
                }
                else {
                    btn_reproducir_musica.setBackgroundResource(R.drawable.icono_pausa);
                    media_player.start();
                }
            }
        });

        //siguiente listener

        media_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                btn_siguiente_cancion.performClick();
            }
        });

        int sesion_audio_id = media_player.getAudioSessionId();
        if (sesion_audio_id != -1) {
            visualizador.setAudioSessionId(sesion_audio_id);
        }

        /**
         *
         * Descripcion: Oyente asociado al boton siguiente cancion de la lista
         *
         */
        this.btn_siguiente_cancion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                media_player.stop();
                media_player.release();

                posicion = ((posicion+1)%lst_Miscanciones.size());
                Uri uri = Uri.parse(lst_Miscanciones.get(posicion).toString());

                media_player = MediaPlayer.create(getApplicationContext(), uri);
                nombre_cancion_id = lst_Miscanciones.get(posicion).getName();
                nombre_cancion.setText(nombre_cancion_id);
                media_player.start();

                btn_reproducir_musica.setBackgroundResource(R.drawable.icono_pausa);
                startAnimation(miniatura_cancion);

                int audios_sesion_id = media_player.getAudioSessionId();
                if (audios_sesion_id != -1) {
                    visualizador.setAudioSessionId(audios_sesion_id);
                }
            }
        });

        /**
         *
         * Descripcion: Oyente asociado al boton anterior cancion de la lista
         *
         */
        this.btn_anterior_cancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                media_player.stop();
                media_player.release();
                posicion = ((posicion-1)<0)?(lst_Miscanciones.size()-1):(posicion-1);
                Uri uri = Uri.parse(lst_Miscanciones.get(posicion).toString());

                media_player = MediaPlayer.create(getApplicationContext(), uri);
                nombre_cancion_id = lst_Miscanciones.get(posicion).getName();
                nombre_cancion.setText(nombre_cancion_id);
                media_player.start();

                btn_reproducir_musica.setBackgroundResource(R.drawable.icono_pausa);
                startAnimation(miniatura_cancion);

                int audios_sesion_id = media_player.getAudioSessionId();
                if (audios_sesion_id != -1) {
                    visualizador.setAudioSessionId(audios_sesion_id);
                }
            }
        });

        /**
         *
         * Descripcion: Oyente asociado al boton favoritos aniade una cancion a la playlist
         *
         */
        this.btn_aniadir_favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /**
         *
         * Descripcion: Oyente asociado al boton comprimir que permite comprimir una cancion
         *
         */
        this.btn_comprimir_cancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tamano_comprimido_auido.setText("Comprimiendo ...");
            }
        });

    }

    /**
     *
     * Descripcion: Metodo que establece los datos al cambiar entre distintas canciones
     *
     * @param view
     */
    public void startAnimation(View view) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(
                this.miniatura_cancion,
                "rotation",
                0f,360f);

        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();
    }

    /**
     *
     * Descripcion:
     *
     * @param duration
     * @return
     */
    public String createTime(int duration) {

        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000%60;

        time+=min+":";

        if (sec<10) {
            time+="0";
        }
        time+=sec;
        return  time;
    }
}