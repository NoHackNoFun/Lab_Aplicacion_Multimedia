package com.example.lab_aplicacion_multimedia.Presentacion;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_aplicacion_multimedia.Adaptadores.AdaptadorListaFoto;
import com.example.lab_aplicacion_multimedia.Dominio.Foto;
import com.example.lab_aplicacion_multimedia.Dominio.FotoFavorita;
import com.example.lab_aplicacion_multimedia.Interfaz.OnItemSelectedListener;
import com.example.lab_aplicacion_multimedia.R;
import java.util.ArrayList;

public class ventana_foto extends AppCompatActivity {

    private ArrayList<Foto> fotos;
    private String [] id_fotos;
    private RecyclerView lstFotos;
    private AdaptadorListaFoto adaptador_fotos;
    private Toast notificacion;

    private Foto gestor_fotos = new Foto();
    private FotoFavorita gestor_fotos_favoritas = new FotoFavorita();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_foto);

        inicializarAdaptador();
        rellenarDatos();
    }

    /**
     *
     * Descripcion: Obtenemos una referencia a la lista grafica y se crea
     * la lista de fotos y anadir algunos datos de prueba
     *
     */
    private void inicializarAdaptador(){

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.lstFotos = findViewById(R.id.lstImagenes);

        this.fotos = new ArrayList<Foto>();
        this.lstFotos.setLayoutManager(mLayoutManager);
        this.adaptador_fotos = new AdaptadorListaFoto(fotos);
        this.lstFotos.setAdapter(adaptador_fotos);
        this.lstFotos.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        this.adaptador_fotos.setItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onMultimediaSeleccionado(int posicion) {
                Log.d("Verificacion", fotos.get(posicion).getNombreImagen());
            }

            @Override
            public void onMenuContextual(int posicion, MenuItem menu) {
                switch (menu.getItemId()){
                    case R.id.MultimediaFavorita:

                        int comprobar_foto = gestor_fotos_favoritas.buscarFotoRegistradaBBDD(ventana_foto.this,
                                fotos.get(posicion).getIdImagen(), ventana_menu_principal.usuario_sesion_iniciada);

                        if(comprobar_foto == 0){
                            gestor_fotos_favoritas.insertarDatosTablaFotosFavoritasBBDD(ventana_foto.this,
                                    ventana_menu_principal.usuario_sesion_iniciada, fotos.get(posicion).getIdImagen());
                            mostrarNotificacion("Nueva FOTO en lista personal");
                        }
                        else if(comprobar_foto > 0){
                            mostrarNotificacion("Esta FOTO ya esta en su lista");
                        }
                        break;

                    case R.id.MultimediaComprimir:

                        Bitmap foto_comprimir = fotos.get(posicion).getFoto();
                        gestor_fotos.guardarComprimirFoto(foto_comprimir);
                        mostrarNotificacion("La FOTO ha sido comprimida a JPEG con EXITO");
                        mostrarNotificacion("Ubicacion: "+Environment.getExternalStorageDirectory() +
                                "/ImagenesComprimidos");
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

        int numero_fotos_sistema = obtenerNumeroFotos();

        if(numero_fotos_sistema != 0){

            this.id_fotos = gestor_fotos.getListaFotosBBDD(ventana_foto.this,
                    numero_fotos_sistema);

            for(int i = 0; i < this.id_fotos.length; i++){

                String parametro_nombre_foto = gestor_fotos.buscarDatosFotosBBDD(ventana_foto.this,
                        this.id_fotos[i], "NombreFoto");

                String parametro_descripcion = gestor_fotos.buscarDatosFotosBBDD(ventana_foto.this,
                        this.id_fotos[i], "DescripcionFoto");

                Bitmap bitmap =  gestor_fotos.buscarImagenFotoBBDD(ventana_foto.this,
                        this.id_fotos[i],"ImagenFoto");

                fotos.add(new Foto(this.id_fotos[i], parametro_nombre_foto, parametro_descripcion, bitmap));
            }
        }
        else{
            fotos.add(new Foto(" No disponible", " No disponible",
                    " No disponible", null));
        }
    }

    /**
     *
     * Descripcion: Metodo que permite obtener el nuemero de fotos totales en la aplicacion
     *
     * @return un entero con el numero de fotos del usuario en la aplicacion
     */
    private int obtenerNumeroFotos(){
        return this.gestor_fotos.getNumeroTotalFotosBBDD(ventana_foto.this);
    }

    /**
     *
     * Descripcion: Metodo que notifica al usuario de una accion
     *
     * @param cadena con el mensaje personaliszado dependiendo de la situacion
     */
    private void mostrarNotificacion(String cadena){

        this.notificacion = Toast.makeText(ventana_foto.this, cadena,
                Toast.LENGTH_LONG);
        this.notificacion.show();
    }
}