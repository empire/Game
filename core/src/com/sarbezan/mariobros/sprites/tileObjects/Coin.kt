package com.sarbezan.mariobros.sprites.tileObjects

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.math.Rectangle
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.scenes.Hud
import com.sarbezan.mariobros.screens.PlayScreen

class Coin(private val screen: PlayScreen, bounds: Rectangle) :
        InteractiveTileObject(screen, bounds) {

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
        val tile = screen.map.tileSets.getTileSet("tileset_gutter").getTile(BLANK_COIN)
        cell.tile = tile
        Hud.addScore(100)
    }
}