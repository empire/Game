package com.sarbezan.mariobros.tools

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.sarbezan.mariobros.sprites.Mario

class MarioContactListener: ContactListener {
    override fun beginContact(contact: Contact) {
        var mario = contact.fixtureA.userData as? Mario ?:
                contact.fixtureB.userData as? Mario
        var obj = if (mario == contact.fixtureA.userData)
            contact.fixtureB.userData else
            contact.fixtureA.userData
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