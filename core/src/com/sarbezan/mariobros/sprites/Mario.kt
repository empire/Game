package com.sarbezan.mariobros.sprites

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.physics.box2d.*
import com.sarbezan.mariobros.MarioBros

class Mario(private val world: World) : Sprite() {
    val body: Body

    init {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(32f / MarioBros.PPM, 32f / MarioBros.PPM)
        body = world.createBody(bodyDef)


        val fixtureDef = FixtureDef()
        fixtureDef.shape = CircleShape().apply {
            radius = 5f / MarioBros.PPM
        }
        body.createFixture(fixtureDef)
    }
}
