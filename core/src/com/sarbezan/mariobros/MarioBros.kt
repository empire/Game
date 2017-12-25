package com.sarbezan.mariobros

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.sarbezan.mariobros.screens.PlayerScreen

class MarioBros : Game() {
    lateinit var batch: SpriteBatch

    companion object {
        val V_WIDTH = 400f
        val V_HEIGHT = 208f
    }

    override fun create() {
        batch = SpriteBatch()
        setScreen(PlayerScreen(this))
    }

    override fun render() {
        super.render()
    }

    override fun dispose() {
        batch.dispose()
    }
}

