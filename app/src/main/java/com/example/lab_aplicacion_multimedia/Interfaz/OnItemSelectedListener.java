package com.example.lab_aplicacion_multimedia.Interfaz;

import android.view.MenuItem;

/**
 *
 * Descripcion: Interfaz usada en las clases del paquete Adaptadores
 *
 */
public interface OnItemSelectedListener {

    void onArtistaSeleccionado(int posicion);
    void onMenuContextualCancion(int posicion, MenuItem menu);

}
