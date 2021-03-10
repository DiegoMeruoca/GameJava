package com.diegomeruoca.plaraforma.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.diegomeruoca.plaraforma.Plataforma;

public class Pipe extends InteractiveTileObject{
    public Pipe(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Plataforma.PIPE_BIT); //Define a categoria do filtro de colisão
    }

    @Override
    public void onHeadHit() {
    }


}
