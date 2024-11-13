package com.example.entities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Autores {

    private final SQLiteAdmin sqliteAdmin;
    private SQLiteDatabase db;

    public Autores (Context ctx, String name, int version){
        this.sqliteAdmin = new SQLiteAdmin(ctx, name, null, version);
        this.db = sqliteAdmin.getWritableDatabase();
    }

    public Autor Create(int id, String nombres, String apellidos, String isoPais, int edad){
        ContentValues datos = new ContentValues();

        datos.put("id", id);
        datos.put("nombres", nombres);
        datos.put("apellidos", apellidos);
        datos.put("isoPais", isoPais);
        datos.put("edad", edad);

        long r = db.insert("autores", "id, apellidos, nombres, isoPais, edad", datos);

        return r < 0 ? null : new Autor(id, nombres, apellidos, isoPais, edad);
    }

    public Autor Update(int id, String nombres, String apellidos, String isoPais, int edad){
        ContentValues datos = new ContentValues();

//        datos.put("id", id);
        datos.put("nombres", nombres);
        datos.put("apellidos", apellidos);
        datos.put("isoPais", isoPais);
        datos.put("edad", edad);

        int r = db.update("autores", datos, "id = "+ id, null);

        return r == 0 ? null : new Autor(id, nombres, apellidos, isoPais, edad);

    }

    public Autor Read_By_Id(int id){

        Cursor cursor;
        String query;
        Autor autor = null;

        query = "SELECT * FROM autores WHERE id = "+ id;
        cursor = db.rawQuery(query, null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            autor = new Autor(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4));
        }

        cursor.close();
        return autor;
    }

    public Autor[] Read_All(){
        Cursor cursor;
        String query;
        Autor autor = null;
        Autor[] autores = null;
        int i = 0;

        query = "SELECT * FROM autores ORDER BY apellidos, nombres";
        cursor = db.rawQuery(query, null);
        autores = new Autor[cursor.getCount()];

        while(cursor.moveToNext()) {
            autor = new Autor(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4));

            autores[i++] = autor;
        }

        cursor.close();
        return autores;
    }

    public boolean Delete(int id){

        int r = db.delete("autores", "id = "+ id, null);
        return r > 0;
    }
}
