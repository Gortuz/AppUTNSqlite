package com.example.entities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteAdmin extends SQLiteOpenHelper {


    public SQLiteAdmin(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query;

        //Borrar tablas
//        query = "DROP TABLE autores";
//        sqLiteDatabase.execSQL(query);
//        query = "DROP TABLE IF EXISTS editoriales";
//        sqLiteDatabase.execSQL(query);

        //Creación de tablas
        query = "CREATE TABLE IF NOT EXISTS autores (id INT not null primary key, nombres TEXT(50), apellidos TEXT(50), isoPais TEXT(50), edad INT)";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS editoriales (id INT not null primary key, nombre TEXT(50), isoPais TEXT(50))";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS libros(id INT not null primary key, " +
                "titulo TEXT(100), idAutor INT, isbn TEXT(30), anioPublicacion INT, revision  INT, nroHojas INT)";
        sqLiteDatabase.execSQL(query);

        sqLiteDatabase.execSQL("INSERT INTO autores (id, nombres, apellidos, isoPais, edad) VALUES \n" +
                "(1, 'Gabriel', 'García Márquez', 'COL', 87),\n" +
                "(2, 'Isabel', 'Allende', 'CHL', 81),\n" +
                "(3, 'J.K.', 'Rowling', 'GBR', 58);");

        sqLiteDatabase.execSQL("INSERT INTO libros (id, titulo, idAutor, isbn, anioPublicacion, revision, nroHojas) VALUES \n" +
                "(1, 'Cien Años de Soledad', 1, '978-0060883287', 1967, 1, 417),\n" +
                "(2, 'La Casa de los Espíritus', 2, '978-8401013888', 1982, 1, 433),\n" +
                "(3, 'Harry Potter y la Piedra Filosofal', 3, '978-0747532699', 1997, 1, 223);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
