package com.diegomeruoca.plaraforma.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.diegomeruoca.plaraforma.Plataforma;
import com.diegomeruoca.plaraforma.sprites.Brick;
import com.diegomeruoca.plaraforma.sprites.Coin;

public class B2WoldCreator {
    public B2WoldCreator(World world, TiledMap map){
        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        //Percorre os objetos do mapa, pegando os objetos da camada 2 (Ground -chão) para criar bodies/fixtures
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth()/2) / Plataforma.PPM, (rect.getY() + rect.getHeight()/2) / Plataforma.PPM);

            body = world.createBody(bodyDef);

            polygonShape.setAsBox((rect.getWidth() / 2) / Plataforma.PPM, (rect.getHeight() / 2) / Plataforma.PPM);

            fixtureDef.shape = polygonShape;
            body.createFixture(fixtureDef);
        }

        //Percorre os objetos do mapa, pegando os objetos da camada 3 (pipes - canos) para criar bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth()/2) / Plataforma.PPM, (rect.getY() + rect.getHeight()/2) / Plataforma.PPM);

            body = world.createBody(bodyDef);

            polygonShape.setAsBox((rect.getWidth() / 2) / Plataforma.PPM, (rect.getHeight() / 2) / Plataforma.PPM);

            fixtureDef.shape = polygonShape;
            body.createFixture(fixtureDef);
        }

        //Percorre os objetos do mapa, pegando os objetos da camada 5 (bricks - blocos) para criar bodies/fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Brick(world,map, rect);
        }

        //Percorre os objetos do mapa, pegando os objetos da camada 4 (Coins - moedas - caixas de ?) para criar bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

           new Coin(world, map, rect);
        }
    }
}
