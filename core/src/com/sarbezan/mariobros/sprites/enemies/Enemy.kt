package com.sarbezan.mariobros.sprites.enemies

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.sarbezan.mariobros.screens.PlayScreen

abstract class Enemy(protected val screen: PlayScreen, x: Float, y: Float) : Sprite() {
    protected val velocity = Vector2(.5f, 0f)
    abstract var body: Body

    init {
        setPosition(x, y)
        defineEnemy()
        body.isActive = false
    }

    protected abstract fun defineEnemy()
    abstract fun onHitHead()
    abstract fun reverseVelocity(x: Boolean, y: Boolean)
}
