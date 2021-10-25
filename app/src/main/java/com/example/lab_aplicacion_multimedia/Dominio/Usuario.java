package com.example.lab_aplicacion_multimedia.Dominio;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.lab_aplicacion_multimedia.Persistencia.UsuarioDAO;

public class Usuario {

    private String nombre_usario_pk; //Atributo que realiza la funcion de clave primaria
    private String correo_electronico;
    private String password;
    private String telefono;
    private String fecha_nacimiento;
    private Bitmap foto_perfil;

    private UsuarioDAO gestor_usuario = new UsuarioDAO();

    /**
     *
     * Descripcion: Constructor con los parametros que debe tener cada usuario de la aplicacion
     *
     * @param nombre_usario_pk
     * @param password
     * @param telefono
     * @param correo_electronico
     * @param fecha_nacimiento
     * @param foto_perfil
     */
    public Usuario(String nombre_usario_pk, String password, String telefono,
                   String correo_electronico, String fecha_nacimiento, Bitmap foto_perfil){

        this.nombre_usario_pk = nombre_usario_pk;
        this.correo_electronico = correo_electronico;
        this.password = password;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.foto_perfil = foto_perfil;

    }

    /**
     *
     * Descripcion: Constructor para hacer consultas
     *
     */
    public Usuario(){

    }

    /**
     *
     * @return nombre del usuario
     */
    public String getNombreUsario_pk(){
        return this.nombre_usario_pk;
    }

    /**
     *
     * @param nu modificacion y asigancion del parametro de nombre de usuario
     */
    public void setNombreUsario_pk(String nu){
        this.nombre_usario_pk = nu;
    }

    /**
     *
     * @return correo electronico del usuario
     */
    public String getCorreoElectronico(){
        return this.correo_electronico;
    }

    /**
     *
     * @param ce modificacion y asignacion del parametro de correo electronico
     */
    public void setCorreoElectronico(String ce){
        this.correo_electronico = correo_electronico;
    }

    /**
     *
     * @return password del usuario
     */
    public String getPassword(){
        return this.password;
    }

    /**
     *
     * @param ps modificacion y asignacion del parametro de password
     */
    public void setPassword(String ps){
        this.password = ps;
    }

    /**
     *
     * @return telefono del usuario
     */
    public String getTelefono(){
        return this.telefono;
    }

    /**
     *
     * @param tf modificacion y asignacion del parametro telefono
     */
    public void setTelefono(String tf){
        this.telefono = tf;
    }

    /**
     *
     * @return fecha nacimiento del usuario
     */
    public String getFechaNacimiento(){
        return this.fecha_nacimiento;
    }

    /**
     *
     * @param fn modificacion y asignacion del parametro fecha de nacimiento
     */
    public void setFechaNacimiento(String fn){
        this.fecha_nacimiento = fn;
    }

    /**
     *
     * @return foto de perfil asociada a la cuenta del usuario
     */
    public Bitmap getFotoPerfil(){
        return this.foto_perfil;
    }

    /**
     *
     * @param fpu modificacion y asignacion del parametro foto de perfil
     */
    public void setFotoPerfil(Bitmap fpu){
        this.foto_perfil = fpu;
    }

    /**
     *
     * Descripcion: Metodo que se comunica con el gestor para encontrar un dato
     *
     * @param context
     * @param nombre_usuario
     * @param parametro
     * @return
     */
    public String buscarDatoUsuarioBBDD(Context context, String nombre_usuario, String parametro){
        return gestor_usuario.buscarDatosUsuarioRegistrado(context, nombre_usuario, parametro);
    }

    public void insertarDatosUsuarioBBDD(Context context, Usuario usuario_app, byte [] imagen){
        gestor_usuario.insertarDatosTablaUsuario(context, usuario_app, imagen);
    }
}