package com.sarbezan.mariobros.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import com.sarbezan.mariobros.MarioBros

class Hud(batch: SpriteBatch) {
    private val viewPort = FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, OrthographicCamera())
    private val stage = Stage(viewPort, batch)

    private var worldTimer = 300
    private var timer = 0f

    private val countDownLabel = Label("%03d".format(worldTimer), Label.LabelStyle(BitmapFont(), Color.WHITE))

    private val timeLabel = Label("TIME", Label.LabelStyle(BitmapFont(), Color.WHITE))
    private val levelLabel = Label("1-1", Label.LabelStyle(BitmapFont(), Color.WHITE))
    private val worldLabel = Label("WORLD", Label.LabelStyle(BitmapFont(), Color.WHITE))
    private val marioLabel = Label("MARIO", Label.LabelStyle(BitmapFont(), Color.WHITE))

    companion object {
        private var score = 0
        private val scoreLabel = Label("%06d".format(score), Label.LabelStyle(BitmapFont(), Color.WHITE))

        fun addScore(value: Int) {
            score += value
            scoreLabel.setText("%06d".format(score))
        }
    }

    init {
        val table = Table().apply {
            top()
            setFillParent(true)
            add(marioLabel).expandX().padTop(10f)
            add(worldLabel).expandX().padTop(10f)
            add(timeLabel).expandX().padTop(10f)
            row()
            add(scoreLabel).expandX()
            add(levelLabel).expandX()
            add(countDownLabel).expandX()
        }
        stage.addActor(table)
    }

    fun update(dt: Float) {
        timer += dt
        if (timer > 1) {
            timer = 0f
            worldTimer --

            countDownLabel.setText("%03d".format(worldTimer))
        }
    }

    fun draw() {
        stage.draw()
    }
}