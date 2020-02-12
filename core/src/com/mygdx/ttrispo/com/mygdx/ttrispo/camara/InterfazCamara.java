package com.mygdx.ttrispo.com.mygdx.ttrispo.camara;

import java.io.File;
import java.util.ArrayList;

public interface InterfazCamara{
    boolean selectImage();
    void setDatos(byte[] bitMap);
    byte[] getDatos();
    String getUrl();
    void setPath(String path);
    void subirImagen(int i);
    void setPosicion(int posicion);

    boolean getResultado1();
    void setResultado1(boolean r1);
    boolean getResultado2();
    void setResultado2(boolean r2);

    int getNumeroDeImagenes();
    ArrayList<File> getArrayImagenes();
    void getImagenConPosicion(int i);

    byte[] convertirFileAbyte(File f);

    long getTamanioTotalImagen();
    long getTamanioDescargadoImagen();
    void setTamanioTotalImagen(long tamanioTotalImagen);
    void setTamanioDescargadoImagen(long tamanioDescargadoImagen);

    float getContadorBytesArray();
    void setContadorBytesArray(float contadorBytesArray);
    float getContadorBytesArchivo();
    void setContadorBytesArchivo(float contadorBytesArchivo);


}
