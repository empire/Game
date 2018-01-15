package com.sarbezan.mariobros.sprites.items

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.screens.PlayScreen

class Mushroom(screen: PlayScreen, x: Float, y: Float) : Item(screen, x, y) {

    init {
        val textRegion = screen.atlas.findRegion("mushroom")
        setRegion(TextureRegion(textRegion, 0, 0, 16, 16))
    }

    private val velocity = Vector2(0f, 0f)

    override fun defineItem() {
        val bodyDef = BodyDef()
        bodyDef.position.set(x, y)
        bodyDef.type = BodyDef.BodyType.DynamicBody
        body = screen.world.createBody(bodyDef)

        val fixtureDef = FixtureDef().apply {
            shape = CircleShape().apply {
                radius = 8f / MarioBros.PPM
            }
        }
        body.createFixture(fixtureDef).userData = this
    }

    override fun use() {
        destroy()
    }

    override fun update(dt: Float) {
        super.update(dt)
        setPosition(body.position.x - width / 2, body.position.y - height / 2)
        body.linearVelocity = velocity
    }
}