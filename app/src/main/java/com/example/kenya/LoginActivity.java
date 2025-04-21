package com.example.kenya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    // Datos hardcodeados para simular la API
    private static final String VALID_USERNAME = "1";
    private static final String VALID_PASSWORD = "1";

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

        // Obtener referencias a los views
        TextInputLayout usernameLayout = findViewById(R.id.textInputLayout);
        TextInputLayout passwordLayout = findViewById(R.id.textInputLayout2);
        Button loginButton = findViewById(R.id.button);

        // Configurar el click listener del botón
        loginButton.setOnClickListener(v -> {
            // Obtener los valores ingresados
            String username = usernameLayout.getEditText().getText().toString().trim();
            String password = passwordLayout.getEditText().getText().toString().trim();

            // Validar los campos
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simular autenticación con API (datos hardcodeados)
            simulateApiLogin(username, password);
        });
    }

    private void simulateApiLogin(String username, String password) {
        // Simular tiempo de espera de red (opcional)
        try {
            Thread.sleep(1000); // 1 segundo de delay para simular conexión
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Validar credenciales hardcodeadas
        if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
            // Login exitoso
            runOnUiThread(() -> {
                Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
            });
        } else {
            // Credenciales incorrectas
            runOnUiThread(() -> {
                Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            });
        }
    }
}