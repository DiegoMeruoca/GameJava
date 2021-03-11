package com.diegomeruoca.plaraforma.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.diegomeruoca.plaraforma.Plataforma;
import com.diegomeruoca.plaraforma.screens.PlayScreen;

public class Mario extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2dBody;
    public TextureAtlas.AtlasRegion atlasRegion;
    private TextureRegion marioStand;
    private Animation marioRun;
    private Animation marioJump;
    private float stateTimer;
    private boolean runningRight;

    public Mario(World world, PlayScreen playScreen){
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        atlasRegion = playScreen.getAtlas().findRegion("Armature_Walk");
        Array<TextureRegion> frames = new Array<TextureRegion>();//Criar um array p receber as texturas

        //Corrida
        for(int i = 1; i<=3; i++){ //Percorrendo da 1 até a 3 (onde estão as imagens da corrida)
            frames.add(new TextureRegion(atlasRegion.getTexture(), i * 16, 0, 16 ,16 )); //Adiciona cada imagem no array
        }
        marioRun = new Animation(0.1f, frames); //Adiciona o array na animação
        frames.clear(); //Limpa o Array

        //Pulo
        for(int i = 4; i<=5; i++){ //Percorrendo da 4 até a 5 (onde estão as imagens do pulo)
            frames.add(new TextureRegion(atlasRegion.getTexture(), i * 16, 0, 16 ,16 )); //Adiciona cada imagem no array
        }
        marioJump = new Animation(0.1f, frames); //Adiciona o array na animação
        frames.clear(); //Limpa o Array

        //Parado
        marioStand = new TextureRegion(atlasRegion.getTexture(), 0 , 0, 16, 16);

        defineMario();
        setBounds(0, 0, 16 / Plataforma.PPM, 16 / Plataforma.PPM);
        setRegion(marioStand);

    }

    public void update(float dt){
        setPosition(b2dBody.getPosition().x - getWidth() / 2, b2dBody.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = (TextureRegion) marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }
        if((b2dBody.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }else if((b2dBody.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        // se currentState for igual previousState, stateTimer recebe ele mai o dt, senão ele recebe 0
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }


    private State getState() {
        //Velocidade vertical maior que 0 (Subindo) ou velovidade vertical meno que 0 (descenso) porem com estado anterior Pulando
        if(b2dBody.getLinearVelocity().y > 0 || (b2dBody.getLinearVelocity().y < 0 && previousState == State.JUMPING)){
            return State.JUMPING;
        }if(b2dBody.getLinearVelocity().y < 0){ //Velocidade vertical menor que 0 - caindo
            return State.FALLING;
        }
        else if(b2dBody.getLinearVelocity().x != 0){ //Velocidade horizontal diferente 0 - correndo
            return State.RUNNING;
        }
        else{
            return State.STANDING;
        }
    }

    private void defineMario() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(300 / Plataforma.PPM, 100 / Plataforma.PPM); //Posição que ira surgir
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2dBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / Plataforma.PPM);
        fixtureDef.filter.categoryBits = Plataforma.MARIO_BIT; //Definir qual tipo de filtro é esse
        //Com quem o mario pode colidir
        fixtureDef.filter.maskBits = Plataforma.DEFAULT_BIT | Plataforma.BRICK_BIT | Plataforma.COIN_BIT | Plataforma.PIPE_BIT;

        fixtureDef.shape = shape;
        b2dBody.createFixture(fixtureDef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / Plataforma.PPM, 7 / Plataforma.PPM), new Vector2(2 / Plataforma.PPM, 7 / Plataforma.PPM));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;

        b2dBody.createFixture(fixtureDef).setUserData("head");
    }
}
