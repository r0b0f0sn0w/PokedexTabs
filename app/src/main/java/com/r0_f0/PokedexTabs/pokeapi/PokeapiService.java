package com.r0_f0.PokedexTabs.pokeapi;

import com.r0_f0.PokedexTabs.models.Pokemon_respuesta;
import com.r0_f0.PokedexTabs.models.Pokemon_tipos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeapiService {

    @GET("pokemon")
    Call<Pokemon_respuesta> obtenerListaPokemon(@Query("limit") int limit, @Query("offset") int offset);
    @GET("pokemon")
    Call<Pokemon_respuesta> obtenerListaPokemonShiny(@Query("limit") int limit, @Query("offset") int offset);
    @GET("pokemon/{id}")
    Call<Pokemon_tipos> obtenerHabilidadesPokemon(@Path ("id") String id);
}
