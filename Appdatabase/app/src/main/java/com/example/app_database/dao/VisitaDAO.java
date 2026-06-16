package com.example.app_database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.app_database.model.Visita;

import java.util.List;

@Dao
public interface VisitaDAO {

    @Insert
    void inserir(Visita visita);


    @Delete
    void deletar(Visita visita);

    @Query("SELECT * FROM Visita WHERE cnpj = :cnpjCliente ORDER BY data DESC")
    List<Visita> listarVisitasDoCliente(String cnpjCliente);
}
