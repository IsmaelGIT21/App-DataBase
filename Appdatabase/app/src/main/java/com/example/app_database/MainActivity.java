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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.app_database.database.AppDatabase;
import com.example.app_database.model.Cidade;
import com.example.app_database.model.Cliente;
import com.example.app_database.model.Visita;
import com.example.app_database.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private Spinner spinnerCidades;
    private ListView listViewClientes;
    private ArrayAdapter<Cidade> spinnerAdapter;
    private AppDatabase db;

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
        listViewClientes = findViewById(R.id.listViewClientes);
        Button btnNovoCliente = findViewById(R.id.btnNovoCliente);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        db = AppDatabase.getDatabase(this);

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, viewModel.obterTodasCidades());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCidades.setAdapter(spinnerAdapter);

        spinnerCidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                atualizarListaClientes(((Cidade) p.getItemAtPosition(pos)).getIbge());
            }
            @Override
            public void onNothingSelected(AdapterView<?> p) {}
        });

        btnNovoCliente.setOnClickListener(v -> startActivity(new Intent(this, CadastroActivity.class)));

        listViewClientes.setOnItemClickListener((p, v, pos, id) -> {
            Cliente c = (Cliente) p.getItemAtPosition(pos);
            if (c != null) {
                startActivity(new Intent(this, VisitaActivity.class).putExtra("cliente_cnpj", c.getCnpj()));
            }
        });

        listViewClientes.setOnItemLongClickListener((p, v, pos, id) -> {
            Cliente c = (Cliente) p.getItemAtPosition(pos);
            if (c != null) {
                List<Visita> visitas = db.visitaDAO().listarVisitasDoCliente(c.getCnpj());
                AlertDialog.Builder popUp = new AlertDialog.Builder(this).setTitle(c.getRazaoSocial());

                if (visitas == null || visitas.isEmpty()) {
                    popUp.setMessage("Nenhuma visita registrada para este cliente ainda.");
                } else {
                    Visita u = visitas.get(0);
                    popUp.setMessage("⭐ Satisfação: " + u.getSatisfacao() + " / 5 estrelas\n" +
                            "💰 Valor do Pedido: R$ " + u.getValorPedido() + "\n" +
                            "📝 Obs: " + u.getObservacao());
                }
                popUp.setPositiveButton("Fechar", null).show();
            }
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (spinnerAdapter != null) {
            spinnerAdapter.clear();
            spinnerAdapter.addAll(viewModel.obterTodasCidades());
            spinnerAdapter.notifyDataSetChanged();

            Cidade s = (Cidade) spinnerCidades.getSelectedItem();
            if (s != null) atualizarListaClientes(s.getIbge());
        }
    }

    private void atualizarListaClientes(long ibge) {
        listViewClientes.setAdapter(new ClienteAdapter(this, viewModel.obterClientesPorCidade(ibge)));
    }
    private class ClienteAdapter extends ArrayAdapter<Cliente> {
        public ClienteAdapter(Context ctx, List<Cliente> clientes) {
            super(ctx, android.R.layout.simple_list_item_1, clientes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            Cliente c = getItem(position);
            int cor = Color.TRANSPARENT;

            if (c != null) {
                if (c.getUltimaVisita() != null && (System.currentTimeMillis() - c.getUltimaVisita().getTime() <= 7L * 24 * 60 * 60 * 1000)) {
                    cor = Color.parseColor("#C8E6C9");
                }
                ((TextView) view.findViewById(android.R.id.text1)).setText(c.getRazaoSocial());
            }
            view.setBackgroundColor(cor);
            return view;
        }
    }
}