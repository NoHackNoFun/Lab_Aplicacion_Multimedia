package com.example.lab_aplicacion_multimedia.Constantes;

/**
 *
 * Descripcion: Clase con las constantes que representan los campos o registros de las tablas
 * de la BBDD
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

    //Constantes campos tabla VIDEO (4)

    public static final String NOMBRE_TABLA_VIDEO_BBDD = "Video";
    public static final String CAMPO_VIDEO_ID = "IdVideo";
    public static final String CAMPO_VIDEO_NOMBRE = "NombreVideo";
    public static final String CAMPO_VIDEO_DESCRIPCION = "DescripcionVideo";
    public static final String CAMPO_VIDEO_IMAGEN = "ImagenVideo";

    //Constantes campos tabla VIDEOS_FAVORITAS (3)

    public static final String NOMBRE_TABLA_VIDEOS_FAVORITOS_BBDD = "VideosFavoritos";
    public static final String CAMPO_VIDEO_FAVORITO_ID = "IdVideoFavorito";
    public static final String CAMPO_VIDEO_FAVORITO_NOMBRE_USUARIO ="VideoFavoritaNombreUsuario";
    public static final String CAMPO_VIDEO_FAVORITO_ID_VIDEO = "VideoFavoritoIdVideo";

    //Constantes campos tabla CANCIONES_FAVORITAS (3)

    public static final String NOMBRE_TABLA_CANCIONES_FAVORITAS_BBDD = "CancionesFavoritas";
    public static final String CAMPO_CANCION_FAVORITA_ID = "IdCancionFavorita";
    public static final String CAMPO_CANCION_FAVORITA_NOMBRE_USUARIO ="CancionFavoritaNombreUsuario";
    public static final String CAMPO_CANCION_FAVORITA_ID_CANCION = "CancionFavoritoIdCancion";

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

    public static final String CREAR_TABLA_VIDEO =
            "CREATE TABLE "+NOMBRE_TABLA_VIDEO_BBDD+" ("+CAMPO_VIDEO_ID+" TEXT, "+CAMPO_VIDEO_NOMBRE+
                    " TEXT, "+CAMPO_VIDEO_DESCRIPCION+" TEXT, "+CAMPO_VIDEO_IMAGEN+" BLOB)";

    public static final String CREAR_TABLA_VIDEO_FAVORITO =
            "CREATE TABLE "+NOMBRE_TABLA_VIDEOS_FAVORITOS_BBDD+" ("+CAMPO_VIDEO_FAVORITO_ID+
                    " TEXT, "+CAMPO_VIDEO_FAVORITO_NOMBRE_USUARIO+
                    " TEXT, "+CAMPO_VIDEO_FAVORITO_ID_VIDEO+" TEXT)";

    public static final String CREAR_TABLA_CANCION_FAVORITA =
            "CREATE TABLE "+NOMBRE_TABLA_CANCIONES_FAVORITAS_BBDD+" ("+CAMPO_CANCION_FAVORITA_ID+
                    " TEXT, "+CAMPO_CANCION_FAVORITA_NOMBRE_USUARIO+
                    " TEXT, "+CAMPO_CANCION_FAVORITA_ID_CANCION+" TEXT)";
}
