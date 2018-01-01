package com.sarbezan.mariobros.tools

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.World
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.scenes.Hud

class Coin(world: World, private val map: TiledMap, bounds: Rectangle) :
        InteractiveTileObject(world, map, bounds) {

    companion object {
        private val BLANK_COIN = 28
    }

    init {
        setCategoryFilter(MarioBros.COIN_BIT)
    }

    override fun onHitHead() {
        if (cell.tile.id == BLANK_COIN) {
            MarioBros.assetManager.get<Sound>("audio/sounds/bump.wav").play()
            return
        } else {
            MarioBros.assetManager.get<Sound>("audio/sounds/coin.wav").play()
        }
        val tile = map.tileSets.getTileSet("tileset_gutter").getTile(BLANK_COIN)
        cell.tile = tile
        Hud.addScore(100)
    }
}