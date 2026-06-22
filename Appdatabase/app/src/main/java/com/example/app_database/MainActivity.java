package com.example.app_database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView; // Trocado de RecyclerView para ListView
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.app_database.model.Cidade;
import com.example.app_database.model.Cliente;
import com.example.app_database.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private Spinner spinnerCidades;
    private Button btnNovoCliente;
    private ListView listViewClientes; // Simplificado aqui!

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

        spinnerCidades = findViewById(R.id.spinnerCidades);
        btnNovoCliente = findViewById(R.id.btnNovoCliente);
        listViewClientes = findViewById(R.id.listViewClientes); // Seu ListView simples

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        configurarSpinner();

        btnNovoCliente.setOnClickListener(v -> {
            // Intent para a tela de cadastro futuramente
        });
    }

    private void configurarSpinner() {
        List<Cidade> listaCidades = viewModel.obterTodasCidades();

        ArrayAdapter<Cidade> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listaCidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCidades.setAdapter(adapter);

        spinnerCidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cidade cidadeSelecionada = (Cidade) parent.getItemAtPosition(position);
                atualizarListaClientes(cidadeSelecionada.getIbge());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void atualizarListaClientes(long ibge) {
        // CORRIGIDO: Removido o "n" do obter
        List<Cliente> clientes = viewModel.obterClientesPorCidade(ibge);

        ArrayAdapter<Cliente> adapterClientes = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, clientes);

        listViewClientes.setAdapter(adapterClientes);
    }
}