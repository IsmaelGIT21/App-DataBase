package com.example.app_database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update; // Certifique-se de importar o Update

import com.example.app_database.model.Cliente;

import java.util.List;

@Dao
public interface ClienteDAO {
    @Insert
    void inserir(Cliente cliente);
    @Update
    void atualizar(Cliente cliente);
    @Delete
    void deletar(Cliente cliente);
    @Query("SELECT * FROM Cliente WHERE cidadeIbge = :ibge ORDER BY ultimaVisita ASC")
    List<Cliente> obterClientesPorCidade(long ibge);
    @Query("SELECT * FROM Cliente WHERE cnpj = :cnpj LIMIT 1")
    Cliente buscarPorCnpj(String cnpj);
}