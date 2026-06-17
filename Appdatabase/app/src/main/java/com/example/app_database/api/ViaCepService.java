package com.example.app_database.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepService {
    @GET("{cep}/json/")
    Call<EnderecoResponse>buscaCEP(@Path("cep")String cep);
}
