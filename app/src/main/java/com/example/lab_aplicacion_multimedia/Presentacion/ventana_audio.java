package com.example.lab_aplicacion_multimedia.Presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lab_aplicacion_multimedia.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ventana_audio extends AppCompatActivity {

    private ListView lstCanciones;
    private String[] items_canciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_audio);

        inicializarDatos();
        runtimePermission();
    }

    /**
     *
     * Descripcion: Obtenemos una referencia a una lista grafica en este caso
     * de canciones
     *
     */
    private void inicializarDatos(){
        lstCanciones = findViewById(R.id.lstAudios);
    }

    /**
     *
     * Descripcion: Obtener permisos para las canciones
     *
     */
    public void runtimePermission() {

        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {

                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport
                                                             multiplePermissionsReport) {
                        displaySongs();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list,
                                                                   PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    /**
     *
     * Descripcion: Metodo que permite encontrar las canciones que se encuentren en el sistema
     * que tengan un formato .mp3 o .wav
     *
     * @param file
     * @return
     */
    public ArrayList<File> findSong (File file) {

        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        for (File singlefile: files) {
            if (singlefile.isDirectory() && !singlefile.isHidden()) {
                arrayList.addAll(findSong(singlefile));
            }
            else {
                if (singlefile.getName().endsWith(".mp3") || singlefile.getName().endsWith(".wav")) {
                    arrayList.add(singlefile);
                }
            }
        }
        return arrayList;
    }

    /**
     *
     * Descripcion: Metodo que muestra las canciones encontradas
     *
     */
    void displaySongs() {

        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());

        this.items_canciones = new String[mySongs.size()];
        for (int i = 0; i<mySongs.size();i++) {
            this.items_canciones[i] = mySongs.get(i).getName().toString().replace(".mp3", "")
                    .replace(".wav", "");
        }

        customAdapter customAdapter = new customAdapter();
        lstCanciones.setAdapter(customAdapter);

        lstCanciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String songName = (String) lstCanciones.getItemAtPosition(i);

                startActivity(new Intent(getApplicationContext(), ventana_reproduccion_audio.class)
                        .putExtra("lista_canciones", mySongs)
                        .putExtra("nombre_cancion_s", songName)
                        .putExtra("index", i));
            }
        });
    }

    /**
     *
     * Descripcion: clase adaptador
     *
     */
    class customAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items_canciones.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View myView = getLayoutInflater().inflate(R.layout.layout_item_audio, null);
            TextView txt_nombre_cancion = myView.findViewById(R.id.lblNombreCancion);
            txt_nombre_cancion.setSelected(true);
            txt_nombre_cancion.setText(items_canciones[i]);

            return myView;
        }
    }
}