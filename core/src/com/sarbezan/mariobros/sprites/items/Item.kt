package com.sarbezan.mariobros.sprites.items

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.physics.box2d.Body
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.screens.PlayScreen

abstract class Item(protected val screen: PlayScreen, x: Float, y: Float): Sprite() {
    private var destroyed = false

    private var toDestroy = false
    protected lateinit var body: Body

    init {
        setPosition(x, y + 16 / MarioBros.PPM)
        setBounds(getX(), getY(), 16f / MarioBros.PPM, 16f / MarioBros.PPM)
        defineItem()
    }

    abstract fun defineItem()
    abstract fun use()

    override fun draw(batch: Batch?) {
        if (!destroyed) {
            super.draw(batch)
        }
    }


    open fun update(dt: Float) {
        if (toDestroy and !destroyed) {
            screen.world.destroyBody(body)
            destroyed = true
        }
    }

    fun destroy() {
        toDestroy = true
    }
}