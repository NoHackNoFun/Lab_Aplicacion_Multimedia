package com.example.lab_aplicacion_multimedia.Presentacion;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lab_aplicacion_multimedia.Dominio.Usuario;
import com.example.lab_aplicacion_multimedia.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ventana_menu_principal extends AppCompatActivity {

    //Variable global con el nombre del usuario que esta actualmente en el sistema
    public static String usuario_sesion_iniciada;

    private BottomNavigationView navigation;
    private String nombre_usuario_registrado;
    private Toast notification;
    private Toolbar toolbar;
    private Usuario usuario_sistema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        inicializarPasoDatos();
        inicializarDatosNavegacion();
        openInitialFragment();
    }

    /**
     *
     * Descripcion: Metodo que permite identificar el usuario en el sistema gracias al parametro que
     * se pasa de la ventana anterior
     *
     */
    private void inicializarPasoDatos(){
        this.toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        this.nombre_usuario_registrado = bundle.getString("nombre_usuario_registrado");
        usuario_sesion_iniciada = bundle.getString("nombre_usuario_registrado");

        notification = Toast.makeText(this, "Bienvenido al reproductor Multimedia "
                + this.nombre_usuario_registrado, Toast.LENGTH_LONG);
        notification.show();
    }

    /**
     *
     * Descripcion: Metodo para inicializar los datos respectivos a la navegacion del sistema
     *
     */
    private void inicializarDatosNavegacion(){

        navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     *
     * Descripcion: Inicializa con el fragmento inicial del sistema
     *
     */
    private void openInitialFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment, new fragment_inicio()).commit();
    }

    /**
     *
     * Descripcion: Permite la navegacion entre paneles
     *
     */
    private final BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch(item.getItemId()){
                case R.id.inicio:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment, new fragment_inicio()).commit();
                    break;

                case R.id.imagenes_menu:
                    Intent parte_imagenes = new Intent(ventana_menu_principal.this,
                            ventana_foto.class);
                    startActivity(parte_imagenes);
                    break;

                case R.id.audio_menu:
                    Intent parte_audio = new Intent(ventana_menu_principal.this,
                            ventana_audio.class);
                    startActivity(parte_audio);
                    break;

                case R.id.video_menu:
                    Intent parte_video = new Intent(ventana_menu_principal.this,
                            ventana_video.class);
                    startActivity(parte_video);
                    break;

                case R.id.perfil:
                    usuario_sistema = inicializarDatosPerfil();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.inicio_fragment, new fragment_perfil(usuario_sistema)).commit();
                    break;
            }
            return false;
        }
    };

    /**
     *
     * Descripcion: Metodo para inicializar el usuario que ha iniciado sesion del sistema
     *
     * @return objeto tipo usuario
     */
    private Usuario inicializarDatosPerfil(){

        Usuario usuario = new Usuario();
        String nombre_usuario = nombre_usuario_registrado;

        String password = usuario.buscarDatoUsuarioBBDD(ventana_menu_principal.this,
                nombre_usuario_registrado,"Password");
        String telefono = usuario.buscarDatoUsuarioBBDD(ventana_menu_principal.this,
                nombre_usuario_registrado,"Telefono");
        String correo_electronico = usuario.buscarDatoUsuarioBBDD(ventana_menu_principal.this,
                nombre_usuario_registrado,"CorreoElectronico");
        String fecha_nacimiento = usuario.buscarDatoUsuarioBBDD(ventana_menu_principal.this,
                nombre_usuario_registrado,"FechaNacimiento");
        Bitmap foto_perfil = usuario.buscarImagenUsarioBBDD(ventana_menu_principal.this,
                nombre_usuario_registrado, "ImagenPerfil");

        usuario = new Usuario(nombre_usuario, password, telefono, correo_electronico,
                fecha_nacimiento, foto_perfil);

        return usuario;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.informacion_app:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Acerca de...");
                builder.setMessage("Aplicaci??n creada por Carlos y Guadalupe");
                builder.setPositiveButton("OK",null);
                builder.create();
                builder.show();
                break;

            case R.id.cerrar_sesion:
                Intent cerrar_sesion = new Intent(ventana_menu_principal.this,
                        ventana_inicio_sesion.class);
                startActivity(cerrar_sesion);
                break;

            case R.id.action_configuracon:
                Intent configuracion = new Intent(ventana_menu_principal.this,
                        ventana_configuracion.class);
                configuracion.putExtra("nombre_usuario_registrado", this.nombre_usuario_registrado);
                startActivity(configuracion);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}