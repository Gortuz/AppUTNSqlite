package com.example.apputnsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LibrosActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation;
    Menu menu_bottom_navigation;
    MenuItem action_libros, action_autores;
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

        bottom_navigation = findViewById(R.id.bottom_navigation);
        menu_bottom_navigation = bottom_navigation.getMenu();
        action_libros = menu_bottom_navigation.findItem(R.id.action_libros);
        action_autores = menu_bottom_navigation.findItem(R.id.action_autores);
        action_autores.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Acción para el ítem de autores
                finish();
                return true;
            }
        });

        action_libros.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Acción para el ítem de libros
                Intent intent = new Intent(LibrosActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
        });
    }
}