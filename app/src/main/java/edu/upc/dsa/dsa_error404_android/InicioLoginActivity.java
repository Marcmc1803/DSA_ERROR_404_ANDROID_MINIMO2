package edu.upc.dsa.dsa_error404_android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.SharedPreferences;
import android.content.Context;

// Importar SessionManager para asegurar la limpieza de la sesión al desloguearse
// Asumo que SessionManager está en el paquete principal si no usas la carpeta 'util'
// import edu.upc.dsa.dsa_error404_android.SessionManager;


public class InicioLoginActivity extends AppCompatActivity {

    Button buttonTienda;
    Button buttonLogOut;
    Button buttonInventario;
    Button btnJoinGroup; // <-- NUEVA DECLARACIÓN

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // =======================================================
        // Botón TIENDA
        // =======================================================
        buttonTienda = findViewById(R.id.buttonTienda);
        buttonTienda.setOnClickListener(v -> {
            Intent intent = new Intent(InicioLoginActivity.this, TiendaActivity.class);
            startActivity(intent);
        });

        // =======================================================
        // Botón LOGOUT
        // =======================================================
        buttonLogOut = findViewById(R.id.buttonLogOut);
        buttonLogOut.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("user_credentials", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            // Limpiar también la sesión del SessionManager (si lo tienes)
            // SessionManager.clearSession(InicioLoginActivity.this);

            Intent intent = new Intent(InicioLoginActivity.this, MainActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            finish();
        });

        // =======================================================
        // Botón INVENTARIO
        // =======================================================
        buttonInventario = findViewById(R.id.buttonInventario);
        buttonInventario.setOnClickListener(v -> {
            Intent intent = new Intent(InicioLoginActivity.this, InventarioActivity.class);
            startActivity(intent);
        });

        // =======================================================
        // NUEVA FUNCIONALIDAD EJ3: Unirse a Grupo
        // =======================================================
        btnJoinGroup = findViewById(R.id.btnJoinGroup); // <-- Inicialización
        btnJoinGroup.setOnClickListener(v -> {
            // Lanza la Activity de listado de grupos
            Intent intent = new Intent(InicioLoginActivity.this, GroupListActivity.class);
            startActivity(intent);
        });
    }
}