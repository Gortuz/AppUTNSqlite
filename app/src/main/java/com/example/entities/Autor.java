package com.example.entities;

public class Autor {
    public int id;
    public String nombres;
    public String apellidos;
    public String isoPais;
    public int edad;

    public Autor(int id, String nombres, String apellidos, String isoPais, int edad) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.isoPais = isoPais;
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", isoPais='" + isoPais + '\'' +
                ", edad=" + edad +
                '}';
    }
}
