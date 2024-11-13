package com.example.apputnsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.entities.Autor;
import com.example.entities.Autores;
import com.example.entities.Libro;
import com.example.entities.Libros;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class LibrosActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation;
    Menu menu_bottom_navigation;
    MenuItem action_libros, action_autores;

    Spinner spinner_autores;
    List<Integer> listaIdAutores;

    Libros librosDB;
    Autores autoresDB;
    EditText et_id, et_titulo, et_isbn, et_anioPublicacion, et_revision, et_nroHojas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_libros);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        autoresDB = new Autores(this, "biblioteca.db", 1);
        librosDB = new Libros(this, "biblioteca.db", 1);

        spinner_autores = findViewById(R.id.spinner_autor);
        listaIdAutores = new ArrayList<>();

        bottom_navigation = findViewById(R.id.bottom_navigation);
        menu_bottom_navigation = bottom_navigation.getMenu();
        action_libros = menu_bottom_navigation.findItem(R.id.action_libros);
        action_autores = menu_bottom_navigation.findItem(R.id.action_autores);

        et_id = findViewById(R.id.et_id);
        et_titulo = findViewById(R.id.et_titulo);
        et_isbn = findViewById(R.id.et_isbn);
        et_anioPublicacion = findViewById(R.id.et_anio_publicacion);
        et_revision = findViewById(R.id.et_revision);
        et_nroHojas = findViewById(R.id.et_nro_hojas);

        Bundle extra = getIntent().getExtras();
//        et_titulo.setText(extra.getString("titulo"));

        llenarSpinnerAutores();

        action_autores.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Acción para el ítem de autores
                finish();
                return true;
            }
        });

    }

    public void cmdRegresar_onClick(View view){
        finish();
    }

    public void llenarSpinnerAutores() {
        Autor[] autores = autoresDB.Read_All();
        List<String> nombresAutores = new ArrayList<>();

        for (Autor autor : autores) {
            nombresAutores.add(autor.nombres + " " + autor.apellidos);  // Mostrar nombres y apellidos
            listaIdAutores.add(autor.id);  // Almacenar los IDs
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresAutores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_autores.setAdapter(adapter);
    }

    public void onClick_CrearLibro(android.view.View view) {
        Libro libro;
        String titulo, isbn;
        int id, idAutor, anioPublicacion, revision, nroHojas;

        // Obtener el ID del autor desde el Spinner
        int selectedPosition = spinner_autores.getSelectedItemPosition();
        if (selectedPosition >= 0) {
            idAutor = listaIdAutores.get(selectedPosition);  // ID del autor seleccionado
        } else {
            Toast.makeText(this, "Seleccione un autor", Toast.LENGTH_SHORT).show();
            return;  // Salir del método si no hay un autor seleccionado
        }

        // Obtener otros valores de los EditTexts
        id = Integer.parseInt(getEditTextValue(et_id));
        titulo = getEditTextValue(et_titulo);
        isbn = getEditTextValue(et_isbn);
        anioPublicacion = Integer.parseInt(getEditTextValue(et_anioPublicacion));
        revision = Integer.parseInt(getEditTextValue(et_revision));
        nroHojas = Integer.parseInt(getEditTextValue(et_nroHojas));

        // Crear el libro con los datos obtenidos
        libro = librosDB.Create(id, titulo, idAutor, isbn, anioPublicacion, revision, nroHojas);

        if (libro != null) {
            Toast.makeText(this, "Libro creado correctamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "ERROR AL CREAR LIBRO", Toast.LENGTH_LONG).show();
        }
    }


    public void onClick_ActualizarLibro(android.view.View view) {
        Libro libro;
        String titulo, isbn;
        int id, idAutor, anioPublicacion, revision, nroHojas;

        // Obtener el ID del autor desde el Spinner
        int selectedPosition = spinner_autores.getSelectedItemPosition();
        if (selectedPosition >= 0) {
            idAutor = listaIdAutores.get(selectedPosition);  // ID del autor seleccionado
        } else {
            Toast.makeText(this, "Seleccione un autor", Toast.LENGTH_SHORT).show();
            return;  // Salir del método si no hay un autor seleccionado
        }

        // Obtener otros valores de los EditTexts
        id = Integer.parseInt(getEditTextValue(et_id));
        titulo = getEditTextValue(et_titulo);
        isbn = getEditTextValue(et_isbn);
        anioPublicacion = Integer.parseInt(getEditTextValue(et_anioPublicacion));
        revision = Integer.parseInt(getEditTextValue(et_revision));
        nroHojas = Integer.parseInt(getEditTextValue(et_nroHojas));

        // Actualizar el libro con los datos obtenidos
        libro = librosDB.Update(id, titulo, idAutor, isbn, anioPublicacion, revision, nroHojas);

        if (libro != null) {
            Toast.makeText(this, "Libro actualizado correctamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "ERROR AL ACTUALIZAR LIBRO", Toast.LENGTH_LONG).show();
        }
    }


    public void onClick_LeerLibro(android.view.View view) {
        int id = Integer.parseInt(getEditTextValue(et_id));
        Libro libro = librosDB.Read_ById(id);

        if (libro != null) {
            et_titulo.setText(libro.titulo);
            et_isbn.setText(libro.isbn);
            et_anioPublicacion.setText(String.valueOf(libro.anioPublicacion));
            et_revision.setText(String.valueOf(libro.revision));
            et_nroHojas.setText(String.valueOf(libro.nroHojas));

            // Encontrar la posición en el spinner para el idAutor
            int position = listaIdAutores.indexOf(libro.idAutor);
            if (position >= 0) {
                spinner_autores.setSelection(position);  // Selecciona el autor correcto en el Spinner
            }

            Toast.makeText(this, "Libro encontrado correctamente", Toast.LENGTH_LONG).show();
        } else {
            limpiarCamposLibro();
            Toast.makeText(this, "Registro NO ENCONTRADO", Toast.LENGTH_LONG).show();
        }
    }


    public void onClick_BorrarLibro(android.view.View view) {
        int id = Integer.parseInt(getEditTextValue(et_id));
        Libro libro = librosDB.Read_ById(id);
        boolean resultado = librosDB.Delete(id);

        if (resultado) {
            Toast.makeText(this, "Libro " + (libro != null ? libro.titulo : "") + " borrado correctamente", Toast.LENGTH_LONG).show();
            limpiarCamposLibro();  // Limpiar los campos después de borrar
        } else {
            Toast.makeText(this, "Registro NO ENCONTRADO", Toast.LENGTH_LONG).show();
        }
    }


    private void limpiarCamposLibro() {
        et_id.setText("");
        et_titulo.setText("");
        et_isbn.setText("");
        et_anioPublicacion.setText("");
        et_revision.setText("");
        et_nroHojas.setText("");

        // Restablecer el Spinner al primer elemento
        if (spinner_autores.getAdapter() != null && spinner_autores.getAdapter().getCount() > 0) {
            spinner_autores.setSelection(0);  // Seleccionar el primer elemento
        }
    }



    private String getEditTextValue(EditText editText) {
        return editText.getText().toString();
    }
}