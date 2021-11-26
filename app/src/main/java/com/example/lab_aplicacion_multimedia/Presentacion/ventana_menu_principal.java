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
import com.example.lab_aplicacion_multimedia.Persistencia.UsuarioDAO;
import com.example.lab_aplicacion_multimedia.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ventana_menu_principal extends AppCompatActivity {

    public static String usuario_sesion_iniciada;

    private BottomNavigationView navigation;
    private String nombre_usuario_registrado;
    private Toast notification;
    private Toolbar toolbar;
    private UsuarioDAO gestor_perfil = new UsuarioDAO();
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

        notification = Toast.makeText(this, "Bienvenido a Music Player BBDD Multimedia "
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

        usuario_sistema = inicializarDatosPerfil();

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
                    break;
                case R.id.audio_menu:
                    break;
                case R.id.video_menu:
                    break;
                case R.id.perfil:

                    usuario_sistema = inicializarDatosPerfil();
                    Bitmap imagen_perfil = inicializarImagenPerfil();

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.inicio_fragment, new fragment_perfil(usuario_sistema)).commit();

                    break;
            }
            return false;
        }
    };

    /**
     *
     * Descripcion: Obtener los datos tipo BLOB de la base de datos
     *
     * @return bitmap
     */
    private Bitmap inicializarImagenPerfil(){

        Bitmap bitmap = gestor_perfil.buscarImagen(ventana_menu_principal.this, nombre_usuario_registrado,
                "ImagenPerfil");

        return bitmap;

    }

    /**
     *
     * Descripcion: Metodo para inicializar el usuario que ha iniciado sesion del sistema
     *
     * @return objeto tipo usuario
     */
    private Usuario inicializarDatosPerfil(){

        Usuario usuario;

        String nombre_usuario = nombre_usuario_registrado;

        String nombre = gestor_perfil.buscarDatosUsuarioRegistrado(ventana_menu_principal.this,
                nombre_usuario_registrado, "Nombre");
        String password = gestor_perfil.buscarDatosUsuarioRegistrado(ventana_menu_principal.this,
                nombre_usuario_registrado,"Password");
        String telefono = gestor_perfil.buscarDatosUsuarioRegistrado(ventana_menu_principal.this,
                nombre_usuario_registrado,"Telefono");
        String correo_electronico = gestor_perfil.buscarDatosUsuarioRegistrado(ventana_menu_principal.this,
                nombre_usuario_registrado,"CorreoElectronico");
        String fecha_nacimiento = gestor_perfil.buscarDatosUsuarioRegistrado(ventana_menu_principal.this,
                nombre_usuario_registrado,"FechaNacimiento");
        Bitmap foto_perfil = gestor_perfil.buscarImagen(ventana_menu_principal.this,
                nombre_usuario_registrado, "ImagenPerfil");

        usuario = new Usuario(nombre_usuario, password, telefono, correo_electronico, fecha_nacimiento, foto_perfil);

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

                break;

            case R.id.cerrar_sesion:

                break;

            case R.id.action_configuracon:

                break;
        }
        return super.onOptionsItemSelected(item);
    }


}