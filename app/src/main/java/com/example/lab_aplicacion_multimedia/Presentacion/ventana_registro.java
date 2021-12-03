package com.example.lab_aplicacion_multimedia.Presentacion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.graphics.Color;
import android.widget.Toast;

import com.example.lab_aplicacion_multimedia.Dominio.Usuario;
import com.example.lab_aplicacion_multimedia.R;

import java.io.ByteArrayOutputStream;

public class ventana_registro extends AppCompatActivity {

    private EditText txt_nombre_usuario;
    private EditText txt_password;
    private EditText txt_confirmar_password;
    private EditText txt_telefono;
    private EditText txt_correo_electronico;
    private EditText txt_fecha_nacimiento;
    private ImageView iv_perfil;

    private Usuario gestor_usuario = new Usuario();
    private Usuario usuario_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_registro);

        inicializarDatos();
    }

    /**
     *
     * Descripcion: Metodo para inicializar los datos de la ventan de registro
     *
     */
    private void inicializarDatos(){

        txt_nombre_usuario = findViewById(R.id.txt_Nombre_Usuario);
        txt_password = findViewById(R.id.txt_Password);
        txt_confirmar_password = findViewById(R.id.txt_Password_Confirmar);
        txt_telefono = findViewById(R.id.txt_Telefono);
        txt_correo_electronico = findViewById(R.id.txt_Correo_Electronico);
        txt_fecha_nacimiento = findViewById(R.id.txt_Fecha_Nacimiento);
        iv_perfil = findViewById(R.id.img_Registro);
    }

    /**
     *
     * Descripcion: Insertar los datos del usuario en la BBDD para que queden registrados
     *
     * @param view
     */
    public void OyenteRealizarRegistro(View view){

        boolean dato_correo_verificado = verificarCorreo();
        int dato_usuario_registrado_sistema = verificarUsusarioRegistrado();
        int validacion_registro_datos = 0;

        if(txt_nombre_usuario.getText().toString().equals("") || dato_usuario_registrado_sistema == 0
                || txt_password.getText().toString().equals("") || txt_confirmar_password.getText().toString().equals("")
                    || dato_correo_verificado == false){

            dialogoAviso("Registro Incompleto. Por favor rellene los campos que faltan.",
                    ventana_registro.this);

            comprobarDatosFormularioRegistro(view);
        }
        else{
            if(txt_password.getText().toString().equals(txt_confirmar_password.getText().toString())){
                txt_nombre_usuario.setTextColor(Color.rgb(0,143,57));
                txt_password.setTextColor(Color.rgb(0,143,57));
                txt_confirmar_password.setTextColor(Color.rgb(0,143,57));
                txt_correo_electronico.setTextColor(Color.rgb(0,143,57));

                validacion_registro_datos = insertarDatosUsuario();

                if(validacion_registro_datos == 1){
                    dialogoAviso("El usuario ha sido registrado con exito.", ventana_registro.this);
                    Intent volver_ventana_login = new Intent(this, ventana_inicio_sesion.class);
                    startActivity(volver_ventana_login);
                }
                else {
                    Toast.makeText(ventana_registro.this, "NO OK", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     *
     * Descripcion: Metodo que permite insertar un usuario en el sistema
     *
     * @return int que permite conocer si la operacion se ha realizado con exito
     */
    private int insertarDatosUsuario(){

        int dato_verificado = 0;
        String cadena_formato_fecha = null; //No es un dato necesario para registrarse
        String cadena_formato_telefono = null; //No es un dato necesario para registrarse

        if(txt_fecha_nacimiento.getText().toString().equals("")){
            cadena_formato_fecha = "Ninguno";
        }
        else{
            cadena_formato_fecha = txt_fecha_nacimiento.getText().toString();
        }

        if(txt_telefono.getText().toString().equals("")){
            cadena_formato_telefono = "Ninguno";
        }
        else{
            cadena_formato_telefono = txt_telefono.getText().toString();
        }

        byte [] data = convertidorImageToByte(iv_perfil);

        usuario_app = new Usuario(txt_nombre_usuario.getText().toString(),
                txt_password.getText().toString(),
                cadena_formato_telefono,
                txt_correo_electronico.getText().toString(),
                cadena_formato_fecha,
                BitmapFactory.decodeByteArray(data, 0, data.length));

        usuario_app.insertarDatosUsuarioBBDD(ventana_registro.this, usuario_app, data);
        dato_verificado = 1;

        return dato_verificado;
    }

    /**
     *
     * Descripcion: Metodo que comprueba los datos que el usuario no ha completado
     *
     * @param view
     */
    private void comprobarDatosFormularioRegistro(View view){

        //Datos Nombre Usuario

        int usuario_disponible = verificarUsusarioRegistrado();

        if(txt_nombre_usuario.getText().toString().equals("")){
            txt_nombre_usuario.setError("Introduzca un nombre de usuario");
            txt_nombre_usuario.setTextColor(Color.rgb(205,92,92));
        }
        else{
            if(usuario_disponible == 0){
                txt_nombre_usuario.setError("Nombre no disponible");
                txt_nombre_usuario.setTextColor(Color.rgb(205,92,92));
                dialogoAviso("Registro Incompleto. Nombre de Usuario no disponible.",
                        ventana_registro.this);
            }
            else {
                txt_nombre_usuario.setTextColor(Color.rgb(0,143,57));
            }
        }

        //Datos Passsword

        if(txt_password.getText().toString().equals("")){
            txt_password.setError("Introduzca una contrasena");
            txt_password.setTextColor(Color.rgb(205,92,92));
        }
        else{
            txt_password.setTextColor(Color.rgb(0,143,57));
        }

        //Datos Password Confirmada

        if(txt_confirmar_password.getText().toString().equals("")){
            txt_confirmar_password.setError("Por favor, repita la contrasena");
            txt_confirmar_password.setTextColor(Color.rgb(205,92,92));
        }
        else{
            if(txt_password.getText().toString().equals(txt_confirmar_password.getText().toString())){
                txt_confirmar_password.setTextColor(Color.rgb(0,143,57));
            }
            else{
                txt_confirmar_password.setError("Las contrasenas no coinciden");
                txt_confirmar_password.setTextColor(Color.rgb(205,92,92));
                dialogoAviso("Las contrasenas no coinciden.", ventana_registro.this);
            }
        }

        //Datos correo electronico

        boolean correo_electronico_comprobacion = verificarCorreo();

        if(txt_correo_electronico.getText().toString().equals("") ||
                correo_electronico_comprobacion == false){

            txt_correo_electronico.setError("Introduzca un correo electronico valido");
            txt_correo_electronico.setTextColor(Color.rgb(205,92,92));
        }
        else{
            txt_correo_electronico.setTextColor(Color.rgb(0,143,57));
        }
    }

    /**
     *
     * Descripcion: Metodo que permite saber si el nombre usuario ya esta registrado en el sistema.
     * El nombre de usuario realiza la funcion de clave primaria (pk) y solo puede estar una ves
     *
     * @return
     */
    private int verificarUsusarioRegistrado(){

        int dato_verificado = -1;
        String cadena_nombre_comprobado = null;

        if(txt_nombre_usuario.getText().toString() != null){

            cadena_nombre_comprobado = gestor_usuario.buscarDatoUsuarioBBDD(ventana_registro.this,
                    txt_nombre_usuario.getText().toString(), "NombreUsuario");

            if(cadena_nombre_comprobado != null){
                dato_verificado = 0; //Usuario en uso
            }
            else{
                dato_verificado = 1; //Usuario disponible
            }
        }
        return dato_verificado;
    }

    /**
     *
     * Descripcion: Permite conocer si el dato de correo es correcto para completar el registro
     *
     * @return boolean
     */
    private boolean verificarCorreo(){

        boolean correo_correcto = false;
        String correo_introducido = txt_correo_electronico.getText().toString();

        for(int i = 0; i<correo_introducido.length(); i++){
            if(correo_introducido.charAt(i) == '@'){
                correo_correcto = true;
            }
        }
        return correo_correcto;
    }

    /**
     *
     * Descripcion: Metodo que permite convertir la imagen asociada al imageView a bytes
     *
     * @param image
     * @return array de bytes de la imagen
     */
    public static byte[] convertidorImageToByte(ImageView image){

        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    /**
     *
     * Descripcion: Metodo que muestra un aviso al usuario dependiendo de las acciones que este realice
     *
     * @param aviso Mensaje personalizado dependiendo del mensaje del aviso
     * @param context contexto en este caso es ventana_registro.this
     */
    private void dialogoAviso(String aviso, Context context){

        AlertDialog.Builder dialogo_builder = new AlertDialog.Builder(context);
        dialogo_builder .setMessage(aviso);
        dialogo_builder.setCancelable(true);

        dialogo_builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialogo_alert = dialogo_builder.create();
        dialogo_alert.show();
    }
}