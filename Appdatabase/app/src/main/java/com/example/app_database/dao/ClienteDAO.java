package com.example.app_database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.app_database.model.Cliente;

import java.util.List;

@Dao
public interface ClienteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Cliente cliente);
    @Delete
    void delete(Cliente cliente);
    @Query("SELECT * FROM Cliente WHERE cidadeIbge = :ibgeCidade ORDER BY ultimaVisita ASC")
    List<Cliente> listarClientesPorCidade(long ibgeCidade);
    @Query("SELECT * FROM Cliente WHERE cnpj = :cnpj LIMIT 1")
    Cliente buscarPorCnpj(String cnpj);

}
