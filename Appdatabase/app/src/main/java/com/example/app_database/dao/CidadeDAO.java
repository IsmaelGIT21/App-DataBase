package com.example.app_database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.app_database.model.Cidade;

import java.util.List;

@Dao
public interface CidadeDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Cidade cidade);
    @Delete
    void delete(Cidade cidade);
    @Query("SELECT * FROM Cidade ORDER BY nome ASC")
    List<Cidade> listarTodos();
    @Query("SELECT * FROM Cidade WHERE ibge = :ibge LIMIT 1")
    Cidade buscaPorID(long ibge);
}