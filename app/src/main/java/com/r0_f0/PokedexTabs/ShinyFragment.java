package com.r0_f0.PokedexTabs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r0_f0.PokedexTabs.models.Pokemon;
import com.r0_f0.PokedexTabs.models.Pokemon_respuesta;
import com.r0_f0.PokedexTabs.pokeapi.PokeapiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShinyFragment extends Fragment {
    private static final String TAG = "POKEDEX";
    private Retrofit retrofit;
    private ListaPokemonAdapterShiny listaPokemonAdapterShiny;
    private RecyclerView recyclerViewShiny;
    private int offset;
    private boolean aptoParaCargar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shiny, container, false);
        recyclerViewShiny= root.findViewById(R.id.recyclerViewShiny);

        listaPokemonAdapterShiny = new ListaPokemonAdapterShiny(getActivity());
        recyclerViewShiny.setHasFixedSize(true);
        final GridLayoutManager layoutManagerShiny = new GridLayoutManager(getActivity(), 3);
        recyclerViewShiny.setLayoutManager(layoutManagerShiny);
        recyclerViewShiny.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = layoutManagerShiny.getChildCount();
                    int totalItemCount = layoutManagerShiny.getItemCount();
                    int pastVisibleItems = layoutManagerShiny.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, " Llegamos al final.");

                            aptoParaCargar = false;
                            offset += 20;
                            obtenerDatosShiny(offset);
                        }
                    }
                }
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aptoParaCargar = true;
        offset = 0;
        obtenerDatosShiny(offset);
        recyclerViewShiny.setAdapter(listaPokemonAdapterShiny);
        return root;
    }
    private void obtenerDatosShiny(int offset) {
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<Pokemon_respuesta> pokemonRespuestaCall = service.obtenerListaPokemonShiny(20, offset);
        pokemonRespuestaCall.enqueue(new Callback<Pokemon_respuesta>() {
            @Override
            public void onResponse(Call<Pokemon_respuesta> call, Response<Pokemon_respuesta> response) {
                aptoParaCargar = true;
                if (response.isSuccessful()) {

                    Pokemon_respuesta pokemonRespuesta = response.body();
                    ArrayList<Pokemon> listaPokemon = pokemonRespuesta.getResults();

                    listaPokemonAdapterShiny.adicionarListaPokemon(listaPokemon);

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Pokemon_respuesta> call, Throwable t) {
                aptoParaCargar = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }
}
