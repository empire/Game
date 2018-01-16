package com.sarbezan.mariobros.sprites.tileObjects

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.scenes.Hud
import com.sarbezan.mariobros.screens.PlayScreen

class Brick(screen: PlayScreen, mapObject: RectangleMapObject) :
        InteractiveTileObject(screen, mapObject) {
    init {
        setCategoryFilter(MarioBros.BRICK_BIT)
    }

    override fun onHitHead() {
        setCategoryFilter(MarioBros.DESTROYED_BIT)
        cell.tile = null
        Hud.addScore(200)
        MarioBros.assetManager.get<Sound>("audio/sounds/breakblock.wav").play()
    }
}