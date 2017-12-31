package com.sarbezan.mariobros

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.sarbezan.mariobros.screens.PlayScreen

class MarioBros : Game() {
    lateinit var batch: SpriteBatch

    companion object {
        val V_WIDTH = 400f
        val V_HEIGHT = 208f
        val PPM = 100
        val DEFAULT_BIT: Short = 1
        val MARIO_BIT: Short = 2
        val COIN_BIT: Short = 4
        val BRICK_BIT: Short = 8
        val DESTROYED_BIT: Short = 16
    }

    override fun create() {
        batch = SpriteBatch()
        setScreen(PlayScreen(this))
    }

    override fun dispose() {
        batch.dispose()
    }
}

