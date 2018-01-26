package com.sarbezan.mariobros.tools

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.sprites.Mario
import com.sarbezan.mariobros.sprites.enemies.Enemy
import com.sarbezan.mariobros.sprites.items.Item
import com.sarbezan.mariobros.sprites.tileObjects.InteractiveTileObject
import kotlin.experimental.or

class MarioContactListener: ContactListener {
    override fun beginContact(contact: Contact) {
        var mario = contact.fixtureA.userData as? Mario ?:
                contact.fixtureB.userData as? Mario
        var obj = if (mario == contact.fixtureA.userData)
            contact.fixtureB.userData else
            contact.fixtureA.userData

        val cDef = contact.fixtureA.filterData.categoryBits or
                contact.fixtureB.filterData.categoryBits
        when (cDef) {
            MarioBros.ENEMY_HEAD_BIT or MarioBros.MARIO_BIT -> (obj as Enemy).onHitHead()
            MarioBros.ENEMY_BIT or MarioBros.OBJECT_BIT -> (obj as Enemy).reverseVelocity(true, false)
            MarioBros.ENEMY_BIT or MarioBros.MARIO_BIT -> Gdx.app.log("MARIO", "DIED")
            MarioBros.ENEMY_BIT or MarioBros.GROUND_BIT -> (obj as? Enemy)?.reverseVelocity(true, false)
            MarioBros.ENEMY_BIT -> {
                (contact.fixtureA.userData as? Enemy)?.reverseVelocity(true, false)
                (contact.fixtureB.userData as? Enemy)?.reverseVelocity(true, false)
            }
            MarioBros.ITEM_BIT or MarioBros.OBJECT_BIT -> (obj as Item).reverseVelocity(true, false)
            MarioBros.ITEM_BIT or MarioBros.MARIO_BIT -> mario?.let { (obj as Item).use(it) }
            (MarioBros.MARIO_HEAD_BIT or MarioBros.COIN_BIT) -> (obj as InteractiveTileObject).onHitHead()
            (MarioBros.MARIO_HEAD_BIT or MarioBros.BRICK_BIT) -> (obj as InteractiveTileObject).onHitHead()
        }
    }

    override fun endContact(contact: Contact) {
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold) {
    }

    override fun postSolve(contact: Contact, impulse: ContactImpulse) {
    }
}