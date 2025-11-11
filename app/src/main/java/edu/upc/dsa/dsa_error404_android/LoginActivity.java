package edu.upc.dsa.dsa_error404_android;

// Imports d'Android
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Imports de Retrofit
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // 1. Declarar variables
    EditText editTextUser, editTextPassword;
    Button buttonLogin;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login); // El teu layout per a Login

        // 2. Enllaçar vistes
        // !!! Assegura't que aquests IDs existeixen al teu "activity_login.xml" !!!
        // (Aquests són els IDs que teníem a activity_main.xml)
        editTextUser = findViewById(R.id.editTextText);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        buttonLogin = findViewById(R.id.Login);

        // 3. Obtenir la instància d'ApiService (requereix RetrofitClient.java)
        apiService = RetrofitClient.getInstance().getApiService();

        // 4. Configurar el listener del botó
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarLogin();
            }
        });

        // 5. El teu codi original per als Insets
        // !!! Assegura't que l'ID "main" existeix al teu "activity_login.xml"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // 6. Funció que executa la crida a l'API
    private void iniciarLogin() {
        String username = editTextUser.getText().toString();
        String password = editTextPassword.getText().toString();

        // Validació de camps buits
        if (username.isEmpty() || password.isEmpty()) {
            // (Si has actualitzat strings.xml, fes servir: getString(R.string.login_toast_empty_fields))
            Toast.makeText(this, "Si us plau, introdueix usuari i contrasenya", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear l'objecte de credencials (requereix LoginCredentials.java)
        LoginCredentials credentials = new LoginCredentials(username, password);

        // Crear la crida (Call) (requereix ApiService.java)
        Call<LoginResponse> call = apiService.login(credentials);

        // Executar la crida de forma asíncrona
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                // Gestionar la resposta del servidor
                if (response.isSuccessful()) {
                    // Login correcte (requereix LoginResponse.java)
                    LoginResponse loginResponse = response.body();
                    String token = loginResponse.getToken();

                    // (Si has actualitzat strings.xml, fes servir: getString(R.string.login_success))
                    Toast.makeText(LoginActivity.this, "Inici de sessió correcte!", Toast.LENGTH_SHORT).show();

                    // Aquí aniria la navegació a la següent pantalla
                    // Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    // intent.putExtra("USER_TOKEN", token);
                    // startActivity(intent);
                    // finish();

                } else {
                    // Error de credencials (usuari/pass incorrectes)
                    // (Si has actualitzat strings.xml, fes servir: getString(R.string.login_error_credentials))
                    Toast.makeText(LoginActivity.this, "Error: Credencials incorrectes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Error de xarxa (no hi ha connexió, API caiguda, etc.)
                // (Si has actualitzat strings.xml, fes servir: getString(R.string.login_error_network))
                Toast.makeText(LoginActivity.this, "Error: Problema de xarxa", Toast.LENGTH_SHORT).show();
            }
        });
    }
}