package com.sarbezan.mariobros.sprites

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Array
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.screens.PlayScreen
import kotlin.experimental.or

class Goomba(screen: PlayScreen, x: Float, y: Float) : Enemy(screen, x, y) {
    private lateinit var body: Body

    private val textRegion = screen.atlas.findRegion("goomba")

    private val goombaHit = TextureRegion(textRegion, 32, 0, 16, 16)
    private var stateTime = 0f

    private var walkingAnimation = getAnimation()
    private var destroyed = false
    private var destroying = false
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
                MarioBros.OBJECT_BIT or
                MarioBros.MARIO_BIT or
                MarioBros.BRICK_BIT
        body.createFixture(fixtureDef).userData = this

        fixtureDef.shape = PolygonShape().apply {
            set(arrayOf(
                    Vector2(-5f / MarioBros.PPM, 8f / MarioBros.PPM),
                    Vector2(5f / MarioBros.PPM, 8f / MarioBros.PPM),
                    Vector2(-3f / MarioBros.PPM, 3f / MarioBros.PPM),
                    Vector2(3f / MarioBros.PPM, 3f / MarioBros.PPM))
            )
        }
        fixtureDef.filter.categoryBits = MarioBros.ENEMY_HEAD_BIT
        body.createFixture(fixtureDef).apply {
            userData = this@Goomba
            restitution = 0.5f
        }
        setBounds(16f, 0f, 16f / MarioBros.PPM, 16f / MarioBros.PPM)
    }

    fun update(dt: Float) {
        stateTime += dt
        if (destroying && !destroyed) {
            screen.world.destroyBody(body)
            destroyed = true
            stateTime = 0f
            return
        }
        body.linearVelocity = velocity
        setPosition(body.position.x - width / 2, body.position.y - height / 2)
        setRegion(getFrame(dt))
    }

    private fun getFrame(dt: Float): TextureRegion {
        if (destroyed) {
            return goombaHit
        }
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

    override fun draw(batch: Batch?) {
        if (!destroyed || stateTime < 1) {
            super.draw(batch)
        }
    }

    override fun onHitHead() {
        destroying = true
    }

    override fun reverseVelocity(x: Boolean, y: Boolean) {
        if (x) velocity.x = -velocity.x
        if (y) velocity.y = -velocity.y
    }
}
