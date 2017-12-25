package com.sarbezan.mariobros.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.sarbezan.mariobros.MarioBros

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.width = MarioBros.V_WIDTH.toInt()
        config.height = MarioBros.V_HEIGHT.toInt()
        LwjglApplication(MarioBros(), config)
    }
}
