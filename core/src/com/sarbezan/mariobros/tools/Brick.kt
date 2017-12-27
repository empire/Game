package com.sarbezan.mariobros.tools

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.World

class Brick(world: World, map: TiledMap, bounds: Rectangle) : InteractiveTileObject(world, map, bounds) {
}