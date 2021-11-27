package com.example.lab_aplicacion_multimedia.Presentacion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.lab_aplicacion_multimedia.Dominio.Usuario;
import com.example.lab_aplicacion_multimedia.Persistencia.FotosFavoritasDAO;
import com.example.lab_aplicacion_multimedia.R;

public class ventana_inicio_sesion extends AppCompatActivity {

    private EditText txt_nombre_usuario_login;
    private EditText txt_password_login;

    private Usuario gestor_usuario_login = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_inicio_sesion);

        inicializarDatos();
    }

    /**
     *
     * Descripcion: Metodo que permite inicializar los datos de la ventana principal
     *
     */
    private void inicializarDatos(){

        txt_nombre_usuario_login = findViewById(R.id.txt_Nombre_Usuario_Login);
        txt_password_login = findViewById(R.id.txt_Password_Login);
    }

    /**
     *
     * Descripcion: Oyente asociado al boton Login que inicializa la actividad para acceder a las
     * funcionalidades sde la aplicacion
     *
     * @param view
     */
    public void OyenteRealizarLogin(View view){

        if(txt_nombre_usuario_login.getText().toString().equals("") ||
                txt_password_login.getText().toString().equals("")){

            dialogoAviso("Datos Incompletos.", ventana_inicio_sesion.this);
            verificarDatosLogin(view);
        }
        else{

            int verificar_usuario = verificarUsuarioRegistrado();
            int verificar_password = verificarPasswordRegistrado();

            if(verificar_password == 0 && verificar_usuario == 0){

                Intent actividad_app_principal = new Intent(this,
                        ventana_menu_principal.class);
                actividad_app_principal.putExtra("nombre_usuario_registrado",
                        txt_nombre_usuario_login.getText().toString());
                startActivity(actividad_app_principal);

            }
            else{
                dialogoAviso("Este usuario con esta contraseba no existe en el sistema",
                        ventana_inicio_sesion.this);
            }
        }
    }

    /**
     *
     * Descripcion: Metodo que comprueba los datos que faltan por completar
     *
     * @param view
     */
    private void verificarDatosLogin(View view){

        //Datos Nombre Usuario

        int verificar_existe_usuario = verificarUsuarioRegistrado();

        if(txt_nombre_usuario_login.getText().toString().equals("")){
            txt_nombre_usuario_login.setError("Introduzca un nombre de usuario.");
        }
        else{
            if(verificar_existe_usuario == -1){
                txt_nombre_usuario_login.setError("Este nombre de usuario no existe");
            }
            else{
                txt_nombre_usuario_login.setTextColor(Color.rgb(0,143,57));
            }
        }

        //Datos Password

        int verificar_password = verificarPasswordRegistrado();

        if(txt_password_login.getText().toString().equals("")){
            txt_password_login.setError("Introduzca una contrasena.");
        }
        else{
            if(verificar_password == -1){
                txt_password_login.setError("Contrasena no valida.");
            }
            else{
                txt_password_login.setTextColor(Color.rgb(0,143,57));
            }
        }

    }

    /**
     *
     * Descripcion: Metodo que verifica si la contrasena se corresponde con el usuario registrado
     * en el sistema
     *
     * @return int
     */
    private int verificarPasswordRegistrado(){

        int dato_verificado = -1;
        String cadena_password_usuario_sistema = null;

        if(txt_password_login.getText().toString() != null){
            cadena_password_usuario_sistema = gestor_usuario_login.buscarDatoUsuarioBBDD(
                    ventana_inicio_sesion.this,
                    txt_nombre_usuario_login.getText().toString(), "Password");

            if(cadena_password_usuario_sistema != null){
                if(txt_password_login.getText().toString().equals(cadena_password_usuario_sistema)){
                    dato_verificado = 0; //Usuario existe con esa password en el sistema
                }
            }
        }
        return dato_verificado;
    }

    /**
     *
     * Descripcion: Metodo que verifica si hay un usuario con ese nombre en el sistema
     *
     * @return int
     */
    private int verificarUsuarioRegistrado(){

        int dato_verificado = -1;
        String cadena_nombre_usuario_sistema = null;

        if(txt_nombre_usuario_login.getText().toString() != null){
            cadena_nombre_usuario_sistema = gestor_usuario_login.buscarDatoUsuarioBBDD(
                    ventana_inicio_sesion.this, txt_nombre_usuario_login.getText().toString(),
                    "NombreUsuario");

            if(cadena_nombre_usuario_sistema != null){
                dato_verificado = 0; //Usuario existe
            }
        }
        return dato_verificado;
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

    /**
     *
     * Descripcion: Oyente asociado al boton Registro que inicializa la actividad para Registrarse
     *
     * @param view
     */
    public void OyenteIniciarRegistro(View view){

        Intent actividad_registro = new Intent(this, ventana_registro.class );
        startActivity(actividad_registro);
    }
}