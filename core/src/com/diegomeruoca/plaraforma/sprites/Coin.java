package com.diegomeruoca.plaraforma.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.diegomeruoca.plaraforma.Plataforma;

public class Coin extends InteractiveTileObject{
    public Coin(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Plataforma.COIN_BIT); //Define a categoria do filtro de colisão
    }

    @Override
    public void onHeadHit() {//Qnd o mario da uma cabeçada
        setCategoryFilter(Plataforma.DESTROYED_BIT); //Define a categoria do filtro de colisão para destruido
        Gdx.app.log("Coin", "Collision");
        getCell().setTile(null);//Definindo a celula que bateu a cabeça como null
    }
}
