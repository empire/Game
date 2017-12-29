package com.sarbezan.mariobros.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.scenes.Hud
import com.sarbezan.mariobros.sprites.Mario

class PlayScreen(private val game: MarioBros) : Screen {
    val atlas = TextureAtlas("Mario_and_Enemies.atlas")
    private val gameCam = OrthographicCamera()
    private val gamePort: Viewport = FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT / MarioBros.PPM, gameCam)
    private val hud = Hud(game.batch)

    private val mapLoader = TmxMapLoader()
    private val map = mapLoader.load("level1.tmx")
    private val renderer = OrthogonalTiledMapRenderer(map, 1f / MarioBros.PPM)

    private val world = World(Vector2(0f, -9.8f), true)
    private val b2dRenderer = Box2DDebugRenderer()

    private val player = Mario(world, this)

    init {
        gameCam.position.set(gamePort.worldWidth / 2f, gamePort.worldHeight / 2f, 0f)

        B2WorldCreator(world, map)
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        update(delta)

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        renderer.render()
        game.batch.projectionMatrix = gameCam.combined
        b2dRenderer.render(world, gameCam.combined)

        game.batch.begin()
        player.draw(game.batch)
        game.batch.end()
//        game.batch.projectionMatrix = hud.combined
        hud.draw()
    }

    private fun update(delta: Float) {
        handleInput(delta)
        world.step(1/60f, 6, 2)

        player.update(delta)

        gameCam.position.x = player.body.position.x

        gameCam.update()

        renderer.setView(gameCam)
    }

    fun handleInput(dt: Float) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.body.applyLinearImpulse(Vector2(0f, 4f), player.body.worldCenter, true)
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.linearVelocity.x <= 2) {
            player.body.applyLinearImpulse(Vector2(0.1f, 0f), player.body.worldCenter, true)
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.linearVelocity.x >= -2) {
            player.body.applyLinearImpulse(Vector2(-0.1f, 0f), player.body.worldCenter, true)
        }
    }


    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {

    }

    override fun resize(width: Int, height: Int) {
        gamePort.update(width, height)
    }

    override fun dispose() {
        //dispose of all our opened resources
        map.dispose()
        renderer.dispose()
        world.dispose()
        b2dRenderer.dispose()
//        hud.dispose()

    }
}
