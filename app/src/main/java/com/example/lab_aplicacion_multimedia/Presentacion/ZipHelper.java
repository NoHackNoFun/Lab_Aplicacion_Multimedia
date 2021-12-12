package com.example.lab_aplicacion_multimedia.Presentacion;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipHelper {

    public void zip(File path, String file_zip_name, String new_name) throws IOException{

        File [] files =  path.listFiles();

        if(files.length == 0){
            throw new IllegalArgumentException("Archivos no encontrados "+
                    path.getAbsolutePath());
        }
        FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory()
                +"/AudiosComprimidos/"+new_name);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        for (File zipThis : files){
            if(zipThis.getName().equals(file_zip_name)){
                FileInputStream fis = new FileInputStream(zipThis);
                ZipEntry zipEntry = new ZipEntry(zipThis.getName());
                zipOut.putNextEntry(zipEntry);
                byte[] bytes = new byte [2048];
                int length;

                while((length = fis.read(bytes)) >= 0){
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
                zipOut.close();
                fos.close();
            }
        }
    }
}
