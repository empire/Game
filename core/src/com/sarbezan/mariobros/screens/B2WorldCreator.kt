package com.sarbezan.mariobros.screens

import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.utils.Array
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.sprites.Goomba
import com.sarbezan.mariobros.tools.Brick
import com.sarbezan.mariobros.tools.Coin

class B2WorldCreator(screen: PlayScreen) {
    val goombas = Array<Goomba>()

    init {
        val bodyDef = BodyDef()
        val shape = PolygonShape()
        val fixtureDef = FixtureDef()

        // Ground
        for (mapObject in screen.map.layers[2].objects
                .getByType(RectangleMapObject::class.java)) {
            with(mapObject.rectangle) {
                bodyDef.position.set((x + width / 2) / MarioBros.PPM, (y + height / 2) / MarioBros.PPM)
                bodyDef.type = BodyDef.BodyType.StaticBody
                val body = screen.world.createBody(bodyDef);

                shape.setAsBox(width / 2 / MarioBros.PPM, height / 2 / MarioBros.PPM)
                fixtureDef.shape = shape
                body.createFixture(fixtureDef)
            }
        }

        // Pipes
        for (mapObject in screen.map.layers[3].objects.getByType(RectangleMapObject::class.java)) {
            bodyDef.type = BodyDef.BodyType.StaticBody
            fixtureDef.shape = shape
            fixtureDef.filter.categoryBits = MarioBros.OBJECT_BIT
            with(mapObject.rectangle) {
                bodyDef.position.set((x + width / 2) / MarioBros.PPM, (y + height / 2) / MarioBros.PPM)
                shape.setAsBox(width / 2 / MarioBros.PPM, height / 2 / MarioBros.PPM)
            }
            val body = screen.world.createBody(bodyDef);

            body.createFixture(fixtureDef)
        }

        // Coins
        for (mapObject in screen.map.layers[4].objects.getByType(RectangleMapObject::class.java)) {
            Coin(screen, mapObject.rectangle)
        }

        // Bricks
        for (mapObject in screen.map.layers[5].objects.getByType(RectangleMapObject::class.java)) {
            Brick(screen, mapObject.rectangle)
        }

        // Goomba
        for (mapObject in screen.map.layers[6].objects.getByType(RectangleMapObject::class.java)) {
            goombas.add(Goomba(screen, mapObject.rectangle.x, mapObject.rectangle.y))
        }
    }
}