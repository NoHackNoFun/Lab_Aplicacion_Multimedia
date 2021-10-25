package com.example.lab_aplicacion_multimedia.Constantes;

/**
 *
 * Descripcion: Clase con las constantes que representan los campos o registros de las tablas
 * de la base de datos
 *
 */
public class Constantes {

    //Constantes campos tabla USUARIOS (6)

    public static final String NOMBRE_TABLA_USARIO_BBDD = "Usuario";
    public static final String CAMPO_USUARIO_NOMBRE_USUARIO = "NombreUsuario";
    public static final String CAMPO_USUARIO_PASSWORD = "Password";
    public static final String CAMPO_USUARIO_TELEFONO = "Telefono";
    public static final String CAMPO_USUARIO_CORREO = "CorreoElectronico";
    public static final String CAMPO_USUARIO_FECHA_NACIMIENTO = "FechaNacimiento";
    public static final String CAMPO_USUARIO_IMAGEN_PERFIL = "ImagenPerfil";

    //Constantes TABLAS

    public static final String CREAR_TABLA_USUARIO =
            "CREATE TABLE "+NOMBRE_TABLA_USARIO_BBDD+" ("+CAMPO_USUARIO_NOMBRE_USUARIO+" TEXT, "+CAMPO_USUARIO_PASSWORD+
                    " TEXT, "+CAMPO_USUARIO_TELEFONO+" TEXT, "+CAMPO_USUARIO_CORREO+" TEXT, "+CAMPO_USUARIO_FECHA_NACIMIENTO+
                    " TEXT, "+CAMPO_USUARIO_IMAGEN_PERFIL+" BLOB)";



}
