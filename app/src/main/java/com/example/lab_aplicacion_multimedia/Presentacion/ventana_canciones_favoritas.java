package com.example.lab_aplicacion_multimedia.Presentacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lab_aplicacion_multimedia.Adaptadores.AdaptadorListaCancionFav;
import com.example.lab_aplicacion_multimedia.Dominio.CancionFavorita;
import com.example.lab_aplicacion_multimedia.R;

import java.util.ArrayList;

public class ventana_canciones_favoritas<Cancion> extends AppCompatActivity {

    private String id_usuario;
    private String [] id_canciones;

    private ArrayList<CancionFavorita> canciones_playlist;
    private RecyclerView lstCancionesPlayList;
    private AdaptadorListaCancionFav adaptador_cancionlist;
    private Toast notification;

    private CancionFavorita gestor_cancion_list = new CancionFavorita();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_canciones_favoritas);

        inicializarDatos();
        inicializarAdaptador();
        rellenarDatos();
    }

    /**
     *
     * Descripcion: Metodo que permite inicializar los datos de la ventana cancion list
     *
     */
    private void inicializarDatos(){
        this.id_usuario = ventana_menu_principal.usuario_sesion_iniciada;
    }

    /**
     *
     * Descripcion: Obtenemos una referencia a la lista grafica y se crea
     * la lista de canciones y anadir algunos datos de prueba
     *
     */
    private void inicializarAdaptador(){

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.lstCancionesPlayList = findViewById(R.id.lstCanciones);

        this.canciones_playlist = new ArrayList<CancionFavorita>();
        this.lstCancionesPlayList.setLayoutManager(mLayoutManager);
        this.adaptador_cancionlist = new AdaptadorListaCancionFav(canciones_playlist);
        this.lstCancionesPlayList.setAdapter(adaptador_cancionlist);
        this.lstCancionesPlayList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    /**
     *
     * Descripcion: Metodo que permite inicializar la lista dependiendo de los datos obtenidos de la
     * consulta
     *
     */
    private void rellenarDatos(){

        int numero_canciones_usuario = obtenerNumeroCancionesUsuario();

        if(numero_canciones_usuario != 0) {

            mostrarNotificacion("Canciones favoritas de "+this.id_usuario);

            this.id_canciones = gestor_cancion_list.getListaCancionesFavoritasBBDD(
                    ventana_canciones_favoritas.this,
                    id_usuario,
                    numero_canciones_usuario);

            for(int i = 0; i< this.id_canciones.length; i++) {

                this.canciones_playlist.add(new CancionFavorita(
                        "2222",
                        id_usuario,
                        this.id_canciones[i]));
            }
        }
        else {
            this.canciones_playlist.add(new CancionFavorita(
                    " No disponible", " No disponible", " No disponible"));
            mostrarNotificacion("Ninguna cancion en favoritos");
        }
    }

    /**
     *
     * Descripcion: Metodo que permite obtener el nuemero de canciones favoritas de un determinado usuario
     * en la aplicacion
     *
     * @return un entero con el numero de canciones del usuario en la aplicacion
     */
    private int obtenerNumeroCancionesUsuario(){
        return this.gestor_cancion_list.getNumeroCancionesUsuarioBBDD(
                ventana_canciones_favoritas.this,
                this.id_usuario);
    }

    /**
     *
     * Descripcion: Metodo que notifica al usuario de una accion
     *
     * @param cadena con el mensaje personaliszado dependiendo de la situacion
     */
    private void mostrarNotificacion(String cadena){

        this.notification = Toast.makeText(ventana_canciones_favoritas.this, cadena,
                Toast.LENGTH_LONG);
        this.notification.show();
    }
}