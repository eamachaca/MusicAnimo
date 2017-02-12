package com.example.deit0.musicanimo;

/**
 * Created by DeIt0 on 29/07/2016.
 */
public class Musica {
    private int ID;
    private String Nombre;
    private String url;

    public Musica(int ID, String nombre, String url) {
        this.ID = ID;
        Nombre = nombre;
        this.url = url;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
