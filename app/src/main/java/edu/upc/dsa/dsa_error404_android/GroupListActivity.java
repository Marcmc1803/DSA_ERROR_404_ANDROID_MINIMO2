package edu.upc.dsa.dsa_error404_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ApiService apiService;
    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SessionManager.getUsername(this) == null) {
            Toast.makeText(this, "Debe iniciar sesión para ver los grupos.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_group_list);

        // Asumiendo que el botón 'Volver' se llama btnBack en el XML
        // Si no lo quieres, puedes comentar o eliminar las dos líneas siguientes
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(this::onBackButtonClick);
        }

        recyclerView = findViewById(R.id.recyclerViewGroups);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = RetrofitClient.getInstance().getMyApi();

        loadGroups();
    }

    private void loadGroups() {
        progressBar.setVisibility(View.VISIBLE);

        apiService.getAllGroups().enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<Group> groups = response.body();

                    adapter = new GroupAdapter(GroupListActivity.this, groups);
                    recyclerView.setAdapter(adapter);

                } else if (response.code() == 404) {
                    Toast.makeText(GroupListActivity.this, "No se encontraron grupos.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(GroupListActivity.this, "Error al cargar los grupos: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(GroupListActivity.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void joinGroup(Group group) {

        final String currentUsername = SessionManager.getUsername(this);

        if (currentUsername == null) {
            Toast.makeText(this, "Sesión caducada. Por favor, reinicie.", Toast.LENGTH_SHORT).show();
            return;
        }

        JoinGroupRequest request = new JoinGroupRequest(group.getId(), currentUsername);

        apiService.joinGroup(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(GroupListActivity.this, "¡Te has unido a " + group.getName() + "!", Toast.LENGTH_LONG).show();
                } else if (response.code() == 400 || response.code() == 409) {
                    Toast.makeText(GroupListActivity.this, "Error: El usuario ya está en un grupo o faltan datos.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(GroupListActivity.this, "Error al unirse: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(GroupListActivity.this, "Fallo de conexión al intentar unirse.", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Método para el botón "Volver"
    public void onBackButtonClick(View view) {
        finish();
    }
}