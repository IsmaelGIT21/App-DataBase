package com.example.app_database.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.app_database.model.Cidade;
import com.example.app_database.model.Cliente;
import com.example.app_database.model.Visita;
import com.example.app_database.repository.AppRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final AppRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        // Inicializa o repositório único que arrumamos
        this.repository = new AppRepository(application);
    }

    // Pega as cidades para o Spinner da tela inicial
    public List<Cidade> obterTodasCidades() {
        return repository.listarCidades();
    }

    // Pega os clientes da cidade selecionada
    public List<Cliente> obterClientesPorCidade(long ibge) {
        return repository.listarClientesPorCidade(ibge);
    }

    // Salva o cliente e a cidade vindos da busca do CEP
    public void cadastrarCliente(Cliente cliente, Cidade cidade) {
        repository.salvarCliente(cliente, cidade);
    }

    // Registra a visita
    public void salvarVisita(Visita visita) {
        repository.registrarVisita(visita);
    }
}   