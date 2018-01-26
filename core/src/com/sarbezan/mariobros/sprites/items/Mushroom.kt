package com.sarbezan.mariobros.sprites.items

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.screens.PlayScreen
import com.sarbezan.mariobros.sprites.Mario
import kotlin.experimental.or

class Mushroom(screen: PlayScreen, x: Float, y: Float) : Item(screen, x, y) {

    init {
        val textRegion = screen.atlas.findRegion("mushroom")
        setRegion(TextureRegion(textRegion, 0, 0, 16, 16))
    }

    private val velocity = Vector2(.7f, 0f)

    override fun defineItem() {
        val bodyDef = BodyDef()
        bodyDef.position.set(x, y)
        bodyDef.type = BodyDef.BodyType.DynamicBody
        body = screen.world.createBody(bodyDef)

        val fixtureDef = FixtureDef().apply {
            shape = CircleShape().apply {
                radius = 7f / MarioBros.PPM
            }
            filter.categoryBits = MarioBros.ITEM_BIT
            filter.maskBits = MarioBros.GROUND_BIT or
                    MarioBros.COIN_BIT or
                    MarioBros.MARIO_BIT or
                    MarioBros.OBJECT_BIT or
                    MarioBros.ENEMY_HEAD_BIT or
                    MarioBros.BRICK_BIT
        }
        body.createFixture(fixtureDef).userData = this
    }

    override fun use(mario: Mario) {
        destroy()
        mario.grow()
    }

    override fun update(dt: Float) {
        super.update(dt)
        setPosition(body.position.x - width / 2, body.position.y - height / 2)
        velocity.y = body.linearVelocity.y
        body.linearVelocity = velocity
    }
    override fun reverseVelocity(x: Boolean, y: Boolean) {
        if (x) velocity.x = -velocity.x
        if (y) velocity.y = -velocity.y
    }
}