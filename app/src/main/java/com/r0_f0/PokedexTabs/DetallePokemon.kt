package com.r0_f0.PokedexTabs

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_detalle_pokemon.*
import okhttp3.*
import java.io.IOException
import com.r0_f0.PokedexTabs.models.PokemonCompleto
class DetallePokemon : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pokemon)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        var id = intent.getIntExtra("id",0)
        var l1 : LinearLayout = this.findViewById(R.id.LL_1)
        var l2 : LinearLayout = this.findViewById(R.id.LL_2)
        var l3 : LinearLayout = this.findViewById(R.id.LL_3)
        var l4 : LinearLayout = this.findViewById(R.id.LL_4)
        var pbCargando : ProgressBar = this.findViewById(R.id.progressBar4)
        var pokemon: PokemonCompleto? = null
        val url = "https://pokeapi.co/api/v2/pokemon/${id}"
        val request = Request.Builder().url(url).build()
        val poke = OkHttpClient()
        poke.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.e("Detalle Poke", "no se puedo obtener datos de $id" )
            }
            override fun onResponse(call: Call?, response: Response?) {
                val respuesta = response?.body()?.string()
                val gson = GsonBuilder().create()
                try {
                    pokemon = gson.fromJson(respuesta, PokemonCompleto::class.java)
                    runOnUiThread {
                        if (pokemon==null){
                            Toast.makeText(this@DetallePokemon,"Error" , Toast.LENGTH_SHORT).show()
                        }else{
                            var link = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon!!.id}.png"
                            Glide.with(this@DetallePokemon).load(link).into(img_oficial3)
                            var link1 = pokemon!!.sprites.front_default
                            var link2 = pokemon!!.sprites.front_female
                            var link3 = pokemon!!.sprites.front_shiny
                            var link4 = pokemon!!.sprites.front_shiny_female
                            if (link1.equals(null)){
                                l1.visibility = View.GONE
                            }else{
                                Glide.with(this@DetallePokemon).load(link1).into(img1)
                                colorizarLinear(l1,R.color.water)
                            }
                            if (link2.equals(null)){
                                l2.visibility = View.GONE
                            }else{
                                l2.visibility = View.GONE
                            }
                            if (link3.equals(null)){
                                l3.visibility = View.GONE
                            }else{
                                Glide.with(this@DetallePokemon).load(link3).into(img3)
                                colorizarLinear(l3,R.color.electric)
                            }
                            if (link4.equals(null)){
                                l4.visibility = View.GONE
                            }else{
                                l4.visibility = View.GONE
                            }
                            lblNombre2.text = pokemon!!.name.capitalize().replace("-"," ")
                            lblNum2.text = "#${pokemon!!.id}"
                            //Tipo de pokemon
                            lblTipo1.visibility = View.GONE
                            lblTipo2.visibility = View.GONE
                            for (cont in 1..pokemon!!.types.size){
                                if (cont==1){
                                    lblTipo1.visibility = View.VISIBLE
                                    colorTipo(pokemon!!.types[0].type.name,lblTipo1)
                                    lblTipo1.text = pokemon!!.types[0].type.name.capitalize()
                                }
                                if(cont==2){
                                    lblTipo2.visibility = View.VISIBLE
                                    colorTipo(pokemon!!.types[1].type.name,lblTipo2)
                                    lblTipo2.text = pokemon!!.types[1].type.name.capitalize()
                                }
                            }
                            lblHabilidad1.visibility = View.GONE
                            lblHabilidad2.visibility = View.GONE
                            for(cont in 1..pokemon!!.abilities.size){
                                if(cont==1){
                                    lblHabilidad1.visibility = View.VISIBLE
                                    lblHabilidad1.text =pokemon!!.abilities[0].ability.name.capitalize().replace("-"," ")
                                }
                                if (cont==2){
                                    lblHabilidad2.visibility = View.VISIBLE
                                    lblHabilidad2.text =pokemon!!.abilities[1].ability.name.capitalize().replace("-"," ")
                                }
                            }
                            lblVelocidad.text = pokemon!!.stats[0].base_stat.toString()
                            lblDefensaE.text = pokemon!!.stats[1].base_stat.toString()
                            lblAtaqueE.text = pokemon!!.stats[2].base_stat.toString()
                            lblDefensa.text = pokemon!!.stats[3].base_stat.toString()
                            lblAtaque.text = pokemon!!.stats[4].base_stat.toString()
                            lblHp.text = pokemon!!.stats[5].base_stat.toString()
                            linear_Cargando.visibility = View.GONE
                            linear_Contenido.visibility = View.VISIBLE
                            pbCargando.visibility = View.GONE
                        }
                    }
                }
                catch(ex : Exception){
                    Log.e("json invalido","${ex.message}")
                }
            }
        } )
    }
    fun colorTipo(n: String, texto : TextView){
        when(n){
            "normal" -> colorizar(texto,R.color.normal)
            "fighting" -> colorizar(texto,R.color.fighting)
            "flying" -> colorizar(texto,R.color.flying)
            "poison" -> colorizar(texto,R.color.posion)
            "ground" -> colorizar(texto,R.color.ground)
            "rock" -> colorizar(texto,R.color.rock)
            "bug" ->  colorizar(texto,R.color.bug)
            "ghost" -> colorizar(texto,R.color.ghost)
            "steel" -> colorizar(texto,R.color.steel)
            "fire" ->  colorizar(texto,R.color.fire)
            "water" ->  colorizar(texto,R.color.water)
            "grass" ->  colorizar(texto,R.color.grass)
            "electric" ->  colorizar(texto,R.color.electric)
            "psychic" ->  colorizar(texto,R.color.psychic)
            "ice" ->  colorizar(texto,R.color.ice)
            "fairy" ->  colorizar(texto,R.color.fairy)
            "dark" ->  colorizar(texto,R.color.dark)
            "shadow" ->  colorizar(texto,R.color.dark)
            "unknown" ->  colorizar(texto,R.color.normal)
            else -> colorizar(texto,R.color.normal)
        }
    }
    fun colorizar(texto : TextView, id: Int){
        texto.setBackgroundColor(ContextCompat.getColor(this,id))
    }
    fun colorizarLinear(li : LinearLayout, id: Int){
        li.setBackgroundColor(ContextCompat.getColor(this,id))
    }
}
