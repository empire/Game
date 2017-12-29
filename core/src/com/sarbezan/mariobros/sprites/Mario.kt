package com.sarbezan.mariobros.sprites

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.*
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.screens.PlayScreen

class Mario(world: World, screen: PlayScreen) : Sprite(screen.atlas.findRegion("little_mario")) {
    val body: Body
    private val marioStand = TextureRegion(texture, 0, 12, 16, 16)

    init {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(32f / MarioBros.PPM, 32f / MarioBros.PPM)
        body = world.createBody(bodyDef)


        val fixtureDef = FixtureDef()
        fixtureDef.shape = CircleShape().apply {
            radius = 7f / MarioBros.PPM
        }
        body.createFixture(fixtureDef)
        setBounds(16f, 0f, 16f / MarioBros.PPM, 16f / MarioBros.PPM)
        setRegion(marioStand)
    }

    fun update(dt: Float) {
        setPosition(body.position.x - width / 2, body.position.y - height / 2)
    }
}
