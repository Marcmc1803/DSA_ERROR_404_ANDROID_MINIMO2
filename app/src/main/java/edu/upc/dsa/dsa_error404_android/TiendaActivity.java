package edu.upc.dsa.dsa_error404_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class TiendaActivity extends AppCompatActivity {
    Button btnBackToInicioLogin, btnComprarEspada, btnComprarEscudo, btnComprarPocion;

    ApiService apiService;
    public static final String BASE_URL = "http://10.0.2.2:8080/dsaApp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tienda);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBackToInicioLogin = findViewById(R.id.btnBackToInicioLogIn);
        btnComprarEspada = findViewById(R.id.btnComprarEspada);
        btnComprarEscudo = findViewById(R.id.btnComprarEscudo);
        btnComprarPocion = findViewById(R.id.btnComprarPocion);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        btnBackToInicioLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TiendaActivity.this, InicioLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnComprarEspada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTienda();
            }
        });

        btnComprarEscudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTienda();
            }
        });

        btnComprarPocion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTienda();
            }
        });
    }

    private void handleTienda(){
        SharedPreferences sharedPreferences = getSharedPreferences("user_credentials", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String userId = sharedPreferences.getString("userId", "");
        String password = sharedPreferences.getString("password", "");
        int monedas = sharedPreferences.getInt("monedas", 0);
        int vidaInicial = sharedPreferences.getInt("vidaInicial", 100); // valor por defecto

        Credentials credentials = new Credentials();
        credentials.setNombre(username);
        credentials.setPassword(password);

//        Call<User> call = apiService.comprarItem(userId, "espada");
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.isSuccessful()) {
//                    // Codi 200
//                    Toast.makeText(TiendaActivity.this,
//                            "Compra realizada.",
//                            Toast.LENGTH_SHORT).show();
//                    finish();
//
//                } else if (response.code() == 402) {
//                    // Codi 402
//                    Log.e("TiendaActivity", "Error en onResponse: " + response.code());
//                    Toast.makeText(TiendaActivity.this,
//                            "Error: Monedas insificientes",
//                            Toast.LENGTH_SHORT).show();
//
//                } else {
//                    // Codi 404
//                    Log.e("TiendaActivity", "Error en onResponse: " + response.code());
//                    Toast.makeText(TiendaActivity.this,
//                            "Error: Objeto o usuario no encontrado",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(TiendaActivity.this, "Fallo de connexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
//                Log.e("TiendaActivity", "Error en onFailure", t);
//            }
//        });
    }
}
