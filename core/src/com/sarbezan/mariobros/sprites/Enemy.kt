package com.sarbezan.mariobros.sprites

import com.badlogic.gdx.graphics.g2d.Sprite
import com.sarbezan.mariobros.screens.PlayScreen

abstract class Enemy(protected val screen: PlayScreen, x: Float, y: Float) : Sprite() {
    init {
        setPosition(x, y)
        defineEnemy()
    }
    protected abstract fun defineEnemy()
    abstract fun onHitHead()
}