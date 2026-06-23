package com.example.app_database;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
    private ListView listViewClientes;
    private ArrayAdapter<Cidade> spinnerAdapter;

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
        listViewClientes = findViewById(R.id.listViewClientes);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        configurarSpinner();

        btnNovoCliente.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (spinnerAdapter != null && viewModel != null) {
            List<Cidade> listaCidadesAtualizada = viewModel.obterTodasCidades();
            spinnerAdapter.clear();
            spinnerAdapter.addAll(listaCidadesAtualizada);
            spinnerAdapter.notifyDataSetChanged();

            if (!listaCidadesAtualizada.isEmpty()) {
                Cidade selecionada = (Cidade) spinnerCidades.getSelectedItem();
                if (selecionada != null) {
                    atualizarListaClientes(selecionada.getIbge());
                }
            }
        }
    }

    private void configurarSpinner() {
        List<Cidade> listaCidades = viewModel.obterTodasCidades();

        spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listaCidades);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCidades.setAdapter(spinnerAdapter);

        spinnerCidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cidade cidadeSelecionada = (Cidade) parent.getItemAtPosition(position);
                if (cidadeSelecionada != null) {
                    atualizarListaClientes(cidadeSelecionada.getIbge());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void atualizarListaClientes(long ibge) {
        // Usa o método do seu ViewModel/Repository
        List<Cliente> clientes = viewModel.obterClientesPorCidade(ibge);

        // Instancia o nosso adapter customizado com a lógica de cores
        ClienteAdapter adapterClientes = new ClienteAdapter(this, clientes);
        listViewClientes.setAdapter(adapterClientes);
    }

    // CLASSE INTERNA: Controla a cor de fundo com base na regra de 7 dias
    private class ClienteAdapter extends ArrayAdapter<Cliente> {
        public ClienteAdapter(Context context, List<Cliente> clientes) {
            super(context, android.R.layout.simple_list_item_1, clientes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            Cliente cliente = getItem(position);

            if (cliente != null && cliente.getUltimaVisita() != null) {
                long dataVisitaMs = cliente.getUltimaVisita().getTime();
                long seteDiasEmMs = 7L * 24 * 60 * 60 * 1000;
                long agora = System.currentTimeMillis();

                // Se a diferença for menor ou igual a 7 dias, pinta de verde claro
                if (agora - dataVisitaMs <= seteDiasEmMs) {
                    view.setBackgroundColor(Color.parseColor("#C8E6C9"));
                } else {
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
            } else {
                view.setBackgroundColor(Color.TRANSPARENT);
            }

            TextView textView = view.findViewById(android.R.id.text1);
            if (cliente != null) {
                textView.setText(cliente.getRazaoSocial());
            }

            return view;
        }
    }
}