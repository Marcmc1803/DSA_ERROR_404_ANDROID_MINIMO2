package edu.upc.dsa.dsa_error404_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import edu.upc.dsa.dsa_error404_android.User;
import edu.upc.dsa.dsa_error404_android.Credentials;

public class SignupActivity extends AppCompatActivity {
    EditText etUsuari, etEmail, etPassword, etPassword2;
    Button btnSignUp, btnBackToMain;
    ApiService apiService;

    //public static final String BASE_URL = "https://dsa4.upc.edu/register.html";
    public static final String BASE_URL = "http://10.0.2.2:8080/dsaApp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etUsuari = findViewById(R.id.editUsuari);
        etEmail = findViewById(R.id.editEmail);
        etPassword = findViewById(R.id.EditPassword);
        etPassword2 = findViewById(R.id.editPassword2);
        btnSignUp = findViewById(R.id.SignUp);
        btnBackToMain = findViewById(R.id.btnBackToMain);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
            }
        });

        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void handleSignUp() {
        String usuari = etUsuari.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String password2 = etPassword2.getText().toString();

        if (usuari.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            Toast.makeText(this, "Datos incorrectos.", Toast.LENGTH_SHORT).show();
            return;
        }

        String emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";

        if (!email.matches(emailRegex)) {
            Toast.makeText(this,"El formato del correo no es válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(password2)){
            Toast.makeText(this, "Contraseñas incorrectos.", Toast.LENGTH_SHORT).show();
            return;
        }

        Credentials credentials = new Credentials();
        credentials.setNombre(usuari);
        credentials.setPassword(password);
        credentials.setEmail(email);

        Call<User> call = apiService.registerUser(credentials);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Codi 201
                    Toast.makeText(SignupActivity.this, "Usuario registrado! Haz login.", Toast.LENGTH_LONG).show();
                    finish();

                } else if (response.code() == 409) {
                    // Codi 409
                    Log.e("SignupActivity", "Error en onResponse: " + response.code());
                    Toast.makeText(SignupActivity.this, "Error: El usuario ya existe", Toast.LENGTH_LONG).show();

                } else {
                    // Altres errors
                    Log.e("SignupActivity", "Error en onResponse: " + response.code());
                    Toast.makeText(SignupActivity.this, "Error desconocido en el registro", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Fallo de connexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("SignupActivity", "Error en onFailure", t);
            }
        });
    }
}