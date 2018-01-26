package com.sarbezan.mariobros.sprites

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Array
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.screens.PlayScreen
import com.sarbezan.mariobros.tools.MarioContactListener
import kotlin.experimental.or

class Mario(private val screen: PlayScreen) : Sprite() {
    val body: Body
    private var stateTimer: Float = 0f

    private var currentState: State = Standing
    private var previousState: State = Standing

    private var runningRight = true
    private var growing = false
    private val littleMarioFrames: MarioFrames =
            LittleMarioFrames(screen.atlas)
    private val bigMarioFrames: MarioFrames =
            BigMarioFrames(screen.atlas)

    private var marioFrames: MarioFrames = littleMarioFrames
    private var growingAnimation = getGrowingAnimation()

    private fun getGrowingAnimation(): Animation<TextureRegion> {
        var frames = Array<TextureRegion>()
        (0..2).forEach {
            frames.add(TextureRegion(screen.atlas.findRegion("big_mario"), 0, 0, 16, 32))
            frames.add(TextureRegion(screen.atlas.findRegion("big_mario"), 15 * 16, 0, 16, 32))
        }
        val animation = Animation(0.1f, frames)
        frames.clear()
        return animation
    }

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
                MarioBros.ENEMY_HEAD_BIT or
                MarioBros.OBJECT_BIT or
                MarioBros.ITEM_BIT or
                MarioBros.BRICK_BIT
        body.createFixture(fixtureDef).userData = this
        setBounds(32f, 0f, 16f / MarioBros.PPM, 16f / MarioBros.PPM)

        screen.world.setContactListener(MarioContactListener())

        fixtureDef.filter.categoryBits = MarioBros.MARIO_HEAD_BIT
        fixtureDef.shape = EdgeShape().apply {
            set(Vector2(-2f / MarioBros.PPM, 6f / MarioBros.PPM),
                    Vector2(2f / MarioBros.PPM, 6f / MarioBros.PPM))
        }
        fixtureDef.isSensor = true
        body.createFixture(fixtureDef).userData = this
    }

    fun update(dt: Float) {
        setPosition(body.position.x - width / 2, body.position.y - height / 2)
        setRegion(getFrame(dt))
    }

    private fun getFrame(dt: Float): TextureRegion {
        currentState = getState()
        stateTimer = if (currentState == previousState) stateTimer + dt else 0f
        val frame = when(currentState) {
            Running -> marioFrames.getRunningKeyFrame(stateTimer)
            Standing -> marioFrames.marioStand
            Jumping -> marioFrames.getJumpingKeyFrame(stateTimer)
            Falling -> marioFrames.marioStand
            GROWING -> {
                growing = !growingAnimation.isAnimationFinished(stateTimer)
                growingAnimation.getKeyFrame(stateTimer, false)
            }
        }

        if ((body.linearVelocity.x < 0 || !runningRight) && !frame.isFlipX) {
            frame.flip(true, false)
            runningRight = false
        } else if ((body.linearVelocity.x > 0 || runningRight) && frame.isFlipX) {
            frame.flip(true, false)
            runningRight = true
        }
        previousState = currentState
        return frame
    }

    private fun getState(): State {
        return when {
            growing -> GROWING
            body.linearVelocity.y > 0 ||
                    (body.linearVelocity.y < 0 && currentState == Jumping) -> Jumping
            body.linearVelocity.y < 0 -> Falling
            body.linearVelocity.x != 0f -> Running
            else -> Standing
        }
    }

    fun grow() {
        setBounds(16f, 0f, 16f / MarioBros.PPM, 32f / MarioBros.PPM)
        marioFrames = bigMarioFrames
        growing = true
        MarioBros.assetManager.get<Sound>("audio/sounds/powerup.wav").play()
    }
}

abstract class MarioFrames(protected val texture: TextureAtlas.AtlasRegion) {
    abstract val height: Int
    abstract val runningAnimation: Animation<TextureRegion>
    abstract val jumpingAnimation: Animation<TextureRegion>
    abstract val marioStand: TextureRegion

    protected fun getAnimation(frameStart: Int, frameCount: Int): Animation<TextureRegion> {
        var frames = Array<TextureRegion>()
        (frameStart..(frameStart + frameCount - 1)).forEach {
            frames.add(TextureRegion(texture, 16 * it, 0, 16, height))
        }
        val animation = Animation(0.1f, frames)
        frames.clear()
        return animation
    }

    fun getRunningKeyFrame(stateTimer: Float): TextureRegion =
            runningAnimation.getKeyFrame(stateTimer, true)

    fun getJumpingKeyFrame(stateTimer: Float): TextureRegion =
            jumpingAnimation.getKeyFrame(stateTimer)
}

class LittleMarioFrames(atlas: TextureAtlas): MarioFrames(atlas.findRegion("little_mario")) {
    override val height: Int = 16
    override val runningAnimation = getAnimation(0, 4)
    override val jumpingAnimation = getAnimation(4, 2)
    override val marioStand = TextureRegion(texture, 0, 0, 16, 16)
}

class BigMarioFrames(atlas: TextureAtlas): MarioFrames(atlas.findRegion("big_mario")) {
    override val height: Int = 32
    override val runningAnimation = getAnimation(0, 4)
    override val jumpingAnimation = getAnimation(4, 2)
    override val marioStand = TextureRegion(texture, 0, 0, 16, 32)
}

sealed class State
object Running : State()
object Standing : State()
object Jumping : State()
object Falling : State()
object GROWING : State()
