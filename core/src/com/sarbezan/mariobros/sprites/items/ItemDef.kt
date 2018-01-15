package com.sarbezan.mariobros.sprites.items

import com.badlogic.gdx.math.Vector2

data class ItemDef(val position: Vector2, val type: Class<out Item>)