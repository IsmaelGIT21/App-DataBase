package com.example.app_database;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_database.database.AppDatabase;
import com.example.app_database.model.Cliente;
import com.example.app_database.model.Visita;

import java.util.Date;

public class VisitaActivity extends AppCompatActivity {

    private TextView txtNomeClienteVisita;
    private RatingBar ratingSatisfacao;
    private EditText editValorPedido;
    private EditText editObservacao;
    private Button btnSalvarVisita;

    private AppDatabase db;
    private String cnpjCliente;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita);

        txtNomeClienteVisita = findViewById(R.id.txtNomeClienteVisita);
        ratingSatisfacao = findViewById(R.id.ratingSatisfacao);
        editValorPedido = findViewById(R.id.editValorPedido);
        editObservacao = findViewById(R.id.editObservacao);
        btnSalvarVisita = findViewById(R.id.btnSalvarVisita);

        db = AppDatabase.getDatabase(this);

        cnpjCliente = getIntent().getStringExtra("cliente_cnpj");

        if (cnpjCliente != null) {

            cliente = db.clienteDAO().buscarPorCnpj(cnpjCliente);
            if (cliente != null) {
                txtNomeClienteVisita.setText("Registrar Visita para:\n" + cliente.getRazaoSocial());
            }
        }

        btnSalvarVisita.setOnClickListener(v -> salvarVisita());
    }

    private void salvarVisita() {
        if (cliente == null) return;

        Visita novaVisita = new Visita();
        novaVisita.setCnpj(cnpjCliente);
        novaVisita.setData(new Date());
        novaVisita.setSatisfacao((int) ratingSatisfacao.getRating());

        String valorStr = editValorPedido.getText().toString().trim();
        double valor = valorStr.isEmpty() ? 0.0 : Double.parseDouble(valorStr);
        novaVisita.setValorPedido(valor);

        novaVisita.setObservacao(editObservacao.getText().toString().trim());

        db.visitaDAO().inserir(novaVisita);

        cliente.setUltimaVisita(new Date());
        db.clienteDAO().atualizar(cliente);

        Toast.makeText(this, "Visita registrada com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}