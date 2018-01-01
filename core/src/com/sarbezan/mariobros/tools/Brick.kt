package com.sarbezan.mariobros.tools

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.World
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.scenes.Hud

class Brick(world: World, map: TiledMap, bounds: Rectangle) :
        InteractiveTileObject(world, map, bounds) {
    init {
        setCategoryFilter(MarioBros.BRICK_BIT)
    }

    override fun onHitHead() {
        setCategoryFilter(MarioBros.DESTROYED_BIT)
        cell.tile = null
        Hud.addScore(200)
    }
}