package com.example.lab_aplicacion_multimedia.Dominio;

import android.graphics.Bitmap;

public class Usuario {

    private String nombre_usario_pk; //Atributo que realiza la funcion de clave primaria
    private String correo_electronico;
    private String password;
    private String telefono;
    private String fecha_nacimiento;
    private Bitmap foto_perfil;

    /**
     *
     * @param nombre_usario_pk
     * @param correo_electronico
     * @param password
     * @param telefono
     * @param fecha_nacimiento
     * @param foto_perfil
     */
    public Usuario(String nombre_usario_pk, String correo_electronico, String password,
                   String telefono, String fecha_nacimiento, Bitmap foto_perfil){

        this.nombre_usario_pk = nombre_usario_pk;
        this.correo_electronico = correo_electronico;
        this.password = password;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.foto_perfil = foto_perfil;

    }

    public String getNombre_usario_pk(){
        return this.nombre_usario_pk;
    }

    public void setNombre_usario_pk(String nu){
        this.nombre_usario_pk = nu;
    }

    public String getCorreo_electronico(){
        return this.correo_electronico;
    }

    public void setCorreo_electronico(String ce){
        this.correo_electronico = correo_electronico;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String ps){
        this.password = ps;
    }
}