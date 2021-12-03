package com.example.lab_aplicacion_multimedia.Interfaz;

import android.view.MenuItem;

/**
 *
 * Descripcion: Interfaz usada en las clases del paquete Adaptadores
 *
 */
public interface OnItemSelectedListener {

    void onMultimediaSeleccionado(int posicion);
    void onMenuContextual(int posicion, MenuItem menu);

}
