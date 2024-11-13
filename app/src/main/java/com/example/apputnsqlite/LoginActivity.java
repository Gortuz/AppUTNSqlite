package com.example.apputnsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    EditText txtUsuario, txtClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtUsuario = findViewById(R.id.et_username);
        txtClave = findViewById(R.id.et_password);
    }

    public void cmdLogin_onClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("usuario", txtUsuario.getText().toString());
        intent.putExtra("clave", txtClave.getText().toString());

        startActivity(intent);

    }

}