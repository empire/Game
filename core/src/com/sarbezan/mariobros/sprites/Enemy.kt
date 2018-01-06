package com.sarbezan.mariobros.sprites

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.sarbezan.mariobros.screens.PlayScreen

abstract class Enemy(protected val screen: PlayScreen, x: Float, y: Float) : Sprite() {
    protected val velocity = Vector2(.5f, 0f)
    init {
        setPosition(x, y)
        defineEnemy()
    }
    protected abstract fun defineEnemy()
    abstract fun onHitHead()
    abstract fun reverseVelocity(x: Boolean, y: Boolean)
}