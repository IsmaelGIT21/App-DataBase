package com.example.app_database.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.app_database.dao.CidadeDAO;
import com.example.app_database.dao.ClienteDAO;
import com.example.app_database.dao.VisitaDAO;
import com.example.app_database.model.Cidade;
import com.example.app_database.model.Cliente;
import com.example.app_database.model.Visita;

@Database(entities = {Cidade.class, Cliente.class, Visita.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract ClienteDAO clienteDAO();
    public abstract CidadeDAO   cidadeDAO();
    public abstract VisitaDAO   visitaDAO();

    public static AppDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "Projeto_visita_db")
                            .allowMainThreadQueries() // CORREÇÃO: Permite ler/escrever na Main Thread sem crashar
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}