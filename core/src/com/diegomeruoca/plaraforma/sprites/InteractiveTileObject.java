package com.diegomeruoca.plaraforma.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.diegomeruoca.plaraforma.Plataforma;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth()/2) / Plataforma.PPM, (bounds.getY() + bounds.getHeight()/2) / Plataforma.PPM);

        body = world.createBody(bodyDef);

        polygonShape.setAsBox((bounds.getWidth() / 2) / Plataforma.PPM, (bounds.getHeight() / 2) / Plataforma.PPM);

        fixtureDef.shape = polygonShape;
        fixture = body.createFixture(fixtureDef);
    }

    public abstract void onHeadHit();

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int) (body.getPosition().x * Plataforma.PPM / 16) , (int)(body.getPosition().y * Plataforma.PPM / 16));
    }

}
