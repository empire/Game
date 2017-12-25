package com.sarbezan.mariobros.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.scenes.Hud

class PlayerScreen(private val game: MarioBros) : Screen {
    private val gameCam = OrthographicCamera()
    private val gamePort: Viewport = FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, gameCam)
    private val hud = Hud(game.batch)

    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        with(game.batch) {
            projectionMatrix = hud.combined
            hud.draw()
        }

    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        gamePort.update(width, height)
    }

    override fun dispose() {
    }

}