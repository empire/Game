package com.sarbezan.mariobros.tools

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.sarbezan.mariobros.MarioBros
import com.sarbezan.mariobros.sprites.Enemy
import com.sarbezan.mariobros.sprites.Mario
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
        if (cDef == MarioBros.ENEMY_HEAD_BIT or MarioBros.MARIO_BIT) {
            (obj as Enemy).onHitHead()
        }
        if (mario != null && obj is InteractiveTileObject) {
            obj.onHitHead()
        }
    }

    override fun endContact(contact: Contact) {
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold) {
    }

    override fun postSolve(contact: Contact, impulse: ContactImpulse) {
    }
}