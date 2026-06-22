package com.example.app_database;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.app_database.api.EnderecoResponse;
import com.example.app_database.api.RetrofitClient;
import com.example.app_database.api.ViaCepService;
import com.example.app_database.model.Cidade;
import com.example.app_database.model.Cliente;
import com.example.app_database.viewmodel.MainViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity {

    private EditText editCnpj, editRazaoSocial, editCep, editLogradouro, editBairro, editNumero;
    private Button btnBuscarCep, btnSalvar;
    private MainViewModel viewModel;

    // Variáveis para guardar temporariamente os dados da Cidade recebidos da API
    private long codigoIbgeSalvo = 0;
    private String nomeCidadeSalva = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Vincular componentes
        editCnpj = findViewById(R.id.editCnpj);
        editRazaoSocial = findViewById(R.id.editRazaoSocial);
        editCep = findViewById(R.id.editCep);
        editLogradouro = findViewById(R.id.editLogradouro);
        editBairro = findViewById(R.id.editBairro);
        editNumero = findViewById(R.id.editNumero);
        btnBuscarCep = findViewById(R.id.btnBuscarCep);
        btnSalvar = findViewById(R.id.btnSalvar);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Evento de clique para Buscar o CEP via Retrofit
        btnBuscarCep.setOnClickListener(v -> buscarCepNoRetrofit());

        // Evento de clique para Salvar o cliente e a cidade no Banco de Dados
        btnSalvar.setOnClickListener(v -> salvarClienteNoBanco());
    }

    private void buscarCepNoRetrofit() {
        String cep = editCep.getText().toString().trim();
        if (cep.isEmpty() || cep.length() < 8) {
            Toast.makeText(this, "Insira um CEP válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Chama direto o getViaCepService() criado no seu RetrofitClient
        ViaCepService service = RetrofitClient.getViaCepService();

        // Usa o método correto buscaCEP da sua Interface
        Call<EnderecoResponse> call = service.buscaCEP(cep);

        call.enqueue(new Callback<EnderecoResponse>() {
            @Override
            public void onResponse(Call<EnderecoResponse> call, Response<EnderecoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EnderecoResponse endereco = response.body();

                    editLogradouro.setText(endereco.getLogradouro());
                    editBairro.setText(endereco.getBairro());

                    // Guarda o nome real da cidade retornado pela API
                    nomeCidadeSalva = endereco.getCidade();

                    // Usa getCodigoibge() mapeado no seu EnderecoResponse
                    try {
                        codigoIbgeSalvo = Long.parseLong(endereco.getCodigoibge());
                    } catch (NumberFormatException e) {
                        codigoIbgeSalvo = 0;
                    }

                    Toast.makeText(CadastroActivity.this, "Endereço localizado!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CadastroActivity.this, "CEP não encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EnderecoResponse> call, Throwable t) {
                Toast.makeText(CadastroActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void salvarClienteNoBanco() {
        if (codigoIbgeSalvo == 0 || nomeCidadeSalva == null || nomeCidadeSalva.isEmpty()) {
            Toast.makeText(this, "Busque um CEP válido primeiro", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Criar o objeto Cidade com os dados reais salvos da busca do CEP
        Cidade cidade = new Cidade();
        cidade.setIbge(codigoIbgeSalvo);
        cidade.setNome(nomeCidadeSalva); // Agora sim! Nome real dinâmico salvo com sucesso

        // 2. Criar e preencher o objeto Cliente
        Cliente cliente = new Cliente();
        cliente.setCnpj(editCnpj.getText().toString().trim());
        cliente.setRazaoSocial(editRazaoSocial.getText().toString().trim());
        cliente.setCep(editCep.getText().toString().trim());
        cliente.setLogradouro(editLogradouro.getText().toString().trim());
        cliente.setBairro(editBairro.getText().toString().trim());
        cliente.setNumero(editNumero.getText().toString().trim());
        cliente.setCidadeIbge(codigoIbgeSalvo); // Salvando a FK!

        // 3. Salvar usando nosso ViewModel
        viewModel.cadastrarCliente(cliente, cidade);

        Toast.makeText(this, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
        finish(); // Fecha a tela e volta para a MainActivity
    }
}