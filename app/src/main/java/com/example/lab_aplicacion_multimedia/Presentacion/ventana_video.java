package com.example.lab_aplicacion_multimedia.Presentacion;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_aplicacion_multimedia.Adaptadores.AdaptadorListaVideo;
import com.example.lab_aplicacion_multimedia.Dominio.Video;
import com.example.lab_aplicacion_multimedia.Dominio.VideoFavorito;
import com.example.lab_aplicacion_multimedia.Interfaz.OnItemSelectedListener;
import com.example.lab_aplicacion_multimedia.R;

import java.util.ArrayList;

public class ventana_video extends AppCompatActivity {

    private ArrayList<Video> videos;
    private String [] id_videos;
    private RecyclerView lstVideos;
    private AdaptadorListaVideo adaptador_videos;
    private Toast notificacion;

    private Video gestor_videos = new Video();
    private VideoFavorito gestor_videos_favoritos = new VideoFavorito();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_video);

        inicializarAdaptador();
        rellenarDatos();
    }

    /**
     *
     * Descripcion: Obtenemos una referencia a la lista grafica y se crea
     * la lista de videos y anadir algunos datos de prueba
     *
     */
    private void inicializarAdaptador(){

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.lstVideos = findViewById(R.id.lstVideos);

        this.videos = new ArrayList<Video>();
        this.lstVideos.setLayoutManager(mLayoutManager);
        this.adaptador_videos = new AdaptadorListaVideo(videos);
        this.lstVideos.setAdapter(adaptador_videos);
        this.lstVideos.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        this.adaptador_videos.setItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onMultimediaSeleccionado(int posicion) {
                Log.d("Verificacion", videos.get(posicion).getNombreVideo());
            }

            @Override
            public void onMenuContextual(int posicion, MenuItem menu) {
                switch (menu.getItemId()){
                    case R.id.MultimediaFavorita:

                        int comprobar_video = gestor_videos_favoritos.buscarVideoRegistradoBBDD(
                                ventana_video.this,
                                videos.get(posicion).getIdVideo(),
                                ventana_menu_principal.usuario_sesion_iniciada);

                        if(comprobar_video == 0){
                            gestor_videos_favoritos.insertarDatosTablaVideosFavoritosBBDD(
                                    ventana_video.this,
                                    ventana_menu_principal.usuario_sesion_iniciada,
                                    videos.get(posicion).getIdVideo());
                            mostrarNotificacion("Nueva VIDEO en lista personal");
                        }
                        else if(comprobar_video > 0){
                            mostrarNotificacion("Esta VIDEO ya esta en su lista");
                        }

                        break;

                    case R.id.MultimediaComprimir:
                        /**
                        Bitmap foto_comprimir = fotos.get(posicion).getFoto();
                        gestor_fotos.guardarComprimirFoto(foto_comprimir);
                        mostrarNotificacion("La foto ha sido comprimida a JPEG con exito");
                         **/
                        break;
                }
            }
        });
    }

    /**
     *
     * Descripcion: Metodo que permite inicializar la lista dependiendo de los datos obtenidos de la
     * consulta
     *
     */
    private void rellenarDatos(){

        int numero_videos_sistema = obtenerNumeroVideos();

        if(numero_videos_sistema != 0){

            this.id_videos = gestor_videos.getListaVideosBBDD(ventana_video.this,
                    numero_videos_sistema);

            for(int i = 0; i < this.id_videos.length; i++){

                String parametro_nombre_video = gestor_videos.buscarDatosVideoBBDD(ventana_video.this,
                        this.id_videos[i], "NombreVideo");

                String parametro_descripcion_video = gestor_videos.buscarDatosVideoBBDD(ventana_video.this,
                        this.id_videos[i], "DescripcionVideo");

                Bitmap bitmap =  gestor_videos.buscarMiniaturaVideoBBDD(ventana_video.this,
                        this.id_videos[i],"ImagenVideo");

                videos.add(new Video(this.id_videos[i], parametro_nombre_video,
                        parametro_descripcion_video, bitmap));
            }
        }
        else{
            videos.add(new Video(" No disponible", " No disponible",
                    " No disponible", null));
        }
    }

    /**
     *
     * Descripcion: Metodo que permite obtener el nuemero de fotos totales en la aplicacion
     *
     * @return un entero con el numero de fotos del usuario en la aplicacion
     */
    private int obtenerNumeroVideos(){
        return this.gestor_videos.getNumeroTotalVideosBBDD(ventana_video.this);
    }

    /**
     *
     * Descripcion: Metodo que notifica al usuario de una accion
     *
     * @param cadena con el mensaje personaliszado dependiendo de la situacion
     */
    private void mostrarNotificacion(String cadena){

        this.notificacion = Toast.makeText(ventana_video.this, cadena,
                Toast.LENGTH_LONG);
        this.notificacion.show();
    }
}