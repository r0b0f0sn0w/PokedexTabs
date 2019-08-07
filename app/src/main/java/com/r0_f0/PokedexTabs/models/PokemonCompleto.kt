package com.r0_f0.PokedexTabs.models

import java.util.*

class PokemonCompleto(var id: Int, var order:Int, var name: String, var abilities : ArrayList<Habilidad>, var sprites:Sprite, var types: ArrayList<Tipo>, var stats: ArrayList<Status>) {
}