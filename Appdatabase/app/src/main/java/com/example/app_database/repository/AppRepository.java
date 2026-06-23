package com.example.app_database.repository;

import android.content.Context;
import com.example.app_database.database.AppDatabase;
import com.example.app_database.dao.CidadeDAO;
import com.example.app_database.dao.ClienteDAO;
import com.example.app_database.dao.VisitaDAO;
import com.example.app_database.model.Cidade;
import com.example.app_database.model.Cliente;
import com.example.app_database.model.Visita;

import java.util.List;

public class AppRepository {

    private final CidadeDAO cidadeDAO;
    private final ClienteDAO clienteDAO;
    private final VisitaDAO visitaDAO;

    public AppRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        this.cidadeDAO = db.cidadeDAO();
        this.clienteDAO = db.clienteDAO();
        this.visitaDAO = db.visitaDAO();
    }

    // CORRIGIDO: de listarTodos() para listarTodos() - Ajustado caso mude, mas o correto agora é bater com o DAO
    public List<Cidade> listarCidades() {
        return cidadeDAO.listarTodos();
    }

    // CORRIGIDO: de listarClientesPorCidade(ibge) para obterClientesPorCidade(ibge)
    public List<Cliente> listarClientesPorCidade(long ibge) {
        return clienteDAO.obterClientesPorCidade(ibge);
    }

    // CORRIGIDO: de insert(cliente) para insert(cliente) batendo com o novo método do DAO
    public void salvarCliente(Cliente cliente, Cidade cidade) {
        cidadeDAO.insert(cidade);
        cliente.setCidadeIbge(cidade.getIbge());
        clienteDAO.insert(cliente);
    }

    public void registrarVisita(Visita visita) {
        visitaDAO.inserir(visita);
    }
}