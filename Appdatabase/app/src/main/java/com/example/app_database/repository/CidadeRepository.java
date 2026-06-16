package com.example.app_database.repository;

import android.content.Context;

import com.example.app_database.dao.CidadeDAO;
import com.example.app_database.database.AppDatabase;
import com.example.app_database.model.Cidade;

import java.util.List;

public class CidadeRepository {
    private CidadeDAO cidadeDAO;
    public CidadeRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        this.cidadeDAO = db.cidadeDAO();
    }
    public void inserir(Cidade cidade){
        cidadeDAO.insert(cidade);
    }
    public Cidade buscarPorId(long ibgeId) {
        return cidadeDAO.buscaPorID(ibgeId);
    }
    public List<Cidade> listarTodos(){
        return cidadeDAO.listarTodos();
    }
}
