package com.sarbezan.mariobros.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.utils.viewport.FitViewport
import com.sarbezan.mariobros.MarioBros

class Hud(private val batch: SpriteBatch) {
    private val cam = OrthographicCamera()
    private val viewPort = FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, cam)
    private val stage = Stage(viewPort, batch)

    private val worldTimer = 300
    private val timeCount = 0
    private val score = 0

    private val countDownLabel = Label("%03d".format(worldTimer), Label.LabelStyle(BitmapFont(), Color.WHITE))
    private val scoreLabel = Label("%06d".format(score), Label.LabelStyle(BitmapFont(), Color.WHITE))
    private val timeLabel = Label("TIME", Label.LabelStyle(BitmapFont(), Color.WHITE))
    private val levelLabel = Label("1-1", Label.LabelStyle(BitmapFont(), Color.WHITE))
    private val worldLabel = Label("WORLD", Label.LabelStyle(BitmapFont(), Color.WHITE))
    private val marioLabel = Label("MARIO", Label.LabelStyle(BitmapFont(), Color.WHITE))

    val combined = stage.camera.combined

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

    fun draw() {
        stage.draw()
    }
}