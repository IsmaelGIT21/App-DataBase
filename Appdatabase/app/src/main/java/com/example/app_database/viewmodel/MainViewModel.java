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
        this.repository = new AppRepository(application);
    }

    public List<Cidade> obterTodasCidades() {
        return repository.listarCidades();
    }

    public List<Cliente> obterClientesPorCidade(long ibge) {
        return repository.listarClientesPorCidade(ibge);
    }

    public void cadastrarCliente(Cliente cliente, Cidade cidade) {
        repository.salvarCliente(cliente, cidade);
    }

    public void salvarVisita(Visita visita) {
        repository.registrarVisita(visita);
    }
}   