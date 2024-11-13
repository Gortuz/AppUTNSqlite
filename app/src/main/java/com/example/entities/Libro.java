package com.example.entities;

public class Libro {
    public int id;
    public String titulo;
    public int idAutor;
    public String isbn;
    public int anioPublicacion;
    public int revision;
    public int nroHojas;

    public Libro(int id, String titulo, int idAutor, String isbn, int anioPublicacion, int revision, int nroHojas) {
        this.id = id;
        this.titulo = titulo;
        this.idAutor = idAutor;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
        this.revision = revision;
        this.nroHojas = nroHojas;
    }

}
