package com.example.apputnsqlite;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.RouteListingPreference;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {
    Autores autoresDB;
    Libros librosDB;
    TableLayout tb_libros;
    EditText et_id, et_nombres, et_apellidos, et_isoPais, et_edad;
    ImageButton btn_crear, btn_leer, btn_actualizar, btn_borrar;
    TextView tv_titulo;
    BottomNavigationView bottom_navigation;
    Menu menu_bottom_navigation;
    MenuItem action_libros, action_autores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_id = findViewById(R.id.et_id);
        et_nombres = findViewById(R.id.et_nombres);
        et_apellidos = findViewById(R.id.et_apellidos);
        et_isoPais = findViewById(R.id.et_isoPais);
        et_edad = findViewById(R.id.et_edad);
        btn_crear = findViewById(R.id.btn_crear);
        btn_leer = findViewById(R.id.btn_leer);
        tb_libros = findViewById(R.id.tb_libros);
        tv_titulo = findViewById(R.id.tv_titulo);



        autoresDB = new Autores(this, "biblioteca.db", 1);
        librosDB = new Libros(this, "biblioteca.db", 1);


        bottom_navigation = findViewById(R.id.bottom_navigation);
        menu_bottom_navigation = bottom_navigation.getMenu();
        action_libros = menu_bottom_navigation.findItem(R.id.action_libros);
        action_autores = menu_bottom_navigation.findItem(R.id.action_autores);

        action_autores.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Acción para el ítem de autores
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
                return true;
            }
        });

        action_libros.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Acción para el ítem de libros
                Intent intent = new Intent(MainActivity.this, LibrosActivity.class);
                startActivity(intent);
                return true;
            }
        });



        Bundle extra = getIntent().getExtras();
        tv_titulo.setText("Bienvenido, "+extra.getString("usuario"));
//        Log.println(Log.VERBOSE, "INFO", extra.getString("usuario"));
//        Log.println(Log.VERBOSE, "INFO", extra.getString("clave"));
//        Autor r = listaAutores.Create(1, "AMADO", "NERVO", "PE", 30);
//        Autor r = listaAutores.Update(1, "AMADO EULALIO", "NERVO PAREDES", "CO", 30);
//        Autor[] r = autoresDB.Read_All();
//        for (Autor autor : r) {
//            Log.println(Log.VERBOSE, "INFO", autor+"");
//        }
//        Log.println(Log.VERBOSE, "INFO", r+ "");
//        boolean r = listaAutores.Delete(1);


    }


    public void onClick_Crear(android.view.View view) {
        Autor r;
        String nombres, apellidos, isoPais;
        int id, edad;

        id = Integer.parseInt(getEditTextValue(et_id));
        nombres = getEditTextValue(et_nombres);
        apellidos = getEditTextValue(et_apellidos);
        isoPais = getEditTextValue(et_isoPais);
        edad = Integer.parseInt(getEditTextValue(et_edad));

        String mensaje;
        r = autoresDB.Create(id, nombres, apellidos, isoPais, edad);

        if(r != null)
        {
            Toast.makeText(
                    this,
                    "Autor creado correctamente",
                    Toast.LENGTH_LONG
            ).show();
        }
        else
        {
            Toast.makeText(
                    this, "ERROR AL CREAR AUTOR",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    public void onClick_Actualizar(android.view.View view) {
        Autor r;
        String nombres, apellidos, isoPais;
        int id, edad;

        id = Integer.parseInt(getEditTextValue(et_id));
        nombres = getEditTextValue(et_nombres);
        apellidos = getEditTextValue(et_apellidos);
        isoPais = getEditTextValue(et_isoPais);
        edad = Integer.parseInt(getEditTextValue(et_edad));

        r = autoresDB.Update(id, nombres, apellidos, isoPais, edad);

        if(r != null)
        {
            Toast.makeText(
                    this,
                    "Autor ACTUALIZADO correctamente",
                    Toast.LENGTH_LONG
            ).show();
        }
        else
        {
            Toast.makeText(
                    this, "ERROR AL ACTUALIZAR AUTOR",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    public void onClick_Leer(android.view.View view) {
        int id;

        id = Integer.parseInt(getEditTextValue(et_id));
        Autor r = autoresDB.Read_By_Id(id);

        if(r != null)
        {

            et_nombres.setText(r.nombres);
            et_apellidos.setText(r.apellidos);
            et_isoPais.setText(r.isoPais);
            et_edad.setText(r.edad + "");

            Toast.makeText(
                    this,
                    "Autor ENCONTRADO correctamente",
                    Toast.LENGTH_LONG
            ).show();
            llenarTablaLibros(r.id);
        }
        else
        {
            et_nombres.setText("");
            et_apellidos.setText("");
            et_isoPais.setText("");
            et_edad.setText("");

            Toast.makeText(
                    this, "Registo NO ENCONTRADO",
                    Toast.LENGTH_LONG
            ).show();
        }
//        Log.println(Log.VERBOSE, "INFO", r+ "");
    }

    public void onClick_Borrar(android.view.View view) {
        int id;

        id = Integer.parseInt(getEditTextValue(et_id));
        Autor a = autoresDB.Read_By_Id(id);
        boolean r = autoresDB.Delete(id);

        if(r)
        {
            Toast.makeText(
                    this,
                    "Autor "+a.nombres+" "+a.apellidos+" BORRADO correctamente",
                    Toast.LENGTH_LONG
            ).show();
        }
        else
        {
            et_nombres.setText("");
            et_apellidos.setText("");
            et_isoPais.setText("");
            et_edad.setText("");

            Toast.makeText(
                    this, "Registo NO ENCONTRADO",
                    Toast.LENGTH_LONG
            ).show();
        }

    }

    private String getEditTextValue(EditText editText) {
        return editText.getText().toString();
    }

    public void llenarTablaLibros(int idAutor){
        if (tb_libros.getChildCount() > 1) {
            tb_libros.removeViews(1, tb_libros.getChildCount() - 1);
        }

        Libro[] listaLibros = librosDB.Read_ByAutor(idAutor);

        // Agregar las filas de los libros
        for (Libro libro : listaLibros) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            // Crear celdas para cada columna
            TextView tvId = crearCelda(String.valueOf(libro.id));
            tvId.setVisibility(View.GONE);
            tvId.setPadding(0,0,0,0);
            TextView tvTitulo = crearCelda(libro.titulo);
            TextView tvAnio = crearCelda(String.valueOf(libro.anioPublicacion));
            // Agregar celdas a la fila
            row.addView(tvId);
            row.addView(tvTitulo);
            row.addView(tvAnio);

            // Agregar la fila a la tabla
            tb_libros.addView(row);
        }
    }

    private TextView crearCelda(String texto) {
        TextView textView = new TextView(this);
        textView.setText(texto);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(8, 8, 8, 8);
        textView.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        return textView;
    }

    public void cmdRegresar_onClick(View view){
        finish();
    }
}