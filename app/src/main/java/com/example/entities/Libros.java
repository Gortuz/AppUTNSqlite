package com.example.entities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Libros {

    private SQLiteAdmin sqlAdmin;
    private SQLiteDatabase db;

    public Libros(Context ctx, String nombreBdd, int version) {
        sqlAdmin = new SQLiteAdmin(ctx, nombreBdd, null, version);
        db = sqlAdmin.getWritableDatabase();
    }

    public Libro Create(int id, String titulo, int idAutor, String isbn, int anioPublicacion, int revision, int nroHojas) {
        ContentValues datos = new ContentValues();
        datos.put("id", id);
        datos.put("titulo", titulo);
        datos.put("idAutor", idAutor);
        datos.put("isbn", isbn);
        datos.put("anioPublicacion", anioPublicacion);
        datos.put("revision", revision);
        datos.put("nroHojas", nroHojas);

        long r = db.insert("libros", null, datos);

        return r == - 1 ? null : new Libro(id, titulo, idAutor, isbn, anioPublicacion, revision, nroHojas);
    }

    public Libro Update(int id, String titulo, int idAutor, String isbn, int anioPublicacion, int revision, int nroHojas) {
        ContentValues datos = new ContentValues();
        datos.put("titulo", titulo);
        datos.put("idAutor", idAutor);
        datos.put("isbn", isbn);
        datos.put("anioPublicacion", anioPublicacion);
        datos.put("revision", revision);
        datos.put("nroHojas", nroHojas);

        int r = db.update("libros", datos, "id=" + id, null);
        return r == 0 ? null : new Libro(id, titulo, idAutor, isbn, anioPublicacion, revision, nroHojas);
    }

    public Libro Read_ById(int id) {
        Libro registro = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM libros WHERE id=" + id, null);
            if (cursor.moveToFirst()) {
                registro = new Libro(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return registro;
    }

    public Libro[] Read_All() {
        Cursor cursor = db.rawQuery("SELECT * FROM libros ORDER BY titulo", null);
        Libro[] datos = new Libro[cursor.getCount()];
        int i = 0;

        while (cursor.moveToNext()) {
            datos[i++] = new Libro(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6));
        }
        cursor.close();
        return datos;
    }

    public Libro[] Read_ByAutor(int idAutor) {
        Cursor cursor = null;
        Libro[] libros = null;

        try {
            cursor = db.rawQuery("SELECT * FROM libros WHERE idAutor=" + idAutor + " ORDER BY titulo", null);
            libros = new Libro[cursor.getCount()];
            int i = 0;

            while (cursor.moveToNext()) {
                libros[i++] = new Libro(
                        cursor.getInt(0),  // Id
                        cursor.getString(1),  // Titulo
                        cursor.getInt(2),  // IdAutor
                        cursor.getString(3),  // ISBN
                        cursor.getInt(4),  // Año Publicación
                        cursor.getInt(5),  // Revisión
                        cursor.getInt(6)   // Nro Hojas
                );
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return libros;
    }

    public boolean Delete(int id) {
        int r = db.delete("libros", "id=" + id, null);
        return r > 0;
    }
}