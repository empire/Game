package com.sarbezan.mariobros.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.sarbezan.mariobros.MarioBros

class PlayerScreen(private val game: MarioBros) : Screen {
    private val img: Texture = Texture("badlogic.jpg")
    private val gameCam = OrthographicCamera()
    private val gamePort: Viewport = FitViewport(800f, 480f, gameCam)

    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        with(game.batch) {
            projectionMatrix = gameCam.combined
            begin()
            draw(img, 0f, 0f)
            end()
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
        img.dispose()
    }

}