package com.sarbezan.mariobros.screens

import com.badlogic.gdx.maps.tiled.TiledMap
import com.sarbezan.mariobros.tools.Coin
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.physics.box2d.*
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.tools.Brick

class B2WorldCreator(world: World, map: TiledMap) {
    init {
        val bodyDef = BodyDef()
        val shape = PolygonShape()
        val fixtureDef = FixtureDef()

        // Ground
        for (mapObject in map.layers[2].objects.getByType(RectangleMapObject::class.java)) {
            with(mapObject.rectangle) {
                bodyDef.position.set((x + width / 2) / MarioBros.PPM, (y + height / 2) / MarioBros.PPM)
                bodyDef.type = BodyDef.BodyType.StaticBody
                val body = world.createBody(bodyDef);

                shape.setAsBox(width / 2 / MarioBros.PPM, height / 2 / MarioBros.PPM)
                fixtureDef.shape = shape
                body.createFixture(fixtureDef)
            }
        }

        // Pipes
        for (mapObject in map.layers[3].objects.getByType(RectangleMapObject::class.java)) {
            bodyDef.type = BodyDef.BodyType.StaticBody
            fixtureDef.shape = shape
            with(mapObject.rectangle) {
                bodyDef.position.set((x + width / 2) / MarioBros.PPM, (y + height / 2) / MarioBros.PPM)
                shape.setAsBox(width / 2 / MarioBros.PPM, height / 2 / MarioBros.PPM)
            }
            val body = world.createBody(bodyDef);

            body.createFixture(fixtureDef)
        }

        // Coins
        for (mapObject in map.layers[4].objects.getByType(RectangleMapObject::class.java)) {
            Coin(world, map, mapObject.rectangle)
        }

        // Bricks
        for (mapObject in map.layers[5].objects.getByType(RectangleMapObject::class.java)) {
            Brick(world, map, mapObject.rectangle)
        }
    }
}