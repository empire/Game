package com.sarbezan.mariobros.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.scenes.Hud
import com.sarbezan.mariobros.sprites.items.ItemDef
import com.sarbezan.mariobros.sprites.Mario
import com.sarbezan.mariobros.sprites.items.Mushroom
import java.util.*

class PlayScreen(private val game: MarioBros) : Screen {
    val atlas = TextureAtlas("Mario_and_Enemies.atlas")
    private val gameCam = OrthographicCamera()
    private val gamePort: Viewport = FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT / MarioBros.PPM, gameCam)
    private val hud = Hud(game.batch)

    private val mapLoader = TmxMapLoader()
    val map: TiledMap = mapLoader.load("level1.tmx")
    private val renderer = OrthogonalTiledMapRenderer(map, 1f / MarioBros.PPM)

    val world = World(Vector2(0f, -9.8f), true)
    private val b2dRenderer = Box2DDebugRenderer()

    private val player = Mario(this)
    private val creator = B2WorldCreator(this)
    private val items = mutableListOf<Mushroom>()

    private val itemsToSpawn = LinkedList<ItemDef>()

    init {
        gameCam.position.set(gamePort.worldWidth / 2f, gamePort.worldHeight / 2f, 0f)
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
        creator.goombas.map { it.draw(game.batch) }
        items.map { it.draw(game.batch) }
        game.batch.end()
//        game.batch.projectionMatrix = hud.combined
        hud.draw()
        MarioBros.assetManager.get("audio/music/mario_music.ogg", Music::class.java).apply {
            isLooping = true
            play()
        }
    }

    private fun update(delta: Float) {
        handleInput(delta)
        handleSpawningItems()
        world.step(1/60f, 6, 2)

        player.update(delta)
        creator.goombas.map {
            it.update(delta)
            if (it.x < player.x + 304 / MarioBros.PPM) {
                it.body.isActive = true
            }
        }
        items.map { it.update(delta) }
        gameCam.position.x = player.body.position.x

        gameCam.update()
        hud.update(delta)

        renderer.setView(gameCam)
    }

    private fun handleInput(dt: Float) {
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

    fun spawnItem(itemDef: ItemDef) {
        itemsToSpawn.add(itemDef)
    }

    private fun handleSpawningItems() {
        if (itemsToSpawn.size == 0) return
        with(itemsToSpawn.pop()) {
            if (Mushroom::class.java == type)
                items.add(Mushroom(this@PlayScreen, position.x, position.y))
        }
    }
}
