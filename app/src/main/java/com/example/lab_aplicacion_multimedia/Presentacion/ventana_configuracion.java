package com.example.lab_aplicacion_multimedia.Presentacion;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.lab_aplicacion_multimedia.Dominio.FotoFavorita;
import com.example.lab_aplicacion_multimedia.Dominio.Usuario;
import com.example.lab_aplicacion_multimedia.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ventana_configuracion extends AppCompatActivity {

    private Usuario usuario_actual = new Usuario();
    private FotoFavorita gestor_fotolist = new FotoFavorita();

    private String nombre_usuario_registrado;
    private String[] id_fotos;

    private EditText txtNuevoEmail;
    private EditText txtNuevoPassword;
    private EditText txtNuevoTelefono;
    private EditText txtNuevoFechaNacimiento;
    private Button btnModificarDatos;
    private Button btnBorrarCuenta;
    private Button btnCambiarFoto;
    private Button btnSubirFoto;
    private ImageView ivNuevaFotoPerfil;
    private Toast notificacion;

    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        inicializarDatos();
        oyentesBotonesConfiguracion();
    }

    /**
     *
     * Descripcion: Metodo que permite inicializar los datos de la ventana configuracion
     *
     */
    private void inicializarDatos(){

        this.txtNuevoEmail = findViewById(R.id.txtCambiar_email);
        this.txtNuevoPassword = findViewById(R.id.txtCambiar_Password);
        this.txtNuevoTelefono = findViewById(R.id.txtCambiar_Telefono);
        this.txtNuevoFechaNacimiento = findViewById(R.id.txtCambiar_FechaNacimiento);

        this.ivNuevaFotoPerfil = (ImageView) findViewById(R.id.imageViewConfiguracion);

        this.btnModificarDatos = findViewById(R.id.btnModificarDatos);
        this.btnBorrarCuenta = findViewById(R.id.btnBorrarCuenta);
        this.btnCambiarFoto = findViewById(R.id.btnAniadirFoto);
        this.btnSubirFoto = findViewById(R.id.btnBuscarFoto);

        Bundle bundle = getIntent().getExtras();
        this.nombre_usuario_registrado = bundle.getString("nombre_usuario_registrado");
    }

    /**
     *
     * Descripcion: Metodo que contiene los oyentes de los botones asociados a esta ventana
     *
     */
    private void oyentesBotonesConfiguracion(){

        /**
         * Descripcion: Oyente que permite acceder a las la fotos de la galeria
         *
         */
        this.btnSubirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(
                        ventana_configuracion.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        /**
         *
         * Descripcion: Oyente que permite aniadir dicha foto a la base de datos y asociarla al
         * usuario
         *
         */
        this.btnCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    usuario_actual.updateDataImagenBBDD(ventana_configuracion.this,
                            nombre_usuario_registrado, imageViewToByte(ivNuevaFotoPerfil));

                    mostrarNotificacion("Nueva foto de perfil");
                    ivNuevaFotoPerfil.setImageResource(R.mipmap.ic_launcher);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        /**
         *
         * Descripcion: Oyente que modifica los datos de un usario registrado en el sistema
         *
         */
        this.btnModificarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtNuevoEmail.getText().toString().equals("")) {
                    mostrarNotificacion("Correo no cambiado");
                } else {
                    usuario_actual.updateParametroUsuarioBBDD(ventana_configuracion.this,
                            nombre_usuario_registrado, "CorreoElectronico",
                            txtNuevoEmail.getText().toString());

                    mostrarNotificacion("Nuevo dato: "+ usuario_actual.buscarDatoUsuarioBBDD(
                            ventana_configuracion.this, nombre_usuario_registrado, "CorreoElectronico"));
                }

                if (txtNuevoPassword.getText().toString().equals("")) {
                    mostrarNotificacion("Contrasena no cambiada");
                }
                else {
                    usuario_actual.updateParametroUsuarioBBDD(ventana_configuracion.this,
                            nombre_usuario_registrado, "Password", txtNuevoPassword.getText().toString());

                    mostrarNotificacion("Nuevo dato: "+ usuario_actual.buscarDatoUsuarioBBDD(
                            ventana_configuracion.this, nombre_usuario_registrado, "Password"));
                }

                if(txtNuevoTelefono.getText().toString().equals("")){
                    mostrarNotificacion("Telefono no cambiado");
                }
                else {
                    usuario_actual.updateParametroUsuarioBBDD(ventana_configuracion.this,
                            nombre_usuario_registrado, "Telefono", txtNuevoTelefono.getText().toString());

                    mostrarNotificacion("Nuevo dato: "+ usuario_actual.buscarDatoUsuarioBBDD(
                            ventana_configuracion.this, nombre_usuario_registrado, "Telefono"));
                }

                if(txtNuevoFechaNacimiento.getText().toString().equals("")){
                    mostrarNotificacion("Fecha de nacimiento no cambiado");
                }
                else {
                    usuario_actual.updateParametroUsuarioBBDD(ventana_configuracion.this,
                            nombre_usuario_registrado, "FechaNacimiento", txtNuevoFechaNacimiento.getText().toString());

                    mostrarNotificacion("Nuevo dato: "+ usuario_actual.buscarDatoUsuarioBBDD(
                            ventana_configuracion.this, nombre_usuario_registrado, "FechaNacimiento"));
                }
            }
        });

        /**
         *
         * Descripcion: Oyente que permite elimianar un usuario y sus canciones asociadas
         *
         */
        this.btnBorrarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ventana_configuracion.this)
                        .setTitle("¿Está seguro que desea elimar su cuenta?")
                        .setMessage("Si elima la cuenta se perderan todos sus datos.")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                borrarDatosAsociados();

                                usuario_actual.eliminarUsuarioBBDD(ventana_configuracion.this,
                                        nombre_usuario_registrado);
                                Intent ventana_login = new Intent(ventana_configuracion.this,
                                        ventana_inicio_sesion.class);
                                startActivity(ventana_login );
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
    }

    /**
     *
     * Descripcion: Solicitar permisos de galeria Android
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "No tienes permisos de acceso",
                        Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     *
     * Descripcion: Obtener datos galeria
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ivNuevaFotoPerfil.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     *
     * Descripcion: Metodo que notifica al usuario de una accion
     *
     * @param cadena con el mensaje personaliszado dependiendo de la situacion
     */
    private void mostrarNotificacion(String cadena){

        this.notificacion = Toast.makeText(ventana_configuracion.this, cadena,
                Toast.LENGTH_LONG);
        this.notificacion.show();
    }

    /**
     *
     * Descripcion: Metodo para obtener un arry de bits a partir de una imagen
     *
     * @param image
     * @return
     */
    public static byte[] imageViewToByte(ImageView image) {

        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    /**
     *
     * Descripcion: Metodo que para borrar los datos de un usuario en el sistema
     *
     */
    private void borrarDatosAsociados(){
        borrarDatosFotosAsociados();
    }

    /**
     *
     * Descripcion: Metodo que obtiene las fotos asociadas al usaurio que quiere eliminar su
     * cuenta en el sistema y borra dichas fotos
     *
     */
    private void borrarDatosFotosAsociados(){

        int numero_fotos_usuario = obtenerNumeroFotosUsuario();

        if(numero_fotos_usuario != 0) {

            this.id_fotos = gestor_fotolist.getListaFotosFavoritas(ventana_configuracion.this,
                    ventana_menu_principal.usuario_sesion_iniciada, numero_fotos_usuario);

            for(int i = 0; i < this.id_fotos.length; i++) {

                gestor_fotolist.eliminarFotoFavoritosBBDD(ventana_configuracion.this,
                        ventana_menu_principal.usuario_sesion_iniciada, id_fotos[i]);
            }
        }
        else{
            mostrarNotificacion("Ninguna Foto asociada que eliminar");
        }
    }

    /**
     *
     * Descripcion: Metodo que permite obtener el numero total de fotos favoritas de un
     * determinado usuario
     *
     * @return entero con la cantidad de fotos favoritas dado un nombre de usuario
     */
    private int obtenerNumeroFotosUsuario(){

        return this.gestor_fotolist.getNumeroFotosUsuarioBBDD(ventana_configuracion.this,
                ventana_menu_principal.usuario_sesion_iniciada);
    }
}

