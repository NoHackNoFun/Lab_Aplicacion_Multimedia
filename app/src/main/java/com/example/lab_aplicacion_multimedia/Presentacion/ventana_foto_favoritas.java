package com.example.lab_aplicacion_multimedia.Presentacion;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_aplicacion_multimedia.Adaptadores.AdaptadorListaFotoFav;
import com.example.lab_aplicacion_multimedia.Dominio.Foto;
import com.example.lab_aplicacion_multimedia.Dominio.FotoFavorita;
import com.example.lab_aplicacion_multimedia.R;

import java.util.ArrayList;

public class ventana_foto_favoritas extends AppCompatActivity {

    private String id_usuario;
    private String [] id_fotos;

    private ArrayList<Foto> fotos_playlist;
    private RecyclerView lstFotoPlayList;
    private AdaptadorListaFotoFav adaptador_fotolist;
    private Toast notification;

    private Foto gestor_foto = new Foto();
    private FotoFavorita gestor_foto_list = new FotoFavorita();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_foto);

        inicializarDatos();
        inicializarAdaptador();
        rellenarDatos();
    }

    /**
     *
     * Descripcion: Metodo que permite inicializar los datos de la ventana foto list
     *
     */
    private void inicializarDatos(){
        this.id_usuario = ventana_menu_principal.usuario_sesion_iniciada;
    }

    /**
     *
     * Descripcion: Obtenemos una referencia a la lista grafica y se crea
     * la lista de fotos y anadir algunos datos de prueba
     *
     */
    private void inicializarAdaptador(){

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.lstFotoPlayList = findViewById(R.id.lstImagenes);

        this.fotos_playlist = new ArrayList<Foto>();
        this.lstFotoPlayList.setLayoutManager(mLayoutManager);
        this.adaptador_fotolist = new AdaptadorListaFotoFav(fotos_playlist);
        this.lstFotoPlayList.setAdapter(adaptador_fotolist);
        this.lstFotoPlayList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    /**
     *
     * Descripcion: Metodo que permite inicializar la lista dependiendo de los datos obtenidos de la
     * consulta
     *
     */
    private void rellenarDatos(){

        int numero_fotos_usuario = obtenerNumeroFotosUsuario();

        if(numero_fotos_usuario != 0) {

            mostrarNotificacion("Fotos favoritas de "+this.id_usuario);

            this.id_fotos = gestor_foto_list.getListaFotosFavoritas(ventana_foto_favoritas.this,
                    id_usuario, numero_fotos_usuario);

            for(int i = 0; i< this.id_fotos.length; i++) {

                String parametro_nombre_foto = gestor_foto.buscarDatosFotosBBDD(ventana_foto_favoritas.this,
                        this.id_fotos[i], "NombreFoto");

                String parametro_descripcion = gestor_foto.buscarDatosFotosBBDD(ventana_foto_favoritas.this,
                        this.id_fotos[i], "DescripcionFoto");

                Bitmap bitmap =  gestor_foto.buscarImagenFotoBBDD(ventana_foto_favoritas.this,
                        this.id_fotos[i],"ImagenFoto");

                this.fotos_playlist.add(new Foto(this.id_fotos[i], parametro_nombre_foto, parametro_descripcion, bitmap));
            }
        }
        else {
            this.fotos_playlist.add(new Foto(" No disponible", " No disponible",
                    " No disponible", null));
            mostrarNotificacion("Ninguna foto en favoritos");
        }
    }

    /**
     *
     * Descripcion: Metodo que permite obtener el nuemero de fotos favoritas de un determinado usuario
     * en la aplicacion
     *
     * @return un entero con el numero de fotos del usuario en la aplicacion
     */
    private int obtenerNumeroFotosUsuario(){
        return this.gestor_foto_list.getNumeroFotosUsuarioBBDD(ventana_foto_favoritas.this, this.id_usuario);
    }

    /**
     *
     * Descripcion: Metodo que notifica al usuario de una accion
     *
     * @param cadena con el mensaje personaliszado dependiendo de la situacion
     */
    private void mostrarNotificacion(String cadena){

        this.notification = Toast.makeText(ventana_foto_favoritas.this, cadena,
                Toast.LENGTH_LONG);
        this.notification.show();
    }
}