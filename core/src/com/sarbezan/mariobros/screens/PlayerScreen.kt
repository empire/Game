package com.sarbezan.mariobros.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.scenes.Hud

class PlayerScreen(private val game: MarioBros) : Screen {
    private val gameCam = OrthographicCamera()
    private val gamePort: Viewport = FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, gameCam)
    private val hud = Hud(game.batch)

    private val mapLoader = TmxMapLoader()
    private val map = mapLoader.load("level1.tmx")
    private val renderer = OrthogonalTiledMapRenderer(map, game.batch)

    private val world = World(Vector2(0f, 0f), true)
    private val b2dRenderer = Box2DDebugRenderer()

    init {
        gameCam.position.set(gamePort.worldWidth / 2f, gamePort.worldHeight / 2f, 0f)

        wrapShapes()
    }

    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        update(delta)

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        renderer.render()
        with(game.batch) {
            projectionMatrix = hud.combined
            hud.draw()
        }
        b2dRenderer.render(world, gameCam.combined)

    }

    private fun update(delta: Float) {
        handleInput(delta)
        renderer.setView(gameCam)
        gameCam.update()
    }

    private fun handleInput(delta: Float) {
        if (Gdx.input.isTouched) {
            gameCam.position.x += 100 * delta
        }
    }

    private fun wrapShapes() {
        val bodyDef = BodyDef()
        val shape = PolygonShape()
        val fixtureDef = FixtureDef()

        // Ground
        for (mapObject in map.layers[2].objects.getByType(RectangleMapObject::class.java)) {
            with(mapObject.rectangle) {
                bodyDef.position.set(x + width / 2, y + height / 2)
                bodyDef.type = BodyDef.BodyType.StaticBody
                val body = world.createBody(bodyDef);


                shape.setAsBox(width / 2, height / 2)
                fixtureDef.shape = shape
                body.createFixture(fixtureDef)
            }
        }

        // Pipes
        for (mapObject in map.layers[3].objects.getByType(RectangleMapObject::class.java)) {
            bodyDef.type = BodyDef.BodyType.StaticBody
            fixtureDef.shape = shape
            with(mapObject.rectangle) {
                bodyDef.position.set(x + width / 2, y + height / 2)
                shape.setAsBox(width / 2, height / 2)
            }
            val body = world.createBody(bodyDef);

            body.createFixture(fixtureDef)
        }

        // Coins
        for (mapObject in map.layers[4].objects.getByType(RectangleMapObject::class.java)) {
            bodyDef.type = BodyDef.BodyType.StaticBody
            fixtureDef.shape = shape
            with(mapObject.rectangle) {
                bodyDef.position.set(x + width / 2, y + height / 2)
                shape.setAsBox(width / 2, height / 2)
            }
            val body = world.createBody(bodyDef);

            body.createFixture(fixtureDef)
        }

        // Bricks
        for (mapObject in map.layers[5].objects.getByType(RectangleMapObject::class.java)) {
            bodyDef.type = BodyDef.BodyType.StaticBody
            fixtureDef.shape = shape
            with(mapObject.rectangle) {
                bodyDef.position.set(x + width / 2, y + height / 2)
                shape.setAsBox(width / 2, height / 2)
            }
            val body = world.createBody(bodyDef);

            body.createFixture(fixtureDef)
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
