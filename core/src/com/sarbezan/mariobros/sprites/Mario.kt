package com.sarbezan.mariobros.sprites

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Array
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.screens.PlayScreen
import com.sarbezan.mariobros.tools.MarioContactListener
import kotlin.experimental.or

class Mario(screen: PlayScreen) : Sprite(screen.atlas.findRegion("little_mario")) {
    val body: Body
    private val marioStand = TextureRegion(texture, 0, 12, 16, 16)
    private val runningAnimation: Animation<TextureRegion>
    private val jumpingAnimation: Animation<TextureRegion>
    private var stateTimer: Float = 0f

    private var currentState: State = Standing
    private var previousState: State = Standing

    private var runningRight = true

    init {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(32f / MarioBros.PPM, 32f / MarioBros.PPM)
        body = screen.world.createBody(bodyDef)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = CircleShape().apply {
            radius = 7f / MarioBros.PPM
        }
        fixtureDef.filter.categoryBits = MarioBros.MARIO_BIT
        fixtureDef.filter.maskBits = MarioBros.GROUND_BIT or
                MarioBros.COIN_BIT or
                MarioBros.ENEMY_BIT or
                MarioBros.OBJECT_BIT or
                MarioBros.BRICK_BIT
        body.createFixture(fixtureDef)
        setBounds(16f, 0f, 16f / MarioBros.PPM, 16f / MarioBros.PPM)
        setRegion(marioStand)

        runningAnimation = getAnimation(0, 4)
        jumpingAnimation = getAnimation(4, 2)
        screen.world.setContactListener(MarioContactListener())

        fixtureDef.shape = EdgeShape().apply {
            set(Vector2(-2f / MarioBros.PPM, 6f / MarioBros.PPM),
                    Vector2(2f / MarioBros.PPM, 6f / MarioBros.PPM))
        }
        fixtureDef.isSensor = true
        body.createFixture(fixtureDef).userData = this
    }

    private fun getAnimation(frameStart: Int, frameCount: Int): Animation<TextureRegion> {
        var frames = Array<TextureRegion>()
        (frameStart..(frameStart + frameCount - 1)).forEach {
            frames.add(TextureRegion(texture, 16 * it, 12, 16, 16))
        }
        val animation = Animation(0.1f, frames)
        frames.clear()
        return animation
    }

    fun update(dt: Float) {
        setPosition(body.position.x - width / 2, body.position.y - height / 2)
        setRegion(getFrame(dt))
    }

    private fun getFrame(dt: Float): TextureRegion {
        currentState = getState()
        val frame = when(currentState) {
            Running -> runningAnimation.getKeyFrame(stateTimer, true)
            Standing -> marioStand
            Jumping -> jumpingAnimation.getKeyFrame(stateTimer)
            Falling -> marioStand
        }

        if ((body.linearVelocity.x < 0 || !runningRight) && !frame.isFlipX) {
            frame.flip(true, false)
            runningRight = false
        } else if ((body.linearVelocity.x > 0 || runningRight) && frame.isFlipX) {
            frame.flip(true, false)
            runningRight = true
        }
        stateTimer = if (currentState == previousState) stateTimer + dt else 0f
        previousState = currentState
        return frame
    }

    private fun getState(): State {
        return when {
            body.linearVelocity.y > 0 ||
                    (body.linearVelocity.y < 0 && currentState == Jumping) -> Jumping
            body.linearVelocity.y < 0 -> Falling
            body.linearVelocity.x != 0f -> Running
            else -> Standing
        }
    }
}

sealed class State
object Running : State()
object Standing : State()
object Jumping : State()
object Falling : State()
