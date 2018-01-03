package com.sarbezan.mariobros

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.sarbezan.mariobros.screens.PlayScreen

class MarioBros : Game() {
    lateinit var batch: SpriteBatch

    companion object {
        val V_WIDTH = 400f
        val V_HEIGHT = 208f
        val PPM = 100
        val GROUND_BIT: Short = 1
        val MARIO_BIT: Short = 2
        val COIN_BIT: Short = 4
        val BRICK_BIT: Short = 8
        val DESTROYED_BIT: Short = 16
        val ENEMY_BIT: Short = 32
        val OBJECT_BIT: Short = 64

        // Not do in production, it is not safe and cause problem on Android
        lateinit var assetManager: AssetManager
    }

    override fun create() {
        batch = SpriteBatch()
        assetManager = AssetManager().apply {
            load("audio/music/mario_music.ogg", Music::class.java)
            load("audio/sounds/breakblock.wav", Sound::class.java)
            load("audio/sounds/bump.wav", Sound::class.java)
            load("audio/sounds/coin.wav", Sound::class.java)
            finishLoading()
        }
        setScreen(PlayScreen(this))
    }

    override fun render() {
        super.render()
        assetManager.update()
    }

    override fun dispose() {
        batch.dispose()
        assetManager.dispose()
        batch.dispose()
    }
}

