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

    //Constantes campos tabla FOTO (4)

    public static final String NOMBRE_TABLA_FOTO_BBDD = "Foto";
    public static final String CAMPO_FOTO_ID = "IdFoto";
    public static final String CAMPO_FOTO_NOMBRE = "NombreFoto";
    public static final String CAMPO_FOTO_DESCRIPCION = "DescripcionFoto";
    public static final String CAMPO_FOTO_IMAGEN = "ImagenFoto";

    //Constantes campos tabla FOTOS_FAVORITAS (3)

    public static final String NOMBRE_TABLA_FOTOS_FAVORITAS_BBDD = "FotosFavoritas";
    public static final String CAMPO_FOTO_FAVORITA_ID = "IdFotoFavorita";
    public static final String CAMPO_FOTO_FAVORITA_NOMBRE_USUARIO ="FotoFavoritaNombreUsuario";
    public static final String CAMPO_FOTO_FAVORITA_ID_FOTO = "FotoFavoritaIdFoto";

    //Constantes TABLAS

    public static final String CREAR_TABLA_USUARIO =
            "CREATE TABLE "+NOMBRE_TABLA_USARIO_BBDD+" ("+CAMPO_USUARIO_NOMBRE_USUARIO+" TEXT, "+CAMPO_USUARIO_PASSWORD+
                    " TEXT, "+CAMPO_USUARIO_TELEFONO+" TEXT, "+CAMPO_USUARIO_CORREO+" TEXT, "+CAMPO_USUARIO_FECHA_NACIMIENTO+
                    " TEXT, "+CAMPO_USUARIO_IMAGEN_PERFIL+" BLOB)";

    public static final String CREAR_TABLA_FOTO =
            "CREATE TABLE "+NOMBRE_TABLA_FOTO_BBDD+" ("+CAMPO_FOTO_ID+" TEXT, "+CAMPO_FOTO_NOMBRE+
                    " TEXT, "+CAMPO_FOTO_DESCRIPCION+" TEXT, "+CAMPO_FOTO_IMAGEN+" BLOB)";

    public static final String CREAR_TABLA_FOTO_FAVORITA =
            "CREATE TABLE "+NOMBRE_TABLA_FOTOS_FAVORITAS_BBDD+" ("+CAMPO_FOTO_FAVORITA_ID+
                    " TEXT, "+CAMPO_FOTO_FAVORITA_NOMBRE_USUARIO+
                    " TEXT, "+CAMPO_FOTO_FAVORITA_ID_FOTO+" TEXT)";
}
