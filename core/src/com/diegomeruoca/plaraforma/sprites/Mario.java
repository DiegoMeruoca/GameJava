package com.diegomeruoca.plaraforma.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.diegomeruoca.plaraforma.Plataforma;
import com.diegomeruoca.plaraforma.screens.PlayScreen;

public class Mario extends Sprite {
    public World world;
    public Body b2dBody;
    public TextureRegion marioStand;

    public Mario(World world, PlayScreen playScreen){
        super(playScreen.getAtlas().findRegion("little_mario"));
        this.world = world;
        defineMario();
        marioStand = new TextureRegion(getTexture(), 0 , 0, 16, 16);
        setBounds(0, 0, 16 / Plataforma.PPM, 16 / Plataforma.PPM);
        setRegion(marioStand);

    }

    public void update(float dt){
        setPosition(b2dBody.getPosition().x - getWidth() / 2, b2dBody.getPosition().y - getHeight() / 2);
    }

    private void defineMario() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / Plataforma.PPM, 32 / Plataforma.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2dBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / Plataforma.PPM);

        fixtureDef.shape = shape;
        b2dBody.createFixture(fixtureDef);
    }
}
