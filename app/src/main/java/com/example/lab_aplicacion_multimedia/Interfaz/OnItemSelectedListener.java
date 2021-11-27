package com.example.lab_aplicacion_multimedia.Interfaz;

import android.view.MenuItem;

/**
 *
 * Descripcion: Interfaz usada en las clases del paquete Adaptadores
 *
 */
public interface OnItemSelectedListener {

    void onFotoSeleccionado(int posicion);
    void onMenuContextualFoto(int posicion, MenuItem menu);

}
