package com.sarbezan.mariobros.tools

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.*
import com.sarbezan.mariobros.MarioBros

abstract class InteractiveTileObject(
        private val world: World,
        private val map: TiledMap,
        private val bounds: Rectangle) {
    private val fixture: Fixture
    private val body: Body

    init {
        val bodyDef = BodyDef()
        val shape = PolygonShape()
        val fixtureDef = FixtureDef()

        bodyDef.type = BodyDef.BodyType.StaticBody
        fixtureDef.shape = shape
        with(bounds) {
            bodyDef.position.set(
                    (x + width / 2) / com.sarbezan.mariobros.MarioBros.PPM,
                    (y + height / 2) / com.sarbezan.mariobros.MarioBros.PPM)
            shape.setAsBox(
                    width / 2 / com.sarbezan.mariobros.MarioBros.PPM,
                    height / 2 / com.sarbezan.mariobros.MarioBros.PPM)
        }
        body = world.createBody(bodyDef)

        fixture = body.createFixture(fixtureDef)
        fixture.userData = this
    }

    protected fun setCategoryFilter(bits: Short) {
        fixture.filterData = Filter().apply {
            categoryBits = bits
        }
    }

    abstract fun onHitHead()
    protected val cell: TiledMapTileLayer.Cell
        get() = (map.layers[1] as TiledMapTileLayer)
                .getCell((body.position.x * MarioBros.PPM).toInt() / 16,
                        (body.position.y * MarioBros.PPM).toInt() / 16)
}