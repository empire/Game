package com.sarbezan.mariobros.tools

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.World
import com.sarbezan.mariobros.MarioBros

class Coin(world: World, map: TiledMap, bounds: Rectangle) :
        InteractiveTileObject(world, map, bounds) {

    init {
        setCategoryFilter(MarioBros.COIN_BIT)
    }

    override fun onHitHead() {
        Gdx.app.log("Coin", "")
    }
}