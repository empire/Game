package com.sarbezan.mariobros.sprites

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Array
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.screens.PlayScreen
class Mario(world: World, screen: PlayScreen) : Sprite(screen.atlas.findRegion("little_mario")) {
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
        body = world.createBody(bodyDef)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = CircleShape().apply {
            radius = 7f / MarioBros.PPM
        }
        body.createFixture(fixtureDef)
        setBounds(16f, 0f, 16f / MarioBros.PPM, 16f / MarioBros.PPM)
        setRegion(marioStand)

        runningAnimation = getAnimation(0, 4)
        jumpingAnimation = getAnimation(4, 2)
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
