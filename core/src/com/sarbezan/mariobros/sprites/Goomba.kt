package com.sarbezan.mariobros.sprites

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.utils.Array
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.screens.PlayScreen
import kotlin.experimental.or

class Goomba(screen: PlayScreen, x: Float, y: Float) : Enemy(screen, x, y) {
    private lateinit var body: Body

    private val textRegion = screen.atlas.findRegion("goomba")
    private var stateTime = 0f

    private var walkingAnimation = getAnimation()

    override fun defineEnemy() {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(x / MarioBros.PPM, y / MarioBros.PPM)
        body = screen.world.createBody(bodyDef)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = CircleShape().apply {
            radius = 7f / MarioBros.PPM
        }
        fixtureDef.filter.categoryBits = MarioBros.ENEMY_BIT
        fixtureDef.filter.maskBits = MarioBros.GROUND_BIT or
                MarioBros.MARIO_BIT or
                MarioBros.BRICK_BIT
        body.createFixture(fixtureDef)
        setBounds(16f, 0f, 16f / MarioBros.PPM, 16f / MarioBros.PPM)
    }

    fun update(dt: Float) {
        setPosition(body.position.x - width / 2, body.position.y - height / 2)
        setRegion(getFrame(dt))
    }

    private fun getFrame(dt: Float): TextureRegion {
        stateTime += dt
        return walkingAnimation.getKeyFrame(stateTime, true)
    }

    private fun getAnimation(): Animation<TextureRegion> {
        var frames = Array<TextureRegion>()
        (0..1).forEach {
            frames.add(TextureRegion(textRegion, 16 * it, 0, 16, 16))
        }
        val animation = Animation<TextureRegion>(0.4f, frames)
        frames.clear()
        return animation
    }
}
