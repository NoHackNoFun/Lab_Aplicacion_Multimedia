package com.example.lab_aplicacion_multimedia.Presentacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lab_aplicacion_multimedia.Adaptadores.AdaptadorListaVideo;
import com.example.lab_aplicacion_multimedia.Adaptadores.AdaptadorListaVideoFav;
import com.example.lab_aplicacion_multimedia.Dominio.Video;
import com.example.lab_aplicacion_multimedia.Dominio.VideoFavorito;
import com.example.lab_aplicacion_multimedia.R;

import java.util.ArrayList;

public class ventana_video_favoritos extends AppCompatActivity {

    private String id_usuario;
    private String [] id_videos;

    private ArrayList<Video> videos_playlist;
    private RecyclerView lstVideoPlayList;
    private AdaptadorListaVideoFav adaptador_videolist;
    private Toast notification;

    private Video gestor_video = new Video();
    private VideoFavorito gestor_video_list = new VideoFavorito();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_video);

        inicializarDatos();
        inicializarAdaptador();
        rellenarDatos();
    }

    /**
     *
     * Descripcion: Metodo que permite inicializar los datos de la ventana video list
     *
     */
    private void inicializarDatos(){
        this.id_usuario = ventana_menu_principal.usuario_sesion_iniciada;
    }

    /**
     *
     * Descripcion: Obtenemos una referencia a la lista grafica y se crea
     * la lista de videos y anadir algunos datos de prueba
     *
     */
    private void inicializarAdaptador(){

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.lstVideoPlayList = findViewById(R.id.lstVideos);

        this.videos_playlist = new ArrayList<Video>();
        this.lstVideoPlayList.setLayoutManager(mLayoutManager);
        this.adaptador_videolist = new AdaptadorListaVideoFav(videos_playlist);
        this.lstVideoPlayList.setAdapter(adaptador_videolist);
        this.lstVideoPlayList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    /**
     *
     * Descripcion: Metodo que permite inicializar la lista dependiendo de los datos obtenidos de la
     * consulta
     *
     */
    private void rellenarDatos(){

        int numero_videos_usuario = obtenerNumeroVideosUsuario();

        if(numero_videos_usuario != 0) {

            mostrarNotificacion("Videos favoritos de "+this.id_usuario);

            this.id_videos = gestor_video_list.getListaVideosFavoritosBBDD(ventana_video_favoritos.this,
                    id_usuario, numero_videos_usuario);

            for(int i = 0; i< this.id_videos.length; i++) {

                String parametro_nombre_video = gestor_video.buscarDatosVideoBBDD(ventana_video_favoritos.this,
                        this.id_videos[i], "NombreVideo");

                String parametro_descripcion = gestor_video.buscarDatosVideoBBDD(ventana_video_favoritos.this,
                        this.id_videos[i], "DescripcionVideo");

                Bitmap bitmap =  gestor_video.buscarMiniaturaVideoBBDD(ventana_video_favoritos.this,
                        this.id_videos[i],"ImagenVideo");

                this.videos_playlist.add(new Video(this.id_videos[i], parametro_nombre_video, parametro_descripcion, bitmap));
            }
        }
        else {
            this.videos_playlist.add(new Video(" No disponible", " No disponible",
                    " No disponible", null));
            mostrarNotificacion("Ningun video en favoritos");
        }
    }

    /**
     *
     * Descripcion: Metodo que permite obtener el nuemero de videos favoritas de un determinado usuario
     * en la aplicacion
     *
     * @return un entero con el numero de fotos del usuario en la aplicacion
     */
    private int obtenerNumeroVideosUsuario(){
        return this.gestor_video_list.getNumeroVideosUsuarioBBDD(ventana_video_favoritos.this,
                this.id_usuario);
    }

    /**
     *
     * Descripcion: Metodo que notifica al usuario de una accion
     *
     * @param cadena con el mensaje personaliszado dependiendo de la situacion
     */
    private void mostrarNotificacion(String cadena){

        this.notification = Toast.makeText(ventana_video_favoritos.this, cadena,
                Toast.LENGTH_LONG);
        this.notification.show();
    }
}