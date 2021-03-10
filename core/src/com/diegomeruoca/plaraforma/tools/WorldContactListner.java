package com.diegomeruoca.plaraforma.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.diegomeruoca.plaraforma.sprites.InteractiveTileObject;

public class WorldContactListner implements ContactListener {
    //Classe responsavel por verificar se duas fixtures estão em contato
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        Fixture head = null;
        Fixture obj = null;

        if(fixA.getUserData() == "head"){
           head = fixA;
           obj = fixB;
        }else if(fixB.getUserData() == "head"){
            head = fixB;
            obj = fixA;
        }
        if(obj != null){
            if (obj.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(obj.getUserData().getClass())){
                ((InteractiveTileObject) obj.getUserData()).onHeadHit();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
