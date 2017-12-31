package com.sarbezan.mariobros.tools

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World

abstract class InteractiveTileObject(
        private val world: World,
        private val map: TiledMap,
        private val bounds: Rectangle) {
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
        val body = world.createBody(bodyDef)

        body.createFixture(fixtureDef).userData = this
    }

    abstract fun onHitHead()
}